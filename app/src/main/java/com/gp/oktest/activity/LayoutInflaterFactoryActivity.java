package com.gp.oktest.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

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

        //onCreate()中的宽度和高度将为零，因为尚未执行ImageView的绘图...您可以在onWindowFocusChanged()中检查这些值。
        // 并且您的onPreDrawListener回调将在绘制时调用宽度和高度值将正确，因为ImageView已经在绘制之前进行了测量和布局

        //即将绘制视图树时执行的回调函数。这时所有的视图都测量完成并确定了框架。 客户端可以使用该方法来调整滚动边框，甚至可以在绘制之前请求新的布局。
        //
        //综上，ViewTreeObserver是用来帮助我们监听某些View的某些变化的。
        //
        //其中最常用的地方就是我们在onDraw的其他地方获取View的长宽测量值。
        ImageView img = (ImageView) findViewById(R.id.iv);
        ViewTreeObserver vto = img.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                // at this point, true width and height are already determined
                int width = img.getMeasuredWidth();
                int height = img.getMeasuredHeight();
                System.out.println(width);// it return value correctly
                System.out.println(height);// it return value correctly
                img.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
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
