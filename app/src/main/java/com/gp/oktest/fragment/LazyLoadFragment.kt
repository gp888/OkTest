package com.gp.oktest.fragment

import android.os.Bundle


abstract class LazyLoadFragment : BaseFragment() {

    protected var isViewInitiated: Boolean = false
    protected var isDataLoaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareRequestData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        prepareRequestData()
    }

    abstract fun requestData()

    @JvmOverloads
    fun prepareRequestData(forceUpdate: Boolean = false): Boolean {
        if (getUserVisibleHint() && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            requestData()
            isDataLoaded = true
            return true
        }
        return false
    }
}