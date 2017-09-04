package com.jp.playnext.voicecards.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ngocdm on 5/29/17.
 */

public class RecordSentence implements Parcelable {
    private String sentence;
    private String audioUrl;

    public RecordSentence(String sentence, String audioUrl) {
        this.sentence = sentence;
        this.audioUrl = audioUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sentence);
        dest.writeString(this.audioUrl);
    }

    protected RecordSentence(Parcel in) {
        this.sentence = in.readString();
        this.audioUrl = in.readString();
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
