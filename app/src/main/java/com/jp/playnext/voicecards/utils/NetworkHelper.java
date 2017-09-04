package com.jp.playnext.voicecards.utils;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ngocdm on 5/31/17.
 */

public class NetworkHelper {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse("audio/mpeg"), file);
    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), s);
    }
}
