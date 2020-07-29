package com.gp.oktest.jetpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

//1. 自定义的LifecycleObserver观察者，在对应方法上用注解声明想要观察的宿主的生命周期事件即可
class LocationObserver implements LifecycleObserver {
    //宿主执行了onstart时，会分发该事件
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(@NotNull LifecycleOwner owner){
        //开启定位
    }

    //宿主执行了onstop时 会分发该事件
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(@NotNull LifecycleOwner owner){
        //停止定位
    }
}

//2. 注册观察者,观察宿主生命周期状态变化
class MyFragment extends Fragment {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocationObserver observer = new LocationObserver();
        getLifecycle().addObserver(observer);
    }
}