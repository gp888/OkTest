package com.gp.oktest.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.gp.oktest.utils.DeviceUtils;

/**
 * 多行文字
 */
public class ImageTextView extends View {

    private static final float RING_WITH = DeviceUtils.dip2px1(20);
    private static final float RADIUS = DeviceUtils.dip2px1(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        paint.setTextSize(DeviceUtils.dip2px(12));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        StaticLayout staticLayout = new StaticLayout()
    }
}
