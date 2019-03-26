package com.gp.oktest.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MoveView extends TextView {

    private int lastX;
    private int lastY;
//    private int mWidth;
//    private int mHeight;

    public MoveView(Context context, AttributeSet attrs) {
        super(context, attrs);

//        mWidth = getMeasuredWidth();
//        mHeight = getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();
                int left = layoutParams.leftMargin + x - lastX;
                int top = layoutParams.topMargin + y - lastY;

                layoutParams.leftMargin = left;
                layoutParams.topMargin = top;
                setLayoutParams(layoutParams);
                requestLayout();
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

}
