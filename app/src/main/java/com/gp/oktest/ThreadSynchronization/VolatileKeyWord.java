package com.gp.oktest.ThreadSynchronization;

/**
 * 使用volatile修饰域相当于告诉虚拟机该域可能会被其他线程更新，
 * 因此每次使用该域就要重新计算，而不是使用寄存器中的值，
 * volatile不会提供任何原子操作，它也不能用来修饰final类型的变量
 *
 *
 *
 * 多线程中的非同步问题主要出现在对域的读写上，如果让域自身避免这个问题，
 * 则就不需要修改操作该域的方法。用final域，
 * 有锁保护的域和volatile域可以避免非同步的问题
 */
public class VolatileKeyWord {

    //需要同步的变量加上volatile
    private volatile int account = 100;

    public int getAccount() {
        return account;
    }

    //这里不再需要synchronized
    public void save(int money) {
        account += money;
    }
}