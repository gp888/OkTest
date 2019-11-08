package com.gp.oktest.activity;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.kotlintest.FirstClass;
import com.gp.oktest.kotlintest.SecondActivity;
import com.gp.oktest.kotlintest.Student;

import java.util.Timer;
import java.util.TimerTask;

public class BitmapOptionsActivity extends BaseActivity {

    ImageView iv_test;
    int rotate = 0;
    long mTaskId;
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmapoptions);

        iv_test = (ImageView) findViewById(R.id.iv_test);
        iv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(rotate) {
                    case 0:
                        rotate = 45;
                        break;
                    case 45:
                        rotate = 90;
                        break;
                    case 90:
                        rotate = 135;
                        break;
                    case 135:
                        rotate = 180;
                        break;
                    case 180:
                        rotate = 225;
                        break;
                    case 225:
                        rotate = 270;
                        break;
                    case 270:
                        rotate = 315;
                        break;
                    case 315:
                        rotate = 0;
                        break;
                }
                iv_test.setImageBitmap(rotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ii), rotate));
            }
        });

        fyBitmap();

        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inTargetDensity = 480;
//        options.inDensity = 320;//8309760
        Bitmap ii_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ii, options);
        Log.i("hehe", "ii_bitmap.size = " + ii_bitmap.getByteCount());

        FirstClass f = new FirstClass("");
        String[] b = new String[]{};
        f.main(b);

        Student s = new Student("1", "xiaoming");
        s.study();


//        下载完成时，发送的广播。
//        对应的Action为：ACTION_DOWNLOAD_COMPLETE
//        Notification被点击时发送的广播。
//        对应的Action为：ACTION_NOTIFICATION_CLICKED
//        查看所有下载情况的广播。
//        对应的Action为：ACTION_VIEW_DOWNLOADS
        downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等
        String url = "http://znt.nxin.com/download/";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/okTest/download", "zntgp.apk");
        //只有允许WIFIi下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //默认只显示下载中通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //下载任务标题
        request.setTitle("智农通");
        //下载文件描述
        request.setDescription("智农通最新版");
//        request.addRequestHeader();
        //表示允许MediaScanner扫描到这个文件，默认不允许。
        request.allowScanningByMediaScanner();
        //下载的文件可以被系统的Downloads应用扫描到并管理
        request.setVisibleInDownloadsUi(true); //默认是允许的。
        mTaskId = downloadManager.enqueue(request);
        Log.i("hehe", "downloadAPK: mTaskId" + mTaskId);

        //注册广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                checkDownloadStatus();
            }
        },1000, 3000);


        Button bu = (Button) findViewById(R.id.publish);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show3(v);
            }
        });

    }

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(BitmapOptionsActivity.this, "完成", Toast.LENGTH_SHORT).show();
        }
    };

    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i("hehe", ">>>下载暂停");
                    break;
                case DownloadManager.STATUS_PENDING:
                    Log.i("hehe",">>>下载延迟");
                    downloadManager.remove(mTaskId);//取消下载
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Log.i("hehe", ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.i("hehe", ">>>下载完成");
                    break;
            }
        }
    }

    private Bitmap rotateBitmap(Bitmap b, float rotateDegree) {
        if (b == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        matrix.postScale(4, 1);
        matrix.setTranslate(2,0);
        matrix.setSkew(10, 0);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);

//        Bitmap rotaBitmap = ThumbnailUtils.extractThumbnail(b, 60, 45);
        return rotaBitmap;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    void fyBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inDensity = 160;
        options.inTargetDensity = 160;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        Log.i("hehe", "bitmap = " + bitmap);
        Log.i("hehe", "bitmap.size = " + bitmap.getByteCount());
        Log.i("hehe", "bitmap.allocSize = " + bitmap.getAllocationByteCount());

        options.inBitmap = bitmap;
        options.inMutable = true;
//        options.inScaled = true;
        options.inDensity = 160;
        options.inTargetDensity = 80;
        options.inSampleSize = 1;
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round, options);
        Log.i("hehe1", "bitmap1 = " + bitmap1);
        Log.i("hehe1", "bitmap.size = " + bitmap.getByteCount());
        Log.i("hehe1", "bitmap.allocSize = " + bitmap.getAllocationByteCount());
        Log.i("hehe1", "bitmap1.size = " + bitmap1.getByteCount());
        Log.i("hehe1", "bitmap1.allocSize = " + bitmap1.getAllocationByteCount());

        Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.ii, null);
        test.getByteCount();
    }

    //定义notification的ID
    private static final int NO_3 =0x3;

    public  void show3(View v){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        //设置Notification.Default_ALL(默认启用全部服务(呼吸灯，铃声等)
        builder.setDefaults(Notification.DEFAULT_LIGHTS);

        //调用NotificationCompat.Builder的setContentIntent()来添加PendingIntent
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("info", "计策！");
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //然后调用NotificationManager.notify()向系统转交
//        manager.notify(NO_3, builder.build());
//        builder.setProgress(100,0,false);
        //下载以及安装线程模拟
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    builder.setProgress(100,i,false);
                    manager.notify(NO_3,builder.build());
                    //下载进度提示
                    builder.setContentText("下载"+i+"%");
                    try {
                        Thread.sleep(50);//演示休眠50毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //下载完成后更改标题以及提示信息
                builder.setContentTitle("开始安装");
                builder.setContentText("安装中...");
                //设置进度为不确定，用于模拟安装
                builder.setProgress(0,0,true);
                manager.notify(NO_3,builder.build());
                //manager.cancel(NO_3);//设置关闭通知栏

            }}).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}
