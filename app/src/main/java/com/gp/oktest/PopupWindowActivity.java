package com.gp.oktest;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Android的对话框有两种：PopupWindow和AlertDialog。它们的不同点在于：
 * •AlertDialog的位置固定，而PopupWindow的位置可以随意
 * •AlertDialog是非阻塞线程的，而PopupWindow是阻塞线程的
 * •PopupWindow的位置按照有无偏移分，可以分为偏移和无偏移两种；按照参照物的不同，
 * 可以分为相对于某个控件（Anchor锚）和相对于父控件。具体如下
 * •showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
 * •showAsDropDown(View anchor,int xoff,int yoff)：相对某个控件的位置，有偏移
 * •showAtLocation(View parent,int gravity,int x,int y)：相对于父控件的位置
 * （例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
 */
public class PopupWindowActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView anchor;
    private TextView mMainView;
    private RelativeLayout mRl_parent;
    private PopupWindow mPopupWindow;
    private int mWidth;
    private boolean mIsClick5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow);

        anchor = findViewById(R.id.anchor);
        mMainView = findViewById(R.id.tv_mainView);
        mRl_parent = findViewById(R.id.rl_parent);
        Button mButton01 = findViewById(R.id.button01);
        Button mButton02 = findViewById(R.id.button02);
        Button mButton03 = findViewById(R.id.button03);
        Button mButton04 = findViewById(R.id.button04);
        Button mButton05 = findViewById(R.id.button05);
        mButton01.setOnClickListener(this);
        mButton02.setOnClickListener(this);
        mButton03.setOnClickListener(this);
        mButton04.setOnClickListener(this);
        mButton05.setOnClickListener(this);
    }


    /**
     * 根据类型设置显示的popupWindow方式
     * @param type 1.为直接显示在某控件下方+点击外部不可关闭
     *             2.显示在某控件下方+点击外部可关闭
     *             3.相对父容器中心显示位置+点击外部可关闭
     *             4.相对父容器脚部显示位置+点击外部可关闭
     *             5.默认点击外部可关闭
     */
    private void initPopupWindow(int type) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View popupWindow = layoutInflater.inflate(R.layout.popup_window, null);
        //mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, MeasureSpec.AT_MOST
        // MeasureSpec.EXACTLY是（完全）精确尺寸，父元素决定子元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
        //      当我们将控件的layout_width或layout_height指定为具体数值时
        //      如andorid:layout_width="50dp"，或者为FILL_PARENT时，都是控件大小已经确定的情况，都是精确尺寸。

        // MeasureSpec.AT_MOST是（至多）最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，
        //      控件大小一般随着控件的子控件或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。
        //      因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。

        // MeasureSpec.UNSPECIFIED是未指定尺寸，父元素不对子元素施加任何束缚，子元素可以得到任意想要的大小；
        //      这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
        //以下方式是为了在popupWindow还没有弹出显示之前就测量获取其宽高（单位是px）
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        TextView mTextView = (TextView) popupWindow.findViewById(R.id.tv_popupTv);
        mTextView.measure(w, h);
        mWidth = mTextView.getMeasuredWidth();//获取测量宽度px
        int mHeight = mTextView.getMeasuredHeight();//获取测量高度px

        //设置点击popupWindow里面文本可以dismiss该popupWindow
        popupWindow.findViewById(R.id.tv_popupTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的显示View
        // 参数2：width 指定PopupWindow的width可以固定死某些数值：
        //       如果不想固定死可以设置为ViewGroup.LayoutParams.WRAP_CONTENT/MATCH_CONTENT
        // 参数3：height 指定PopupWindow的height
        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //方式1：xml配置文件mPopupWindow.setAnimationStyle(R.anim.popupwindow_enter);
        //方式2：直接设置该popupWindow中的View的动画setPopupAnimation(popupWindow);

        mPopupWindow.setFocusable(true); //这里很重要，设置该popupWindow可以获取焦点，不然无法响应点击事件

        switch (type) {
            case 1:
                //方式2：直接设置该popupWindow中的View的动画
                setPopupAnimation(popupWindow);
                mPopupWindow.setOutsideTouchable(false);//设置点击外面不可以消失~注意该效果在设置背景的情况下是无效的,6.0无效
                break;
            case 2:
                //方式1：xml配置文件
                mPopupWindow.setAnimationStyle(R.anim.popupwindow_enter);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.setOutsideTouchable(true);//设置点击外面可以消失~注意必须要设置该popupWindow背景才有效
                break;
            case 3:
                //方式2：直接设置该popupWindow中的View的动画
                setPopupAnimation(popupWindow);
                mPopupWindow.setOutsideTouchable(false);//设置点击外面不可以消失~注意该效果在设置背景的情况下是无效的,6.0无效
                break;
            case 4:
                mPopupWindow.setAnimationStyle(R.anim.popupwindow_enter);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.setOutsideTouchable(true);//设置点击外面可以消失~注意则必须要设置该popupWindow背景才有效
                break;
            case 5:
                mMainView.setVisibility(mIsClick5 ? View.VISIBLE : View.GONE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, dip2px(48));
                mMainView.measure(w, h);
                int mMainViewWidth = mMainView.getMeasuredWidth();//获取测量宽度px
                int width = (anchor.getWidth() - mMainViewWidth) / 2;//获取x轴偏移量px
                params.setMargins(anchor.getLeft() + width, anchor.getHeight(), 0, 0);
                mMainView.setLayoutParams(params);//设置位置
                if (mIsClick5) {
                    mMainView.setAnimation(getAnimation());//设置动画
                }
                break;
            default:
                mPopupWindow.setAnimationStyle(R.anim.popupwindow_enter);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.setOutsideTouchable(true);//设置点击外面可以消失~注意必须设置该popupWindow背景才有效
                break;
        }
    }

    /**
     * 动画效果
     *
     * @return
     */
    public Animation getAnimation() {
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float paramFloat) {
                return 10.0F * paramFloat;
            }
        });
        localAlphaAnimation.setDuration(500L);
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 1, 0.5F, -1, 0.0F);
        localScaleAnimation.setDuration(500L);
        AnimationSet localAnimationSet = new AnimationSet(true);
        localAnimationSet.addAnimation(localScaleAnimation);
        localAnimationSet.addAnimation(localAlphaAnimation);
        return localAnimationSet;
    }

    /**
     * 设置组合动画
     *
     * @param paramView
     */
    private void setPopupAnimation(View paramView) {
        //透明度动画
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float paramFloat) {
                return 10.0F * paramFloat;
            }
        });
        localAlphaAnimation.setDuration(800L);//动画持续时长
        //缩放动画:
        // 参数：
        // 1.为x轴起始缩放度 2.为x结束缩放度
        // 3.为y起始缩放度 4.为y结束缩放度
        // 5.为相对x轴类型为顶部 6.为该类型上起始度（0.5f为中间位置）
        // 7.为相对y轴类型 8.为该类型起始位置（0F为原位置）
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0F, 1.0F, 0F, 1.0F, Animation.ZORDER_TOP, 0.5F, Animation.ZORDER_TOP, 0F);
        localScaleAnimation.setDuration(500L);//动画持续时长
        AnimationSet localAnimationSet = new AnimationSet(true);
        localAnimationSet.addAnimation(localScaleAnimation);
        localAnimationSet.addAnimation(localAlphaAnimation);
        paramView.startAnimation(localAnimationSet);
    }

    /**
     * dip与px之间转换
     *
     * @param dipValue
     * @return
     */

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 相对某个控件的位置（正左下方），无偏移
            case R.id.button01:
                initPopupWindow(1);
                mPopupWindow.showAsDropDown(anchor);
                break;

            // 相对某个控件的位置（正左下方），有偏移
            case R.id.button02:
                initPopupWindow(2);
                //控件获取中间位置偏移量方式：(对应控件宽度-popup宽度)/2
                int tv_width = anchor.getWidth();//获取对应的控件view宽度px值
                int width = (tv_width - mWidth) / 2;//获取x轴偏移量px
                mPopupWindow.showAsDropDown(anchor, width, 0);
                break;

            // 相对于父控件的位置，无偏移~参数1为父容器~参数2为相对父容器相对类型~参数34为偏移量
            case R.id.button03:
                initPopupWindow(3);
//                int[] locaitons = new int[2];//存放相应控件在屏幕的xy轴坐标点；单位px
//                mTv.getLocationOnScreen(locaitons);//locaitons[0]为x轴 locaitons[1]为y轴
                // X、Y方向偏移量:设置x轴偏移量为相应控件中心;y轴无偏移
                mPopupWindow.showAtLocation(mRl_parent, Gravity.CENTER, 0, 0);
                break;

            // 相对于父控件的位置，有偏移~参数1为父容器~参数2为相对父容器相对类型~参数34为偏移量
            case R.id.button04:
                initPopupWindow(4);
                mPopupWindow.showAtLocation(mRl_parent, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.button05:
                mIsClick5 = !mIsClick5;
                initPopupWindow(5);
                break;
            default:
                break;
        }
    }
}
