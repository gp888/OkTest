package com.gp.oktest.ThreadSynchronization;

public class ThreadJoin {


    public static void main(String[] args) {
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("线程"+Thread.currentThread().getId()+" 打印信息");
            }
        });
        thread.start();

        try {
            //线程中的run方法结束后，才往下执行
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("主线程打印信息");
    }
}