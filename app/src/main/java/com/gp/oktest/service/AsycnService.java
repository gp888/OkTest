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
        Log.d(TAG, "子线程 id（Intent 服务）: " + Thread.currentThread().getId());
        //在此执行耗时逻辑
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}