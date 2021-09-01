package com.gp.oktest.jetpack

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*

//自定义 LifecycleOwner 的场景
class AppLifecycleOwner : LifecycleOwner {
    var count = 0;
    val registry : LifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle() : Lifecycle {
        return  registry
    }

    fun init(application : Application){
        //利用application的  ActivityLifecycleCallbacks 去监听每一个  Activity的onstart,onStop事件。
        //计算出可见的Activity数量，从而计算出当前处于前台还是后台。然后分发  给每个观察者
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityStarted(activity: Activity) {
                if(count == 0) {
//                    dispatch()
                }
                count++;
            }

            override fun onActivityDestroyed(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                TODO("Not yet implemented")
            }

            override fun onActivityStopped(activity: Activity) {
                count--;
                if(count == 0){
//                    dispatch()
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onActivityResumed(activity: Activity) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun dispatch(event: Lifecycle.Event) {
        if (lifecycle is LifecycleRegistry) {
            (lifecycle as LifecycleRegistry).handleLifecycleEvent(event)
        }
    }

    enum class MyEvent {
        ON_FRONT,
        ON_BACKGROUND
    }
}