package com.gp.oktest.androidtest;

import android.view.View;

/**
 * 最简单view双击判断
 */
public abstract class IClickListener implements View.OnClickListener {

    long mLastClickTime = 0L;
    long TIME_INTERVAL = 500;
    @Override
    public final void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
            onIClick(v);
            mLastClickTime = System.currentTimeMillis();
        } else {
            onReClick(v);
        }
    }

    protected abstract void onIClick(View v);

    protected abstract void onReClick(View v);

//    btTest2.setOnClickListener(new IClickListener() {
//        @Override
//        protected void onIClick(View v) {
//            //do something
//        }
//
//        @Override
//        protected void onReClick(View v) {
//            Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
//        }
//    });
}