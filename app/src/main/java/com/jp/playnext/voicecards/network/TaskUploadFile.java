package com.jp.playnext.voicecards.network;

import android.text.TextUtils;
import android.util.Log;

import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.RecordSentence;
import com.jp.playnext.voicecards.model.RecordSentenceInterface;
import com.jp.playnext.voicecards.model.Theme;
import com.jp.playnext.voicecards.utils.NetworkHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ngocdm on 5/31/17.
 */

public class TaskUploadFile {

    private ArrayList<RecordSentence> sentences;
    private String topic;
    private Theme theme;
    private String header;

    public TaskUploadFile(String header, ArrayList<RecordSentence> sentences, String topic, Theme theme) {
        this.sentences = sentences;
        this.topic = topic;
        this.theme = theme;
        this.header = header;
    }

    public void uploadFile(final ICallBackData listener) {
        RecordSentenceInterface recordSentenceInterface = InterfaceFactory.createRetrofitService(RecordSentenceInterface.class);

        ArrayList<String> arrSentences = new ArrayList<String>();
        for(RecordSentence sentence : sentences) {
            arrSentences.add(sentence.getSentence());
        }

        String joinedSentence = TextUtils.join(",", arrSentences);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("sentence", NetworkHelper.createRequestBody(joinedSentence));
        params.put("topic", NetworkHelper.createRequestBody(topic));

        Map<String, RequestBody> files = new HashMap<>();

        int pos = 0;
        ArrayList<String> keys = new ArrayList<String>();
        for(RecordSentence sentence : sentences) {
            RequestBody requestBody = NetworkHelper.createRequestBody(new File(sentence.getAudioUrl()));

            String key = String.format("%1$s\"; filename=\"%1$s", "file" + String.valueOf(pos + 1));
            keys.add(String.format("file" + String.valueOf(pos + 1)));
            files.put(key, requestBody);
            pos++;
        }

        String joinedKey = TextUtils.join(",", keys);
        params.put("file_name", NetworkHelper.createRequestBody(joinedKey));

        Call<ResponseBody> call = recordSentenceInterface.upload(header, theme.getId(), params,files);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onResponse(call, response);
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(call, t);
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public interface ICallBackData {
        void onResponse(Call<ResponseBody> call, Response<ResponseBody> response);
        void onFailure(Call<ResponseBody> call, Throwable t);
    }

}
