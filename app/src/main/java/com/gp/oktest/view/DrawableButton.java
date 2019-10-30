package com.gp.oktest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.gp.oktest.R;

/**
 * Created by guoping on 2017/5/18.
 */

public class DrawableButton extends AppCompatButton {

    private float textMarginRight, textMarginLeft;
    private int pl, pr,textPaddingRight,textPaddingLeft;
    private Drawable mDrawableLeft,mDrawableRight;

    public DrawableButton(Context context) {
        super(context);
    }

    public DrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public DrawableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LGDrawableButton, 0, 0);
            try {
                //textMarginRight:文本和drawableRight之间的距离,类似于drawablePadding
                textMarginRight = a.getDimension(R.styleable.LGDrawableButton_textMarginRight, 0);
                //textMarginLeft：文本和drawableLeft之间的距离,类似于drawablePadding
                textMarginLeft = a.getDimension(R.styleable.LGDrawableButton_textMarginLeft, 0);
                setDrawable(a.getDrawable(R.styleable.LGDrawableButton_drawableLeft), a.getDrawable(R.styleable.LGDrawableButton_drawableRight));
            } finally {
                a.recycle();
            }
        }
        pl = getPaddingLeft();
        pr = getPaddingRight();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (mDrawableRight != null) {
            width += mDrawableRight.getIntrinsicWidth();
            height = mDrawableRight.getIntrinsicHeight();
        }
        if (mDrawableLeft != null) {
            width += mDrawableLeft.getIntrinsicWidth();
            int drawableLeftHeight = mDrawableLeft.getIntrinsicHeight();
            height = height > drawableLeftHeight ? height : drawableLeftHeight;
        }
        setMeasuredDimension(Math.max(getMeasuredWidth(), width), Math.max(getMeasuredHeight(), height));
    }

    @Override
    //更新文本的padding值
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mDrawableRight != null) {
            textPaddingRight = mDrawableRight.getIntrinsicWidth() / 2;
        }
        if (mDrawableLeft != null) {
            textPaddingLeft = mDrawableLeft.getIntrinsicWidth() / 2;
        }
        if (mDrawableLeft != null && mDrawableRight != null) {
            textPaddingRight = 0;
            textPaddingLeft = 0;
        }
        setPadding(pl + textPaddingLeft * 2 + (int)textMarginLeft / 2, getPaddingTop(),
                pr + textPaddingRight * 2 + (int)textMarginRight / 2, getPaddingBottom());
    }

    public void setDrawable(final Drawable left, final Drawable right) {
        int[] state;
        state = getDrawableState();
        if (left != null) {
            left.setState(state);
            //设置drawable的绘图坐标为drawable图片左上角（方便定位drawable的位置）
            left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
            left.setCallback(this);
        }
        if (right != null) {
           right.setState(state);
            right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
            right.setCallback(this);
        }
        mDrawableRight = right;
        mDrawableLeft = left;
        requestLayout();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawableRight != null) {
            //更新mDrawableRight的x坐标
            Rect rect = mDrawableRight.getBounds();
            float textWidth = getPaint().measureText(getText().toString());
            float drawableX = getWidth() / 2 + textWidth / 2 - textPaddingRight + textMarginRight / 2;
            if (drawableX + rect.width() >= getWidth()) {
                drawableX = getWidth() - rect.width();
            }
            canvas.save();
            canvas.translate(drawableX, getHeight() / 2 - rect.bottom / 2);
            mDrawableRight.draw(canvas);
            canvas.restore();
        }
        if (mDrawableLeft != null) {
            //更新mDrawableLeft的x坐标
            Rect rect = mDrawableLeft.getBounds();
            float textWidth = getPaint().measureText(getText().toString());
            float drawableX = getWidth() / 2 + textWidth / 2 - textPaddingLeft + textMarginLeft / 2;
            if (drawableX + rect.width() >= getWidth()) {
                drawableX = getWidth() - rect.width();
            }
            canvas.save();
            canvas.translate(drawableX, getHeight() / 2 - rect.bottom / 2);
            mDrawableLeft.draw(canvas);
            canvas.restore();
        }
    }
}

//用法
//<com.gui.lgdrawablebutton.LGDrawableButton
//        android:id="@+id/center_left"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        app:textMarginLeft="20dp"
//        android:text="中国你好"
//        app:drawableLeft="@android:drawable/ic_delete" />
//
//<com.gui.lgdrawablebutton.LGDrawableButton
//        android:id="@+id/center_right"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:text="中国你好"
//        app:textMarginRight="20dp"
//        app:drawableRight="@android:drawable/ic_delete" />
//
//<com.gui.lgdrawablebutton.LGDrawableButton
//        android:id="@+id/center_both"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:text="中国你好"
//        app:drawableRight="@android:drawable/ic_delete"
//        app:drawableLeft="@mipmap/ic_launcher"/>
