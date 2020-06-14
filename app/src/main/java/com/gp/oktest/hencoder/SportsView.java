package com.gp.oktest.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.gp.oktest.utils.DeviceUtils;

/**
 * 绘制文字的位置对齐
 */
public class SportsView extends View {

    private static final float RING_WITH = DeviceUtils.dip2px1(20);
    private static final float RADIUS = DeviceUtils.dip2px1(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect rect = new Rect();
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportsView(Context context) {
        super(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        paint.setTextSize(DeviceUtils.dip2px(100));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Quicksand-Regular.otf"));
        paint.getFontMetrics(fontMetrics);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WITH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        //绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, -90, 225, false, paint);

        //绘制文字
        paint.setStyle(Paint.Style.FILL);

        //文字默认绘制在，x坐标和文字左边间隙对齐，y坐标和baseLine对齐，左下角那个点
        //getTextBounds这种方式，文字非常居中，但文字变化有跳动，适合固定内容
        //文字有top bottom baseline bounds descent ascent

//        paint.getTextBounds("abab", 0, "abab".length(), rect);//左上右下
//        int offset = (rect.top + rect.bottom) / 2;

        //descent ascent 的中心是最多的中心，适合动态的文字
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("abab", getWidth() / 2, getHeight() / 2 - offset, paint);


        //字母a的左右都有间隙，textbounds会消除间隙
        paint.setTextSize(DeviceUtils.dip2px(100));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds("aaaa", 0, "aaaa".length(), rect);
        //文字贴左上角
        canvas.drawText("aaaa", -rect.left, -rect.top, paint);

        paint.setTextSize(DeviceUtils.dip2px(15));
        canvas.drawText("aaaa", 0, -rect.top + paint.getFontSpacing(), paint);
    }
}
