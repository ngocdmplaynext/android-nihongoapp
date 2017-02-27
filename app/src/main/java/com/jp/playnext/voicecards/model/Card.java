package com.jp.playnext.voicecards.model;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;


import com.jp.playnext.voicecards.utils.TouchableSpan;
import com.jp.playnext.voicecards.utils.Utils;
import com.jp.playnext.voicecards.database.DBHelper;

/**
 * Created by danielmorais on 2/8/17.
 */

public class Card implements Parcelable {

    private static final String TAG = Card.class.getSimpleName() + "123";
    private int id;
    private String sentence;
    private String title;
    private String parentDeck;
    private float bestScore;


    public Card() {

    }


    public Card(String sentence, String parentDeck) {
        this(sentence, sentence, parentDeck, -1, 0.0f);
    }

    public Card(String sentence, String title, String parentDeck, int id, float bestScore) {
        this.sentence = sentence;
        this.title = title;
        this.id = id;
        this.parentDeck = parentDeck;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sentence);
    }

    protected Card(Parcel in) {
        this.sentence = in.readString();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false;
        Card otherCard = (Card) obj;

        return TextUtils.equals(sentence, otherCard.getSentence());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Remove spaces if a sentence is Japanese
     *
     * @return
     */
    public String getDisplaySentence() {
        if (Utils.isJapanese(sentence))
            return sentence.replaceAll("\\s+", "");

        return sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Card ID: " + id + ". Title:" + title + ". Sentence:" + sentence + ". Parent Deck:" + parentDeck;
    }

    public String getParentDeck() {
        return parentDeck;
    }

    public void setParentDeck(String parentDeck) {
        this.parentDeck = parentDeck;
    }

    public float getBestScore() {
        return bestScore;
    }

    public String getBestScoreString() {
        return String.format("%.2f", bestScore) + "%";
    }


    public void setBestScore(Context context, float bestScore) {
        if (bestScore > this.bestScore) {
            this.bestScore = bestScore;
            if (id < -1)
                DBHelper.getInstance(context).dbCardHelper.insertCard(this);
            else
                DBHelper.getInstance(context).dbCardHelper.updateCard(this);
        }
    }

}
