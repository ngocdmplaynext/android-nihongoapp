package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ngocdm on 5/17/17.
 */

public interface ThemeInterface {
    @GET("/api/v1/themes")
    public Call<ArrayList<Theme>> getTheme();
}
