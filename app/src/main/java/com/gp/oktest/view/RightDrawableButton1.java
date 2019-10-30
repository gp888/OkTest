package com.gp.oktest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * drawableright，drawable和文字居中应该这么写
 * Created by guoping on 2017/5/18.
 */

public class RightDrawableButton1 extends AppCompatButton {
    private final static String TAG = "RightDrawableButton";
    private Drawable[] drawables;
    private float textWidth;
    private float bodyWidth;
    public RightDrawableButton1(Context context) {
        super(context);
        init();
    }

    public RightDrawableButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RightDrawableButton1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        drawables = getCompoundDrawables();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        textWidth = getPaint().measureText(getText().toString());
        Drawable drawableRight = drawables[2];
        int totalWidth = getWidth();
        if (drawableRight != null) {
            int drawableWidth = drawableRight.getIntrinsicWidth();
            int drawablePadding = getCompoundDrawablePadding();
            bodyWidth = textWidth + drawableWidth + drawablePadding;
            setPadding(0,0,(int)(totalWidth - bodyWidth),0);
        }
    }

    public void setText(String text){
        if(text.equals(getText().toString()))
            return;
        super.setText(text);
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        canvas.translate((width - bodyWidth) / 2, 0);
        super.onDraw(canvas);
    }
}