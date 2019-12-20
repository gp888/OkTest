package com.gp.oktest.javatest;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class FieldUpdaterCounter {
    private volatile  int count;

    public int getCount() {
        return count;
    }

    public void addCount(){
        AtomicIntegerFieldUpdater<FieldUpdaterCounter> updater
                = AtomicIntegerFieldUpdater.newUpdater(FieldUpdaterCounter.class,"count");
        updater.getAndIncrement(this);
    }
}