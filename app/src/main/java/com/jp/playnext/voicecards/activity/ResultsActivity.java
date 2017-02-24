package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.utils.Utils;
import com.jp.playnext.voicecards.model.Card;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsActivity extends VoiceListenerActivity {

    public final static String TAG = ResultsActivity.class.getSimpleName();

    private static final String ARGS_CARD = "args_card";
    private static final String ARGS_SENTENCE = "args_sentence";
    private static final String ARGS_PERCENTAGE = "args_percentage";


    public Card card;

    protected String sResultSentence;
    protected float fConfidence;

    protected String prompt = "";

    @BindView(R.id.tv_card_sentence) TextView tvCardSentence;
    @BindView(R.id.tv_received_sentence_result) TextView tvReceivedSentence;
    @BindView(R.id.tv_percentage_result) TextView tvPercentage;

    public static void newInstance(Context context, Card card, String resultSentence, float resultPercentage) {
        Intent intent = new Intent(context, ResultsActivity.class);
        intent.putExtra(ARGS_CARD, card);
        intent.putExtra(ARGS_SENTENCE, resultSentence);
        intent.putExtra(ARGS_PERCENTAGE, resultPercentage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            card = (Card) getIntent().getParcelableExtra(ARGS_CARD);
            sResultSentence = getIntent().getStringExtra(ARGS_SENTENCE);
            fConfidence = getIntent().getFloatExtra(ARGS_PERCENTAGE, 0.0f);
        }

        tvCardSentence.setText(difference(card, sResultSentence));
        tvCardSentence.setMovementMethod(LinkMovementMethod.getInstance());
        tvReceivedSentence.setText("Received:" + sResultSentence);
        tvPercentage.setText(Utils.confidentToString(fConfidence));
        //card.setBestScore(this.getContext(), confidence[0] * 100);
        //tvBestPercentage.setText(card.getBestScoreString());
    }

    @Override
    public void processResults(Intent intent) {
        String word = prompt;
        //Show popup with word results
        float[] confidence = intent.getFloatArrayExtra(
                RecognizerIntent.EXTRA_CONFIDENCE_SCORES);
        Toast.makeText(this, word + ":" + Utils.confidentToString(confidence[0]), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClickWord(String wordClicked) {
        Log.v(TAG, "Word:" + wordClicked);
        prompt = wordClicked;
        onMic(wordClicked);
        //Listen for word

    }
}
