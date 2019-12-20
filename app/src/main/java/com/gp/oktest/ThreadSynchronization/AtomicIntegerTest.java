package com.gp.oktest.ThreadSynchronization;


public class AtomicIntegerTest {

    //main函数中启动100条线程并让他们启动
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i=0;i<100;i++){
            threads[i] = new AtomicIntegerThread();
            threads[i].start();
        }

        for (int j=0;j<100;j++){
            threads[j].join();
        }

        System.out.println("value:"+ AtomicIntegerThread.value + ",counter:" + AtomicIntegerThread.counter.getCount());
    }
}