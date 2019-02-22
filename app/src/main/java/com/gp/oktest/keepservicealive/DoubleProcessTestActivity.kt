package com.gp.oktest.keepservicealive

import android.content.Intent
import android.os.Bundle
import com.gp.oktest.base.BaseActivity

class DoubleProcessTestActivity : BaseActivity(){

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = Intent(this, DoubleProcessLiveService::class.java)
        startService(intent)
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        //双守护线程，优先级不一样
        startAllServices()
    }

    /**
     * 开启所有守护Service
     */
    private fun startAllServices() {
        startService(Intent(this, StepService::class.java))
        startService(Intent(this, GuardService::class.java))
    }
}