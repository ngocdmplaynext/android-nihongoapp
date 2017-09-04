package com.jp.playnext.voicecards.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ngocdm on 6/5/17.
 */

public interface LoginInterface {
    @POST("/api/v1/sessions")
    @FormUrlEncoded
    Call<UserLogin> login(@Field("email") String email,
                             @Field("password") String password);
}
