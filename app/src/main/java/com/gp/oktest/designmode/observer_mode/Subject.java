package com.gp.oktest.designmode.observer_mode;

import java.util.ArrayList;
import java.util.List;

/**
 *抽象被观察者
 * 观察者模式：目标对象模板
 *
 */
public interface Subject<T> {
    // 存储订阅者
    List<Observer> list = new ArrayList<>();
    // 注册订阅者
    public void registerObserver(T obs);
    // 移除订阅者
    public void removeObserver(T obs);
    //通知所有的观察者更新状态
    public void notifyAllObservers();
}