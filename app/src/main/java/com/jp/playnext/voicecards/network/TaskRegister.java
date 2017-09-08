package com.jp.playnext.voicecards.network;

import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.RegisterInterface;
import com.jp.playnext.voicecards.model.UserLogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ngocdm on 9/7/17.
 */

public class TaskRegister {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    public TaskRegister(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void register(final ICallBackData listener) {
        RegisterInterface registerInterface = InterfaceFactory.createRetrofitService(RegisterInterface.class);
        Call<ResponseBody> call = registerInterface.register(name, email, password, confirmPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(call, t);
            }
        });
    }

    public interface ICallBackData {
        void onResponse(Call<ResponseBody> call, Response<ResponseBody> response);
        void onFailure(Call<ResponseBody> call, Throwable t);
    }
}
