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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gp.oktest.adapter.BaseAdapter;
import com.gp.oktest.adapter.MainAdapter;
import com.gp.oktest.aidltest.AidlActivity;
import com.gp.oktest.androidmedia.H264EncodeActivity;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.camera.CameraPreviewActivity;
import com.gp.oktest.handlerthread.HandlerThreadActivity;
import com.gp.oktest.minivideo.MiniVideoActivity;
import com.gp.oktest.model.TypeBean;
import com.gp.oktest.mp4player.Mp4PlayActivity;
import com.gp.oktest.pcmtowav.PcmToWavActivity;
import com.gp.oktest.recordplaypcm.PcmRecordPlay;
import com.gp.oktest.service.ForegroundService1;
import com.gp.oktest.utils.DeviceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.format.DateUtils.FORMAT_24HOUR;
import static android.text.format.DateUtils.FORMAT_ABBREV_ALL;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;

public class MainActivity extends BaseActivity implements BaseAdapter.onRVItemClick {

    private static final int REQ_PERMISSION_CODE_FIND_LOCATION = 0X113;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<TypeBean> typeBeans = new ArrayList<>();

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        adapter = new MainAdapter(MainActivity.this, getData());
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new SuperDividerItemDecoration.Builder(this).build());
        recyclerView.setAdapter(adapter);



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

    private ArrayList<TypeBean> getData() {
        typeBeans.add(new TypeBean("CountDownTimer倒计时", 0));
        typeBeans.add(new TypeBean("LoadNetImage", 1));
        typeBeans.add(new TypeBean("RecyclerView", 2));
        typeBeans.add(new TypeBean("MoveView", 3));
        typeBeans.add(new TypeBean("PhotosActivity", 4));
        typeBeans.add(new TypeBean("RxPermissionActivity", 5));
        typeBeans.add(new TypeBean("ThemeBaseActivity", 6));
        typeBeans.add(new TypeBean("popmenu", 7));
        typeBeans.add(new TypeBean("PopupWindowActivity", 8));
        typeBeans.add(new TypeBean("SpannableActivity", 9));
        typeBeans.add(new TypeBean("tomap", 10));
        typeBeans.add(new TypeBean("AppCompatPopupWin", 11));
        typeBeans.add(new TypeBean("ServiceActivity", 12));
        typeBeans.add(new TypeBean("FileProvider7Activity", 13));
        typeBeans.add(new TypeBean("KeyBoardAvtivity", 14));
        typeBeans.add(new TypeBean("RetrofitActivity", 15));
        typeBeans.add(new TypeBean("CameraPreviewActivity", 16));
        typeBeans.add(new TypeBean("PcmRecordPlay", 17));
        typeBeans.add(new TypeBean("miniVideo", 18));
        typeBeans.add(new TypeBean("Aidl", 19));
        typeBeans.add(new TypeBean("handlerthead", 20));
        typeBeans.add(new TypeBean("StickMenuActivity", 21));
        typeBeans.add(new TypeBean("MiniVideoActivity", 22));
        typeBeans.add(new TypeBean("PcmToWavActivity", 23));
        typeBeans.add(new TypeBean("Mp4PlayActivity", 24));
        typeBeans.add(new TypeBean("AlipayHome", 25));
        return typeBeans;
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

    @Override
    public void onClick(View view, int position) {
        switch (typeBeans.get(position).getType()) {
            case 0:
                startActivity(new Intent(MainActivity.this, CountDownTimerActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, LoadNetImageActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, MoveViewActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this, view, "sharedView").toBundle());
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, PhotosActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, RxPermissionActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, ThemeBaseActivity.class));
                break;
            case 7:
                PopupMenu popupMenu = new PopupMenu(this, view);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popupMenu.setGravity(Gravity.RIGHT);
                }
                popupMenu.getMenuInflater().inflate(R.menu.menu_navigationview, popupMenu.getMenu());
                view.setOnTouchListener(popupMenu.getDragToOpenListener());
                popupMenu.show();
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, PopupWindowActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, SpannableActivity.class));
                break;
            case 10:
                //显示地图
//                Uri uri = Uri.parse("geo:38.899533,-77.036476");
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(it);

                //路径规划
                Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
            case 11:
                new AppCompatPopupWin(this).showPop();
                break;
            case 12:
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
//                DeviceUtils.testAndfix();
                break;
            case 13:
                startActivity(new Intent(this, FileProvider7Activity.class));
                break;
            case 14:
                startActivity(new Intent(this, KeyBoardAvtivity.class));
                break;
            case 15:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case 16:
                boolean isHasCamera = DeviceUtils.hasCameraDevice(MainActivity.this);
                Log.d(TAG,  isHasCamera+ "");

                int cameras = Camera.getNumberOfCameras();
                Log.d(TAG, cameras + "");
                startActivity(new Intent(this, CameraPreviewActivity.class));
                break;
            case 17:
                startActivity(new Intent(this, PcmRecordPlay.class));
                break;
            case 18:
                startActivity(new Intent(this, H264EncodeActivity.class));
                break;
            case 19:
                startActivity(new Intent(this, AidlActivity.class));
                startService(new Intent(this, ForegroundService1.class));
                break;
            case 20:
                startActivity(new Intent(this, HandlerThreadActivity.class));
                break;
            case 21:
                startActivity(new Intent(this, StickMenuActivity.class));
                break;
            case 22:
                startActivity(new Intent(this, MiniVideoActivity.class));
                break;
            case 23:
                startActivity(new Intent(this, PcmToWavActivity.class));
                break;
            case 24:
                startActivity(new Intent(this, Mp4PlayActivity.class));
                break;
            case 25:
                startActivity(new Intent(this, AlipayHome.class));
                break;
            default:
                break;
        }
    }
}
