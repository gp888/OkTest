package com.gp.oktest.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gp.oktest.R
import com.gp.oktest.animator.Point
import com.gp.oktest.animator.PointEvaluator
import com.gp.oktest.animator.SpringInterceptor
import com.gp.oktest.base.BaseActivity
import kotlinx.android.synthetic.main.activity_animatortest.*
import kotlinx.android.synthetic.main.activity_minivideo.view.*

class AnimatorTest : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animatortest)

        start.setOnClickListener(View.OnClickListener {

            ObjectAnimator.ofObject(pointView, "point", PointEvaluator(), Point(500f, 600f)).apply {
                duration = 2000
                interpolator = SpringInterceptor(0.6f)
                start()
            }
        })

    }
}