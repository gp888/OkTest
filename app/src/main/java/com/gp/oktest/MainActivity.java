package com.gp.oktest;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public final String TAG = getClass().getSimpleName();

    private String login = "gp888";
    Button v_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.toCountDownTimer).setOnClickListener(this);
        findViewById(R.id.toloadimage).setOnClickListener(this);
        findViewById(R.id.recycler).setOnClickListener(this);
        v_move = findViewById(R.id.v_move);
        v_move.setOnClickListener(this);
        findViewById(R.id.toPhotos).setOnClickListener(this);
        findViewById(R.id.rxpermission).setOnClickListener(this);
        findViewById(R.id.themeActivity).setOnClickListener(this);

        httpGithubString();
        httpGithubJson();
        httpGithubResponseBody();
        httpGithubRxjava();

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 100);
        } else {
            Toast.makeText(this, "permission allow", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
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
        Call<String> call = service.getData(login);//可以自己传入github的用户名

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
        Call<GitModel> call = service.getUserInfo(login);//可填入自己Github账号用户名
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
        Call<ResponseBody> call = service.getResponseBody(login);
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
        Observable<GitModel> obserable = service.rxUser(login);
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
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permission sucess", Toast.LENGTH_SHORT).show();
                //成功
            } else {
                // Permission Denied
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}