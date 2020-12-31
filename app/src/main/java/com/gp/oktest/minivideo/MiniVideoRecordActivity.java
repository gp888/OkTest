package com.gp.oktest.minivideo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gp.oktest.R;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class MiniVideoRecordActivity extends AppCompatActivity implements VideoRecordSurface.OnRecordListener{

    protected FrameLayout frameLayout;
    RecordedButton mRecordButton;
    //拍摄进度
    protected VideoProgressView videoProgressView;
    protected Button btnSwitch;
    private float iTime;
    private VideoRecordSurface videoRecordSurface;
    private String videoSavePath;
    public static final String kVideoSavePath = "videoSavePath";

    private OrientationSensorListener listener;
    private SensorManager sm;
    private Sensor sensor;
    //是否在录制视频
    private AtomicBoolean isRecordVideo = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener();
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

        //必须传递的值
        videoSavePath = getIntent().getStringExtra(kVideoSavePath);
        File file = new File(videoSavePath);
        if (!file.exists()) {
            file.mkdir();
        }
        initView();

        videoRecordSurface = new VideoRecordSurface(MiniVideoRecordActivity.this, videoSavePath);
        frameLayout.addView(videoRecordSurface);
        mRecordButton.setOnGestureListener(new RecordedButton.OnGestureListener() {
            @Override
            public void onLongClick() {

            }

            @Override
            public void onClick() {
                if(isRecordVideo.get()){
                    isRecordVideo.set(false);
//                    videoProgressView.stopProgress();
                    if (iTime <= videoRecordSurface.mRecordMiniTime) {
                        Toast.makeText(MiniVideoRecordActivity.this, "录制时间太短", Toast.LENGTH_SHORT).show();
                        videoRecordSurface.stopRecord();
                        videoRecordSurface.repCamera();
                    } else if(iTime < videoRecordSurface.mRecordMaxTime){
                        videoRecordSurface.stop();
                    }
                } else {
                    isRecordVideo.set(true);
                    videoRecordSurface.record(MiniVideoRecordActivity.this, listener.getOrientationHintDegrees());
//                    videoProgressView.startProgress(videoRecordSurface.mRecordMaxTime);
                }
            }

            @Override
            public void onLift() {

            }

            @Override
            public void onOver() {

            }
        });
    }

    private void initView() {
        mRecordButton = (RecordedButton) findViewById(R.id.mRecodButton);
        mRecordButton.setMax(VideoRecordSurface.mRecordMaxTime);
        frameLayout = (FrameLayout) findViewById(R.id.libVideoRecorder_fl);
        videoProgressView = (VideoProgressView) findViewById(R.id.libVideoRecorder_progress);
        btnSwitch = (Button) findViewById(R.id.btnSwitch);
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoRecordSurface.switchCamera();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoRecordSurface.stopRecord();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 录制结束
     */
    @Override
    public void onRecordFinish() {
        if (videoProgressView != null) {
            videoProgressView.stopProgress();
        }
        String recordThumbDir = videoRecordSurface.getRecordThumbDir(); //视频首张照片
        String recordMp4Dir = videoRecordSurface.getRecordDir(); //视频播放地址
        startActivity(new Intent(this,MiniVideoPlayActivity.class).putExtra(kVideoSavePath, recordMp4Dir));
    }

    @Override
    protected void onPause() {
        sm.unregisterListener(listener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onRecordProgress(float progress) {
        iTime = progress;
        mRecordButton.setProgress(progress);
    }


    /**
     * 开启录制
     *
     * @param videoPath 小视频录制后存储位置
     */
    public static Intent startRecordActivity(@NonNull String videoPath, AppCompatActivity activity) {
        Intent intent = new Intent(activity, MiniVideoRecordActivity.class);
        intent.putExtra(kVideoSavePath, videoPath);
        return intent;
    }

    /**
     * shut-off-the-sound-mediarecorder-plays-when-the-state-changes
     * Android 7(API 24级)中,我的应用不允许静音手机(将铃声模式设置为静音)
     */
    private void shutOffVolumn(){

//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        //勿扰权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
//            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//            startActivity(intent);
//        }
//
//
//        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//
//        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
//        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
//        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
//        audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, 0);
//        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
//        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
    }

}
