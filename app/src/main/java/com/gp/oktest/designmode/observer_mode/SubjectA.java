package com.gp.oktest.designmode.observer_mode;


/**
 * 观察者模式:目标对象(主体对象 topic)
 *
 */
public class SubjectA implements Subject<Observer> {


    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        //主题对象(目标对象)值发生了变化，请通知所有的观察者
        this.notifyAllObservers();
    }

    @Override
    public void notifyAllObservers() {
        for (Observer obs : list) {
            // 更新每一个观察者中的信息
            obs.update(this);
        }
    }

    @Override
    public void registerObserver(Observer obs) {
        list.add(obs);
    }


    @Override
    public void removeObserver(Observer obs) {
        list.remove(obs);
    }
}
