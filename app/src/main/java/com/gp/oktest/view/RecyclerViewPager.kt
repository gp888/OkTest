package com.gp.oktest.view

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gp.oktest.R
import android.widget.TextView


/*ViewPager有个天生的缺陷是View无法重用，此外ViewPager的滑动过程会频繁requestLayout，
尽管可以通过addViewInLayout和removeViewInLayout配合PagerAdapter 的startUpdate和finishUpdate可以减少重绘，
但在ListView和RecyclerView中仍然达不到最好的效果。因此，使用一种新的方式十分必要*/

class RecyclerViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : RecyclerView(context, attrs, defStyle), Handler.Callback {

    private lateinit var onPageChangeListener: OnPageChangeListener

    private val mRecyclerHandler: Handler
    private val MSG_PLAY_NEXT = 112
    @Volatile
    private var isPlaying = false
    private var lastIsPlayState = false
    private var realPosition = -1

    companion object {
        private val TASK_TIMEOUT: Long = 3000
    }

    var currentItem: Int
        get() {
            val linearLayoutManager = layoutManager as LinearLayoutManager
            return linearLayoutManager.findFirstVisibleItemPosition()
        }
        set(position) = setCurrentItem(position, true)

    init {
        mRecyclerHandler = Handler(Looper.getMainLooper(), this)
    }

    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener
        if (this.onPageChangeListener != null) {
            addOnScrollListener(this.onPageChangeListener)
            val currentItem = currentItem
            this.onPageChangeListener.onPageSelection(currentItem)
        }
    }

    fun setCurrentItem(position: Int, isAnimate: Boolean) {
        val adapter = adapter
        if (adapter == null || adapter.itemCount <= position) {
            return
        }
        if (!isAnimate) {
            scrollToPosition(position)
        } else {
            smoothScrollToPosition(position)
        }
    }


    override fun fling(velocityX: Int, velocityY: Int): Boolean {

        val linearLayoutManager = layoutManager as LinearLayoutManager

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        // views on the screen
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        val lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition)
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition)

        // distance we need to scroll
        val leftMargin = (screenWidth - lastView.width) / 2
        val rightMargin = (screenWidth - firstView.width) / 2 + firstView.width
        val leftEdge = lastView.left
        val rightEdge = firstView.right
        val scrollDistanceLeft = leftEdge - leftMargin
        val scrollDistanceRight = rightMargin - rightEdge

        val targetPosition: Int

        if (Math.abs(velocityX) < 1500) {
            // The fling is slow -> stay at the current page if we are less than half through,
            // or go to the next page if more than half through

            if (leftEdge > screenWidth / 2) {
                // go to next page
                smoothScrollBy(-scrollDistanceRight, 0)
                targetPosition = firstVisibleItemPosition

            } else if (rightEdge < screenWidth / 2) {
                // go to next page
                smoothScrollBy(scrollDistanceLeft, 0)
                targetPosition = firstVisibleItemPosition + 1
            } else {
                // stay at current page
                if (velocityX > 0) {
                    smoothScrollBy(-scrollDistanceRight, 0)
                } else {
                    smoothScrollBy(scrollDistanceLeft, 0)
                }
                targetPosition = firstVisibleItemPosition
            }
        } else {
            // The fling is fast -> go to next page

            if (velocityX > 0) {
                smoothScrollBy(scrollDistanceLeft, 0)
                targetPosition = firstVisibleItemPosition + 1
            } else {
                smoothScrollBy(-scrollDistanceRight, 0)
                targetPosition = firstVisibleItemPosition
            }

        }

        Log.e("RecyclerPagerView", "nextPage=$targetPosition")
        if (this.onPageChangeListener != null) {
            realPosition = targetPosition
            this.onPageChangeListener!!.onPageSelection(targetPosition)
        }
        return true
    }


    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            val linearLayoutManager = layoutManager as LinearLayoutManager

            val screenWidth = Resources.getSystem().displayMetrics.widthPixels

            val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
            val lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition)
            val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
            val firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition)

            // distance we need to scroll
            val leftMargin = (screenWidth - lastView.width) / 2
            val rightMargin = (screenWidth - firstView.width) / 2 + firstView.width
            val leftEdge = lastView.left
            val rightEdge = firstView.right
            val scrollDistanceLeft = leftEdge - leftMargin
            val scrollDistanceRight = rightMargin - rightEdge
            var targetPosition = -1
            if (leftEdge > screenWidth / 2) {
                smoothScrollBy(-scrollDistanceRight, 0)
                targetPosition = firstVisibleItemPosition + 1
            } else if (rightEdge < screenWidth / 2) {
                smoothScrollBy(scrollDistanceLeft, 0)
                targetPosition = lastVisibleItemPosition
            } else {
                targetPosition = firstVisibleItemPosition
            }
            if (this.onPageChangeListener != null) {
                realPosition = targetPosition
                this.onPageChangeListener!!.onPageSelection(targetPosition)
            }
        }

    }

    override fun handleMessage(msg: Message): Boolean {
        val what = msg.what
        when (what) {
            MSG_PLAY_NEXT -> showNextPage()
        }
        return false
    }

    private fun showNextPage() {
        if (!isPlaying) {
            return
        }
        if (!canRecyclePlaying()) {
            isPlaying = false
            return
        }
        val adapter = adapter
//        var currentItem = currentItem
        if (adapter != null && adapter.itemCount > 0) {
            if (currentItem == RecyclerView.NO_POSITION) {
                currentItem = 0
            } else {
                currentItem = currentItem + 1
            }
        }

        mRecyclerHandler.sendEmptyMessageDelayed(MSG_PLAY_NEXT, TASK_TIMEOUT)
    }

    fun startPlay() {
        if (isPlaying) {
            stopPlay()
        }
        if (!canRecyclePlaying()) {
            isPlaying = false
            return
        }

        isPlaying = true
        mRecyclerHandler.sendEmptyMessageDelayed(MSG_PLAY_NEXT, TASK_TIMEOUT)
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        super.setAdapter(adapter)

        if (canRecyclePlaying()) {
            if (realPosition == -1) {
                realPosition = 1000
            }
            setCurrentItem(realPosition, false)
        }
    }

    private fun canRecyclePlaying(): Boolean {
        val adapter = adapter
        return if (adapter == null || adapter.itemCount < 1) false else true
    }

    private fun stopPlay() {
        isPlaying = false
        mRecyclerHandler.removeMessages(MSG_PLAY_NEXT)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (lastIsPlayState) {
            startPlay()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lastIsPlayState = isPlaying
        stopPlay()
    }
}

abstract class OnPageChangeListener : RecyclerView.OnScrollListener() {
    abstract fun onPageSelection(position: Int)
}

class BannerAdapter constructor(val imagelist : MutableList<String>) : RecyclerView.Adapter<ViewPageHolder>() {
    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        holder.image.setImageResource(R.mipmap.ic_launcher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageHolder {
        return ViewPageHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_banner_image, parent, false))
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }
}

class ViewPageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var image: ImageView
    init {
        image = view.findViewById(R.id.image)
    }
}

class PagerChangeListener(private val tipTextView: TextView, private val size: Int) : OnPageChangeListener() {

    override fun onPageSelection(position: Int) {
        tipTextView.setText((position % size + 1).toString() + "/" + size)
    }
}