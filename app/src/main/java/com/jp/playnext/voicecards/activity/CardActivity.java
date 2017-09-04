package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.utils.Utils;
import com.jp.playnext.voicecards.fragment.CardFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by danielmorais.
 * Displays card, extends VoiceListenerActivity for Voice recognition
 */
public class CardActivity extends VoiceListenerActivity
        implements CardFragment.OnDeckFragmentInteraction {

    public final static String TAG = CardActivity.class.getSimpleName();

    private static final String DECK_KEY = "deck_key";
    private static final String CARD_KEY = "card_key";

    @BindView(R.id.vp_cards) ViewPager vpCards;
    @BindView(R.id.inc_results_popup) View resultsPopUp;
    @BindView(R.id.tv_percentage_popup) TextView tvPercentage;

    CardSlidePagerAdapter mPagerAdapter;

    TextToSpeech textToSpeech;

    public static void newInstance(Context context, Deck deck, Card selectedCard) {
        Intent intent = new Intent(context, CardActivity.class);
        intent.putExtra(DECK_KEY, deck);
        intent.putExtra(CARD_KEY, selectedCard);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            Deck deck = getIntent().getParcelableExtra(DECK_KEY);
            Card card = getIntent().getParcelableExtra(CARD_KEY);

            mPagerAdapter = new CardSlidePagerAdapter(getSupportFragmentManager(), deck);
            vpCards.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    resultsPopUp.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            vpCards.setAdapter(mPagerAdapter);
            vpCards.setOffscreenPageLimit(mPagerAdapter.getCount());
            vpCards.setCurrentItem(mPagerAdapter.getItemPosition(card));
            resultsPopUp.setVisibility(View.INVISIBLE);
            final Context context = this;

            //TODO: DELETE, TEST PURPOSE ONLY
            resultsPopUp.findViewById(R.id.btn_result_screen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResultsActivity.newInstance(context, mPagerAdapter.getCurrentCard()," test", 5.2f);
                }
            });


            resultsPopUp.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultsPopUp.setVisibility(View.INVISIBLE);
                }
            });


            findViewById(R.id.btn_record_voice).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMic(mPagerAdapter.getCurrentCard().getDisplaySentence());
                }
            });

            InitTextToSpeech();

        } else {
            Toast.makeText(this, "Deck is Empty", Toast.LENGTH_SHORT);
            this.finish();
        }


    }


    public void InitTextToSpeech() {
        if (textToSpeech == null)
            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(myLanguage);
                    }
                }
            });
    }

    /**
     * Change Language
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_japanese:
                if (checked)
                    myLanguage = Locale.JAPAN;
                break;
            case R.id.rb_english:
                if (checked)
                    myLanguage = Locale.US;
                break;
        }

        if(textToSpeech != null)
            textToSpeech.setLanguage(myLanguage);

    }


    /**
     * Analyze results of voice recognition
     * @param intent
     */
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
        resultsPopUp.findViewById(R.id.btn_result_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsActivity.newInstance(context, mPagerAdapter.getCurrentCard(),receivedSentence, confidenceFloat);
            }
        });

        mPagerAdapter.getCurrentFragment().displayResult(result, confidence, "");

    }

    @Override
    public void onClickWord(String wordClicked) {
        Log.v(TAG, "on word clicked");
    }

    @Override
    public void onPlaySoundClicked(Card card) {
        String toSpeak = card.getDisplaySentence();
        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        super.onPause();
    }


    /**
     * A simple pager adapter for cardsFragments objects, in
     * sequence.
     */
    private class CardSlidePagerAdapter extends FragmentStatePagerAdapter {

        Deck deck;
        private CardFragment mCurrentFragment;

        public CardSlidePagerAdapter(FragmentManager fm, Deck deck) {
            super(fm);
            this.deck = deck;
        }

        @Override
        public Fragment getItem(int position) {
            return new CardFragment();
        }

        @Override
        public int getCount() {
            return 1;
        }

        public int getItemPosition(Card card) {
            int index = 1;
            if (index >= 0)
                return index;

            Log.e(TAG, "CARD DOESN'T EXIST");
            return 0;

        }


        public CardFragment getCurrentFragment() {
            return mCurrentFragment;
        }

        public Card getCurrentCard() {
            return mCurrentFragment.getCard();
        }


        //...
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((CardFragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }
    }
}