package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by ngocdm on 5/22/17.
 */

public interface CardInterface {
    @GET("/api/v1/decks/{deck_id}/cards")
    public Call<ArrayList<Card>> getCards(@Header("Auth-Token") String header, @Path("deck_id") Integer themeId);

    @GET("/api/v1/cards/{card_id}/audio_url")
    public Call<Audio> getCardAudioUrl(@Header("Auth-Token") String header, @Path("card_id") Integer cardId);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
