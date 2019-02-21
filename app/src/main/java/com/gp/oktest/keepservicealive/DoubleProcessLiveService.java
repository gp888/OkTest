package com.gp.oktest.keepservicealive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作用: 保活此Service
 */
public class DoubleProcessLiveService extends Service {

    Timer timer = new Timer();

    @Override
    public void onCreate() {
        super.onCreate();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("LiveService", "双进程保活");
            }
        }, 0, 60 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LiveService","onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
