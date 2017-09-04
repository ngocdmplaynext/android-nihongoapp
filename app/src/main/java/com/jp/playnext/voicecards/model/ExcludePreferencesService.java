package com.jp.playnext.voicecards.model;

import android.support.annotation.NonNull;

/**
 * Created by ngocdm on 6/5/17.
 */

public interface ExcludePreferencesService {

    void setString(@NonNull String data, @NonNull String key);

    @NonNull
    String getString(@NonNull String key, @NonNull String defValue);

    void setInt(int data, @NonNull String key);

    int getInt(@NonNull String key, int defValue);

    void removeValue(@NonNull String key);
}
