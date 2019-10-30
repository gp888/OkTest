package com.gp.oktest.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.util.Log
import com.gp.oktest.R
import java.util.*

class ForegroundService1 : Service() {
    private val timer = Timer()
    override fun onCreate() {
        super.onCreate()
        startForeground()
        timer.schedule(object : TimerTask() {
            override fun run() {
                Log.d("ForegroundService1", "前台服务保活")
            }
        }, 0, 60 * 1000)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private val notificationManager: NotificationManager by lazy {
        application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun startForeground() {
        //适配Android8.0以上的通知（用户可操作的通知）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "上传睡眠质量", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "上传睡眠质量"

            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC//是否在锁定的屏幕上显示
            channel.setShowBadge(true)//是否显示角标
            channel.setBypassDnd(true)//绕过免打扰

            channel.enableLights(true)//呼吸灯
//            channel.enableVibration(true)//开启震动
            //channel.setSound(Uri.fromFile(), new AudioAttributes.Builder());//声音
            notificationManager.createNotificationChannel(channel)
        }

        /**创建Notification*/
        fun createNotify(): Notification? {
            val builder = NotificationCompat.Builder(application, CHANNEL_ID)
            builder.setLargeIcon(BitmapFactory.decodeResource(application.resources, R.mipmap.ic_launcher))//设置大图标 不写会无提示的失败
            builder.setContentTitle("睡眠质量检测")
            builder.setContentText("前台服务保活")
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            builder.setTicker("开启睡眠质量检测")//通知时状态栏向上飘的文字
            builder.setAutoCancel(false)//可滑动取消
            builder.setOngoing(true)//此通知正在进行,不能被用户撤销
//            builder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)//声音+震动 在Android8.0以上，声音/震动只能在 Channel 中设置

            //点击关闭 - 点击后会自动关闭，那么做一个无用的点击处理即可，例如发一个不处理的广播
//        builder.setContentIntent(PendingIntent.getBroadcast(application, 101, Intent("userTouchNotification"), PendingIntent.FLAG_ONE_SHOT))
            return builder.build()
        }

        val notification = createNotify()

        startForeground(NOTIFY_ID, notification)
        //关闭？要什么关闭!
    }

    companion object {
        val CHANNEL_ID = "${ForegroundService::class.java.name} upHeartRate"
        const val NOTIFY_ID = 15362317
    }
}