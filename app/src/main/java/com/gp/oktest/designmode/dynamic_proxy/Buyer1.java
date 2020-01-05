package com.gp.oktest.designmode.dynamic_proxy;

// （3.1）实现目标对象类 1
public class Buyer1 implements ISubject {
    @Override
    public void buy() {
        System.out.println("buyer1 buy");
    }
}
