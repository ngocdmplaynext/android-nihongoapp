package com.jp.playnext.voicecards.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ngocdm on 5/24/17.
 */

public class Audio {
    @SerializedName("audio_url")
    @Expose
    private String audioUrl;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

}
