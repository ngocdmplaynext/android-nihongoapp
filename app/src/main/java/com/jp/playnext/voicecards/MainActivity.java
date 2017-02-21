package com.jp.playnext.voicecards;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jp.playnext.voicecards.fragment.CardFragment;
import com.jp.playnext.voicecards.model.Card;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = MainActivity.class.getSimpleName();

    private static final int VR_Request = 100;

    @BindView(R.id.vp_cards)ViewPager vpCards;
    CardSlidePagerAdapter mPagerAdapter;


    String myLanguage = Locale.JAPAN.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mPagerAdapter = new CardSlidePagerAdapter(getSupportFragmentManager(),
                getResources().getStringArray(R.array.Words));
        vpCards.setAdapter(mPagerAdapter);
        vpCards.setOffscreenPageLimit(mPagerAdapter.getCount());

        findViewById(R.id.btn_record_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMic();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_japanese:
                if (checked)
                    myLanguage = Locale.JAPAN.toString();
                break;
            case R.id.rb_english:
                if (checked)
                    myLanguage = Locale.US.toString();
                break;
        }
    }

    public void onMic() {
        promptSpeechInput();
    }

    public void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, myLanguage);
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else
            toastString += " URI is null";

        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
        mPagerAdapter.getCurrentFragment().displayResult(result, confidence, "");


    }

    /**
     * A simple pager adapter for cardsFragments objects, in
     * sequence.
     */
    private class CardSlidePagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Card> alWordsBank = new ArrayList<>();

        private CardFragment mCurrentFragment;

        public CardSlidePagerAdapter(FragmentManager fm, String[] wordBankArray) {
            super(fm);
            for (String s : wordBankArray)
                alWordsBank.add(new Card(s));
        }

        @Override
        public Fragment getItem(int position) {
            return new CardFragment().newInstance(alWordsBank.get(position));
        }

        @Override
        public int getCount() {
            return alWordsBank.size();
        }


        public CardFragment getCurrentFragment() {
            return mCurrentFragment;
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
