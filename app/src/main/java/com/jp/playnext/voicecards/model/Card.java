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


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jp.playnext.voicecards.utils.TouchableSpan;
import com.jp.playnext.voicecards.utils.Utils;
import com.jp.playnext.voicecards.database.DBHelper;

/**
 * Created by danielmorais on 2/8/17.
 */

public class Card implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("deck_id")
    @Expose
    private Integer deckId;
    @SerializedName("best_score")
    @Expose
    private Integer bestScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getBestScore() {
        return bestScore;
    }

    public void setBestScore(Integer bestScore) {
        this.bestScore = bestScore;
    }

    public String getDisplaySentence() {
        if (Utils.isJapanese(name))
            return name.replaceAll("\\s+", "");

        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.deckId);
        dest.writeInt(this.bestScore);
    }

    protected Card(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.deckId = in.readInt();
        this.bestScore = in.readInt();
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
}
