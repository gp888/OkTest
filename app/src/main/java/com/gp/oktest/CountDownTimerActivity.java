package com.gp.oktest;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.view.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoping on 2017/11/15.
 */

public class CountDownTimerActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll)
    LinearLayout ll;

    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.colorList)
    TextView colorTextView;

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
        setContentView(R.layout.activity_countdowntimer);
        ButterKnife.bind(this);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        lp.width = metric.widthPixels * 9 / 10;
        tv.setLayoutParams(lp);

        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        countDownTimer.start();


        colorTextView.setText("Sodino\nNormal:0xffffffff\nPressed:0xffffff00\nFocused:0xff0000ff\nUnable:0xffff0000");
        colorTextView.setTextColor(createColorStateList(0xffffffff, 0xffffff00, 0xffff0000, 0xff0000ff));
        colorTextView.setOnClickListener(this);


        Banner banner = new Banner(this);
        banner.setData();
        ll.addView(banner);
    }

    private ColorStateList createColorStateList(int normal, int pressed, int unable, int select) {
        int[] colors = new int[] { select, pressed, normal, unable, normal };
        int[][] states = new int[5][];
        states[0] = new int[] { android.R.attr.state_selected, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_window_focused };
        states[4] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static void setSelectorDrawable(ImageView view, Drawable drawableNormal, Drawable drawableSelect) {

        StateListDrawable drawable = new StateListDrawable();

        //选中
        drawable.addState(new int[]{android.R.attr.state_selected}, drawableSelect);
        //未选中
        drawable.addState(new int[]{-android.R.attr.state_selected}, drawableNormal);
        view.setImageDrawable(drawable);
    }

    @Override
    public void onClick(View v) {
        if (v == colorTextView) {
//            colorTextView.setEnabled(false);
            colorTextView.setSelected(true);
        }
    }
}
