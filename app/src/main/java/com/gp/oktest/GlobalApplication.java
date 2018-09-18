package com.gp.oktest;

import android.app.Application;
import android.content.Context;
import com.gp.oktest.networklistener.NetChangeObserver;
import com.gp.oktest.networklistener.NetStateReceiver;
import com.gp.oktest.networklistener.NetworkType;
import com.gp.oktest.networklistener.NetworkUtil;
import com.gp.oktest.utils.ToastUtil;

/**
 * Created by guoping on 2017/11/15.
 */

public class GlobalApplication extends Application {

    public static Context globalContext;
    public NetworkType mNetType;
    public NetStateReceiver netStateReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = getApplicationContext();

        initNetChangeReceiver();

        //andfix
        AndFixManager.getAndFixManager().initAndFix(this);
    }

    /**
     * 应用全局的网络变化处理
     */
    private void initNetChangeReceiver() {
        //获取当前网络类型
        mNetType = NetworkUtil.getNetworkType(this);
        //定义网络状态的广播接受者
        netStateReceiver = NetStateReceiver.getReceiver();
        //给广播接受者注册一个观察者
        netStateReceiver.registerObserver(netChangeObserver);
        //注册网络变化广播
        NetworkUtil.registerNetStateReceiver(this, netStateReceiver);
    }

    private NetChangeObserver netChangeObserver = new NetChangeObserver() {
        @Override
        public void onConnect(NetworkType type) {
            if (type == mNetType) return; //net not change
            switch (type) {
                case NETWORK_WIFI:
                    ToastUtil.showToastLong("已切换到 WIFI 网络");
                    break;
                case NETWORK_MOBILE:
                    ToastUtil.showToastLong("已切换到 2G/3G/4G 网络");
                    break;
            }
            mNetType = type;
        }
        @Override
        public void onDisConnect() {
            ToastUtil.showToastShort("网络已断开,请检查网络设置");
            mNetType = NetworkType.NETWORK_NONE;
        }
    };

    //释放广播接受者(建议在 最后一个 Activity 退出前调用)
    public void destroyReceiver() {
        //移除里面的观察者
        netStateReceiver.removeObserver(netChangeObserver);
        //解注册广播接受者,
        NetworkUtil.unRegisterNetStateReceiver(this, netStateReceiver);
    }
}
