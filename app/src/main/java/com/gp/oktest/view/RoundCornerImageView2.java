package com.gp.oktest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角的imageview
 */
public class RoundCornerImageView2 extends ImageView {
    public RoundCornerImageView2(Context context) {
        super(context);
    }
    public RoundCornerImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RoundCornerImageView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    // clipPath which is not hardware accelerated and not anti-aliased.
    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(0, 0, w, h), 20.0f, 20.0f, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
