package com.gp.oktest.utils;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

//关闭其他声音
//AudioFocusUtils.requestAudioFoucs(this);
//AudioFocusUtils.releaseAudioFocus();
public class AudioFocusUtils {
    static final String TAG = AudioFocusUtils.class.getSimpleName();
    static boolean isActive;
    private static AudioManager mAudioManager;

    public static void requestAudioFoucs(Context context) {
        mAudioManager = (AudioManager) context.getApplicationContext()
                .getSystemService(Context.AUDIO_SERVICE);
        isActive = mAudioManager.isMusicActive();
        if(isActive) {
            int result = mAudioManager.requestAudioFocus(null,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.d(TAG, "requestAudioFocus successfully.");
            }
            else {
                Log.d(TAG, "requestAudioFocus failed.");
            }
        }
    }

    public static void releaseAudioFocus(){
        if(isActive && mAudioManager != null) {
            mAudioManager.abandonAudioFocus(null);
        }
    }

}

