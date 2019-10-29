package com.gp.oktest.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;

import com.gp.oktest.R;

/**
 * fullSheetDialogFragment = new FullSheetDialogFragment();
 *
 * fullSheetDialogFragment.show(getSupportFragmentManager(), "dialog");
 */
public class FullSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
        dialog.setContentView(view);

        mBehavior = BottomSheetBehavior.from((View) view.getParent());

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //0.9屏幕高度
        int height = (int) (dialog.getContext().getResources().getDisplayMetrics().heightPixels * 0.8);
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);

        //去除层叠显示（就是去掉完全展开，中间状态，隐藏中的中间状态）：

        //height如果设置为0也可以实现效果，但会导致滑动关闭后，有一层阴影遮罩，
        //需要点击一次才能消失。原因是设置为0，滑动到看不见时，其实是处于中间状态，并没有完全关闭dialog。
        mBehavior.setPeekHeight(height);

        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void doclick(View v) {
        //点击任意布局关闭
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

}
