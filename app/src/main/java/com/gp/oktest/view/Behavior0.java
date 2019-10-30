package com.gp.oktest.view;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.gp.oktest.R;


/**
 * Created by dodo on 2016/10/31.
 * qq: 2390183798
 *
 * 在y轴 方向跟着移动的 Behavior
 */
public class Behavior0 extends CoordinatorLayout.Behavior<Button> {

    private int targetId;

    public Behavior0(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if(a.getIndex(i) == R.styleable.Follow_target){
                targetId = a.getResourceId(attr, -1);
            }
        }
        a.recycle();
    }

    //是否关联

    /**
     * @param parent CoordinatorLayout
     * @param child 该Behavior对应的那个View
     * @param dependency 要检查的View
     * @return true 依赖, false 不依赖
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //如果dependency 是 MoveView类型， 就依赖
        //dependency.getId() == R.id.xxx
        return dependency instanceof MoveView;
    }

    //每次dependency位置发生变化，都会执行onDependentViewChanged方法
    //为true的时候可以设置对应 位置 和 大小
    //自己返回true， 则改变， 返回false， 自己不改变 位置和大小

    /**
     * @param parent CoordinatorLayout
     * @param btn 该Behavior对应的那个View
     * @param dependency child依赖dependency
     * @return true 处理了, false 没处理
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button btn, View dependency) {

        //根据dependency的位置，设置Button的位置
        int x = 0;
        int y = (int) dependency.getY();
        setPosition(btn, x, y);
        return true;
    }

    private void setPosition(View v, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        v.setLayoutParams(layoutParams);
    }

}