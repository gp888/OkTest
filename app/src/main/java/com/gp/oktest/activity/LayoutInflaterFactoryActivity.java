package com.gp.oktest.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import com.gp.oktest.R;
import com.gp.oktest.view.FontTextView;

//替换字体
public class LayoutInflaterFactoryActivity extends AppCompatActivity {

    CustomFontCompatDelegate mCustomFontCompatDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //所有布局的控件，都会经过 LayoutInflaterFactory.onCreateView() 方法走一遍，
        // 去实现初始化的过程，在其中可以有效的分辨出是什么控件，以及它有什么属性
//        LayoutInflaterCompat.setFactory(getLayoutInflater(), new LayoutInflaterFactory() {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                return null;
//            }
//        });
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Nullable
            @Override
            public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                Log.i("gp", "=======");
                Log.i("gp", "name:==" + name);
                int n = attrs.getAttributeCount();
                for(int i = 0; i < n; i++) {
                    Log.i("gp", attrs.getAttributeName(i) + "," + attrs.getAttributeValue(i));
                }
                return null;
            }

            @Nullable
            @Override
            public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                return null;
            }
        });

        LayoutInflaterCompat.setFactory2(getLayoutInflater(), getLayoutFactory());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inflaterfacotry);
    }

    public CustomFontCompatDelegate getLayoutFactory(){
        if(mCustomFontCompatDelegate == null) {
            mCustomFontCompatDelegate = new CustomFontCompatDelegate();
        }
        return mCustomFontCompatDelegate;
    }

    public class CustomFontCompatDelegate implements LayoutInflater.Factory2{

        @Nullable
        @Override
        public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            switch(name) {
                case "TextView":
                    return new FontTextView(context, attrs);
            }
            return null;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return null;
        }
    }
}
