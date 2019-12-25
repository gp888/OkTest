package com.gp.oktest.ThreadSynchronization;

/**
 * 调用wait/notifyAll方法的时候一定要获得当前线程的锁，否则会发生IllegalMonitorStateException异常
 */
public class WaitNotifyAllTest {

    public synchronized void doWait() {
        System.out.println(Thread.currentThread().getName() + " run");
        System.out.println(Thread.currentThread().getName() + " wait for condition");
        try {
            this.wait();
            System.out.println(Thread.currentThread().getName() + " continue");
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void doNotify() {
        try {
            System.out.println(Thread.currentThread().getName() + " run");
            System.out.println(Thread.currentThread().getName() + " sleep 5 secs");
            Thread.sleep(5000l);
            this.notifyAll();
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        WaitNotifyAllTest waitNotifyAllTest = new WaitNotifyAllTest();
        Thread thread1 = new Thread(() -> waitNotifyAllTest.doWait());
        Thread thread2 = new Thread(() -> waitNotifyAllTest.doNotify());
        thread1.start();
        thread2.start();
    }

}
