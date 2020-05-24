package com.gp.oktest.mvp;

import android.content.Context;

/**
 * 不应该直接或者间接引用View层android.view.View的子类,甚至是操作的参数中也最好不要有android.view.View的子类传进来
 * 负责业务逻辑和数据的处理并通过统一的接口IView传递到View层
 */
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