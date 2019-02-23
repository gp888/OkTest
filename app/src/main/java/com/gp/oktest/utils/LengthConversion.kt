package com.gp.oktest.utils

import android.util.TypedValue
import com.gp.oktest.App

object LengthConversion {
    private val displayMetrics get() = App.globalContext.resources.displayMetrics

    /**dp转px*/
    fun dp2px(dp: Float): Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()

    /**px转dp*/
    fun px2dp(px: Int): Float = px / displayMetrics.density
    //return TypedValue.complexToDimensionPixelOffset(px, displayMetrics)无用

    fun sp2px(sp: Float): Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, displayMetrics).toInt()

    fun px2sp(px: Int): Float = px / displayMetrics.scaledDensity
}