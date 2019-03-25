package com.gp.oktest.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.service.AsycnService;
import com.gp.oktest.service.ForegroundService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {

    @OnClick(R.id.startService) void startService() {
        Intent intentOne = new Intent(this, ForegroundService.class);
        startService(intentOne);
    }

    @OnClick(R.id.bindService) void bindService() {
        Intent intent = new Intent(this, ForegroundService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.unbindService) void unbindService() {
        if (isBind) {
            Log.i(TAG, "unbindService");
            unbindService(connection);
        }
    }

    @OnClick(R.id.startAsync) void startAsync() {
        Intent intentService = new Intent(this, AsycnService.class);
        startService(intentService);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        Log.d(TAG, "主线程 id: " + Thread.currentThread().getId());

    }

    private ForegroundService service = null;
    private boolean isBind = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBind = true;
            ForegroundService.MyBinder myBinder = (ForegroundService.MyBinder) binder;
            service = myBinder.getService();
            Log.i(TAG, "onServiceConnected");
            int num = service.getRandomNumber();
            Log.i(TAG, "getRandomNumber = " + num);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            Log.i(TAG, "onServiceDisconnected");
        }
    };
}
