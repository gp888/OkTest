package com.gp.oktest.androidtest;

import android.util.Log;
import android.view.View;

public class ClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private IreClick mIreClick;

    long mLastClickTime = 0L;
    long TIME_INTERVAL = 500;

    public ClickProxy(View.OnClickListener origin, IreClick mIreClick) {
        this.origin = origin;
        this.mIreClick = mIreClick;
    }

    public ClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
            Log.i("ClickProxy", "ClickProxy in");
            origin.onClick(v);
            mLastClickTime = System.currentTimeMillis();
        } else {
            if (mIreClick != null) mIreClick.onReClick();
        }
    }

    public interface IreClick {
        void onReClick();//重复点击
    }

//    btTest3.setOnClickListener(new ClickProxy(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //do something
//        }
//    }, new ClickProxy.IreClick() {
//        @Override
//        public void onReClick() {
//            Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
//        }
//    }));
}


