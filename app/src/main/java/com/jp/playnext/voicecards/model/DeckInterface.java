package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by ngocdm on 5/18/17.
 */

public interface DeckInterface {
    @GET("/api/v1/themes/{theme_id}/decks")
    public Call<ArrayList<Deck>> getDecks(@Header("Auth-Token") String authToken, @Path("theme_id") Integer themeId);
}
