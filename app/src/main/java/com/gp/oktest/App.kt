package com.gp.oktest

import android.app.Application
import com.facebook.stetho.Stetho
import com.gp.oktest.networklistener.NetChangeObserver
import com.gp.oktest.networklistener.NetStateReceiver
import com.gp.oktest.networklistener.NetworkType
import com.gp.oktest.networklistener.NetworkUtil
import com.gp.oktest.utils.ToastUtil

class App : Application() {

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
}