package com.jp.playnext.voicecards.model;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by ngocdm on 5/30/17.
 */

public interface RecordSentenceInterface {
    @Multipart
    @POST("api/v1/themes/{theme_id}/decks")
    Call<ResponseBody> upload(
            @Header("Auth-Token") String header,
            @Path("theme_id") Integer themeId,
            @PartMap Map<String, RequestBody> params,
            @PartMap Map<String, RequestBody> files
    );
}
