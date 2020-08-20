package com.gp.oktest.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class FontTextView extends AppCompatTextView {
    static final String CUSTOM_FONT = "fonts/dnmbhs.ttf";
    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void replaceCustomFont(){
        Typeface typeface = getTypeface();
        int style = Typeface.NORMAL;
        if(typeface != null) {
            style = typeface.getStyle();
        }
        Typeface newTypeface = Typeface.createFromAsset(getContext().getAssets(), CUSTOM_FONT);
        setTypeface(newTypeface);
    }
}
