package com.jp.playnext.voicecards.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/20/17.
 */

public class Theme implements Parcelable {

    private String sName;

    private ArrayList<Deck> alDeck;

    public Theme (){

        this(Deck.class.getName());
    }

    public Theme (String name){

        this(name, new ArrayList<Deck>());
    }

    public Theme (String name, ArrayList<Deck> decks){

        this.sName = name;
        this.alDeck = decks;
    }

    public void addDeck(Deck deck){
        alDeck.add(deck);
    }

    public void removeDeck(Deck deck){
        alDeck.remove(deck);
    }

    public Deck get(int position){
        return alDeck.get(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sName);
        dest.writeTypedList(this.alDeck);
    }

    protected Theme(Parcel in) {
        this.sName = in.readString();
        this.alDeck = in.createTypedArrayList(Deck.CREATOR);
    }

    public static final Parcelable.Creator<Theme> CREATOR = new Parcelable.Creator<Theme>() {
        @Override
        public Theme createFromParcel(Parcel source) {
            return new Theme(source);
        }

        @Override
        public Theme[] newArray(int size) {
            return new Theme[size];
        }
    };

    public ArrayList<Deck> getDeck() {
        return alDeck;
    }

    public String getName() {
        return sName;
    }
}
