package com.gp.oktest.activity

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import com.gp.oktest.R
import com.gp.oktest.animator.Point
import com.gp.oktest.animator.PointEvaluator
import com.gp.oktest.animator.SpringInterceptor
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.databinding.ActivityAnimatortestBinding

class AnimatorTestActivity : BaseActivity<ActivityAnimatortestBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.start.setOnClickListener(View.OnClickListener {

            ObjectAnimator.ofObject(binding.pointView, "point", PointEvaluator(), Point(500f, 600f)).apply {
                duration = 2000
                interpolator = SpringInterceptor(0.6f)
                start()
            }
        })

        binding.btn.setOnClickListener({
//            showPropertyValuesHolderAnim()
            rotationAnim()
        })

    }


    /**PropertyValuesHolder这个类可以先将动画属性和值暂时的存储起来，后一起执行，在有些时候可以使用替换掉AnimatorSet，减少代码量 */
    private fun showPropertyValuesHolderAnim() {

        //keyframe
        //参数1为该关键帧处于动画的执行百分比
        // 参数2为该关键字的动画属性值
        val keyframe1: Keyframe = Keyframe.ofFloat(0.0f, 0f)
        val keyframe2: Keyframe = Keyframe.ofFloat(0.25f, -30f)
        val keyframe3: Keyframe = Keyframe.ofFloat(0.5f, 0f)
        val keyframe4: Keyframe = Keyframe.ofFloat(0.75f, 30f)
        val keyframe5: Keyframe = Keyframe.ofFloat(1.0f, 0f)
        val rotation: PropertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation", keyframe1, keyframe2, keyframe3, keyframe4, keyframe5)
        val alpha: PropertyValuesHolder = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.2f, 1.0f)
        val scaleX: PropertyValuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.2f, 1.0f)
        val scaleY: PropertyValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.2f, 1.0f)
        val color: PropertyValuesHolder = PropertyValuesHolder.ofInt("BackgroundColor", -0x100, -0xffff01)
        val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.btn, alpha, scaleX, scaleY, color, rotation)
        animator.interpolator = OvershootInterpolator()
        animator.setDuration(5000).start()
    }

    /**
     * 在2.5s内从初始位置旋转360度，然后再反向旋转2.5s后回到初始位置。如果用Keyframe该如何处理呢？从动画效果上来看，有3个时间点0s、2.5s、5s,也就是动画应该有三个关键帧，其动画百分比分别说0f、0.5f、1f,动画属性分别说0f、360f、0f。既然分析出了关键帧
     */
    private fun rotationAnim(){
        // 1. 创建Keyframe实例
// 参数1为该关键帧处于动画的执行百分比
// 参数2为该关键字的动画属性值
        val keyframe_0 = Keyframe.ofFloat(0f, 0f)
        val keyframe_1 = Keyframe.ofFloat(0.5f, 360f)
        val keyframe_2 = Keyframe.ofFloat(1f, 0f)


// 设置Keyframe的插值器
        keyframe_1.interpolator = LinearInterpolator()
        keyframe_2.interpolator = AccelerateDecelerateInterpolator()


//  2. 创建PropertyValuesHolder对象
        val holder: PropertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation", keyframe_0, keyframe_1, keyframe_2)

// 3. 创建ValueAnimator实例
        val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.btn, holder)
        animator.duration = 5000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }
}