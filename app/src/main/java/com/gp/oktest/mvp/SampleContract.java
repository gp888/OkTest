package com.gp.oktest.mvp;


import android.content.Intent;

public interface SampleContract {

    /**
     * 操作需要什么
     * 操作的结果，对应的反馈
     * 操作过程中对应的交互
     */
    interface ISampleView extends IBaseView<Presenter>{

        Intent initIntent();

        void refreshSuccess();

        void refreshFailed();

        void loadMoreSuccess();

        void loadMoreFailed();

        void stopRefresh();

        void stopLoadMore();


    }

    interface Presenter extends IBasePresenter {
        void refreshView(String url);

        void loadMore(String url);
    }
}