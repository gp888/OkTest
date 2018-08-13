package com.gp.oktest.threadpool;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;


public class ThreadPoolManager implements IThreadPoolManager {
    /**
     * 不同类型的线程池，可以同时管理多个线程池
     */
    @SuppressLint("UseSparseArrays")
    private final Map<Integer, BaseThreadPool> threadPoolMap = new HashMap<Integer, BaseThreadPool>();

    @Override
    public void addTask(ThreadTaskObject task) {
        if (task != null) {
            BaseThreadPool threadPool = null;
            synchronized (threadPoolMap) {
                threadPool = threadPoolMap.get(task.getThreadPoolType());
                //指定类型的线程池不存在则创建一个新的
                if (threadPool == null) {
                    threadPool = new BaseThreadPool(ThreadPoolParams.getInstance(task.getThreadPoolType()));
                    threadPoolMap.put(task.getThreadPoolType(), threadPool);
                }
            }
            threadPool.execute(task);
        }
    }

    @Override
    public BaseThreadPool getThreadPool(int threadPoolType) {
        BaseThreadPool threadPool = null;
        synchronized (threadPoolMap) {
            threadPool = threadPoolMap.get(threadPoolType);
            //指定类型的线程池不存在则创建一个新的
            if (threadPool == null) {
                threadPool = new BaseThreadPool(ThreadPoolParams.getInstance(threadPoolType));
            }
        }

        return threadPool;
    }

    @Override
    public boolean removeTask(ThreadTaskObject task) {
        BaseThreadPool threadPool = threadPoolMap.get(task.getThreadPoolType());

        if (threadPool != null) {
            return threadPool.remove(task);
        }

        return false;
    }

    @Override
    public void stopAllTask() {
        if (threadPoolMap != null) {
            for (Integer key : threadPoolMap.keySet()) {
                BaseThreadPool threadPool = threadPoolMap.get(key);

                if (threadPool != null) {
                    threadPool.shutdownNow();//试图停止所有正在执行的线程，不再处理还在池队列中等待的任务
                }
            }

            threadPoolMap.clear();
        }
    }
}
