package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/20/17.
 */

public class Deck {

    private String sName;

    private ArrayList<Card> alCards;

    public Deck (){

        this(Deck.class.getName());
    }

    public Deck (String name){

        this(name, new ArrayList<Card>());
    }

    public Deck (String name, ArrayList<Card> cards){

        this.sName = name;
        this.alCards = cards;
    }

    public void addCard(Card card){
        alCards.add(card);
    }

    public void removeCard(Card card){
        alCards.remove(card);
    }
}
