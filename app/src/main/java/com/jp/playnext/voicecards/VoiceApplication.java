package com.jp.playnext.voicecards;

/**
 * Created by danielmorais on 2/22/17.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import com.jp.playnext.voicecards.database.DBHelper;
import com.jp.playnext.voicecards.database.DummyInsertData;
import com.jp.playnext.voicecards.utils.TouchableSpan;

public class VoiceApplication extends Application {

    private static final String TAG = VoiceApplication.class.getSimpleName();
    public static SharedPreferences prefs;

    private final static String DUMMY_DATA_INSERT = "dummy_data_insert";

    @Override
    public void onCreate() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //INIT DB with application context
        DBHelper.getInstance(getApplicationContext());


//        if (!prefs.getBoolean(DUMMY_DATA_INSERT, false)) {
//            DummyInsertData.insertDummyData(this);
//            prefs.edit().putBoolean(DUMMY_DATA_INSERT, true).commit();
//            Log.v(TAG, "=============Dummy Data added============");
//        }
//
//
//        DummyInsertData.getAllCards(this);
//        DummyInsertData.getDeckCards(this);
//        DummyInsertData.getAllDecks(this);
//        DummyInsertData.getThemeDecks(this);
    }


}