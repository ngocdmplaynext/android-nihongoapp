package com.jp.playnext.voicecards.network;

import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.LoginInterface;
import com.jp.playnext.voicecards.model.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ngocdm on 6/5/17.
 */

public class TaskLogin {
    private String email;
    private String password;

    public TaskLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void login(final ICallBackData listener) {
        LoginInterface loginInterface = InterfaceFactory.createRetrofitService(LoginInterface.class);
        Call<UserLogin> call = loginInterface.login(this.email, this.password);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                listener.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                listener.onFailure(call, t);
            }
        });
    }


    public interface ICallBackData {
        void onResponse(Call<UserLogin> call, Response<UserLogin> response);
        void onFailure(Call<UserLogin> call, Throwable t);
    }
}
