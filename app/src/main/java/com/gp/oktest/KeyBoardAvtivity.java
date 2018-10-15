package com.gp.oktest;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoping on 2018/3/27.
 */

public class KeyBoardAvtivity extends AppCompatActivity {

    @BindView(R.id.ed) EditText ed;

    @BindView(R.id.view_keyboard) KeyboardView keyboardView;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        ButterKnife.bind(this);


       Keyboard keyboradNumber = new Keyboard(this, R.xml.keyboard);
       ed.setShowSoftInputOnFocus(false);
       keyboardView.setKeyboard(keyboradNumber);
       keyboardView.setEnabled(true);
       //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
       keyboardView.setPreviewEnabled(true);
       //设置键盘按键监听器
       keyboardView.setOnKeyboardActionListener(listener);
       showKeyboard();
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }
        @Override
        public void swipeLeft() {
        }
        @Override
        public void swipeDown() {
        }
        @Override
        public void onText(CharSequence text) {
        }
        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Log.d("keycode", primaryCode + "");
//            if(primaryCode == KEY_PRIMARY_CODE_DELETE){
//                onDeleteAction();
//            }else if(primaryCode == KEY_PRIMARY_CODE_DONE){
//                onNextPress();
//            }else { //其它字符按键
//                onCharKeyAction(primaryCode);
//            }
        }
    };

}
