package com.gp.oktest.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class AsycnService extends IntentService {
    private static final String TAG = "AsycnService";

    public AsycnService() {
        super("AsycnService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "子线程 id: " + Thread.currentThread().getId());
        android.os.Handler handler = new android.os.Handler();
        //在此执行耗时逻辑
        for(int i = 0;i < 5; i++) {
            Log.d(TAG, "run" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "run down");

//        Snackbar snackbar = new Snackbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}