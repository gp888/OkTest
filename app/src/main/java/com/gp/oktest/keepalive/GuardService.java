package com.gp.oktest.keepalive;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gp.oktest.KeepAliveConnection;

/**
 * 守护进程 双进程通讯
 */
public class GuardService extends Service {
    private final static String TAG = GuardService.class.getSimpleName();
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "GuardService:建立链接");
            boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(getApplicationContext(),DoubleProcessLiveService.class.getName());
            if (!isServiceRunning) {
                Intent i = new Intent(GuardService.this, DoubleProcessLiveService.class);
                startService(i);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 断开链接
            startService(new Intent(GuardService.this, StepService.class));
            // 重新绑定
            bindService(new Intent(GuardService.this, StepService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new KeepAliveConnection.Stub() {
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());
        // 绑定建立链接
        bindService(new Intent(this, StepService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

}
