package com.gp.oktest.threadpool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolFactory {

    private static ExecutorService pool = null;

    public static IThreadPoolManager getThreadPoolManager() {
        return SingletonFactory.getInstance(ThreadPoolManager.class);
    }

    public static ExecutorService getFexThreadPoolManager() {
        InitThreadExecutor();
        return pool;
    }

    private static void InitThreadExecutor() {
        if (pool == null) {
            pool = Executors.newCachedThreadPool();
        }
    }
}
