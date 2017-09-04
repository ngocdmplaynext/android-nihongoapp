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
    OnAudioPlayListInteraction mListener;

    private MediaPlayer currentMedia;

    public static synchronized AudioPlayList getInstance(Context context, ArrayList<String> paths) {
        if (sInstance == null) {
            sInstance = new AudioPlayList(context, paths);
        }
        return sInstance;
    }

    public AudioPlayList(Context context, ArrayList<String> paths) {
        this.paths = paths;
        if (context instanceof  OnAudioPlayListInteraction) {
            this.mListener = (OnAudioPlayListInteraction) context;
        }
    }

    public void play() {
        play(0, paths.size() - 1);
        Integer size = paths.size();
        Log.v("aaa", size.toString());
    }

    public boolean isPlaying() {
        if (currentMedia != null) {
            return currentMedia.isPlaying();
        }
        return false;
    }

    public void stop() {
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
                if (position < limit) {
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
