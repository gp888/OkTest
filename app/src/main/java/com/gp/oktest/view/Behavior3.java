package com.gp.oktest.view;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dodo  2390183798 on 2016/10/31.
 * 参考：  http://blog.csdn.net/qibin0506/article/details/50290421
 *
 * 对应的一起滑动的
 *      原理也简单， 是上下滑动， 就设置对应的y值为 Main View的y值
 *
 */
public class Behavior3 extends CoordinatorLayout.Behavior<View> {

    public Behavior3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 判断是否接收后续事件
     * 由于是竖直方向的滑动监听（直接true包含横向也行，后面不会获取对应的值）
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
//        return false;
    }

//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
//        int followScrolled = target.getScrollY();
//        child.setScrollY(followScrolled);
//    }

    //对应滑动的时候，处理的事情
    //当然，这里换成void onNestedPreScroll 效果是差不多的， 具体只是2个方法有先后顺序而已
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        int followScrolled = target.getScrollY();
        child.setScrollY(followScrolled);
    }


    //对应的滑动较快，也就是fling事件触发的时候调用
    //这里不能换成 onNestedPreFling，替换后，会有卡顿
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        if(child instanceof NestedScrollView){
            ((NestedScrollView) child).fling((int)velocityY);
        }
        return true;
    }
}
