package com.gp.oktest.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gp.oktest.MainActivity;
import com.gp.oktest.R;

import java.util.Random;


public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";

    //client 可以通过Binder获取Service实例
    public class MyBinder extends Binder {
        public ForegroundService getService() {
            return ForegroundService.this;
        }
    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand" + "-主线程id:" + Thread.currentThread().getId());

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
                stopSelf();
            }
        }).start();

//        return super.onStartCommand(intent, flags, startId);
        //START_STICKY
        return START_NOT_STICKY;//内存匮乏,杀掉之后，不会重新创建该Service
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private final Random generator = new Random();
    //getRandomNumber是Service暴露出去供client调用的公共方法
    public int getRandomNumber() {
        return generator.nextInt();
    }


}
