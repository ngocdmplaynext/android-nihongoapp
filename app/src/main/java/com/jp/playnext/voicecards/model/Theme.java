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
 * This class holds a collection of decks
 */

public class Theme implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;

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


    //===================PARCELABLE=================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    protected Theme(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
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
}
