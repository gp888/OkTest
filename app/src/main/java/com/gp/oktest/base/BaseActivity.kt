package com.gp.oktest.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gp.oktest.ForegroundCallbacks
import java.lang.reflect.ParameterizedType


open class BaseActivity<VB : ViewBinding> : AppCompatActivity(), ForegroundCallbacks.Listener {
    @JvmField
    val TAG = BaseActivity::class.simpleName


    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding(LayoutInflater.from(this))
        setContentView(binding.root)

        // 注册监听
        ForegroundCallbacks.get(this).addListener(this);
    }

    private fun initBinding(inflater: LayoutInflater?): VB {
        val clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        return clazz.getMethod("inflate", LayoutInflater::class.java).invoke(this, inflater) as VB
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