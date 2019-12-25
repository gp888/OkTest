package com.gp.oktest.ThreadSynchronization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * thread1调用Condition的await方法之后，thread1线程释放锁，
 * 然后马上加入到Condition的等待队列，由于thread1释放了锁，thread2获得锁并执行，
 * thread2执行signalAll方法之后，Condition中的等待队列thread1被取出并加入到AQS中，
 * 接下来thread2执行完毕之后释放锁，由于thread1已经在AQS的等待队列中，
 * 所以thread1被唤醒，继续执行
 *
 *
 * 传统线程的通信方式，Condition都可以实现。Condition的强大之处在于它可以为多个线程间建立不同的Condition。
 *
 * 注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
 */
public class ConditionTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " run");
                System.out.println(Thread.currentThread().getName() + " wait for condition");
                try {
                    condition.await();//当前线程会被挂起，直到thread2调用了condition.signalAll()方法之后，thread1才会重新被激活执行。
                    System.out.println(Thread.currentThread().getName() + " continue");
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                }
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " run");
                System.out.println(Thread.currentThread().getName() + " sleep 5 secs");
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                }
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        });
        thread1.start();
        thread2.start();
    }
}
