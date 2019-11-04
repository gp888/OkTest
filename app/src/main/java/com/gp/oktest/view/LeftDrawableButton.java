package com.gp.oktest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class LeftDrawableButton extends AppCompatButton {

    private Drawable[] drawables;
    private float bodyWidth;

    public LeftDrawableButton(Context context) {
        super(context);
        init();
    }

    public LeftDrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        drawables = getCompoundDrawables();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        float textWidth = getPaint().measureText(getText().toString());
        Drawable drawableLeft = drawables[0];
        int totalWidth = getWidth();
        if (drawableLeft != null) {
            int drawableWidth = drawableLeft.getIntrinsicWidth();
            int drawablePadding = getCompoundDrawablePadding();
            bodyWidth = textWidth + drawableWidth + drawablePadding;
            setPadding(0,0, (int)(totalWidth - bodyWidth),0);
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