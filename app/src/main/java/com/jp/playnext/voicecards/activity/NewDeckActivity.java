package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.NewDeckListFragment;
import com.jp.playnext.voicecards.model.RecordSentence;
import com.jp.playnext.voicecards.model.Theme;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.network.TaskUploadFile;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class NewDeckActivity extends VoiceListenerActivity implements NewDeckListFragment.OnNewDeckListFragmentInteraction {
    private static final String ARGS_THEME = "args_theme";
    Integer recordingNumber = 0;
    Theme theme;
    ArrayList<RecordSentence> sentences;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.btn_new_deck_record)
    Button btnRecord;
    @BindView(R.id.btn_new_deck_edit)
    Button btnEdit;
    @BindView(R.id.btn_new_deck_register)
    Button btnRegister;
    @BindView(R.id.button_close)
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);

        if(getIntent() != null){
            theme = getIntent().getParcelableExtra(ARGS_THEME);
        }else {
            Toast.makeText(this, "THEME DOESN'T EXIST", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        initData();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_new_deck_fragment, NewDeckListFragment.newInstance(sentences), NewDeckListFragment.class.getName())
                    .commit();
        }

        ButterKnife.bind(this);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMic();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void uploadFile() {
        if (etTitle.getText() == null) {
            Toast.makeText(this, "Please input topic", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sentences.size() == 0) {
            Toast.makeText(this, "Please record sentence", Toast.LENGTH_SHORT).show();
            return;
        }

        final Context context = this;
        UserDefaultImpl userDefault = new UserDefaultImpl(this);
        TaskUploadFile taskUploadFile = new TaskUploadFile(userDefault.getToken(), sentences, etTitle.getText().toString(), theme);
        taskUploadFile.uploadFile(new TaskUploadFile.ICallBackData() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "Create successful", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Create failure! Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void newInstance(Context context, Theme theme){
        Intent intent = new Intent(context, NewDeckActivity.class);
        intent.putExtra(ARGS_THEME, theme);
        context.startActivity(intent);
    }

    private void initData() {
        sentences = new ArrayList<RecordSentence>();
    }

    private void updateData(ArrayList<RecordSentence> sentences) {
        NewDeckListFragment newDeckListFragment = (NewDeckListFragment) getSupportFragmentManager().findFragmentByTag(NewDeckListFragment.class.getName());
        newDeckListFragment.updateListView(sentences);
    }

    @Override
    public void processResults(Intent intent) {
        final File audioFile = getAudio(intent, recordingNumber++);

        if (audioFile == null) {
            Toast.makeText(this, "Recorded file not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        final String receivedSentence = result.get(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(receivedSentence);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecordSentence sentence = new RecordSentence(receivedSentence, audioFile.getAbsolutePath());
                sentences.add(sentence);
                updateData(sentences);
            }
        });

        builder.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClickWord(String wordClicked) {
        Log.v(TAG, "on word clicked");
    }

    @Override
    public void onEditClicked(final RecordSentence sentence) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(sentence.getSentence());
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sentence.setSentence(input.getText().toString());
                updateData(sentences);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
