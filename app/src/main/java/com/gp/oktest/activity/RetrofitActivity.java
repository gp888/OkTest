package com.gp.oktest.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gp.oktest.GitHubService;
import com.gp.oktest.GitModel;
import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.utils.SoftKeyBroadManager;
import com.gp.oktest.utils.ToastUtil;

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

public class RetrofitActivity extends BaseActivity{

    private String loginName = "gp888";

    private SoftKeyBroadManager.SoftKeyboardStateListener softKeyboardStateListener = new
            SoftKeyBroadManager.SoftKeyboardStateListener() {

                @Override
                public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                    ToastUtil.showToastShort("键盘打开" + keyboardHeightInPx);
                }

                @Override
                public void onSoftKeyboardClosed() {
                    ToastUtil.showToastShort("键盘关闭");
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        httpGithubString();
        httpGithubJson();
        httpGithubResponseBody();
        httpGithubRxjava();


        ConstraintLayout root = findViewById(R.id.root);
        SoftKeyBroadManager softKeyBroadManager = new SoftKeyBroadManager(root);
        softKeyBroadManager.addSoftKeyboardStateListener(softKeyboardStateListener);
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
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(GitModel gitModel) {
                        Log.d(TAG, "onNext: " + gitModel.getLogin());
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }
                });
    }
}
