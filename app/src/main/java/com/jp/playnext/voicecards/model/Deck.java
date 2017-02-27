package com.jp.playnext.voicecards.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.jp.playnext.voicecards.database.DBHelper;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/20/17.
 * This class holds a collection of cards
 */


public class Deck implements Parcelable {

    private int id;

    private String sName;
    private String sThemeName;


    private ArrayList<Card> alCards;

    public Deck() {

        this(Deck.class.getName(), "NONE");
    }

    public Deck(String name, String sThemeName) {

        this(name, sThemeName, new ArrayList<Card>());
    }

    //FOR TESTING PURPOSE ONLY
    public Deck(String name, String sThemeName, String[] words) {
        this.sName = name;
        this.sThemeName = sThemeName;
        alCards = new ArrayList<>();
        for (String s : words)
            alCards.add(new Card((s), name));
    }


    public Deck(String name, String sThemeName, ArrayList<Card> cards) {

        this.sName = name;
        this.alCards = cards;
        this.sThemeName = sThemeName;
    }

    //=================== ARRAY LIST=================================


    public void addCard(Card card) {
        getCards().add(card);
    }


    public void removeCard(Card card) {
        getCards().remove(card);
    }


    public Card get(int position) {
        return alCards.get(position);
    }

    //=======================DB====================================

    public void reloadCards(Context context) {
        alCards = new ArrayList<>();
        loadCards(context);
    }

    public void loadCards(Context context) {
        if (alCards.size() == 0) {
            alCards = DBHelper.getInstance(context).dbCardHelper.getDeckCards(sName);
        }
    }


    //===================PARCELABLE=================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sName);
        dest.writeTypedList(this.getCards());
    }

    protected Deck(Parcel in) {
        this.sName = in.readString();
        this.alCards = in.createTypedArrayList(Card.CREATOR);
    }

    public static final Parcelable.Creator<Deck> CREATOR = new Parcelable.Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel source) {
            return new Deck(source);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

    //===================GET/SETTERS=================================


    public ArrayList<Card> getCards() {
        return alCards;
    }

    public String getName() {
        return sName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String sName) {
        this.sName = sName;
    }

    public ArrayList<Card> getAlCards() {
        return alCards;
    }

    public void setAlCards(ArrayList<Card> alCards) {
        this.alCards = alCards;
    }

    public String getsThemeName() {
        return sThemeName;
    }

    public void setsThemeName(String sThemeName) {
        this.sThemeName = sThemeName;
    }

    @Override
    public String toString() {
        return "Deck ID:"+id+". Name:"+sName+". Parent Theme:"+sThemeName;
    }
}
