package com.gp.oktest.keepservicealive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class DoubleProcessTestActivity extends AppCompatActivity {
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mDownloadBinder = (DownloadService.DownloadBinder) service;
//            mDownloadBinder.setOnTimeChangeListener(new DownloadService.OnTimeChangeListener() {
//                @Override
//                public void showTime(final String time) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mShowTimeTv.setText(time);
//                        }
//                    });
//                }
//            });
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, DoubleProcessLiveService.class);
        startService(intent);
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        //双守护线程，优先级不一样
        startAllServices();
    }

    /**
     * 开启所有守护Service
     */
    private void startAllServices() {
        startService(new Intent(this, StepService.class));
        startService(new Intent(this, GuardService.class));
    }
}
