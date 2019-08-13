package com.gp.oktest.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class CreateHandler {
//UI线程已经初始化Looper，所以不需要再次初始化，而其他线程并没有初始化，所以需要初始化。


    //UI线程
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

//非UI线程
    class LooperThread extends Thread{
        @Override
        public void run() {
            //1.初始化Looper
            Looper.prepare();
            //2.创建Handler实例
            new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    return false;
                }
            });
            //3.Looper循环
            Looper.loop();
        }
    }
}
