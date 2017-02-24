package com.jp.playnext.voicecards.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.utils.TouchableSpan;
import com.jp.playnext.voicecards.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by danielmorais on 2/24/17.
 */

public abstract class VoiceListenerActivity extends AppCompatActivity {

    public final static String TAG = VoiceListenerActivity.class.getSimpleName();

    protected static final int VR_Request = 100;
    protected Locale myLanguage = Locale.JAPAN;

    public void onMic() {
        promptSpeechInput();
    }

    public void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, myLanguage.toString());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, myLanguage);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, myLanguage);
        intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word!");

        // for getting audio file returned
        //NOT WORKING
        //intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
        intent.putExtra("android.speech.extra.GET_AUDIO", true);


        try {
            startActivityForResult(intent, VR_Request);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Your device doesn't support speech recognition,", Toast.LENGTH_LONG).show();
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


    public abstract void processResults(Intent intent);

    public File getAudio(Intent intent) {


        //Code to get audio
        Uri audioUri = intent.getData();
        ContentResolver contentResolver = getContentResolver();
        String toastString = " Failed to save";
        if (audioUri != null) {
            try {
                InputStream filestream = contentResolver.openInputStream(audioUri);

                File sdcard = Environment.getExternalStorageDirectory();
                File storagePath = new File(sdcard.getAbsolutePath() + "/Music");
                File audioFile = new File(storagePath + "/" + "test" + ".wav");
                if (Utils.saveAudio(filestream, audioFile))
                    toastString = "Saved file to location:" + audioFile.getAbsolutePath();

                return audioFile;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else
            toastString += " URI is null";

        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();

        return null;


    }


    /**
     * @param sentence
     * @param resultText
     * @return difference between both texts
     */
    public SpannableString difference(String text1, String text2) {
        return differenceTest(text1, text1, text2);
    }


    /**
     * @param card
     * @param resultText
     * @return
     */
    public SpannableString difference(Card card, String resultText) {
        return differenceTest(card.getSentence(), card.getDisplaySentence(), resultText);
    }

    private SpannableString differenceTest(String sentence, String displaySentence, String resultText) {
        String[] sentenceArray = sentence.split("\\s+");

        SpannableString spannable = new SpannableString(displaySentence);
        String backgroundColor = "#FF0000";
        String textColor = "#FFFFFF";

        int start = 0;
        int end = 0;
        for (final String s : sentenceArray) {
            end = end + s.length();

            if (!resultText.toLowerCase().contains(s.toLowerCase())) {

                spannable.setSpan(new TouchableSpan(Color.parseColor(textColor), Color.parseColor(backgroundColor)) {
                                      @Override
                                      public void onClick(View widget) {
                                          onClickWord(s);
                                      }
                                  },
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            start = end;
        }

        return spannable;
    }

    public abstract void onClickWord(String wordClicked);

}
