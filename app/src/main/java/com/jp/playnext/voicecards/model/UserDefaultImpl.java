package com.jp.playnext.voicecards.model;

import android.content.Context;

/**
 * Created by ngocdm on 6/5/17.
 */

public class UserDefaultImpl implements UserDefault {
    static final String KEY_AUTH_TOKEN = "authToken";
    static final String KEY_USER_NAME = "userName";
    static final String KEY_USER_TYPE = "userType";

    ExcludePreferencesServiceImpl mPreferences;

    public UserDefaultImpl(Context context) {
        mPreferences = new ExcludePreferencesServiceImpl(context);
    }

    @Override
    public String getToken() {
        return mPreferences.getString(KEY_AUTH_TOKEN, "");
    }

    @Override
    public void setToken(String token) {
        mPreferences.setString(token, KEY_AUTH_TOKEN);
    }

    @Override
    public void resetToken() {
        mPreferences.removeValue(KEY_AUTH_TOKEN);
    }

    @Override
    public String getUsername() {
        return mPreferences.getString(KEY_USER_NAME, "");
    }

    @Override
    public void setUsername(String username) {
        mPreferences.setString(username, KEY_USER_NAME);
    }

    @Override
    public void resetUsername() {
        mPreferences.removeValue(KEY_USER_NAME);
    }

    @Override
    public Integer getUserType() {
        return mPreferences.getInt(KEY_USER_TYPE, 0);
    }

    @Override
    public void setUserType(Integer userType) {
        mPreferences.setInt(userType, KEY_USER_TYPE);
    }

    @Override
    public void resetUserType() {
        mPreferences.removeValue(KEY_USER_TYPE);
    }

    @Override
    public void resetUserInfo() {
        mPreferences.removeValue(KEY_AUTH_TOKEN);
        mPreferences.removeValue(KEY_USER_NAME);
        mPreferences.removeValue(KEY_USER_TYPE);
    }
}
