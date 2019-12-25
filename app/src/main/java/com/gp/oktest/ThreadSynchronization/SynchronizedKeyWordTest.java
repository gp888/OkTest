package com.gp.oktest.ThreadSynchronization;

public class SynchronizedKeyWordTest {

    /**
     * 内置锁，可重入锁
     */
    public synchronized void execute() {
        System.out.println(Thread.currentThread().getName() + " do something synchronize");
        try {
            anotherLock();
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void anotherLock() {
        System.out.println(Thread.currentThread().getName() + " invoke anotherLock");
    }

    public static void main(String[] args) {
        SynchronizedKeyWordTest reentrantLockTest = new SynchronizedKeyWordTest();
        Thread thread1 = new Thread(() -> reentrantLockTest.execute());
        Thread thread2 = new Thread(() -> reentrantLockTest.execute());
        thread1.start();
        thread2.start();
    }

}
