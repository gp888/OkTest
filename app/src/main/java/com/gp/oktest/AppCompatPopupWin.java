package com.gp.oktest;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 背景半透明兼容方案
 */
public class AppCompatPopupWin extends PopupWindow{

    private Context mContext;
    private PopupWindow pop;

    public AppCompatPopupWin(Context context) {
        this.mContext = context;
    }

    public void showPop() {
        View contentView;
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        contentView = mLayoutInflater.inflate(R.layout.layout_popupwindow, null);
        pop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.chat_forward_width));
//        ListView listView = (ListView) contentView.findViewById(R.id.list);
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = ((AppCompatActivity)mContext).getWindow().getAttributes();
        lp.alpha = 0.4f;
        ((AppCompatActivity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((AppCompatActivity)mContext).getWindow().setAttributes(lp);
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        pop.update();
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                ((Activity)mContext).getWindow().setAttributes(lp);
            }
        });
//        listView.setOnItemClickListener(onItemClickListener);
//        listView.setAdapter(adapter);
    }
}
