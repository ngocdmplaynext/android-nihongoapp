package com.jp.playnext.voicecards.libs;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.jp.playnext.voicecards.model.Deck;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ngocdm on 5/25/17.
 */

public class AudioPlayList {
    private static AudioPlayList sInstance;
    private ArrayList<String> paths;
    private Boolean isPlaying = false;
    OnAudioPlayListInteraction mListener;

    private MediaPlayer currentMedia;

    public static synchronized AudioPlayList getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AudioPlayList(context);
        }
        return sInstance;
    }

    public AudioPlayList(Context context) {
        if (context instanceof  OnAudioPlayListInteraction) {
            this.mListener = (OnAudioPlayListInteraction) context;
        }
    }

    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }

    public void play() {
        isPlaying = true;
        play(0, paths.size() - 1);
        Integer size = paths.size();
        Log.v("aaa", size.toString());
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void stop() {
        isPlaying = false;
        if (currentMedia != null) {
            currentMedia.stop();
        }
    }

    private void play(final Integer position, final Integer limit) {
        final MediaPlayer mPlayer = new MediaPlayer();
        currentMedia = mPlayer;
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                // Code to start the next audio in the sequence
                if (position < limit && isPlaying) {
                    play(position + 1, limit);
                }

                if (position == limit) {
                    mListener.audioPlayListCompletion();
                }
            }
        });

        String path = paths.get(position);

        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnAudioPlayListInteraction {
        // TODO: Update argument type and name
        void audioPlayListCompletion();
    }
}
