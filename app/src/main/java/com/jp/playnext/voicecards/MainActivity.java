package com.jp.playnext.voicecards;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.model.Card;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = MainActivity.class.getSimpleName();

    private static final int VR_Request = 100;

    TextView tvCard;
    TextView tvSaidSentence;
    TextView tvPercentage;

    ArrayList<Card> alWordsBank;


    //DEBUG
    TextView tvTextResult;
    TextView tvDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCard = (TextView) findViewById(R.id.tv_card);
        tvSaidSentence = (TextView) findViewById(R.id.tv_said_sentence);
        tvPercentage = (TextView) findViewById(R.id.tv_percentage);


        String[] wordBankArray = getResources().getStringArray(R.array.Words);
        alWordsBank = new ArrayList();
        for (String s : wordBankArray)
            alWordsBank.add(new Card(s));


        if (alWordsBank.size() != 0) {
            tvCard.setText(alWordsBank.get(0).getSentence());
        }


        findViewById(R.id.btn_record_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMic();
            }
        });

        //Debug
        tvTextResult = (TextView) findViewById(R.id.tv_result_text);
        tvDisplayText = (TextView) findViewById(R.id.tv_result_analysis);

    }

    public void onMic() {
        promptSpeechInput();
    }

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        String myLanguage = Locale.US.toString();
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, myLanguage);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, myLanguage);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, myLanguage);
        intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word!");

        try {
            startActivityForResult(intent, VR_Request);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(MainActivity.this, "Your device doesn't support speech recognition,", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == VR_Request) {
            switch (resultCode) {
                case RESULT_OK:
                    Log.i(TAG, "RESULT_OK");
                    processResults(intent);
                    break;
                case RESULT_CANCELED:
                    Log.i(TAG, "RESULT_CANCELED");
                    break;
                case RecognizerIntent.RESULT_AUDIO_ERROR:
                    Log.i(TAG, "RESULT_AUDIO_ERROR");
                    break;
                case RecognizerIntent.RESULT_CLIENT_ERROR:
                    Log.i(TAG, "RESULT_CLIENT_ERROR");
                    break;
                case RecognizerIntent.RESULT_NETWORK_ERROR:
                    Log.i(TAG, "RESULT_NETWORK_ERROR");
                    break;
                case RecognizerIntent.RESULT_NO_MATCH:
                    Log.i(TAG, "RESULT_NO_MATCH");
                    break;
                case RecognizerIntent.RESULT_SERVER_ERROR:
                    Log.i(TAG, "RESULT_SERVER_ERROR");
                    break;
                default:
                    Log.i(TAG, "RESULT_UNKNOWN");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void processResults(Intent intent) {

        ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        // The confidence array
        float[] confidence = intent.getFloatArrayExtra(
                RecognizerIntent.EXTRA_CONFIDENCE_SCORES);


        tvSaidSentence.setText(alWordsBank.get(0).difference(result.get(0)));
        tvPercentage.setText(getString(R.string.percentage) + String.format("%.2f", confidence[0] * 100) + "%");


        //==========================DEBUG===========================================================

        String displayText = "";

        for (int i = 0; i < result.size(); i++) {
            Log.d("Text", result.get(i));
            displayText += "Result " + i + ":" + result.get(i);
            if (confidence.length > i)
                displayText += " confidence:" + String.valueOf(confidence[i] * 100) + "\n";
        }

        for (float textConfidence : confidence) {
            Log.d("Confidence", String.valueOf(textConfidence * 100));
            displayText += textConfidence + " !;! ";

        }

        tvDisplayText.setText(displayText);

        String[] arr = result.get(0).split(" ");

        tvTextResult.setText("");

        for (String s : arr) {
            SpannableString str = new SpannableString(s);
            if (alWordsBank.contains(s))
                str.setSpan(new BackgroundColorSpan(Color.RED), 0, str.length(), 0);

            tvTextResult.setText(TextUtils.concat(tvTextResult.getText(), str) + " ");
        }

        //==========================================================================================


    }
}
