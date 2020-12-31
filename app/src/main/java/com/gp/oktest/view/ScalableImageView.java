package com.gp.oktest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.gp.oktest.utils.DeviceUtils;

/**
 * 双击放大，滑动，imageView
 */
public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
        Runnable, ScaleGestureDetector.OnScaleGestureListener {
    private static final float IMAGE_WIDTH = DeviceUtils.dip2px1(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float originalOffsetX;
    float originalOffsetY;
    float offsetX;
    float offsetY;
    float smallScale;
    float bigScale;
    boolean big;

//    float scaleFraction;// 0~1f
    float currentScale;
    float initialScale;
    ObjectAnimator scaleAnimator;
//    GestureDetector;
    GestureDetectorCompat detector;
    ScaleGestureDetector scaleGesture;
    OverScroller overScroller;
    Scroller scroller;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = DeviceUtils.getAvatar(getResources(), (int)IMAGE_WIDTH);
        detector = new GestureDetectorCompat(context, this);

        //关闭长按
//        detector.setIsLongpressEnabled(false);
//        detector.setOnDoubleTapListener(this);

        overScroller = new OverScroller(context);
        scroller  = new Scroller(context);
        scaleGesture = new ScaleGestureDetector(context, this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        if((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()){
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleGesture.onTouchEvent(event);
        if(!scaleGesture.isInProgress()) {
            result = detector.onTouchEvent(event);
        }
        return result;
//        return scaleGesture.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //a -> c -> b  (c - a) /( b -a)
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);//二次偏移和缩放挂钩
//        float scale = smallScale + (bigScale - smallScale) * scaleFraction;

        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    private ObjectAnimator getScaleAnimator(){
        if(scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
//            scaleAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    //缩小动画结束后重置
//                    offsetX = 0;
//                    offsetY = 0;
//                }
//            });
            scaleAnimator.setFloatValues(smallScale, bigScale);

        }
        return scaleAnimator;
    }

    private float getCurrentScale() {
        return currentScale;
    }

    private void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //按下100ms
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        //如果开启长按：按下500ms之内抬起回调；500ms之后不会触发。关闭长按 按下多久抬起都会回调
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent down, MotionEvent motionEvent, float distanceX, float distanceY) {
        //distanceX 上一个点位置 到当前点位置的距离
        if(big){
            offsetX -= distanceX;
            offsetY -= distanceY;
            fixOffsets();
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent down, MotionEvent motionEvent, float velocityX, float velocityY) {
        if(big) {
            overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    -(int)(bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int)(bitmap.getWidth() * bigScale - getWidth()) / 2,
                    -(int)(bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int)(bitmap.getHeight() * bigScale - getHeight()) / 2, 100, 100);

            //手动加动画  100ms 10帧
//            for(int i = 10; i < 100; i += 10) {
//                postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refresh();
//                    }
//                }, i);
//           }

            //启动动画，下一帧，动画被执行
            postOnAnimation(this);
        }
        return false;
    }

    void refresh(){
        //计算得到位置，赋值，刷新
        overScroller.computeScrollOffset();
        offsetX = overScroller.getCurrX();
        offsetY = overScroller.getCurrY();
        invalidate();
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        //单击确认，单击抬起，过一小会儿300ms，回调；按下超过500ms抬起
        //设置双击监听后，监听单击用此方法
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //两次按下间隔300ms内，第二次摸到触发；两次间隔40ms之内，会排出
        // doubleTapTimeout可以设置，ViewConfigration

        big = !big;
        if(big) {
            //初始值 - 结束值，逆向偏移，给偏移回来
            //双击点的位置保持不动
            offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f) * bigScale / smallScale;
            offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f) * bigScale / smallScale;
            fixOffsets();
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        //双击，直到抬起，之间都会回调
        return false;
    }

    /**
     * fling
     */
    @Override
    public void run() {
        if(overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();
            //推到下一帧
            postOnAnimation(this);

            //立即去主线程执行，
//            post(this);
//            ViewCompat.postOnAnimation(this, this);
        }
    }

    /**
     * 缩放轴心，缩放最大值最小值
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //倍数
        currentScale = initialScale * detector.getScaleFactor();// 1~ 1.1 2; 1 ~ 0.8
        currentScale = Math.min(currentScale, bigScale);
        currentScale = Math.max(currentScale, smallScale * 0.8f);
        invalidate();
        //焦点
//        detector.getFocusX();
//        detector.getFocusY();
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        initialScale = currentScale;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    private void fixOffsets(){
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);

        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }
}
