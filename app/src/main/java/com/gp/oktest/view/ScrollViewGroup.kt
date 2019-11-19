package com.gp.oktest.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.Scroller

class ScrollViewGroup @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attributeSet, defStyleAttr) {

    lateinit var scroller : Scroller
    init{
        scroller = Scroller(context)
    }
    private var lastX = 0
    private var currX = 0
    private var offX = 0

//    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        for (i in 0 until childCount) {
//            var v = getChildAt(i)
//            var childHeight = v.measuredHeight
//            var currentHeight = 0
//
//            v.layout(l, currentHeight, r + v.measuredWidth, currentHeight + childHeight)
//            currentHeight += childHeight
//        }
//    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x.toInt()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                currX = event.x.toInt()
                offX = currX - lastX
//                scrollBy(-offX, 0)

                scroller.startScroll(getScrollX(), 0, -offX, 0)
            }
            MotionEvent.ACTION_UP -> {
//                scrollTo(0, 0)

                scroller.startScroll(getScrollX(), 0, -100, 0);
            }
        }
        invalidate()
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0)
            invalidate();
        }
    }

}
