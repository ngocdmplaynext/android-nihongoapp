package com.jp.playnext.voicecards.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/20/17.
 */


public class Deck implements Parcelable {

    private String sName;

    private ArrayList<Card> alCards;

    public Deck() {

        this(Deck.class.getName());
    }

    public Deck(String name) {

        this(name, new ArrayList<Card>());
    }

    public Deck(String name, String[] words) {
        this.sName = name;
        alCards = new ArrayList<>();
        for (String s : words)
            alCards.add(new Card((s), name));
    }

    public Deck(String name, ArrayList<Card> cards) {

        this.sName = name;
        this.alCards = cards;
    }

    public void addCard(Card card) {
        getCards().add(card);
    }

    public void removeCard(Card card) {
        getCards().remove(card);
    }

    public Card get(int position) {
        return alCards.get(position);
    }

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

    public ArrayList<Card> getCards() {
        return alCards;
    }

    public String getName() {
        return sName;
    }
}
