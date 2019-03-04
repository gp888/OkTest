package com.gp.oktest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.view.Banner;
/**
 * Created by guoping on 2017/11/15.
 */

public class CountDownTimerActivity extends BaseActivity {
    TextView tv;
    long TOTAL_TIME = 10000, ONECE_TIME = 1000;

    /**
     * CountDownTimer 实现倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            tv.setText(value);
        }

        @Override
        public void onFinish() {
            tv.setText("down");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdowntimer);
        LinearLayout ll = findViewById(R.id.ll);
        tv = findViewById(R.id.tv);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        lp.width = metric.widthPixels * 9 / 10;
        tv.setLayoutParams(lp);

        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        countDownTimer.start();

        Banner banner = new Banner(this);
        banner.setData();
        ll.addView(banner);
    }
}
