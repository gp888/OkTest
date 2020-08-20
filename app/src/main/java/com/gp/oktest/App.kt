package com.gp.oktest

import android.app.Activity
import android.app.Application
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
        val TAG = App::class.simpleName
    }

    lateinit var mNetType: NetworkType;
    lateinit var netStateReceiver: NetStateReceiver;

    var count = 0 //记录Activity的总个数

    override fun onCreate() {
        super.onCreate()

        globalContext = this

        initNetChangeReceiver()

        //andfix
        AndFixManager.getAndFixManager().initAndFix(this)
        Stetho.initializeWithDefaults(this)

        //app前后台监控
        ForegroundCallbacks.init(this)
        ForegroundCallbacks.get().addListener(object : ForegroundCallbacks.Listener {
            override fun onBecameForeground() {
                Log.d(TAG, "当前程序切换到前台")
//                val intent = Intent(applicationContext, CheckGesPwdActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
            }

            override fun onBecameBackground() {
                Log.d(TAG, "当前程序切换到后台")
            }
        })
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

    fun register(){
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle) {
                Log.i(TAG, "onActivityCreated()")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.i(TAG, "onActivityStarted()")

                if (count == 0) { //后台切换到前台
                    Log.v(TAG, ">>>>>>>>>>>>>>>>>>>App切到前台");
                }
                count++;
            }

            override fun onActivityResumed(activity: Activity) {
                Log.i(TAG, "onActivityResumed()")
            }

            override fun onActivityPaused(activity: Activity) {
                Log.i(TAG, "onActivityPaused()")
//                在 onActivityPaused() 方法的时候 postDelayed() 一个 Runnable，大概延迟 500 毫秒，
//                然后在 onActivityResumed() 的时候，移除掉此 Runnable。
//                原理就是，如果正常的页面跳转，一个 Activity 走到了 onActivityPaused() ，应该马上就有另外一个 Activity 走到了 onActivityResume() ，
//                这是一个正常的页面跳转，反之，则认为是退出到后台了。

            }

            override fun onActivityStopped(activity: Activity) {
                Log.i(TAG, "onActivityStopped()")
                count--;
                if (count == 0) { //前台切换到后台
                    Log.v(TAG, ">>>>>>>>>>>>>>>>>>>App切到后台");
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
                Log.i(TAG, "onActivitySaveInstanceState()")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i(TAG, "onActivityDestroyed()")
            }
        })

        //推荐注册手机电源按键的监听一起使用，这样可以完美监听，手机的状态
        val screenStateFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        registerReceiver(object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
//                isBackground = true
//                notifyBackground()
            }
        }, screenStateFilter)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if(level == TRIM_MEMORY_UI_HIDDEN) {
            //app进入后台
        }
    }

    fun register2(){
        registerComponentCallbacks(object:ComponentCallbacks2{
            override fun onLowMemory() {
            }

            override fun onConfigurationChanged(newConfig: Configuration) {
            }

            //4.0
            override fun onTrimMemory(level: Int) {
                if(level == TRIM_MEMORY_UI_HIDDEN) {
                    //app进入后台

//                    TRIM_MEMORY_UI_HIDDEN：App 的所有 UI 界面被隐藏，最常见的就是 App 被 home 键或者 back 键，置换到后台了。
//                    TRIM_MEMORY_RUNNING_MODERATE：表示 App 正常运行，并且不会被杀掉，但是目前手机内存已经有点低了，系统可能会根据 LRU List 来开始杀进程。
//                    TRIM_MEMORY_RUNNING_LOW：表示 App正常运行，并且不会被杀掉。但是目前手机内存已经非常低了。
//                    TRIM_MEMORY_RUNNING_CRITICAL：表示 App 正在正常运行，但是系统已经开始根据 LRU List 的缓存规则杀掉了一部分缓存的进程。这个时候应该尽可能的释放掉不需要的内存资源，否者系统可能会继续杀掉其他缓存中的进程。
//                    TRIM_MEMORY_BACKGROUND：表示 App 退出到后台，并且已经处于 LRU List 比较靠后的位置，暂时前面还有一些其他的 App 进程，暂时不用担心被杀掉。
//                    TRIM_MENORY_MODERATE：表示 App 退出到后台，并且已经处于 LRU List 中间的位置，如果手机内存仍然不够的话，还是有被杀掉的风险的。
//                    TRIM_MEMORY_COMPLETE：表示 App 退出到后台，并且已经处于 LRU List 比较考靠前的位置，并且手机内存已经极低，随时都有可能被系统杀掉。

                }
            }

        })
    }
}