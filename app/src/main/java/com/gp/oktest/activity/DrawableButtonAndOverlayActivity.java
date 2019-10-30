package com.gp.oktest.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

public class DrawableButtonAndOverlayActivity extends BaseActivity {

    private static ImageView overlay = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawablebutton);

        add();
    }

    public static void removeWindow() {
        mWindowManager.removeViewImmediate(overlay);
    }

    public void add() {
        mContext = DrawableButtonAndOverlayActivity.this;
        overlay = new ImageView(mContext);
        overlay.setImageResource(R.mipmap.ic_launcher);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        //WindowManager.LayoutParams.TYPE_TOAST, TYPE_SYSTEM_ALERT, TYPE_APPLICATION
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // PixelFormat.TRANSLUCENT,不设置这个弹出框的透明遮罩显示为黑色
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(overlay, params);
    }
}
