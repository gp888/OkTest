package com.gp.oktest.activity;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoping on 2018/3/27.
 */

public class KeyBoardAvtivity extends BaseActivity {

    @BindView(R.id.etIntput) EditText etIntput;

    @BindView(R.id.view_keyboard) KeyboardView keyboardView;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        ButterKnife.bind(this);


       Keyboard keyboradNumber = new Keyboard(this, R.xml.keyboard);
       etIntput.setShowSoftInputOnFocus(false);
       keyboardView.setKeyboard(keyboradNumber);
       keyboardView.setEnabled(true);
       //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
       keyboardView.setPreviewEnabled(true);
       //设置键盘按键监听器
       keyboardView.setOnKeyboardActionListener(listener);
       showKeyboard();

        //3过滤
       etIntput.setKeyListener(DigitsKeyListener.getInstance("abcd"));
        //4过滤
       etIntput.setFilters(new InputFilter[] {new InputFilter() {

           @Override
           public CharSequence filter(CharSequence source, int start, int end,
                                      Spanned dest, int dstart, int dend) {
               //source：你即将输入的字符序列
               Log.d("jie","source = "+source);

               //start：默认为0，  end：你即将输入的字符序列的长度
               Log.d("jie","start = "+ start);
               Log.d("jie","end = "+ end);

               //dest：当前EditText显示的内容
               Log.d("jie","dest = "+dest);

               //经测试dstart和dend 总是相等，都表示输入前光标所在位置
               Log.d("jie","dstart = "+dstart);
               Log.d("jie","dend = "+dend);

               StringBuffer sb = new StringBuffer();
               for (int i = 0; i < source.length(); i++) {
                   if ("wxyz".indexOf(source.charAt(i)) >= 0) {
                       sb.append(source.charAt(i));
                   }
               }
               return sb;
           }
       }});
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

//    方式1：xml中配置inputType。 常用于限制为 Date，time，number，Email，phone等常用的格式
//    android:inputType="number"
//    方式2：xml中配置digits。可以自定义限制的区间。
//    android:digits=" .@~-,:*?!#/\\=+?^;%$()[]{}|`&lt;&gt;&amp;&quot;&apos;_0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLIMNOPQRSTUVWXYZ"

//    方式3：java中使用setKeyListener，添加DigitsKeyListener。（方法2就是最终就是通过该方法实现）
//    "abc"
//    方法4：java中使用setFilters，添加InputFilter。可以在回调函数filter中自己写处理，最后返回处理过的CharSequence对象。
//    "wxyz"

}
