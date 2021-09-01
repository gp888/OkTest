package com.gp.oktest.jetpack;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * //MVP中使用Lifecycle
 * getLifecycle().addObserver(new MyPresenter(this));
 */
class MyPresenter implements LifecycleObserver {
    private static final String TAG = "Lifecycle_Test";
    private final IView mView;

    public MyPresenter(IView view) { mView = view;}

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    private void getDataOnStart(LifecycleOwner owner){
        Log.i(TAG, "getDataOnStart: ");

//        Util.checkUserStatus(result -> {
//            //checkUserStatus是耗时操作，回调后检查当前生命周期状态
//            if (owner.getLifecycle().getCurrentState().isAtLeast(STARTED)) {
//                start();
//                mView.showView();
//            }
//        });
    }
    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    private void hideDataOnStop(){
        Log.i(TAG, "hideDataOnStop: ");
//        stop();
        mView.hideView();
    }
}

//IView
interface IView {
    void showView();
    void hideView();
}
