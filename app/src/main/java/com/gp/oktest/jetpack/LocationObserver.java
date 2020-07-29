package com.gp.oktest.jetpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

import static androidx.lifecycle.Lifecycle.Event.ON_CREATE;

//LifecycleObserver 配合注解
//1. 自定义的LifecycleObserver观察者，在对应方法上用注解声明想要观察的宿主的生命周期事件即可
class LocationObserver0 implements LifecycleObserver {
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

//FullLifecyclerObserver 拥有宿主所有生命周期事件
//需要监听那个事件，复写那个方法即可
//class LocationObserver1 extends FullLifecycleObserver{
//    void onStart(LifecycleOwner owner){}
//    void onStop(LifecycleOwner owner){}
//}


//宿主生命周期事件封装成 Lifecycle.Event
class LocationObserver2 implements LifecycleEventObserver {

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event){
        //需要自行判断life-event是onstart, 还是onstop
        if(event == ON_CREATE){

        }
    }
}

//2. 注册观察者,观察宿主生命周期状态变化
class MyFragment extends Fragment {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocationObserver2 observer = new LocationObserver2();
        getLifecycle().addObserver(observer);
    }
}

//LocationObserver0,1,2
//推荐第二、第三种，因为第一种你虽然用注解很爽，但是如果没有添加 lifecycle-compiler 这个注解处理器的话，
// 运行时会使用反射的形式回调到对应的方法上。