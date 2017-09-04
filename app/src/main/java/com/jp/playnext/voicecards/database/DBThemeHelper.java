package com.jp.playnext.voicecards.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.jp.playnext.voicecards.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by danielmorais on 2/22/17.
 */


public class DBThemeHelper {

    private Context context;

    public static final String THEME_TABLE_NAME = "themes";
    public static final String THEME_COLUMN_ID = "id";
    public static final String THEME_COLUMN_NAME = "name";


    private HashMap hp;

    public DBThemeHelper(Context context) {
        this.context = context;
    }

    public String createTable() {

        return "create table " + THEME_TABLE_NAME
                + "("
                + THEME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + THEME_COLUMN_NAME + " text"
                + ")";
        /*
         "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
         */
    }


    public String upgradeTable() {
        return "DROP TABLE IF EXISTS " + THEME_TABLE_NAME;
        /*
          // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
         */
    }

    public boolean insertTheme(Theme theme) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(THEME_COLUMN_NAME, theme.getName());

        db.insert(THEME_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + THEME_TABLE_NAME + " where id=" + id + "", null);
        return cursor;
    }


    public int numberOfRows() {
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, THEME_TABLE_NAME);
        return numRows;
    }

    public boolean updateTheme(Theme theme) {
        if (theme.getId() < 0)
            return false;

        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(THEME_COLUMN_ID, theme.getId());
        contentValues.put(THEME_COLUMN_NAME, theme.getName());
        db.update(THEME_TABLE_NAME, contentValues, "id = ? ", new String[]{String.valueOf(theme.getId())});
        return true;
    }


    public Integer deleteTheme(Integer id) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        return db.delete(THEME_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


//    public ArrayList<Theme> getAllTheme() {
//        ArrayList<Theme> array_list = new ArrayList<Theme>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + THEME_TABLE_NAME, null);
//        return parseCursor(cursor);
//    }


//    private ArrayList<Theme> parseCursor(Cursor cursor) {
//        ArrayList<Theme> array_list = new ArrayList<Theme>();
//
//        cursor.moveToFirst();
//
//        while (cursor.isAfterLast() == false) {
//            int id = cursor.getInt(cursor.getColumnIndex(THEME_COLUMN_ID));
//            String name = cursor.getString(cursor.getColumnIndex(THEME_COLUMN_NAME));
//            Theme theme = new Theme();
//            theme.setId(id);
//            array_list.add(theme);
//            cursor.moveToNext();
//        }
//
//        return array_list;
//
//    }
}