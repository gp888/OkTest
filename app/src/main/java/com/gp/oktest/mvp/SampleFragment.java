package com.gp.oktest.mvp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SampleFragment extends Fragment implements SampleContract.ISampleView{

    private SampleContract.Presenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SamplePresenter(getContext(), this);
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
        mPresenter = presenter;
    }
}
