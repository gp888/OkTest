package com.gp.oktest.eventbus.myeventbus;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


//@IntDef({ThreadMode.MAIN, ThreadMode.POST, ThreadMode.ASYNC})
//@Retention(RetentionPolicy.CLASS)
//public @interface ThreadMode {
//
//    int MAIN = 0x000;
//    int POST = 0x001;
//    int ASYNC = 0x002;
//}


public enum ThreadMode {
    MAIN, // 主线程
    POST, // 发送消息的线程
    ASYNC // 新开一个线程发送
}
