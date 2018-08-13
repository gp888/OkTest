package com.gp.oktest.threadpool;

public interface IThreadPoolManager {
	/**
	 * 往线程池中增加一个线程任务
	 * @param task 线程任务
	 */
	void addTask(ThreadTaskObject task);

	/**
	 * 
	 * @description:获取指定类型的线程池，如果不存在则创建
	 * @param @param ThreadPoolType
	 * @return BaseThreadPool
	 * @throws
	 */
	BaseThreadPool getThreadPool(int threadPoolType);
	
	/**
	 * 从线程队列中移除一个线程任务
	 * @param task 线程任务
	 * @return 是否移除成功
	 */
	boolean removeTask(ThreadTaskObject task);
	
	/**
	 * 停止所有任务
	 */
	void stopAllTask();
}
