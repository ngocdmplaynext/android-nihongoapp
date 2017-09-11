package com.jp.playnext.voicecards.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ngocdm on 5/26/17.
 */

public class InterfaceFactory {
    static String baseUrl = "http://172.16.96.20:3000";
    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
