package com.gp.oktest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：tuke on 2017/6/17 11:31
 * 邮箱：2297535832@qq.com
 */
public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量所有子控件的宽和高,只有先测量了所有子控件的尺寸，后面才能使用child.getMeasuredWidth()
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //调用系统的onMeasure一般是测量自己(当前ViewGroup)的宽和高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * @param changed  该参数支出当前ViewGroup的尺寸或者位置是否发生了改变
     * @param l,t,r,b       当前ViewGroup相对于父控件的坐标位置，注意 ，一定是相对于父控件。
     * 函数的参数l,t,r,b，也是由该VieGroup的父控件传过来的
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int mViewGroupWidth=getMeasuredWidth(); //当前ViewGroup的总宽度

        int mPainterPosX=l;//当前绘制光标X坐标
        int mPainterPosY=t;//当前绘制光标Y坐标

        int childCount=getChildCount();//子控件的数量
        //遍历所有子控件，并在其位置上绘制子控件
        for (int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            //子控件的宽和高
            int width=childView.getMeasuredWidth();
            int height=childView.getMeasuredHeight();

            CustomViewGroup.LayoutParams params= (CustomViewGroup.LayoutParams) childView
                    .getLayoutParams();//在onLayout中就可以获取子控件的mergin值了

            //ChildView占用的width  = width+leftMargin+rightMargin
            //ChildView占用的height = height+topMargin+bottomMargin


            //如果剩余控件不够，则移到下一行开始位置
            if(mPainterPosX+width+params.leftMargin+params.rightMargin>mViewGroupWidth){
                mPainterPosX=l;
                mPainterPosY+=height+params.topMargin+params.bottomMargin;
            }
            //执行childView的绘制
            childView.layout(mPainterPosX+params.leftMargin,mPainterPosY+params.topMargin,
                    mPainterPosX+width+params.leftMargin+params.rightMargin,mPainterPosY+height+params.topMargin+params.bottomMargin);
            //下一次绘制的X坐标
            mPainterPosX+=width+params.leftMargin+params.rightMargin;
        }

    }
    //要使子控件的margin属性有效，必须定义静态内部类，继承ViewGroup.MarginLayoutParams
    public static class LayoutParams extends ViewGroup.MarginLayoutParams{

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
    //要使子控件的margin属性有效，必须重写该函数，返回内部类实例
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomViewGroup.LayoutParams(getContext(),attrs);
    }
}
