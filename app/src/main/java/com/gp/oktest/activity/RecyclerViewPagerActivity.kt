package com.gp.oktest.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.view.BannerAdapter
import com.gp.oktest.view.PagerChangeListener
import kotlinx.android.synthetic.main.activity_recycler_viewpager.*

class RecyclerViewPagerActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_recycler_viewpager)


        val lm = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        recycler_pager.setLayoutManager(lm)
        val imagelist = mutableListOf<String>("banner_t1", "banner_t3", "banner_t3", "banner_t4")
        recycler_pager.setAdapter1(BannerAdapter(imagelist))
        recycler_pager.setOnPageChangeListener(PagerChangeListener(tip_text, imagelist.size))
        recycler_pager.startPlay()
    }
}