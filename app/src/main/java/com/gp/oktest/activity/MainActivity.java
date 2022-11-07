package com.gp.oktest.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.gp.oktest.AppCompatPopupWin;
import com.gp.oktest.GlideApp;
import com.gp.oktest.R;
import com.gp.oktest.ZoomImageView.ImageEntranceActivity;
import com.gp.oktest.adapter.BaseAdapter;
import com.gp.oktest.adapter.MainAdapter;
import com.gp.oktest.aidltest.AidlActivity;
import com.gp.oktest.androidmedia.H264EncodeActivity;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.camera.CameraPreviewActivity;
import com.gp.oktest.database.SqliteActivity;
import com.gp.oktest.handlerthread.HandlerThreadActivity;
import com.gp.oktest.hencoder.HencoderActivity;
import com.gp.oktest.longconnect.LongConnectActivity;
import com.gp.oktest.mediacodec.EGLPlayerActivity;
import com.gp.oktest.mediacodec.MultiOpenGLPlayerActivity;
import com.gp.oktest.mediacodec.OpenGLPlayerActivity;
import com.gp.oktest.mediacodec.SimplePlayerActivity;
import com.gp.oktest.mediacodec.SimpleRenderActivity;
import com.gp.oktest.mediacodec.SoulPlayerActivity;
import com.gp.oktest.mediacodec.SynthesizerActivity;
import com.gp.oktest.minivideo.MiniVideoActivity;
import com.gp.oktest.model.TypeBean;
import com.gp.oktest.mp4player.Mp4PlayActivity;
import com.gp.oktest.opengl.CameraRecordActivity;
import com.gp.oktest.pcmtowav.PcmToWavActivity;
import com.gp.oktest.receiver.SendSMSActivity;
import com.gp.oktest.recordplaypcm.PcmRecordPlay;
import com.gp.oktest.searchview.SearchviewActivity;
import com.gp.oktest.service.ForegroundService1;
import com.gp.oktest.utils.DeviceUtils;
import com.gp.oktest.view.FlowLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.internal.FastBlur;

import static android.text.format.DateUtils.FORMAT_24HOUR;
import static android.text.format.DateUtils.FORMAT_ABBREV_ALL;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQ_PERMISSION_CODE_FIND_LOCATION = 0X113;

    private ArrayList<TypeBean> typeBeans = new ArrayList<>();

    MainAdapter adapter;
    boolean isAudioFocus;
    @BindView(R.id.root)
    ConstraintLayout root;
    @BindView(R.id.flowlayout)
    FlowLayout flowLayout;

    @BindView(R.id.action)
    TextView action;
    @BindView(R.id.hello)
    TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        adapter = new MainAdapter(MainActivity.this, getData());
//        adapter.setClickListener(this);
//        recyclerView.addItemDecoration(new SuperDividerItemDecoration.Builder(this).build());

        List<TypeBean> views = getData();
        for(TypeBean bean : views) {
            TextView tv = new TextView(this);
            tv.setText(bean.getTitle());
            tv.setTag(bean.getType());
            tv.setOnClickListener(this);
            tv.setBackground(getDrawable(R.drawable.selector_main_text));
            flowLayout.addView(tv);
        }


        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQ_PERMISSION_CODE_FIND_LOCATION);
        } else {
            Toast.makeText(this, "permission allow", Toast.LENGTH_SHORT).show();
        }

        //andfix
//        FileUtils.createProjectSdcardFile();
//        AndFixManager.getAndFixManager().addPatch(Constant.DIR_DOWNLOAD + File.separator + "mypatch1.apatch");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        exeCommand("ps");//ps, ps | grep com.gp.oktest




        action.setText(Html.fromHtml("<a href='treasure9aefcdebb262d0952e92bc615d2d70e3://treasure.com'>CLICK THIS NODATA</a>"));
        //激活链接
        action.setMovementMethod(LinkMovementMethod.getInstance());

        action.setTextColor(getColor(R.color.blue));
//        action.setText(ffmpegInfo());

        String am = TimeUtils.isAm() ? "上午好" : "下午好";
        hello.setText(TimeUtils.getNowDate() + "， " + am);
        BarUtils.isStatusBarLightMode(this);
        BarUtils.setStatusBarLightMode(this, true);
    }

    /**
     * 进程信息
     * @param command
     */
    private void exeCommand(String command){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("ps--" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<TypeBean> getData() {
        typeBeans.add(new TypeBean("CountDownTimer", 0));
        typeBeans.add(new TypeBean("LoadNetImage", 1));
        typeBeans.add(new TypeBean("RecyclerView", 2));
        typeBeans.add(new TypeBean("MoveViewActivity", 3));
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
        typeBeans.add(new TypeBean("H264Encode", 18));
        typeBeans.add(new TypeBean("Aidl", 19));
        typeBeans.add(new TypeBean("handlerthead", 20));
        typeBeans.add(new TypeBean("StickMenuActivity", 21));
        typeBeans.add(new TypeBean("MiniVideoActivity", 22));
        typeBeans.add(new TypeBean("PcmToWavActivity", 23));
        typeBeans.add(new TypeBean("Mp4PlayActivity", 24));
        typeBeans.add(new TypeBean("AlipayHomeActivity", 25));
        typeBeans.add(new TypeBean("BitmapOptions", 26));
        typeBeans.add(new TypeBean("DrawableButtonAndOverlayActivity", 27));
        typeBeans.add(new TypeBean("ExpandListView", 28));
        typeBeans.add(new TypeBean("sendsms", 29));
        typeBeans.add(new TypeBean("Sqlite", 30));
        typeBeans.add(new TypeBean("longconnect", 31));
        typeBeans.add(new TypeBean("Gesture", 32));
        typeBeans.add(new TypeBean("Coordinate", 33));
        typeBeans.add(new TypeBean("FlutterActivity", 34));
        typeBeans.add(new TypeBean("ZoomImageActivity", 35));
        typeBeans.add(new TypeBean("RecyclerViewPager", 36));
        typeBeans.add(new TypeBean("gridviewTest", 37));
        typeBeans.add(new TypeBean("FlowLayout", 38));
        typeBeans.add(new TypeBean("Animator", 39));
        typeBeans.add(new TypeBean("Hencoder", 40));
        typeBeans.add(new TypeBean("cameraPreview", 41));
        typeBeans.add(new TypeBean("scalableImageView", 42));
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
        valueAnimator.start();
    }

    private String getTimeStr() {
        String dataStr = DateUtils.formatDateTime(MainActivity.this, System.currentTimeMillis(),
                FORMAT_SHOW_YEAR | FORMAT_SHOW_DATE | FORMAT_SHOW_TIME | FORMAT_24HOUR | FORMAT_ABBREV_ALL);
        return dataStr;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取view高度，位置等

        int[] location = new int[2];
        flowLayout.getLocationInWindow(location);
        int x = location[0]; // view距离window 左边的距离（即x轴方向）
        int y = location[1]; // view距离window 顶边的距离（即y轴方向）



        int[] location1 = new int[2];
        flowLayout.getLocationOnScreen(location1);
        int x1 = location[0]; // view距离 屏幕左边的距离（即x轴方向）
        int y1 = location[1]; // view距离 屏幕顶边的距离（即y轴方向）
    }


    @Override
    public void onClick(View v) {
        switch ((int)v.getTag()) {
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
                        ActivityOptions.makeSceneTransitionAnimation(this, v, "sharedView").toBundle());
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
                PopupMenu popupMenu = new PopupMenu(this, v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popupMenu.setGravity(Gravity.RIGHT);
                }
                popupMenu.getMenuInflater().inflate(R.menu.menu_navigationview, popupMenu.getMenu());
                v.setOnTouchListener(popupMenu.getDragToOpenListener());
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
                Log.d(TAG, isHasCamera + "");

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
                startActivity(new Intent(this, AlipayHomeActivity.class));
                break;
            case 26:
                startActivity(new Intent(this, BitmapOptionsActivity.class));
                break;
            case 27:
                startActivity(new Intent(this, DrawableButtonAndOverlayActivity.class));
                break;
            case 28:
                startActivity(new Intent(this, ExpandableListActivity.class));
                break;
            case 29:
                startActivity(new Intent(this, SendSMSActivity.class));
                break;
            case 30:
                startActivity(new Intent(this, SqliteActivity.class));
                break;
            case 31:
                startActivity(new Intent(this, LongConnectActivity.class));
                break;
            case 32:
                startActivity(new Intent(this, GestureActivity.class));
                break;
            case 33:
                startActivity(new Intent(this, CoordinateActivity.class));
                break;
            case 34:
//                View flutterView = Flutter.createView(
//                        MainActivity.this,
//                        getLifecycle(),
//                        "route1"
//                );
//                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layout.gravity = Gravity.CENTER;
//                addContentView(flutterView, layout);

                //flutter fragment
//                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
//                tx.replace(R.id.root, Flutter.createFragment("route1"));
//                tx.commit();

//                startActivity(new Intent(this, MyFlutterActivity.class));

//                if(isAudioFocus){
//                    stopService(new Intent(MainActivity.this,MusicService.class));
//                    isAudioFocus = false;
//                } else {
//                    startService(new Intent(MainActivity.this, MusicService.class));
//                    isAudioFocus = true;
//                }
                break;
            case 35:
                startActivity(new Intent(this, ImageEntranceActivity.class));
                break;
            case 36:
                startActivity(new Intent(this, RecyclerViewPagerActivity.class));
                overridePendingTransition(R.anim.activity_in, 0);

//                EveryDaySignInDialog dialog = new EveryDaySignInDialog(this);
//                dialog.show();
                break;

            case 37:
                startActivity(new Intent(this, SearchviewActivity.class));
                break;
            case 38:
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;

            case 39:
                startActivity(new Intent(this, AnimatorTestActivity.class));
                break;
            case 40:
                startActivity(new Intent(this, HencoderActivity.class));
                break;
            case 41:
                startActivity(new Intent(this, CameraRecordActivity.class));
                break;
            case 42:
//                startActivity(new Intent(this, ScalableImageActivity.class));
//                startActivity(new Intent(this, SimplePlayerActivity.class));
//                startActivity(new Intent(this, SimpleRenderActivity.class));
//                startActivity(new Intent(this, OpenGLPlayerActivity.class));
//                startActivity(new Intent(this, MultiOpenGLPlayerActivity.class));
//                startActivity(new Intent(this, PicMatrix.class));
//                startActivity(new Intent(this, EGLPlayerActivity.class));
//                startActivity(new Intent(this, SoulPlayerActivity.class));
//                startActivity(new Intent(this, SynthesizerActivity.class));
                startActivity(new Intent(this, BarActivity.class));
                break;
            default:
                break;
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference reference;
        public MyHandler(Activity context) {
            reference = new WeakReference<Activity>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) reference.get();
            if(activity != null){
                //处理message
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHandler.removeCallbacksAndMessages(null);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //activity在栈顶，触摸点击，按home,back,menu键会触发此方法
        Log.d(TAG, "===onUserInteraction===");

//        GlideApp.with(context)
//                .load(resouce)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//缓存
//
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 1)))
//
//                .transition(DrawableTransitionOptions.withCrossFade())//淡入淡出
//
//                .listener(new LoadListen())
//                .into(imageView);

    }

    //图片加载监听器
    class LoadListen implements RequestListener<Drawable> {


        @Override

        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {

            return false;

        }


        @Override

        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

            return false;

        }

    }

    static {
        System.loadLibrary("native-lib");
    }

    public native String ffmpegInfo();
    public native void set(String str);

}
