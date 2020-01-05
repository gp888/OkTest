package com.gp.oktest.ThreadSynchronization;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch是一个计数器，它的构造方法中需要设置一个数值，
 * 用来设定计数的次数。每次调用countDown()方法之后，
 * 这个计数器都会减去1，CountDownLatch会一直阻塞着调用await()方法的线程，直到计数器的值变为0。
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for(int i = 0; i < 5; i ++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " " + new Date() + " run");
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all thread over");
    }
}