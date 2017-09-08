package com.jp.playnext.voicecards.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ngocdm on 9/7/17.
 */

public interface RegisterInterface {
    @POST("/api/v1/users")
    @FormUrlEncoded
    Call<ResponseBody> register(@Field("name") String name,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("password_confirmation") String passwordConfirm);
}
