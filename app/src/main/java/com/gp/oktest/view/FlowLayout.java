package com.gp.oktest.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item纵向间距

    private List<List<View>> allLines;// 保存每一行view的列表
    private List<Integer> heights; // 记录每一行的行高

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private void initMeasureParams() {
        allLines = new ArrayList<>();
        heights = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        initMeasureParams();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        int lineWidthUsed = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();
        int parentNeededWidth = 0;
        int parentNeededHeight = 0;

        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            LayoutParams layoutParams = childView.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height);
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            //确定是否换行
            int childMeasureWidth = childView.getMeasuredWidth();
            int childMeasureHeight = childView.getMeasuredHeight();
            if (childMeasureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                allLines.add(lineViews);
                heights.add(lineHeight);

                //遍历子view，记录子view的最大宽高，作为自身的尺寸
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;

            }

            lineViews.add(childView);
            lineWidthUsed = lineWidthUsed + childMeasureWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childMeasureHeight);

            if(i == childCount - 1) {
                allLines.add(lineViews);
                heights.add(lineHeight);

                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
            }
        }


        //处理了wrapcontent情况
        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;
        setMeasuredDimension(realWidth,realHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();
        //在Measure与Layout过程完成之后，View的width、height、top、left等属性才被正确赋值
        int curX = getPaddingLeft();
        int curT = 0;

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);
            int lineHeight = heights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);
                int left = curX;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                curX = right + mHorizontalSpacing;
            }
            curT = curT + lineHeight + mVerticalSpacing;
            curX = getPaddingLeft();
        }
    }

    public static int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    private String getModeString(int mode) {
        String result = "Unknow";
        switch (mode) {
            case MeasureSpec.UNSPECIFIED :
                result = "Unspecified";
                break;
            case MeasureSpec.EXACTLY:
                result = "EXACTLY";
                break;
            case MeasureSpec.AT_MOST:
                result = "AT_MOST";
                break;
            default:
        }
        return  result;
    }
}
