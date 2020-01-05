package com.gp.oktest.ThreadSynchronization;

import java.text.SimpleDateFormat;
import java.util.Date;


//把变量放到线程本地的方式来实现线程同步的。比如：SimpleDateFormat不是一个线程安全的类，可以使用ThreadLocal实现同步
public class ThreadLocalTest {

    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                System.out.println(dateFormatThreadLocal.get().format(date));
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                System.out.println(dateFormatThreadLocal.get().format(date));
            }
        });
        thread1.start();
        thread2.start();
    }
}