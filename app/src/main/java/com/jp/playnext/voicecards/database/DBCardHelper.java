package com.jp.playnext.voicecards.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.jp.playnext.voicecards.model.Card;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by danielmorais on 2/22/17.
 */


public class DBCardHelper {

    private Context context;

    public static final String CARD_TABLE_NAME = "cards";
    public static final String CARD_COLUMN_ID = "id";
    public static final String CARD_COLUMN_SENTENCE = "sentence";
    public static final String CARD_COLUMN_TITLE = "title";
    public static final String CARD_COLUMN_PARENT_DECK = "parent_deck";
    public static final String CARD_COLUMN_BEST_SCORE = "best_score";


    private HashMap hp;

    public DBCardHelper(Context context) {
        this.context = context;
    }

    public String createTable() {

        return "create table " + CARD_TABLE_NAME
                + "("
                + CARD_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CARD_COLUMN_SENTENCE + " text,"
                + CARD_COLUMN_TITLE + " text,"
                + CARD_COLUMN_PARENT_DECK + " text,"
                + CARD_COLUMN_BEST_SCORE + " float"
                + ")";
        /*
         "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
         */
    }


    public String upgradeTable() {
        return "DROP TABLE IF EXISTS " + CARD_TABLE_NAME;
        /*
          // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
         */
    }

    public boolean insertCard(Card card) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(CARD_COLUMN_ID, card.getId());
        contentValues.put(CARD_COLUMN_TITLE, card.getTitle());
        contentValues.put(CARD_COLUMN_SENTENCE, card.getSentence());
        contentValues.put(CARD_COLUMN_PARENT_DECK, card.getParentDeck());
        contentValues.put(CARD_COLUMN_BEST_SCORE, card.getBestScore());
        db.insert(CARD_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CARD_TABLE_NAME + " where id=" + id + "", null);
        return cursor;
    }


    public int numberOfRows() {
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CARD_TABLE_NAME);
        return numRows;
    }

    public boolean updateCard(Card card) {
        if (card.getId() < 0)
            return false;

        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARD_COLUMN_ID, card.getId());
        contentValues.put(CARD_COLUMN_TITLE, card.getTitle());
        contentValues.put(CARD_COLUMN_SENTENCE, card.getSentence());
        contentValues.put(CARD_COLUMN_PARENT_DECK, card.getParentDeck());
        contentValues.put(CARD_COLUMN_BEST_SCORE, card.getBestScore());
        db.update(CARD_TABLE_NAME, contentValues, "id = ? ", new String[]{String.valueOf(card.getId())});
        return true;
    }


    public Integer deleteCard(Integer id) {
        SQLiteDatabase db = DBHelper.getInstance(context).getWritableDatabase();
        return db.delete(CARD_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<Card> getAllCard() {
        ArrayList<Card> array_list = new ArrayList<Card>();

        //hp = new HashMap();
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CARD_TABLE_NAME, null);
        return parseCursor(cursor);
    }

    public ArrayList<Card> getDeckCards(String deckName) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        //final String whereClause = CARD_COLUMN_ID + " in (" + inClause + ")";
        //Cursor cursor  = db.query(true,CARD_TABLE_NAME, null, whereClause, null, null, null, null,null);
        Cursor cursor = db.rawQuery("select * from " + CARD_TABLE_NAME
                + " where " + CARD_COLUMN_PARENT_DECK + "=? ", new String[]{deckName});

        return parseCursor(cursor);
    }

    private ArrayList<Card> parseCursor(Cursor cursor) {
        ArrayList<Card> array_list = new ArrayList<Card>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            int id = cursor.getInt(cursor.getColumnIndex(CARD_COLUMN_ID));
            String sentence = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_SENTENCE));
            String title = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_TITLE));
            String parentDeck = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_PARENT_DECK));
            float bestScore = cursor.getFloat(cursor.getColumnIndex(CARD_COLUMN_BEST_SCORE));
            array_list.add(new Card(sentence, title, parentDeck, id, bestScore));
            cursor.moveToNext();
        }

        return array_list;

    }
}