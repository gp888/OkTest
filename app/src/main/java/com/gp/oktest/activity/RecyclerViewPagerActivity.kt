package com.gp.oktest.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.databinding.ActivityRecyclerViewpagerBinding
import com.gp.oktest.view.BannerAdapter
import com.gp.oktest.view.PagerChangeListener

class RecyclerViewPagerActivity: BaseActivity<ActivityRecyclerViewpagerBinding>() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPager.setLayoutManager(lm)
        val imagelist = mutableListOf<String>("banner_t1", "banner_t3", "banner_t3", "banner_t4")
        binding.recyclerPager.setAdapter1(BannerAdapter(imagelist))
        binding.recyclerPager.setOnPageChangeListener(PagerChangeListener(binding.tipText, imagelist.size))
        binding.recyclerPager.startPlay()
    }
}