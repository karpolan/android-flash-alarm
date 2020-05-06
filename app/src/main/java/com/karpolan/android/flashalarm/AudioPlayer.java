package com.karpolan.android.flashalarm;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Plays sound form "raw" resources (.vaw, .mp3, and so on)
 * Supports sound looping, just set the repeatAfterEnd param true
 */
public class AudioPlayer {

    private MediaPlayer mMediaPlayer;

    public void onDestroy() {
        stop();
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void play(Context c, int rid) {
        play(c, rid, false);
    }

    public void play(Context c, int rid, boolean repeatAfterEnd) {
        // Stop current sound
        stop();

        // Configure new MediaPlayer
        mMediaPlayer = MediaPlayer.create(c, rid);
        mMediaPlayer.setLooping(repeatAfterEnd);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        // Start new sound
        mMediaPlayer.start();
    }

}