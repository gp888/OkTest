package com.gp.oktest.mvp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gp.oktest.R;

public class SampleActivity extends AppCompatActivity implements SampleContract.ISampleView {

    private SampleContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SamplePresenter mPresenter = new SamplePresenter(this, this);

        initView();
        mPresenter.start();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }


    @Override
    public Intent initIntent() {
        return null;
    }

    @Override
    public void refreshSuccess() {

    }

    @Override
    public void refreshFailed() {

    }

    @Override
    public void loadMoreSuccess() {

    }

    @Override
    public void loadMoreFailed() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void stopLoadMore() {

    }

    @Override
    public void setPresenter(SampleContract.Presenter presenter) {

    }
}
