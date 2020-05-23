package com.gp.oktest.mvp;

import android.content.Context;

public class SamplePresenter implements SampleContract.Presenter {

    private Context context;
    private SampleContract.ISampleView sampleView;


    public SamplePresenter(Context context, SampleContract.ISampleView sampleView) {
        this.context = context;
        this.sampleView = sampleView;
        sampleView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void refreshView(String url) {

    }

    @Override
    public void loadMore(String url) {

    }
}