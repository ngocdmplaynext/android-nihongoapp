package com.jp.playnext.voicecards.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jp.playnext.voicecards.database.DBHelper;

import java.util.ArrayList;

/**
 * Created by danielmorais on 2/20/17.
 * This class holds a collection of cards
 */


public class Deck implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("theme_id")
    @Expose
    private Integer themeId;

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

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }


    //===================PARCELABLE=================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.themeId);
    }

    protected Deck(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.themeId = in.readInt();
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

    @Override
    public String toString() {
        return "Deck ID:"+id+". Name:"+name+". Parent Theme ID:"+themeId;
    }
}
