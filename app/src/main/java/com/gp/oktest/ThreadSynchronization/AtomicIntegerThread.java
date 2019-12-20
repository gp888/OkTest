package com.gp.oktest.ThreadSynchronization;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * atomicInteger测试
 */
public class AtomicIntegerThread extends Thread {

    public static AtomicInteger value = new AtomicInteger();
    public static FieldUpdaterCounter counter = new FieldUpdaterCounter();

    @Override
    public void run(){
        try {
            Thread.sleep((long) ((Math.random())*100));
            //原子自增
            value.incrementAndGet();
            counter.addCount();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
