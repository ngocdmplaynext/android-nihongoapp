package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.Utils;
import com.jp.playnext.voicecards.model.Card;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsActivity extends AppCompatActivity {

    private static final String ARGS_CARD = "args_card";
    private static final String ARGS_SENTENCE = "args_sentence";
    private static final String ARGS_PERCENTAGE = "args_percentage";


    public Card card;

    public String sResultSentence;
    public float fConfidence;

    @BindView(R.id.tv_expected_sentence_result) TextView tvExpectedSentence;
    @BindView(R.id.tv_received_sentence_result) TextView tvReceivedSentence;
    @BindView(R.id.tv_result_percentage) TextView tvPercentage;

    public static void newInstance(Context context, Card card, String resultSentence, float resultPercentage){
        Intent intent = new Intent();
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

        if(getIntent() != null){
            card = (Card) getIntent().getParcelableExtra(ARGS_CARD);
            sResultSentence = getIntent().getStringExtra(ARGS_SENTENCE);
            fConfidence = getIntent().getFloatExtra(ARGS_PERCENTAGE, 0.0f);
        }

        tvExpectedSentence.setText(card.difference(sResultSentence));
        tvReceivedSentence.setText("Received:" + sResultSentence);
        tvPercentage.setText(Utils.confidentToString(fConfidence));
        //card.setBestScore(this.getContext(), confidence[0] * 100);
        //tvBestPercentage.setText(card.getBestScoreString());
    }
}
