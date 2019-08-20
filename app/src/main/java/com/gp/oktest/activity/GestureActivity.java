package com.gp.oktest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;


//mScroller = new Scroller(getContext());
// mScroller.forceFinished(true);

//scrollTo（int x,int y）：xy偏移量
//移动view到坐标点（100，100），那么我的偏移量就是(0,，0)  - （100，100） = （-100 ，-100）  ，
// 我就要执行view.scrollTo(-100,-100),达到这个效果。

//scrollBy（int x,int y）
//相对我们当前位置偏移

//getScrollX横向滑动距离
//mScrollX：表示离视图起始位置的x水平方向的偏移量
//mScrollY：表示离视图起始位置的y垂直方向的偏移量
//分别通过getScrollX() 和getScrollY()方法获得。
//向右滑，getScrollX()为负值
//注意：mScrollX和mScrollY指的并不是坐标，而是偏移量。
public class GestureActivity extends BaseActivity {
    Button bt;
    RelativeLayout rl;
    TextView tv;
    GestureDetector detector;
    int screenWith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        rl = findViewById(R.id.rl);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        bt.setBackgroundColor(Color.RED);

        detector = new GestureDetector(this, new MyGestureDetectorListener());



//        rl.setOnTouchListener(onTouchListener);
//        bt.setOnTouchListener(onTouchListener);
//        tv.setOnTouchListener(onTouchListener);

        rl.setOnLongClickListener(onLongClickListener);
        bt.setOnLongClickListener(onLongClickListener);
        tv.setOnLongClickListener(onLongClickListener);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.rl:

                    break;
                case R.id.tv:

                    break;
            }
            return detector.onTouchEvent(event);
        }
    };


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt:
                    if (v.isPressed()) {
                        Log.d("markgpp", "bt.onClick");
                    } else {
                        bt.setBackgroundColor(Color.BLUE);
                    }
                    break;
                case R.id.tv:
                    Log.d("markgpp", "tv.onClick");
                    break;
                default:
                    break;
            }
        }
    };

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.bt:
                    Log.d("markgpp", "bt.onLongClick");
                    break;
                case R.id.tv:
                    Log.d("markgpp", "tv.onLongClick");
                    break;
                case R.id.rl:
                    Log.d("markgpp", "rl.onLongClick");
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            // Touch down时触发, e为down时的MotionEvent
            // 按下时立刻触发
            //在这里 如果想要监听到滑动的话需要返回true
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            // 短按，在Touch down之后一定时间（115ms）触发，会触发这个手势，如果迅速抬起则不会
            //e为down时的MotionEvent
            super.onShowPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //第二次单击down时触发，e为第一次down时的MotionEven
            Log.d("markgpp", "onDoubleTap");
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // 长按，触摸屏按下后既不抬起也不移动，在Touch down之后一定时间（500ms）触发,e为down时的MotionEvent
            Log.d("markgpp", "onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // 滑动，按下后滑动执行的方法
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // 手指在触摸屏上迅速移动，并松开的动作,velocityX速度
            //velocityX > 0  正向，向右滑

            final int FLING_MIN_DISTANCE=100;//X或者y轴上移动的距离(像素)
            final int FLING_MIN_VELOCITY=200;//x或者y轴上的移动速度(像素/秒)
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY) {
                //从右往左滑动
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY) {
                //从左往右滑动
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // 释放，手指离开时触发(长按、滚动、滑动时，不会触发这个手势)
            //用于判断点击事件
            float x = e.getX();//手指离开的x
            //width是屏幕的1/3宽  用来判断点击的位置是在 左中右 哪个区域
            if(x > 0 && x <= screenWith/3){
                //左 点击区域在左执行的方法
            }else if(x > screenWith/3 &&x <= screenWith/3*2){
                //中  点击区域在中执行的方法
            }else{
                //右  点击区域在右执行的方法
            }
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            // 第二次单击down,move和up时都触发，e为不同时机下的MotionEvent
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // 完成一次单击，并确定300ms内没有二击事件后触发，e为down时的MotionEvent
            Log.d("markgpp", "onSingleTapConfirmed:");
            return super.onSingleTapConfirmed(e);
        }
    }
}
