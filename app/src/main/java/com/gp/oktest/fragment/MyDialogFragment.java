package com.gp.oktest.fragment;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * MyDialogFragment dialogFragment = new MyDialogFragment();
 *         dialogFragment.show(getSupportFragmentManager(), "tag");
 *         单个实例只能show一次
 */
public class MyDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AppCompatDialog appCompatDialog = new AppCompatDialog(requireActivity());
        TextView textView = new TextView(requireActivity());
        textView.setText("通过onCreateDialog使用DialogFragment");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        appCompatDialog.setContentView(textView);
        return appCompatDialog;
    }

    /**
     * 创建的View将会作为Dialog的内容布局,优先级高于@onCreateDialog
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(requireActivity());
        textView.setText("通过onCreateView使用DialogFragment");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        return textView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置Dialog的样式
        //必须在onCretaeDialog方法后面执行，不然得不到Dialog实例

        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置Dialog窗口的高度
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        //设置Dialog窗口的宽度
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置Dialog的居中方向
        attributes.gravity = Gravity.CENTER;
        //设置Dialog弹出时背景的透明度
        attributes.dimAmount = 0.6f;
        //设置Dialog水平方向的间距
        attributes.horizontalMargin = 0f;
        //设置Dialog垂直方向的间距
        attributes.verticalMargin = 0f;
        //设置Dialog显示时X轴的坐标,具体屏幕X轴的偏移量
        attributes.x = 0;
        //设置Dialog显示时Y轴的坐标,距离屏幕Y轴的偏移量
        attributes.y = 0;
        //设置Dialog的透明度
        attributes.alpha = 0f;
        //设置Dialog显示和消失时的动画
        attributes.windowAnimations = 0;
        window.setAttributes(attributes);
    }
}
