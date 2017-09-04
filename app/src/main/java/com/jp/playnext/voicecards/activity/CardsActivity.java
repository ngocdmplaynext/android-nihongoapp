package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.CardListFragment;
import com.jp.playnext.voicecards.libs.AudioPlayList;
import com.jp.playnext.voicecards.model.Audio;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.CardInterface;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardsActivity extends VoiceListenerActivity
implements  CardListFragment.OnCardListFragmentInteraction, AudioPlayList.OnAudioPlayListInteraction {
    private static final String TAG = DeckActivity.class.getSimpleName();
    private static final String ARGS_DECK = "args_deck";
    Deck deck;
    ArrayList<Card> cards;
    ArrayList<String> audioUrls = new ArrayList<String>();
    Card selectedCard;
    MediaPlayer mediaPlayer;

    private static int counter = 0;

    @BindView(R.id.inc_results_popup)
    View resultsPopUp;
    @BindView(R.id.tv_percentage_popup)
    TextView tvPercentage;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.btn_play_all)
    Button btnPlayAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        if(getIntent() != null){
            deck = getIntent().getParcelableExtra(ARGS_DECK);
        }else {
            Toast.makeText(this, "THEME DOESN'T EXIST", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        initData();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_cards_fragment, CardListFragment.newInstance(cards), CardListFragment.class.getName())
                    .commit();
        }

        ButterKnife.bind(this);

        getCardsData();

        progressBar.setVisibility(View.INVISIBLE);
        resultsPopUp.setVisibility(View.INVISIBLE);
        final Context context = this;
        final Card card = selectedCard;

        //TODO: DELETE, TEST PURPOSE ONLY
        resultsPopUp.findViewById(R.id.btn_result_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsActivity.newInstance(context, card," test", 5.2f);
            }
        });

        resultsPopUp.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsPopUp.setVisibility(View.INVISIBLE);
            }
        });

        btnPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cards.size() == 0) return;
                audioUrls.clear();
                for(Card card : cards) {
                    String filePath = getExternalFilesDir(null) + File.separator + "card" + card.getId().toString() + ".acc";
                    audioUrls.add(filePath);
                }
                AudioPlayList instance = AudioPlayList.getInstance(context, audioUrls);
                boolean isPlaying = instance.isPlaying();
                if (isPlaying) {
                    instance.stop();
                    btnPlayAll.setText("Play");
                } else {
                    instance.play();
                    btnPlayAll.setText("Stop");
                }
            }
        });
    }

    private void initData() {
        cards = new ArrayList<Card>();
    }

    public static void newInstance(Context context, Deck deck){
        Intent intent = new Intent(context, CardsActivity.class);
        intent.putExtra(ARGS_DECK, deck);
        context.startActivity(intent);
    }

    private void getCardsData() {
        final CardInterface cardInterface = InterfaceFactory.createRetrofitService(CardInterface.class);
        final UserDefaultImpl userDefault = new UserDefaultImpl(this);
        Call<ArrayList<Card>> callCard = cardInterface.getCards(userDefault.getToken() ,deck.getId());

        callCard.enqueue(new Callback<ArrayList<Card>>() {
            @Override
            public void onResponse(Call<ArrayList<Card>> call, Response<ArrayList<Card>> response) {
                cards = response.body();
                updateData(cards);

                if (isFilesExist(cards)) return;

                progressBar.setVisibility(View.VISIBLE);
                for(Card card : cards) {
                    final Card fCard = card;
                    Call<Audio> callAudioUrl = cardInterface.getCardAudioUrl(userDefault.getToken(), card.getId());
                    callAudioUrl.enqueue(new Callback<Audio>() {
                        @Override
                        public void onResponse(Call<Audio> call, Response<Audio> response) {
                            Audio audio = response.body();
                            Log.v(TAG, "audio url: " + audio.getAudioUrl());

                            Call<ResponseBody> callDownload = cardInterface.downloadFileWithDynamicUrlSync(audio.getAudioUrl());
                            callDownload.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        boolean writtenToDisk = writeResponseBodyToDisk(response.body(), fCard);
                                        Log.v(TAG, "download successful: " + writtenToDisk);
                                        asyncTaskCompleted();
                                    } else {
                                        Log.v(TAG, "download fail");
                                        asyncTaskCompleted();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.v(TAG, "download fail 2");
                                    asyncTaskCompleted();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Audio> call, Throwable t) {
                            Log.v(TAG, "get audio error");
                            asyncTaskCompleted();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Card>> call, Throwable t) {

            }
        });
    }

    public synchronized void asyncTaskCompleted() {
        counter++;
        if(counter == cards.size()) {
            progressBar.setVisibility(View.INVISIBLE);
            counter = 0;
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, Card card) {
        try {
            String filePath = getExternalFilesDir(null) + File.separator + "card" + card.getId().toString() + ".acc";
            Log.v(TAG, filePath);
            File futureStudioIconFile = new File(filePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void updateData(ArrayList<Card> cards) {
        CardListFragment cardListFragment = (CardListFragment) getSupportFragmentManager().findFragmentByTag(CardListFragment.class.getName());
        cardListFragment.updateListView(cards);
    }

    private boolean isFilesExist(ArrayList<Card> cards) {
        for(Card card : cards) {
            String filePath = getExternalFilesDir(null) + File.separator + "card" + card.getId().toString() + ".acc";
            File file = new File(filePath);
            if (!file.exists()) return false;
        }

        return true;
    }

    public void audioPlayer(String path){
        //set up MediaPlayer

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processResults(Intent intent) {
        ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        // The confidence array
        float[] confidence = intent.getFloatArrayExtra(
                RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

        resultsPopUp.setVisibility(View.VISIBLE);
        tvPercentage.setText(Utils.confidentToString(confidence[0]));

        final String receivedSentence = result.get(0);
        final float confidenceFloat = confidence[0];
        final Context context = this;
        final Card card = selectedCard;
        resultsPopUp.findViewById(R.id.btn_result_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsActivity.newInstance(context, card, receivedSentence, confidenceFloat);
            }
        });
    }

    @Override
    public void onClickWord(String wordClicked) {
        Log.v(TAG, "on word clicked");
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onBtnTouch(Card card) {
        Log.v(TAG, card.getName());
        selectedCard = card;
        onMic(card.getDisplaySentence());
    }

    @Override
    public void onBtnCancel(Card card) {
        Log.v(TAG, "btn cancel" + card.getName() );
    }

    @Override
    public void onCardClicked(Card card) {
        Log.v(TAG, "card clicked:" + card.getName() );
        String audioPath = getExternalFilesDir(null) + File.separator + "card" + card.getId().toString() + ".acc";
        audioPlayer(audioPath);
    }

    @Override
    public void audioPlayListCompletion() {
        btnPlayAll.setText("Play");
    }

    // Override super class function

}
