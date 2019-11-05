package com.gp.oktest.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import java.util.*


class SoftKeyBroadManager @JvmOverloads constructor(private val activityRootView: View, var isSoftKeyboardOpened: Boolean = false)
    : ViewTreeObserver.OnGlobalLayoutListener {

    private val listeners = LinkedList<SoftKeyboardStateListener>()
    var lastSoftKeyboardHeightInPx: Int = 0
        private set

    interface SoftKeyboardStateListener {
        fun onSoftKeyboardOpened(keyboardHeightInPx: Int)
        fun onSoftKeyboardClosed()
    }

    init {
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val r = Rect()
        activityRootView.getWindowVisibleDisplayFrame(r)

        val heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top)

        if (!isSoftKeyboardOpened && heightDiff > 500) {
            // 如果高度超过500 键盘可能被打开
            isSoftKeyboardOpened = true
            notifyOnSoftKeyboardOpened(heightDiff)
        } else if (isSoftKeyboardOpened && heightDiff < 500) {
            isSoftKeyboardOpened = false
            notifyOnSoftKeyboardClosed()
        }
    }

    fun addSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.add(listener)
    }

    fun removeSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.remove(listener)
    }

    private fun notifyOnSoftKeyboardOpened(keyboardHeightInPx: Int) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx

        for (listener in listeners) {
            listener.onSoftKeyboardOpened(keyboardHeightInPx)
        }
    }

    private fun notifyOnSoftKeyboardClosed() {
        for (listener in listeners) {
            listener.onSoftKeyboardClosed()
        }
    }

}