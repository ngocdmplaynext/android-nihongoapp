package com.jp.playnext.voicecards.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.jp.playnext.voicecards.R;

/**
 * Created by ngocdm on 6/5/17.
 */

public class ExcludePreferencesServiceImpl implements ExcludePreferencesService {
    Context mContext;

    public ExcludePreferencesServiceImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setString(@NonNull String data, @NonNull String key) {
        if (data == null || key == null) {
            return;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.exclude_preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    @Override
    public String getString(@NonNull String key, @NonNull String defValue) {
        if (key == null || defValue == null) {
            return defValue;
        }
        if (mContext == null) {
            return defValue;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.exclude_preferences_file_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public void setInt(int data, @NonNull String key) {
        if (key == null) {
            return;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.exclude_preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, data);
        editor.apply();
    }

    @Override
    public int getInt(@NonNull String key, int defValue) {
        if (key == null) {
            return defValue;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.exclude_preferences_file_name), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public void removeValue(@NonNull String key) {
        if (key == null) {
            return;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.exclude_preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
