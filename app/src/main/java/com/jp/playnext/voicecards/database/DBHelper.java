package com.jp.playnext.voicecards.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danielmorais on 2/22/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;

    public static synchronized DBHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }



    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VoiceCards.db";

    public  DBCardHelper dbCardHelper;
    public  DBDeckHelper dbDeckHelper;
    public  DBThemeHelper dbThemeHelper;


    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbCardHelper = new DBCardHelper(context);
        dbDeckHelper = new DBDeckHelper(context);
        dbThemeHelper = new DBThemeHelper(context);
    }


    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(dbCardHelper.createTable());
        db.execSQL(dbDeckHelper.createTable());
        db.execSQL(dbThemeHelper.createTable());
    }




    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
       // db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(dbCardHelper.upgradeTable());
        db.execSQL(dbDeckHelper.upgradeTable());
        db.execSQL(dbThemeHelper.upgradeTable());
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}