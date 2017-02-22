package com.jp.playnext.voicecards.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by danielmorais on 2/22/17.
 */


public class DBDeckHelper {

    private Context context;

    public static final String DECK_TABLE_NAME = "decks";
    public static final String DECK_COLUMN_ID = "id";
    public static final String DECK_COLUMN_NAME = "name";
    public static final String DECK_COLUMN_THEME_NAME = "theme_name";


    private HashMap hp;

    public DBDeckHelper(Context context) {
        this.context = context;
    }

    public String createTable() {

        return "create table " + DECK_TABLE_NAME
                + "("
                + DECK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DECK_COLUMN_NAME + " text,"
                + DECK_COLUMN_THEME_NAME + " text"
                + ")";
        /*
         "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
         */
    }


    public String upgradeTable() {
        return "DROP TABLE IF EXISTS " + DECK_TABLE_NAME;
        /*
          // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
         */
    }

    public boolean insertDeck(Deck deck) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DECK_COLUMN_NAME, deck.getName());
        contentValues.put(DECK_COLUMN_THEME_NAME, deck.getsThemeName());

        db.insert(DECK_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DECK_TABLE_NAME + " where id=" + id + "", null);
        return cursor;
    }


    public int numberOfRows() {
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DECK_TABLE_NAME);
        return numRows;
    }

    public boolean updateDeck(Deck deck) {
        if (deck.getId() < 0)
            return false;

        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DECK_COLUMN_ID, deck.getId());
        contentValues.put(DECK_COLUMN_NAME, deck.getName());
        contentValues.put(DECK_COLUMN_THEME_NAME, deck.getsThemeName());
        db.update(DECK_TABLE_NAME, contentValues, "id = ? ", new String[]{String.valueOf(deck.getId())});
        return true;
    }


    public Integer deleteDeck(Integer id) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        return db.delete(DECK_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<Deck> getThemeDecks(String themeName) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        //final String whereClause = CARD_COLUMN_ID + " in (" + inClause + ")";
        //Cursor cursor  = db.query(true,CARD_TABLE_NAME, null, whereClause, null, null, null, null,null);
        Cursor cursor = db.rawQuery("select * from " + DECK_TABLE_NAME
                + " where " + DECK_COLUMN_THEME_NAME + "=? ", new String[]{themeName});

        return parseCursor(cursor);
    }

    public ArrayList<Deck> getAllDeck() {
        ArrayList<Deck> array_list = new ArrayList<Deck>();

        //hp = new HashMap();
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DECK_TABLE_NAME, null);
        return parseCursor(cursor);
    }


    private ArrayList<Deck> parseCursor(Cursor cursor) {
        ArrayList<Deck> array_list = new ArrayList<Deck>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            int id = cursor.getInt(cursor.getColumnIndex(DECK_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DECK_COLUMN_NAME));
            String themeName = cursor.getString(cursor.getColumnIndex(DECK_COLUMN_THEME_NAME));
            Deck deck = new Deck(name, themeName);
            deck.setId(id);
            array_list.add(deck);
            cursor.moveToNext();
        }

        return array_list;

    }
}