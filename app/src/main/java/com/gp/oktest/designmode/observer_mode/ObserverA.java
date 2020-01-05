package com.gp.oktest.designmode.observer_mode;

/**
 * 观察者模式：观察者 实现
 *
 */
public class ObserverA implements Observer {

    //myState需要跟目标对象的state值保持一致！
    private int myState;

    /**
     * 更新为和目标对象的值一致
     */
    @Override
    public void update(Subject subject) {
        myState = ((SubjectA)subject).getState();
    }

    public int getMyState() {
        return myState;
    }
    public void setMyState(int myState) {
        this.myState = myState;
    }
}