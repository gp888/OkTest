package com.gp.oktest.activity;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StickMenuActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<ImageView> list;
    private int[] a = new int[]{R.drawable.www555, R.drawable.tt3366, R.drawable.wzryfx, R.mipmap.ic_launcher};
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_menu);
        viewPager = findViewById(R.id.viewpager);
        tvData = findViewById(R.id.tvData);
        initList();
        initVp();//初始化viewpager
        initEvent();//初始化点击事件
    }


    private void initList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append("今天是第" + (i + 1) + "天\n");
        }
        String s = sb.toString();
        tvData.setText(s);
    }

    private void initVp() {
        list = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            View v = View.inflate(this, R.layout.layout_image, null);
            ImageView mimg = (ImageView) v.findViewById(R.id.img);
            mimg.setImageResource(a[i]);
            list.add(mimg);
        }
        MyAdapter adapter = new MyAdapter(list);
        viewPager.setAdapter(adapter);
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class MyAdapter extends PagerAdapter {
        private List<ImageView> imgList;

        public MyAdapter(List<ImageView> imgList) {
            this.imgList = imgList;
        }

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int p = position % imgList.size();
            ImageView img = imgList.get(p);
            ViewParent vp = img.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(img);
            }
            container.addView(imgList.get(p));
            return imgList.get(p);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int p = position % imgList.size();
            container.removeView(imgList.get(p));
        }
    }

}
