package com.gp.oktest.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class MusicService extends Service {

//    Intent i = new Intent("com.android.music.musicservicecommand.pause");
//        i.putExtra("command", "pause");
//        sendBroadcast(i);

    private final String TAG = "MusicService";
    /**
     * 音频管理类
     */
    private AudioManager mAudioManager;
    /**
     * 是否播放音乐
     */
    private static boolean vIsActive=false;
    /**
     * 音乐监听器
     */
    private MyOnAudioFocusChangeListener mListener;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        // 获取系统音乐服务
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(
                Context.AUDIO_SERVICE);
        // 获取系统音乐服务状态
        vIsActive = mAudioManager.isMusicActive();
        mListener = new MyOnAudioFocusChangeListener();
        if(vIsActive) {//播放状态
            int result = mAudioManager.requestAudioFocus(mListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.d(TAG, "requestAudioFocus successfully.");
            }
            else {
                Log.d(TAG, "requestAudioFocus failed.");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(vIsActive) {
            mAudioManager.abandonAudioFocus(mListener);
        }
        Log.d(TAG, "onDestroy");
    }

    /**
     * 内部类：音乐监听器
     */
    public class MyOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {

        }
    }
}