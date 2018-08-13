package com.gp.oktest.threadpool;


public class ThreadTaskObject implements Runnable {

    /**
     * 线程池类型
     */
    protected int iThreadPoolType;
    protected String taskName = null;

    public ThreadTaskObject(ThreadPoolTypeEnum threadPoolType, String threadTaskName) {
        initThreadTaskObject(threadPoolType, threadTaskName);
    }

    public ThreadTaskObject(ThreadPoolTypeEnum threadPoolType) {
        initThreadTaskObject(threadPoolType, this.toString());
    }

    /**
     * 在默认线程池中执行
     */
    public ThreadTaskObject() {
        initThreadTaskObject(ThreadPoolTypeEnum.THREAD_TYPE_WORK, this.toString());
    }

    /**
     * 初始化线程任务
     *
     * @param threadPoolType 线程池类型
     * @param threadTaskName 线程任务名称
     */
    private void initThreadTaskObject(ThreadPoolTypeEnum threadPoolType, String threadTaskName) {
        this.iThreadPoolType = threadPoolType.getValue();
        String name = ThreadPoolParams.getInstance(iThreadPoolType).name();
        if (threadTaskName != null) {
            name = name + "_" + threadTaskName;
        }
        setTaskName(name);
    }

    /**
     * 取得线程池类型
     */
    public int getThreadPoolType() {
        return iThreadPoolType;
    }

    /**
     * 开始任务
     */
    public void start() {
        ThreadPoolFactory.getThreadPoolManager().addTask(this);
    }

    /**
     * 取消任务
     */
    public void cancel() {
        ThreadPoolFactory.getThreadPoolManager().removeTask(this);
    }

    @Override
    public void run() {
        if (onRunListener != null) {
            onRunListener.onRun();
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public interface OnRunListener {
        void onRun();
    }

    OnRunListener onRunListener;

    public void setOnRunListener(OnRunListener onRunListener) {
        this.onRunListener = onRunListener;
    }
}
