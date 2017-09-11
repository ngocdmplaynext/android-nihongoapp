package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.model.UserLogin;
import com.jp.playnext.voicecards.network.TaskLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_login_email)
    EditText etEmail;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.btn_login_login)
    Button btnLogin;
    @BindView(R.id.btn_login_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        final Context context = this;
        final UserDefaultImpl userDefault = new UserDefaultImpl(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText() == null || etPassword == null) {
                    Toast.makeText(context, "Please input data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                TaskLogin taskLogin = new TaskLogin(email, password);
                taskLogin.login(new TaskLogin.ICallBackData() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                        if (response.isSuccessful()) {
                            UserLogin userLogin = response.body();
                            userDefault.setToken(userLogin.getAuthToken());
                            userDefault.setUsername(userLogin.getName());
                            userDefault.setUserType(userLogin.getType());

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
