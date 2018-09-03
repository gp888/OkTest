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

/**
 * Service分为本地服务（LocalService）和远程服务（RemoteService）：
 * LocalService依附在主进程上而不是独立的进程，这样在一定程度上节约了资源，另外Local服务因为是在同一进程因此不需要IPC，
 *  也不需要AIDL。相应bindService会方便很多。主进程被Kill后，服务便会终止。
 *
 *
 *  远程服务为独立的进程(独立进程的 main 线程上)，对应进程名格式为所在包名加上你指定的android:process字符串。由于是独立的进程，因此在Activity所在进程被Kill的时候，该服务依然在运行，
 *  不受其他进程影响，有利于为多个进程提供服务具有较高的灵活性。该服务是独立的进程，会占用一定资源，并且使用AIDL进行IPC稍微麻烦一点。
 *
 *  startService 启动的服务：主要用于启动一个服务执行后台任务，不进行通信。停止服务使用stopService；

    bindService 启动的服务：该方法启动的服务可以进行通信。停止服务使用unbindService；

    startService 同时也 bindService 启动的服务：停止服务应同时使用stopService与unbindService


 通过StartService启动Service
 startService启动后，service会一直无限期运行下去，只有外部调用了stopService()或stopSelf()方法时，该Service才会停止运行并销毁。
 onCreate()
 1.如果service没被创建过，调用startService()后会执行onCreate()回调；
 2.如果service已处于运行中，调用startService()不会执行onCreate()方法。
 也就是说，onCreate()只会在第一次创建service时候调用，多次执行startService()不会重复调用onCreate()，此方法适合完成一些初始化工作。

 onStartCommand()
 如果多次执行了Context的startService()方法，那么Service的onStartCommand()方法也会相应的多次调用。onStartCommand()方法很重要，我们在该方法中根据传入的Intent参数进行实际的操作，比如会在此处创建一个线程用于下载数据或播放音乐等。

 onBind()
 Service中的onBind()方法是抽象方法，Service类本身就是抽象类，所以onBind()方法是必须重写的，即使我们用不到。






 bindService启动服务特点：
 1.bindService启动的服务和调用者之间是典型的client-server模式。调用者是client，service则是server端。service只有一个，但绑定到service上面的client可以有一个或很多个。
 这里所提到的client指的是组件，比如某个Activity。
 2.client可以通过IBinder接口获取Service实例，从而实现在client端直接调用Service中的方法以实现灵活交互，这在通过startService方法启动中是无法实现的。
 3.bindService启动服务的生命周期与其绑定的client息息相关。当client销毁时，client会自动与Service解除绑定。当然，
 client也可以明确调用Context的unbindService()方法与Service解除绑定。当没有任何client与Service绑定时，Service会自行销毁。


 client端要做的事情：
 1.创建ServiceConnection类型实例，并重写onServiceConnected()方法和onServiceDisconnected()方法。
 2.当执行到onServiceConnected回调时，可通过IBinder实例得到Service实例对象，这样可实现client与Service的连接。
 3.onServiceDisconnected回调被执行时，表示client与Service断开连接，在此可以写一些断开连接后需要做的处理。
 */
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

//    提高Service的优先级
//    在AndroidManifest.xml文件中对于intent-filter可以通过android:priority = "1000"这个属性设置最高优先级，1000是最高值，如果数字越小则优先级越低，同时适用于广播



//    提升Service进程的优先级
//
//    当系统进程空间紧张时，会依照优先级自动进行进程的回收。
//    Android将进程分为6个等级，按照优先级由高到低依次为：
//
//    前台进程foreground_app
//            可视进程visible_app
//    次要服务进程secondary_server
//            后台进程hiddena_app
//    内容供应节点content_provider
//            空进程empty_app
//    可以使用startForeground将service放到前台状态，这样低内存时，被杀死的概率会低一些。




//    4.在onDestroy方法里重启Service
//    当service走到onDestroy()时，发送一个自定义广播，当收到广播时，重新启动service。
//
// 5.系统广播监听Service状态
//  6.将APK安装到/system/app，变身为系统级应用
}
