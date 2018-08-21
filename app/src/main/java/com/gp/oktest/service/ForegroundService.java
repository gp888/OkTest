package com.gp.oktest.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gp.oktest.MainActivity;
import com.gp.oktest.R;

/**
 * Service分为本地服务（LocalService）和远程服务（RemoteService）：
 * 本地服务依附在主进程上而不是独立的进程，这样在一定程度上节约了资源，另外Local服务因为是在同一进程因此不需要IPC，
 *  也不需要AIDL。相应bindService会方便很多。主进程被Kill后，服务便会终止。
 *
 *
 *  远程服务为独立的进程，对应进程名格式为所在包名加上你指定的android:process字符串。由于是独立的进程，因此在Activity所在进程被Kill的时候，该服务依然在运行，
 *  不受其他进程影响，有利于为多个进程提供服务具有较高的灵活性。该服务是独立的进程，会占用一定资源，并且使用AIDL进行IPC稍微麻烦一点。
 *
 *  startService 启动的服务：主要用于启动一个服务执行后台任务，不进行通信。停止服务使用stopService；

    bindService 启动的服务：该方法启动的服务可以进行通信。停止服务使用unbindService；

    startService 同时也 bindService 启动的服务：停止服务应同时使用stopService与unbindService
 */
public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        //设置为前台服务
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("梅西生涯最大尴尬 战法国能否破荒？")
                .setContentText("世界杯1/8决赛，法国对阵阿根廷，法国队主帅德尚将迎来80战里程碑，成为队史执教场次最多的主教练，高卢雄鸡能否保持过去40年世界杯遇南美球队不败的金身，格里兹曼能否找回最佳状态，梅西能否打破此前世界杯淘汰赛666分钟的进球荒，都是此役的关键看点。")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
    }

    //服务一旦启动后，就会一直处于运行状态，直到调用 stopService() 或者 stopSelf() 才会停止服务
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        //在子线程中来执行耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 10; i++) {
                    Log.d(TAG, "run" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "run down");
                //耗时操作
                stopSelf();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
