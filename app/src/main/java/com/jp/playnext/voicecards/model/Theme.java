package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/20/17.
 */

public class Theme  {

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
}
