package com.gp.oktest;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.handlerthread.HandlerThreadActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    //传入github的用户名
    private String loginName = "gp888";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        httpGithubString();
        httpGithubJson();
        httpGithubResponseBody();
        httpGithubRxjava();

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
            R.id.popupwindow, R.id.span, R.id.tomap, R.id.popup, R.id.toService, R.id.fileprovider7, R.id.handlerthead})
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
                popupMenu.setGravity(Gravity.RIGHT);
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
            default:
                break;
        }
    }

    private void httpGithubString() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASEURL)
                //添加String支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<String> call = service.getData(loginName);

        // 异步请求
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // 处理返回数据
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求数据失败");
            }
        });
    }

    private void httpGithubJson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASEURL)
                // 添加Json转换器支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<GitModel> call = service.getUserInfo(loginName);
        call.enqueue(new Callback<GitModel>() {

            @Override
            public void onResponse(Call<GitModel> call, Response<GitModel> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getLogin());
                }
            }

            @Override
            public void onFailure(Call<GitModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void httpGithubResponseBody() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASEURL)
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> call = service.getResponseBody(loginName);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Log.d(TAG, "onResponse: "+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void httpGithubRxjava () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Observable<GitModel> obserable = service.rxUser(loginName);
        obserable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitModel>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(GitModel gitModel) {
                        Log.d(TAG, "onNext: " + gitModel.getLogin());
                    }
                });
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
}
