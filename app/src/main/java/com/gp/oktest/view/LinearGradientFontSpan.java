package com.gp.oktest.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gp.oktest.utils.DeviceUtils;

public class LinearGradientFontSpan extends ReplacementSpan {
    private int mSize;
    private int startColor;
    private int endColor;

    public LinearGradientFontSpan(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        mSize = (int) paint.measureText(text , start , end);
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        if (fm != null) {
            fm.top = metrics.top;
            fm.ascent = metrics.ascent;
            fm.descent = metrics.descent;
            fm.bottom = metrics.bottom;
        }
        return mSize;
    }

    //paint.descent() - paint.ascent()
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        LinearGradient lg = new LinearGradient(0, 0, mSize, 0,
                new int[]{startColor, endColor},
                new float[]{0.8f, 1f},
                Shader.TileMode.CLAMP);
        paint.setShader(lg);
        //paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
        canvas.drawRoundRect(oval, DeviceUtils.dip2px(2), DeviceUtils.dip2px(2), paint);

        paint.setShader(null);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, start, end, x, y, paint);//绘制文字
    }
}
