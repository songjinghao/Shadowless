package com.paul.song.medialibrary.player;

import android.util.Log;

import com.paul.song.medialibrary.entity.TrackEntity;

/**
 * Created by songjinghao on 2019/10/18.
 */
public class AudioPlayer {

    private static final String TAG = AudioPlayer.class.getSimpleName();

    private volatile static AudioPlayer sInstance;

    private AudioPlayer() {}

    public static AudioPlayer getInstance() {
        if (sInstance == null) {
            synchronized (AudioPlayer.class) {
                if (sInstance == null) {
                    sInstance = new AudioPlayer();
                }
            }
        }
        return sInstance;
    }

    public void play(TrackEntity trackEntity) {
        /** 这里将要插入代码 */
        Log.d(TAG, "这里将要插入代码!!!");
    }
}
