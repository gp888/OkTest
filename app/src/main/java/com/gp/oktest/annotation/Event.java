package com.gp.oktest.annotation;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一是我们经常搞不清楚某一个事件是来自哪里的，因为任何地方都可以发送同一个事件；二是我们同样也经常搞不清楚某个事件会在哪些地方被接收，因为任何地方都可以注册并接收事件
 * EventBus 事件注解，用于标识某个方法是 EventBus 方法，并且注明事件的接收方或发送方。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Event {
    Class[] from() default {};
    Class[] to() default {};
}

class A {

    @Event(to = {C.class, D.class})
    public void sendEvent() {
        EventBus.getDefault().post(new MyEvent());
    }
}

class B {

    @Event(to = {C.class, D.class})
    public void sendEvent() {
        EventBus.getDefault().post(new MyEvent());
    }
}
class C {

    @Event(from = {A.class, B.class})
    public void onEventMainThread(MyEvent event) {
        // do something
    }
}

class D {

    @Event(from = {A.class, B.class})
    public void onEventMainThread(MyEvent event) {
        // do something
    }
}