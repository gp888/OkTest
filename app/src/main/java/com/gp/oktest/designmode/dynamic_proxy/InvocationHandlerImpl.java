package com.gp.oktest.designmode.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// （2）声明调用处理类（实现 InvocationHandler 接口）
public class InvocationHandlerImpl implements InvocationHandler {
    private Object mRealObject;

    public InvocationHandlerImpl(Object realObject) {
        mRealObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy invoke, proxy = " + proxy.getClass() + ", realObject = " + mRealObject.getClass());
        Object result = method.invoke(mRealObject, args);
        return result;
    }
}

