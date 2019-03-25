package com.gp.oktest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gp.oktest.activity.CountDownTimerActivity;
import com.gp.oktest.R;
import com.gp.oktest.utils.DeviceUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class Banner extends LinearLayout {

    private Context mContext;
    private LayoutInflater inflater;
    private UltraViewPager ultraViewPager;
    int arraySize;

    List<String> mList = new ArrayList<>();
    List<Bitmap> mBitmaps = new ArrayList<>();

    public Banner(Context context) {
        super(context);
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(){
        mList.add("https://files.nxin.com/public/yuantu/2018/7/19/55/d08aed9d-8341-4a54-8a43-173d87cba7e8.png");
        mList.add("https://files.nxin.com/public/yuantu/2017/4/10/d2/a674a57b-7145-42e2-8ca6-3f9bea34ffdb.png");
        mList.add("https://files.nxin.com/public/yuantu/2018/9/29/ae/2f05c43c-ce2a-4845-9a22-0e09a9290a13.jpg");
        mList.add("https://files.nxin.com/public/yuantu/2018/12/4/f0/6dcd023e-3e06-4c00-b564-be12728164f4.jpg");
        mList.add("https://files.nxin.com/public/yuantu/2018/4/25/17/6ffe0881-ef6f-452d-a336-0c5006c4b9af.jpg");
        mList.add("https://files.nxin.com/public/yuantu/2018/4/25/4a/be9a3d60-2651-4f08-888d-7725d9019b13.jpg");
        mList.add("https://files.nxin.com/public/yuantu/2018/4/25/ac/6eaef739-048f-46bb-a31f-a41e355b3252.jpg");
        mList.add("https://files.nxin.com/public/yuantu/2018/4/25/a7/de6d9073-17a4-435b-a21b-a675feac5140.jpg");
        mList.add("https://files.nxin.com/public/yuantu/2018/4/24/a4/4b33d4e5-0d8d-4b6b-801a-0af1e741bdd2.png");
        mList.add("https://files.nxin.com/public/yuantu/2018/4/25/45/905b5a71-b9b8-4cac-adbf-d0505c8f9876.png");


        for (int i = 0; i < mList.size(); i++) {

            Glide.with(mContext).load(mList.get(i))
                    .asBitmap()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mBitmaps.add(resource);

                            if (mBitmaps.size() >= 3 && ultraViewPager == null){
                                initUI(mBitmaps.size());
                            }
                            if (ultraViewPager != null) {
                                ultraViewPager.refresh();
                            }
                        }
                    });
        }
    }

    private void initUI(int count){
        View view = inflater.inflate(R.layout.view_banner,null);
        ultraViewPager = view.findViewById(R.id.banner);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ultraViewPager.getLayoutParams();
        if (count <= 2) {
            ultraViewPager.setMultiScreen(1.0f);
            lp.leftMargin = DeviceUtils.dip2px(12);
            lp.rightMargin = DeviceUtils.dip2px(12);
        } else {
            ultraViewPager.setMultiScreen(0.9f);
            lp.leftMargin = DeviceUtils.dip2px(0);
            lp.rightMargin = DeviceUtils.dip2px(0);
        }
        ultraViewPager.setLayoutParams(lp);
//        ultraViewPager.setRatio(4.68f);
        ultraViewPager.setAutoMeasureHeight(true);
//        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());


        PagerAdapter adapter = new UltraPagerAdapter(false);
        ultraViewPager.setAdapter(adapter);


        ultraViewPager.initIndicator();
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusResId(R.drawable.ic_ad_indicator_selected)
                .setNormalResId(R.drawable.ic_ad_indicator_normal);
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        ultraViewPager.getIndicator().setMargin(0,0,0, DeviceUtils.dip2px(5));
        ultraViewPager.getIndicator().build();

        if (arraySize == 1){
            ultraViewPager.setInfiniteLoop(false);
        } else {
            ultraViewPager.setInfiniteLoop(true);
        }
        ultraViewPager.setAutoScroll(5000);

        addView(view);
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
            return mBitmaps.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout layout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
            ImageView imageView = layout.findViewById(R.id.image);
//            Glide.with(CountDownTimerActivity.this).load(mList.get(position)).into(imageView);
            imageView.setImageBitmap(mBitmaps.get(position));

            CardView.LayoutParams lp = (CardView.LayoutParams) imageView.getLayoutParams();
            DisplayMetrics metric = new DisplayMetrics();
            ((CountDownTimerActivity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
            lp.width = metric.widthPixels * 9 / 10 - DeviceUtils.dip2px(10);
            lp.height = lp.width  * 160 / 750;
            imageView.setLayoutParams(lp);


            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
