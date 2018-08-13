package com.gp.oktest.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaseThreadPool extends ThreadPoolExecutor {
    public BaseThreadPool(ThreadPoolParams threadPoolParamter) {
        super(threadPoolParamter.getCorePoolSize(),
                threadPoolParamter.getMaximumPoolSize(),
                threadPoolParamter.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(threadPoolParamter.getPoolQueueSize()),
                new CallerRunsPolicy());
        this.allowCoreThreadTimeOut(threadPoolParamter.isAllowCoreThreadTimeOut());
    }
}
