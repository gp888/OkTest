package com.gp.oktest.jetpack;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.math.BigDecimal;

/**
 * liveData扩展使用
 *
 *  //获取StockLiveData单实例，添加观察者，更新UI
 *             StockLiveData.get(symbol).observe(getViewLifecycleOwner(), price -> {
 *                 // Update the UI.
 *             });
 */
public class StockLiveData extends LiveData<BigDecimal> {
    private static StockLiveData sInstance; //单实例
//    private StockManager stockManager;
//
//    private SimplePriceListener listener = new SimplePriceListener() {
//        @Override
//        public void onPriceChanged(BigDecimal price) {
//            setValue(price);//监听到股价变化 使用setValue(price) 告知所有活跃观察者
//        }
//    };

    //获取单例
    @MainThread
    public static StockLiveData get(String symbol) {
        if (sInstance == null) {
            sInstance = new StockLiveData(symbol);
        }
        return sInstance;
    }

    private StockLiveData(String symbol) {
//        stockManager = new StockManager(symbol);
    }

    //活跃的观察者（LifecycleOwner）数量从 0 变为 1 时调用
    @Override
    protected void onActive() {
//        stockManager.requestPriceUpdates(listener);//开始观察股价更新
    }

    //活跃的观察者（LifecycleOwner）数量从 1 变为 0 时调用。这不代表没有观察者了，可能是全都不活跃了。可以使用hasObservers()检查是否有观察者。
    @Override
    protected void onInactive() {
//        stockManager.removeUpdates(listener);//移除股价更新的观察
    }



/** liveData map数据修改
    //Integer类型的liveData1
    MutableLiveData<Integer> liveData1 = new MutableLiveData<>();
    //转换成String类型的liveDataMap
    LiveData<String> liveDataMap = Transformations.map(liveData1, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            String s = input + " + Transformations.map";
            Log.i("TAG", "apply: " + s);
            return s;
        }
    });
    liveDataMap.observe(this, new Observer<String>() {

        @Override
        public void onChanged(String s) {
            Log.i("TAG", "onChanged1: "+s);
        }
    });

    liveData1.setValue(100);

 **/


/** liveData switchMap数据切换

//两个liveData，由liveDataSwitch决定 返回哪个livaData数据
MutableLiveData<String> liveData3 = new MutableLiveData<>();
    MutableLiveData<String> liveData4 = new MutableLiveData<>();

    //切换条件LiveData，liveDataSwitch的value 是切换条件
    MutableLiveData<Boolean> liveDataSwitch = new MutableLiveData<>();

    //liveDataSwitchMap由switchMap()方法生成，用于添加观察者
    LiveData<String> liveDataSwitchMap = Transformations.switchMap(liveDataSwitch, new Function<Boolean, LiveData<String>>() {
        @Override
        public LiveData<String> apply(Boolean input) {
            //这里是具体切换逻辑：根据liveDataSwitch的value返回哪个liveData
            if (input) {
                return liveData3;
            }
            return liveData4;
        }
    });

        liveDataSwitchMap.observe(this, new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Log.i("TAG", "onChanged2: " + s);
        }
    });

    boolean switchValue = true;
        liveDataSwitch.setValue(switchValue);//设置切换条件值

        liveData3.setValue("liveData3");
        liveData4.setValue("liveData4");

 **/

/**  liveData mediator 观察多个数据
 *
    MediatorLiveData<String> mediatorLiveData = new MediatorLiveData<>();

    MutableLiveData<String> liveData5 = new MutableLiveData<>();
    MutableLiveData<String> liveData6 = new MutableLiveData<>();

    //添加 源 LiveData
        mediatorLiveData.addSource(liveData5, new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Log.i(TAG, "onChanged3: " + s);
            mediatorLiveData.setValue(s);
        }
    });
    //添加 源 LiveData
        mediatorLiveData.addSource(liveData6, new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Log.i(TAG, "onChanged4: " + s);
            mediatorLiveData.setValue(s);
        }
    });

    //添加观察
        mediatorLiveData.observe(this, new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Log.i(TAG, "onChanged5: "+s);
            //无论liveData5、liveData6更新，都可以接收到
        }
    });

        liveData5.setValue("liveData5");
    //liveData6.setValue("liveData6");

 **/

}
