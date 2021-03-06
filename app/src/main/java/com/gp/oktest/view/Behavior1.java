package com.gp.oktest.view;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


/**
 * Created by dodo on 2016/10/31.
 * qq: 2390183798
 *
 * 可以跟着Main View 旋转， 对着
 */
public class Behavior1 extends CoordinatorLayout.Behavior<Button> {

    private Context mContext;

    private int width;
    private int height;

    public Behavior1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //如果dependency是TempView的实例，说明它就是我们所需要的Dependency
        return dependency instanceof MoveView;
    }

    //每次dependency位置发生变化，都会执行onDependentViewChanged方法
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button btn, View dependency) {

        //根据dependency的位置，设置Button的位置

        int top = dependency.getTop();
        int left = dependency.getLeft();

        int x = width - left - btn.getWidth();
        int y = height - top - btn.getHeight();

        setPosition(btn, x, y);
        return true;
    }

    private void setPosition(View v, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        layoutParams.width = y/2;
        v.setLayoutParams(layoutParams);
    }
}