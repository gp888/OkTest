package com.gp.oktest.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gp.oktest.ForegroundCallbacks


open class BaseActivity : AppCompatActivity(), ForegroundCallbacks.Listener {
    @JvmField
    val TAG = BaseActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 注册监听
        ForegroundCallbacks.get(this).addListener(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除监听
        ForegroundCallbacks[this].removeListener(this)
    }

    override fun onBecameForeground() {
        //切换为前台
    }

    override fun onBecameBackground() {
        //切换为后台
    }
}