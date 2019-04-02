package com.gp.oktest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;

//NestedScrollingChild ， NestedScrollingParent ， Behavior子类

/**
 * 实现NestedScrollingChild接口，获得事件，准备传递给 NestedScrollingParent
 * 实现NestedScrollingParent接口，获取传递的事件，消费或者传递给Behavior子类消费
 * 继承抽象类Behavior，获得事件，进行消费。完成对应View动作
 *
 */
public class CustomNestedScrollingChildView extends View implements NestedScrollingChild {
    private NestedScrollingChildHelper mChildHelper = new NestedScrollingChildHelper(this);

    public CustomNestedScrollingChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置当前View能否滑动
     * @param enabled
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }
    /**
     * 判断当前View能否滑动
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }
    /**
     * 启动嵌套滑动事件流
     * 1. 寻找可以接收 NestedScroll 事件的 parent view，即实现了 NestedScrollingParent 接口的 ViewGroup
     * 2. 通知该 parent view，现在我要把滑动的参数传递给你
     * @param axes
     * @return
     */
    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }
    /**
     * 停止嵌套滑动事件流
     */
    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }
    /**
     * 是否存在接收 NestedScroll 事件的 parent view
     * @return
     */
    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }
    /**
     * 在滑动之后，向父view汇报滚动情况，包括child view消费的部分和child view没有消费的部分。
     * @param dxConsumed x方向已消费的滑动距离
     * @param dyConsumed y方向已消费的滑动距离
     * @param dxUnconsumed x方向未消费的滑动距离
     * @param dyUnconsumed y方向未消费的滑动距离
     * @param offsetInWindow 如果parent view滑动导致child view的窗口发生了变化（child View的位置发生了变化）
     *                       该参数返回x(offsetInWindow[0]) y(offsetInWindow[1])方向的变化
     *                       如果你记录了手指最后的位置，需要根据参数offsetInWindow计算偏移量，
     *                       才能保证下一次的touch事件的计算是正确的。
     * @return 如果parent view接受了它的滚动参数，进行了部分消费，则这个函数返回true，否则为false。
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow);
    }
    /**
     * 在滑动之前，先问一下 parent view 是否需要滑动，
     * 即child view的onInterceptTouchEvent或onTouchEvent方法中调用。
     * 1. 如果parent view滑动了一定距离，你需要重新计算一下parent view滑动后剩下给你的滑动距离剩余量，
     *      然后自己进行剩余的滑动。
     * 2. 该方法的第三第四个参数返回parent view消费掉的滑动距离和child view的窗口偏移量，
     *      如果你记录了手指最后的位置，需要根据第四个参数offsetInWindow计算偏移量，
     *      才能保证下一次的touch事件的计算是正确的。
     * @param dx x方向的滑动距离
     * @param dy y方向的滑动距离
     * @param consumed 如果不是null, 则告诉child view现在parent view滑动的情况，
     *                 consumed[0]parent view告诉child view水平方向滑动的距离(dx)
     *                 consumed[1]parent view告诉child view垂直方向滑动的距离(dy)
     * @param offsetInWindow 可选 length=2 的数组，
     *                       如果parent view滑动导致child View的窗口发生了变化（子View的位置发生了变化）
     *                       该参数返回x(offsetInWindow[0]) y(offsetInWindow[1])方向的变化
     *                       如果你记录了手指最后的位置，需要根据参数offsetInWindow计算偏移量，
     *                       才能保证下一次的touch事件的计算是正确的。
     * @return 如果parent view对滑动距离进行了部分消费，则这个函数返回true，否则为false。
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }
    /**
     * 在嵌套滑动的child view快速滑动之后再调用该函数向parent view汇报快速滑动情况。
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @param consumed true 表示child view快速滑动了, false 表示child view没有快速滑动
     * @return true 表示parent view快速滑动了, false 表示parent view没有快速滑动
     */
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }
    /**
     * 在嵌套滑动的child view快速滑动之前告诉parent view快速滑动的情况。
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @return true 表示parent view快速滑动了, false 表示parent view没有快速滑动
     */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}