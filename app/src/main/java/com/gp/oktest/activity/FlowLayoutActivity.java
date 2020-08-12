package com.gp.oktest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.utils.AppUtils;
import com.gp.oktest.utils.DeviceUtils;
import com.gp.oktest.view.FlowLayout;
import com.gp.oktest.view.FlowLayout1;
import com.gp.oktest.view.FlowLayout2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlowLayoutActivity extends BaseActivity {

    private String mNames[] = {
            "welcome","android","TextView",
            "apple","jamy","kobe bryant",
            "jordan","layout","viewgroup",
            "margin","padding","text",
            "name","type","search","logcat"};
    private FlowLayout1 mFlowLayout1;
    private FlowLayout2 mFlowLayout2;
    private FlowLayout flowlayout0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);

        initChildViews0();
        initChildViews1();
        initChildViews2();
    }

    private void initChildViews0() {
        flowlayout0 = findViewById(R.id.flowlayout0);
        List<String> treeStr = Arrays.asList(mNames);
        //往容器内添加TextView数据
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (flowlayout0 != null) {
            flowlayout0.removeAllViews();
        }
        for (int i = 0; i < treeStr.size(); i++) {
            TextView tv = new TextView(this);
            //todo DeviceUtils.dip2px(R.dimen.dimens_6)
            int haha = DeviceUtils.dip2px(getResources().getDimension(R.dimen.dimens_6));

            int haha1 = DeviceUtils.dip2px(R.dimen.dimens_6);


            tv.setPadding(DeviceUtils.dip2px(R.dimen.dimens_6), 0, DeviceUtils.dip2px(R.dimen.dimens_6), 0);
            tv.setText(treeStr.get(i));
//            tv.setTextSize(DeviceUtils.dip2px(8));
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setBackgroundResource(R.drawable.aat_shape_exam_point);
            tv.setLayoutParams(layoutParams);
            tv.setGravity(Gravity.CENTER);
            flowlayout0.addView(tv);
        }
    }

    private void initChildViews1() {
        mFlowLayout1 = findViewById(R.id.flowlayout1);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for(int i = 0; i < mNames.length; i ++){
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.flow_textview_bg));
            mFlowLayout1.addView(view,lp);
        }
    }

    private void initChildViews2() {
        mFlowLayout2 = findViewById(R.id.flowlayout2);
        List<String> list=new ArrayList<>();
        for (int i = 0; i <10; i++) {
            list.add("Android");
            list.add("Java");
            list.add("IOS");
            list.add("python");
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (mFlowLayout2 != null) {
            mFlowLayout2.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(this);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.flow_textview_bg);
            tv.setLayoutParams(layoutParams);
            mFlowLayout2.addView(tv, layoutParams);
        }
    }
}
