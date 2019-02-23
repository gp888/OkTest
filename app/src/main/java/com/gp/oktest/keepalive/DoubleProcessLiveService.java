package com.gp.oktest.keepalive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作用: 保活此Service
 */
public class DoubleProcessLiveService extends Service {

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

    @Override
    public void onCreate() {
        super.onCreate();

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d("LiveService", "双进程保活");
            }
        }, 0, 60, TimeUnit.SECONDS);
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
