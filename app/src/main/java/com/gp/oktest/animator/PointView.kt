package com.gp.oktest.animator

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PointView
    @JvmOverloads
    constructor(context:Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attributeSet, defStyleAttr) {

    var point: Point = Point(40f, 40f)
        set(value) {
            field = value
            invalidate()
        }

    val paint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(point.x, point.y, 30f, paint)
    }
}


data class Point(var x:Float, var y:Float)

//中间帧属性的计算
class PointEvaluator : TypeEvaluator<Point> {
    override fun evaluate(fraction: Float, startValue: Point, endValue: Point): Point {
        val x = startValue.x + (endValue.x - startValue.x) * fraction
        val y = startValue.y + (endValue.y - startValue.y) * fraction
        return Point(x, y)
    }
}

class SpringInterceptor(private val factor: Float) : TimeInterpolator {
    override fun getInterpolation(input: Float): Float {
        return (Math.pow(2.0, -10.0 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1).toFloat()
    }
}



