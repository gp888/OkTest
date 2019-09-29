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
public class RoundCornerImageView2 extends android.support.v7.widget.AppCompatImageView {
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


//        if (width >= 12 && height > 12) {
//            Path path = new Path();
//            path.moveTo(12, 0);
//            path.lineTo(width - 12, 0);
//            path.quadTo(width, 0, width, 12);
//            path.lineTo(width, height - 12);
//            path.quadTo(width, height, width - 12, height);
//            path.lineTo(12, height);
//            path.quadTo(0, height, 0, height - 12);
//            path.lineTo(0, 12);
//            path.quadTo(0, 0, 12, 0);
//            canvas.clipPath(path);
//        }
    }
}
