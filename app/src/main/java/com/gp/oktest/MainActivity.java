package com.gp.oktest;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gp.oktest.aidltest.AidlActivity;
import com.gp.oktest.androidmedia.H264EncodeActivity;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.camera.CameraPreviewActivity;
import com.gp.oktest.handlerthread.HandlerThreadActivity;
import com.gp.oktest.mp4player.Mp4PlayActivity;
import com.gp.oktest.recordplaypcm.PcmRecordPlay;
import com.gp.oktest.utils.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.format.DateUtils.FORMAT_24HOUR;
import static android.text.format.DateUtils.FORMAT_ABBREV_ALL;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;

public class MainActivity extends BaseActivity {

    private static final int REQ_PERMISSION_CODE_FIND_LOCATION = 0X113;

    @BindView(R.id.v_move)
    Button v_move;

    @BindView(R.id.toCountDownTimer)
    Button toCountDownTimer;

    @BindView(R.id.toloadimage)
    Button toloadimage;

    @BindView(R.id.recycler)
    Button recycler;

    @BindView(R.id.toPhotos)
    Button toPhotos;

    @BindView(R.id.rxpermission)
    Button rxpermission;

    @BindView(R.id.themeActivity)
    Button themeActivity;

    @BindView(R.id.popmenu)
    Button popmenu;

    @BindView(R.id.popupwindow)
    Button popupwindow;

    @BindView(R.id.span)
    Button span;

    @BindView(R.id.tomap)
    Button tomap;

    @BindView(R.id.popup)
    Button popup;

    @BindView(R.id.toService)
    Button fService;

    @BindView(R.id.fileprovider7)
    Button fileprovider7;

    @BindView(R.id.keyboard)
    Button keyboard;

    @BindView(R.id.pcm)
    Button pcm;

    @BindView(R.id.miniVideo)
    Button miniVideo;

    @BindView(R.id.mp4)
    Button mp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQ_PERMISSION_CODE_FIND_LOCATION);
        } else {
            Toast.makeText(this, "permission allow", Toast.LENGTH_SHORT).show();
        }

        //andfix
//        FileUtils.createProjectSdcardFile();
//        AndFixManager.getAndFixManager().addPatch(Constant.DIR_DOWNLOAD + File.separator + "mypatch1.apatch");

    }

    @OnClick({R.id.toCountDownTimer, R.id.toloadimage, R.id.recycler, R.id.v_move, R.id.toPhotos, R.id.rxpermission, R.id.themeActivity, R.id.popmenu,
            R.id.popupwindow, R.id.span, R.id.tomap, R.id.popup, R.id.toService, R.id.fileprovider7, R.id.handlerthead, R.id.keyboard, R.id.retrofit,
    R.id.camera, R.id.pcm, R.id.miniVideo,R.id.mp4})
    public void ViewOnClick(View v) {
        switch (v.getId()) {
            case R.id.toCountDownTimer:
                startActivity(new Intent(MainActivity.this, CountDownTimerActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.toloadimage:
                startActivity(new Intent(MainActivity.this, LoadNetImageActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.recycler:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.v_move:
                startActivity(new Intent(MainActivity.this, MoveViewActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, v_move, "sharedView").toBundle());
                break;
            case R.id.toPhotos:
                startActivity(new Intent(MainActivity.this, PhotosActivity.class));
                break;
            case R.id.rxpermission:
                startActivity(new Intent(MainActivity.this, RxPermissionActivity.class));
                break;
            case R.id.themeActivity:
                startActivity(new Intent(MainActivity.this, ThemeBaseActivity.class));
                break;
            case R.id.popmenu:
                View menu = findViewById(R.id.popmenu);
                PopupMenu popupMenu = new PopupMenu(this, menu);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popupMenu.setGravity(Gravity.RIGHT);
                }
                popupMenu.getMenuInflater().inflate(R.menu.menu_navigationview, popupMenu.getMenu());
                menu.setOnTouchListener(popupMenu.getDragToOpenListener());
                popupMenu.show();
                break;
            case R.id.popupwindow:
                startActivity(new Intent(MainActivity.this, PopupWindowActivity.class));
                break;
            case R.id.span:
                startActivity(new Intent(MainActivity.this, SpannableActivity.class));
                break;
            case R.id.tomap:
                //显示地图
//                Uri uri = Uri.parse("geo:38.899533,-77.036476");
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(it);

                //路径规划
                Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
            case R.id.popup:
                new AppCompatPopupWin(this).showPop();
                break;
            case R.id.toService:
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
//                DeviceUtils.testAndfix();
                break;
            case R.id.fileprovider7:
                startActivity(new Intent(this, FileProvider7Activity.class));
                break;
            case R.id.handlerthead:
                startActivity(new Intent(this, HandlerThreadActivity.class));
                break;
            case R.id.keyboard:
                startActivity(new Intent(this, KeyBoardAvtivity.class));
                break;
            case R.id.retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.camera:
                boolean isHasCamera = DeviceUtils.hasCameraDevice(MainActivity.this);
                Log.d(TAG,  isHasCamera+ "");

                int cameras = Camera.getNumberOfCameras();
                Log.d(TAG, cameras + "");
                startActivity(new Intent(this, CameraPreviewActivity.class));
                break;
            case R.id.pcm:
                startActivity(new Intent(this, PcmRecordPlay.class));
                break;
            case R.id.miniVideo:
//                startActivity(new Intent(this, MiniVideoActivity.class));
//                startActivity(new Intent(this, StickMenuActivity.class));
                startActivity(new Intent(this, H264EncodeActivity.class));
//                startActivity(new Intent(this, PcmToWavActivity.class));
                break;
            case R.id.mp4:
//                startActivity(new Intent(this, Mp4PlayActivity.class));
                startActivity(new Intent(this, AidlActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQ_PERMISSION_CODE_FIND_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permission sucess", Toast.LENGTH_SHORT).show();
            } else {
                // Permission Denied
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void initAnim() {
        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animate_test);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                pg.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(MainActivity.this, "动画开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(MainActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Toast.makeText(MainActivity.this, "动画取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Toast.makeText(MainActivity.this, "动画重复", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTimeStr() {
        String dataStr = DateUtils.formatDateTime(MainActivity.this, System.currentTimeMillis(),
                FORMAT_SHOW_YEAR | FORMAT_SHOW_DATE | FORMAT_SHOW_TIME | FORMAT_24HOUR | FORMAT_ABBREV_ALL);
        return dataStr;
    }
}
