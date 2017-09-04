package com.jp.playnext.voicecards.model;

/**
 * Created by ngocdm on 6/5/17.
 */

public interface UserDefault {
    String getToken();
    void setToken(String token);
    void resetToken();

    String getUsername();
    void setUsername(String username);
    void resetUsername();

    Integer getUserType();
    void setUserType(Integer userType);
    void resetUserType();

    void resetUserInfo();

}
