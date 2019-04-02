package com.gp.oktest.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dodo on 2016/11/1.
 * qq: 2390183798
 *
 *
 * 在Main View 下方
 *      原理也简单， 只要是 Main View 为 MoveView，  就设置 一起动的View的Y值为 自己的Y值 + MainView的Height
 */
public class Behavior2 extends CoordinatorLayout.Behavior<View> {

    public Behavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY()+dependency.getHeight());
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof MoveView;
    }

}
