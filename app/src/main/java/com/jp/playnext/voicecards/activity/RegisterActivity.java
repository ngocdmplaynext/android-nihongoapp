package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.network.TaskRegister;
import com.jp.playnext.voicecards.utils.FileUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_register_name)
    TextView etName;
    @BindView(R.id.et_register_email)
    EditText etEmail;
    @BindView(R.id.et_register_password)
    EditText etPassword;
    @BindView(R.id.et_register_password_confirm)
    EditText etPasswordConfirm;
    @BindView(R.id.btn_register_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        final Context context = this;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText() == null || etEmail.getText() == null || etPassword == null || etPasswordConfirm.getText() == null) {
                    Toast.makeText(context, "Please input data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();
                TaskRegister taskRegister = new TaskRegister(name, email, password, passwordConfirm);
                taskRegister.register(new TaskRegister.ICallBackData() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int code = response.code();
                        if (code != 200) { // has error
                            String message;
                            if (code == 422) {
                                message = "Invalid input field";
                            } else {
                                message = response.message();
                            }
                            Toast.makeText(context, "Invalid input field", Toast.LENGTH_SHORT).show();
                        } else {
                            etName.setText(null);
                            etEmail.setText(null);
                            etPassword.setText(null);
                            etPasswordConfirm.setText(null);
                            String message = "Successful!\n" +
                                    "This account is being checked. Please wait";
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(message);
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "はい",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
