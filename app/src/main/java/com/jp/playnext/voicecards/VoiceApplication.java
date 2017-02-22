package com.jp.playnext.voicecards;

/**
 * Created by danielmorais on 2/22/17.
 */

import android.app.Application;

import com.jp.playnext.voicecards.database.DBHelper;
import com.jp.playnext.voicecards.database.DummyInsertData;

public class VoiceApplication extends Application {

    @Override
    public void onCreate() {
        //INIT DB with application context
        DBHelper.getInstance(getApplicationContext());


        //DummyInsertData.insertDummyData(this);

        DummyInsertData.getAllCards(this);
        DummyInsertData.getDeckCards(this);
    }


}