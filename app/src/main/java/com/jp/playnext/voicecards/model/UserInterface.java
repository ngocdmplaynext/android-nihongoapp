package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by ngocdm on 9/8/17.
 */

public interface UserInterface {
    @GET("/api/v1/teachers")
    public Call<ArrayList<User>> getTeachers(@Header("Auth-Token") String header);
}
