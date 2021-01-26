package com.gp.oktest.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.NonNull;



public class RadiusBackgroundSpan extends ReplacementSpan {
    private int mStartColor;
    private int mEndColor;
    private float mBgWidth;  //Icon背景宽度
    private float mRadius;  //Icon圆角半径

    public RadiusBackgroundSpan(int startColor, int endColor, int radius) {
        mStartColor = startColor;
        mEndColor = endColor;
        mRadius = radius;
    }

    /**
     * 设置宽度，宽度=背景宽度+右边距
     */
    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mBgWidth = (int) (paint.measureText(text, start, end) + 2 * mRadius);
        return (int) mBgWidth;
    }

    /**
     * draw
     *
     * @param text   完整文本
     * @param start  setSpan里设置的start
     * @param end    setSpan里设置的end
     * @param x      起始横坐标
     * @param top    当前span所在行的上方y
     * @param y      y其实就是metric里baseline的位置
     * @param bottom 当前span所在行的下方y(包含了行间距)，会和下一行的top重合
     * @param paint  使用此span的画笔
     */
    //y为baseline对应的坐标，top为起始高度，bottom为结束高度。
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //画背景
        LinearGradient lg = new LinearGradient(0, 0, mBgWidth, 0, new int[]{mStartColor, mEndColor}, null, Shader.TileMode.CLAMP);
        paint.setShader(lg);

        RectF bgRect = new RectF(x, y + paint.ascent(), x + mBgWidth, y + paint.descent());
        canvas.drawRoundRect(bgRect, mRadius, mRadius, paint);

        //把字画在背景中间
        paint.setShader(null);
        paint.setColor(Color.WHITE);
        paint.setTextSize(paint.getTextSize());
        canvas.drawText(text, start, end, x + mRadius, y, paint);
    }
}