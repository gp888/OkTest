package com.gp.oktest.designmode.dynamic_proxy;

// （3.2）实现目标对象类 2
public class Buyer2 implements ISubject {
    @Override
    public void buy() {
        System.out.println("buyer2 buy");
    }
}
