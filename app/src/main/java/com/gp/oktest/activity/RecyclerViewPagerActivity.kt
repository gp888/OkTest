package com.gp.oktest.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.gp.oktest.R
import com.gp.oktest.view.BannerAdapter
import com.gp.oktest.view.PagerChangeListener
import kotlinx.android.synthetic.main.activity_recycler_viewpager.*

class RecyclerViewPagerActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_recycler_viewpager)


        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recycler_pager.setLayoutManager(lm);
        val imagelist = mutableListOf<String>("banner_t1", "banner_t3", "banner_t3", "banner_t4")
        recycler_pager.setAdapter(BannerAdapter(imagelist));
        recycler_pager.setOnPageChangeListener(PagerChangeListener(tip_text, imagelist.size));
        recycler_pager.startPlay();
    }
}