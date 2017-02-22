package com.jp.playnext.voicecards.database;

import android.content.Context;
import android.util.Log;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.Theme;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/22/17.
 */

public class DummyInsertData {

    private static final String TAG = DummyInsertData.class.getSimpleName();

    public static void insertDummyData(Context context) {
        ArrayList<Theme> alThemes = new ArrayList<>();
        alThemes = new ArrayList<>();
        Theme theme = new Theme("Language");
        theme.addDeck(new Deck("English", context.getResources().getStringArray(R.array.deck_english)));
        theme.addDeck(new Deck("Japanese", context.getResources().getStringArray(R.array.deck_japanese)));
        alThemes.add(theme);

        theme = new Theme("Animal");
        theme.addDeck(new Deck("Wild animals", context.getResources().getStringArray(R.array.wild_animals)));
        theme.addDeck(new Deck("Domestic animals", context.getResources().getStringArray(R.array.domestic_animals)));
        theme.addDeck(new Deck("Sea animals", context.getResources().getStringArray(R.array.sea_animals)));
        alThemes.add(theme);

        for(Theme t: alThemes){
            //DBHelper.getInstance(context).dbDeckHelper.insertTheme(t);

            for(Deck d: theme.getDeck()){
                //DBHelper.getInstance(context).dbDeckHelper.insertDeck(d);

                for(Card c: d.getCards()){
                    DBHelper.getInstance(context).dbCardHelper.insertCard(c);
                }
            }
        }
    }

    public static void getAllCards(Context context){
        ArrayList<Card> alCards = new ArrayList<>();

        alCards = DBHelper.getInstance(context).dbCardHelper.getAllCard();
        Log.v(TAG, "============== DB CARDS ===============");
        for(Card c: alCards){
            Log.v(TAG, c.toString());
        }
        Log.v(TAG, "============== END DB CARDS ===============");

    }

    public static void getDeckCards(Context context){
        ArrayList<Card> alCards = new ArrayList<>();

        alCards = DBHelper.getInstance(context).dbCardHelper.getDeckCards("Wild animals");
        Log.v(TAG, "============== DB SOME CARDS ===============");
        for(Card c: alCards){
            Log.v(TAG, c.toString());
        }
        Log.v(TAG, "============== END SOME DB CARDS ===============");
    }
}
