package com.gp.oktest.ThreadSynchronization;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用阻塞队列实现线程同步 LinkedBlockingQueue的使用
 *
 * 在某些情况下会挂起线程（即阻塞），一旦条件满足，被挂起的线程又会自动被唤醒
 */
public class BlockingSynchronizedThread {
    /**
     * 定义一个阻塞队列用来存储生产出来的商品
     */
    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
    /**
     * 定义生产商品个数
     */
    private static final int size = 10;
    /**
     * 定义启动线程的标志，为0时，启动生产商品的线程；为1时，启动消费商品的线程
     */
    private int flag = 0;

    private class LinkBlockThread implements Runnable {
        @Override
        public void run() {
            int new_flag = flag++;

            System.out.println("启动线程 " + new_flag);
            if (new_flag == 0) {
                for (int i = 0; i < size; i++) {
                    int b = new Random().nextInt(255);
                    System.out.println("生产商品：" + b + "号");
                    try {
                        queue.put(b);//加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("仓库中还有商品：" + queue.size() + "个");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (int i = 0; i < size / 2; i++) {
                    try {
                        int n = queue.take();//取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入;
                        System.out.println("消费者买去了" + n + "号商品");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("仓库中还有商品：" + queue.size() + "个");
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingSynchronizedThread bst = new BlockingSynchronizedThread();
        LinkBlockThread lbt = bst.new LinkBlockThread();
        Thread thread1 = new Thread(lbt);
        Thread thread2 = new Thread(lbt);
        thread1.start();
        thread2.start();
    }
}
