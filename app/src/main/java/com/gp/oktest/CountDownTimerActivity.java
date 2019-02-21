package com.gp.oktest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gp.oktest.utils.DeviceUtils;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

/**
 * Created by guoping on 2017/11/15.
 */

public class CountDownTimerActivity extends AppCompatActivity {
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
        tv = findViewById(R.id.tv);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        lp.width = metric.widthPixels * 8 / 10;
        tv.setLayoutParams(lp);



        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        countDownTimer.start();


        UltraViewPager ultraViewPager = findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        ultraViewPager.setMultiScreen(0.7f);
        ultraViewPager.setHGap(10);
        ultraViewPager.setRatio(4.68f);
        ultraViewPager.setAutoMeasureHeight(true);
        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());

        PagerAdapter adapter = new UltraPagerAdapter(false);
        ultraViewPager.setAdapter(adapter);

        ultraViewPager.initIndicator();
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusResId(R.drawable.ic_ad_indicator_selected)
                .setNormalResId(R.drawable.ic_ad_indicator_normal);
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        ultraViewPager.getIndicator().setMargin(0,0,0, DeviceUtils.dip2px(this,8));
        ultraViewPager.getIndicator().build();

        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setAutoScroll(2000);

    }

    private final class UltraPagerAdapter extends PagerAdapter {

        private boolean isMultiScr;

        public UltraPagerAdapter(boolean isMultiScr) {
            this.isMultiScr = isMultiScr;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CardView layout = (CardView) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);

            ImageView imageView = layout.findViewById(R.id.image);

            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels * 8 / 10;     // 屏幕宽度（像素）
            int height1 = width  * 160/ 750;
            int width1 = width;
            CardView.LayoutParams lp = new CardView.LayoutParams(width1, height1);
            layout.setLayoutParams(lp);


            imageView.setImageResource(R.drawable.ad);
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }


}
