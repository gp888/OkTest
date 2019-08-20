package com.gp.oktest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.support.v7.widget.AppCompatEditText;

//设置imeOptions 不设置singleLine 可多行显示
public class MutilineImeOptionsEditText extends AppCompatEditText {
    public MutilineImeOptionsEditText(Context context) {
        super(context);
    }

    public MutilineImeOptionsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnection = super.onCreateInputConnection(outAttrs);
        if(inputConnection != null){
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
        return inputConnection;
    }
}
