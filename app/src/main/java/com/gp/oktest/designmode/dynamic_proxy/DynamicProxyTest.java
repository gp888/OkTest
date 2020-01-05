package com.gp.oktest.designmode.dynamic_proxy;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    public static void main(String[] args) {

        // 在工程目录下生成 $Proxy0 的 class 文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Buyer1 buyer1 = new Buyer1(); //创建目标对象的对象
        InvocationHandlerImpl invocationHandlerImpl1 = new InvocationHandlerImpl(buyer1); //创建调用处理类对象

        //（4）反射生成代理类对象
        ISubject buyer1Proxy = (ISubject) Proxy.newProxyInstance(buyer1.getClass().getClassLoader(), buyer1.getClass().getInterfaces(), invocationHandlerImpl1);

        //（5）通过代理类对象，调用目标对象的方法(会回调 InvocationHandlerImpl invoke())
        buyer1Proxy.buy();

        System.out.println("目标对象1：" + buyer1.getClass());
        System.out.println("代理对象1：" + buyer1Proxy.getClass());

        Buyer2 buyer2 = new Buyer2();
        InvocationHandlerImpl invocationHandlerImpl2 = new InvocationHandlerImpl(buyer2);
        ISubject buyer2Proxy = (ISubject) Proxy.newProxyInstance(buyer2.getClass().getClassLoader(), buyer2.getClass().getInterfaces(), invocationHandlerImpl2);
        buyer2Proxy.buy();

        System.out.println("目标对象2：" + buyer2.getClass());
        System.out.println("代理对象2：" + buyer2Proxy.getClass());

    }
}
