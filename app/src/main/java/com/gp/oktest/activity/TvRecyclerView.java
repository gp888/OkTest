package com.gp.oktest.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class TvRecyclerView extends RecyclerView {

    private static final String TAG = "TvRecyclerView";
    private int mPosition;
    private Fragment mBindFragment;

    public TvRecyclerView(Context context) {
        this(context, null);
    }
    public TvRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }
    public TvRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
    private void init(Context context, AttributeSet attrs, int defStyle) {
        initView();
        //initAttr(context, attrs);
    }
    public void setBindFragment(Fragment fragment) {
        this.mBindFragment = fragment;
    }
    private void initView() {
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setHasFixedSize(true);
        setWillNotDraw(true);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setChildrenDrawingOrderEnabled(true);setClipChildren(false);
        setClipToPadding(false);setClickable(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
    /**
     防止RecyclerView刷新时焦点不错乱bug的步骤如下:
     (1)adapter执行setHasStableIds(true)方法
     (2)重写getItemId()方法,让每个view都有各自的id
     (3)RecyclerView的动画必须去掉
     */
        setItemAnimator(null);
    }
    private int getFreeWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
    private int getFreeHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }@Override
    public boolean hasFocus() {
        return super.hasFocus();
    }@Override
    public boolean isInTouchMode() {
        // 解决4.4版本抢焦点的问题
        if (Build.VERSION.SDK_INT == 19) {
            return !(hasFocus() && !super.isInTouchMode());
        } else {
            return super.isInTouchMode();
        }
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = getWidth() - getPaddingRight();final int parentTop = getPaddingTop();
        final int parentBottom = getHeight() - getPaddingBottom();final int childLeft = child.getLeft() + rect.left;
        final int childTop = child.getTop() + rect.top;final int childRight = childLeft + rect.width();
        final int childBottom = childTop + rect.height();final int offScreenLeft = Math.min(0, childLeft - parentLeft);
        final int offScreenRight = Math.max(0, childRight - parentRight);final int offScreenTop = Math.min(0, childTop - parentTop);
        final int offScreenBottom = Math.max(0, childBottom - parentBottom);
        final boolean canScrollHorizontal = getLayoutManager().canScrollHorizontally();
        final boolean canScrollVertical = getLayoutManager().canScrollVertically();// Favor the "start" layout direction over the end when bringing one side or the other
// of a large rect into view. If we decide to bring in end because start is already
// visible, limit the scroll such that start won't go out of bounds.
        final int dx;
        if (canScrollHorizontal) {
            if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                dx = offScreenRight != 0 ? offScreenRight
                        : Math.max(offScreenLeft, childRight - parentRight);
            } else {
                dx = offScreenLeft != 0 ? offScreenLeft
                        : Math.min(childLeft - parentLeft, offScreenRight);
            }
        } else {
            dx = 0;
        }// Favor bringing the top into view over the bottom. If top is already visible and
// we should scroll to make bottom visible, make sure top does not go out of bounds.
        final int dy;
        if (canScrollVertical) {
            dy = offScreenTop != 0 ? offScreenTop : Math.min(childTop - parentTop, offScreenBottom);
        } else {
            dy = 0;
        }if (dx != 0 || dy != 0) {
            if (immediate) {
                scrollBy(dx, dy);
            } else {
                smoothScrollBy(dx, dy);
            }
            postInvalidate();
            return true;
        }
        return false;
    }
    @Override
    public int getBaseline() {
        return -1;
    }@Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }/**
     * 判断是垂直,还是横向.
     */
    private boolean isVertical() {
        LayoutManager manager = getLayoutManager();
        if (manager != null) {
            LinearLayoutManager layout = (LinearLayoutManager) getLayoutManager();
            return layout.getOrientation() == LinearLayoutManager.VERTICAL;}
        return false;
    }@Override
    protected int getChildDrawingOrder(int childCount, int i) {
        View view = getFocusedChild();
        if (null != view) {mPosition = getChildAdapterPosition(view) - getFirstVisiblePosition();
            if (mPosition < 0) {
                return i;
            } else {
                if (i == childCount - 1) {
                    if (mPosition > i) {
                        mPosition = i;
                    }
                    return mPosition;
                }
                if (i == mPosition) {
                    return childCount - 1;
                }
            }
        }
        return i;
    }public int getFirstVisiblePosition() {
        if (getChildCount() == 0)
            return 0;
        else
            return getChildAdapterPosition(getChildAt(0));
    }public int getLastVisiblePosition() {
        final int childCount = getChildCount();
        if (childCount == 0)
            return 0;
        else
            return getChildAdapterPosition(getChildAt(childCount - 1));
    }/**
     * 设置为0,这样可以防止View获取焦点的时候,ScrollView自动滚动到焦点View的位置
     */protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean result = super.dispatchKeyEvent(event);
        View focusView = this.getFocusedChild();
        if (mBindFragment != null) {
//            mBindFragment.setCacheViewFromContent(focusView);
        }
        if (focusView == null) {
            return result;
        } else {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                return true;
            } else {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                        if (rightView != null) {
                            rightView.requestFocus();
                            return true;
                        } else {
                            return false;
                        }
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        View leftView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                        Log.i(TAG, "leftView is null:" + (leftView == null));
                        if (leftView != null) {
                            leftView.requestFocus();
                            return true;
                        } else {
                            return false;
                        }
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (isVisBottom(this)) {
                            this.smoothScrollToPosition(getLastVisiblePosition());
                            return result;
                        }
                        View downView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_DOWN);
                        Log.i(TAG, " downView is null:" + (downView == null));
                        if (downView != null) {
                            downView.requestFocus();
                            int downOffset = downView.getTop() + downView.getHeight() / 2 - getHeight() / 2;
                            this.smoothScrollBy(0, downOffset);
                            return true;
                        } else {
                            return true;
                        }
                    case KeyEvent.KEYCODE_DPAD_UP:
                        View upView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_UP);
                        Log.i(TAG, "upView is null:" + (upView == null));
                        if (upView != null) {
                            upView.requestFocus();
                            int upOffset = getHeight() / 2 - (upView.getBottom() - upView.getHeight() / 2);
                            this.smoothScrollBy(0, -upOffset);
                            return true;
                        } else {
                            Log.i(TAG, "tab cache view");
                            if (mBindFragment != null) {
//                                mBindFragment.setmCacheViewFromTab(focusView);
                            }
                            return result;//返回false,否则第一行按上键回不到导航栏
                        }
                }
            }
        }
        return result;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    // /防止Activity时,RecyclerView崩溃
    @Override
    protected void onDetachedFromWindow() {
        if (getLayoutManager() != null) {
            super.onDetachedFromWindow();
        }
    }

    /**
     * 是否是最右边的item,如果是竖向,表示右边,如果是横向表示下边
     *
     * @param childPosition
     * @return
     */
    public boolean isRightEdge(int childPosition) {
        LayoutManager layoutManager = getLayoutManager();if (layoutManager instanceof GridLayoutManager) {GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookUp = gridLayoutManager.getSpanSizeLookup();int totalSpanCount = gridLayoutManager.getSpanCount();
            int totalItemCount = gridLayoutManager.getItemCount();
            int childSpanCount = 0;for (int i = 0; i <= childPosition; i++) {
                childSpanCount += spanSizeLookUp.getSpanSize(i);
            }
            if (isVertical()) {
                if (childSpanCount % gridLayoutManager.getSpanCount() == 0) {
                    return true;
                }
            } else {
                int lastColumnSize = totalItemCount % totalSpanCount;
                if (lastColumnSize == 0) {
                    lastColumnSize = totalSpanCount;
                }
                if (childSpanCount > totalItemCount - lastColumnSize) {
                    return true;
                }
            }} else if (layoutManager instanceof LinearLayoutManager) {
            if (isVertical()) {
                return true;
            } else {
                return childPosition == getLayoutManager().getItemCount() - 1;
            }
        }return false;
    }/**
     * 是否是最左边的item,如果是竖向,表示左方,如果是横向,表示上边
     *
     * @param childPosition
     * @return
     */
    public boolean isLeftEdge(int childPosition) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookUp = gridLayoutManager.getSpanSizeLookup();int totalSpanCount = gridLayoutManager.getSpanCount();
            int childSpanCount = 0;
            for (int i = 0; i <= childPosition; i++) {
                childSpanCount += spanSizeLookUp.getSpanSize(i);
            }
            if (isVertical()) {
                if (childSpanCount % gridLayoutManager.getSpanCount() == 1) {
                    return true;
                }
            } else {
                if (childSpanCount <= totalSpanCount) {
                    return true;
                }
            }} else if (layoutManager instanceof LinearLayoutManager) {
            if (isVertical()) {
                return true;
            } else {
                return childPosition == 0;
            }}return false;
    }/**
     * 是否是最上边的item,以recyclerview的方向做参考
     *
     * @param childPosition
     * @return
     */
    public boolean isTopEdge(int childPosition) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookUp = gridLayoutManager.getSpanSizeLookup();int totalSpanCount = gridLayoutManager.getSpanCount();int childSpanCount = 0;
            for (int i = 0; i <= childPosition; i++) {
                childSpanCount += spanSizeLookUp.getSpanSize(i);
            }if (isVertical()) {
                if (childSpanCount <= totalSpanCount) {
                    return true;
                }
            } else {
                if (childSpanCount % totalSpanCount == 1) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (isVertical()) {
                return childPosition == 0;
            } else {
                return true;
            }}return false;
    }/**
     * 是否是最下边的item,以recyclerview的方向做参考
     *
     * @param childPosition
     * @return
     */
    public boolean isBottomEdge(int childPosition) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookUp = gridLayoutManager.getSpanSizeLookup();
            int itemCount = gridLayoutManager.getItemCount();
            int childSpanCount = 0;
            int totalSpanCount = gridLayoutManager.getSpanCount();
            for (int i = 0; i <= childPosition; i++) {
                childSpanCount += spanSizeLookUp.getSpanSize(i);
            }
            if (isVertical()) {
//最后一行item的个数
                int lastRowCount = itemCount % totalSpanCount;
                if (lastRowCount == 0) {
                    lastRowCount = gridLayoutManager.getSpanCount();
                }
                if (childSpanCount > itemCount - lastRowCount) {
                    return true;
                }
            } else {
                if (childSpanCount % totalSpanCount == 0) {
                    return true;
                }
            }} else if (layoutManager instanceof LinearLayoutManager) {
            if (isVertical()) {
                return childPosition == getLayoutManager().getItemCount() - 1;
            } else {
                return true;
            }}
        return false;
    }public interface OnInterceptListener {
        boolean onIntercept(KeyEvent event);
    }/**
     * 判断是否已经滑动到底部
     *
     * @param recyclerView
     * @return
     */
    private boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1) {
            return true;
        } else {
            return false;
        }
    }}