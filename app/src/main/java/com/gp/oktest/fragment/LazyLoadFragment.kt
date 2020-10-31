package com.gp.oktest.fragment

import android.os.Bundle

/**
 * Fragment中的setUserVisibleHint()，此方法会在onCreateView(）之前执行，
 * 当viewPager中fragment改变可见状态时也会调用,当fragment 从可见到不见，或者从不可见切换到可见，
 * 都会调用此方法，使用getUserVisibleHint() 可以返回fragment是否可见状态。
 */
abstract class LazyLoadFragment : BaseFragment() {

    //该页面是否已经准备完毕，onCreateView方法已调用完毕,执行过findviewbyid
    protected var isPrepared: Boolean = false
    //该fragment，是否已经执行过懒加载
    protected var isDataLoaded: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyLoad()
    }

    abstract fun onLazyLoad()

    fun lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isDataLoaded) {
            onLazyLoad()
            isDataLoaded = true
        }
    }
}