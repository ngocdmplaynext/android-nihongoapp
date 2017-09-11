package com.jp.playnext.voicecards.model;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ngocdm on 9/8/17.
 */

public interface UserInterface {
    @GET("/api/v1/teachers")
    public Call<ArrayList<User>> getTeachers(@Header("Auth-Token") String header);

    @POST("/api/v1/users/{user_id}/bookmarks")
    public Call<ResponseBody> bookmarks(@Header("Auth-Token") String header, @Path("user_id") Integer userId);

    @DELETE("/api/v1/users/{user_id}/bookmarks")
    public Call<ResponseBody> unBookmarks(@Header("Auth-Token") String header, @Path("user_id") Integer userId);
}
