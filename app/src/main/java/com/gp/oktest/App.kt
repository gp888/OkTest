package com.gp.oktest

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.gp.oktest.networklistener.NetChangeObserver
import com.gp.oktest.networklistener.NetStateReceiver
import com.gp.oktest.networklistener.NetworkType
import com.gp.oktest.networklistener.NetworkUtil
import com.gp.oktest.utils.ToastUtil

class App : MultiDexApplication() {

    companion object {
        lateinit var globalContext: Application
            private set
    }
    lateinit var mNetType: NetworkType;
    lateinit var netStateReceiver: NetStateReceiver;

    override fun onCreate() {
        super.onCreate()

        globalContext = this

        initNetChangeReceiver()

        //andfix
        AndFixManager.getAndFixManager().initAndFix(this)
        Stetho.initializeWithDefaults(this)
    }

    //权限判断
    val EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"

    fun hasExternalStoragePermission(context: Context) : Boolean{
        var perm = checkCallingOrSelfPermission (EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 应用全局的网络变化处理
     */
    private fun initNetChangeReceiver() {
        //获取当前网络类型
        mNetType = NetworkUtil.getNetworkType(this)
        //定义网络状态的广播接受者
        netStateReceiver = NetStateReceiver.getReceiver()
        //给广播接受者注册一个观察者
        netStateReceiver.registerObserver(netChangeObserver)
        //注册网络变化广播
        NetworkUtil.registerNetStateReceiver(this, netStateReceiver)
    }

    private val netChangeObserver = object : NetChangeObserver {
        override fun onConnect(type: NetworkType) {
            if (type == mNetType) return  //net not change
            when (type) {
                NetworkType.NETWORK_WIFI -> ToastUtil.showToastLong("已切换到 WIFI 网络")
                NetworkType.NETWORK_MOBILE -> ToastUtil.showToastLong("已切换到 2G/3G/4G 网络")
            }
            mNetType = type
        }

        override fun onDisConnect() {
            ToastUtil.showToastShort("网络已断开,请检查网络设置")
            mNetType = NetworkType.NETWORK_NONE
        }
    }

    //释放广播接受者(建议在 最后一个 Activity 退出前调用)
    fun destroyReceiver() {
        //移除里面的观察者
        netStateReceiver.removeObserver(netChangeObserver)
        //解注册广播接受者,
        NetworkUtil.unRegisterNetStateReceiver(this, netStateReceiver)
    }


    fun registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity : Activity, savedInstanceState : Bundle) {
            }

            override fun onActivityStarted(activity : Activity) {
            }

            override fun onActivitySaveInstanceState(activity : Activity, savedInstanceState : Bundle) {
            }

            override fun onActivityResumed(activity : Activity) {
            }

            override fun onActivityPaused(activity : Activity) {
            }

            override fun onActivityStopped(activity : Activity) {
            }

            override fun onActivityDestroyed(activity : Activity) {
            }
        })
    }


    class AppLifecycleOwner : LifecycleOwner {
        val registry : LifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return  registry
        }

        fun init(application : Application): Unit{
            //利用application的  ActivityLifecycleCallbacks 去监听每一个  Activity的onstart,onStop事件。
            //计算出可见的Activity数量，从而计算出当前处于前台还是后台。然后分发  给每个观察者
        }
    }

}