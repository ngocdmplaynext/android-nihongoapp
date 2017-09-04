package com.jp.playnext.voicecards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.model.UserDefaultImpl;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UserDefaultImpl userDefault = new UserDefaultImpl(this);
        if (userDefault.getToken().isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
