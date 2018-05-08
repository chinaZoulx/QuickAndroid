package org.chris.quick.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.chris.quick.m.Log
import java.lang.IllegalArgumentException


/**
 * 1、嵌套滚动兼容
 * 2、单击事件
 * 3、子View缩放兼容
 *
 * @author chrisZou
 * @blog http://blog.csdn.net/mcy478643968/article/details/19609407
 * @blog http://blog.csdn.net/leewenjin/article/details/21011841
 * @from http://blog.csdn.net/fy993912_chris/article/details/75006138
 * @email chrisSpringSmell@gmail.com
 */
class CustomCompatViewPager : ViewPager {
    private var onItemClickListener: OnItemClickListener? = null

    var curX = 0f
    var curY = 0f

    var downX = 0f
    var downY = 0f

    private var realSize = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setRealSize(realSize: Int) {
        this.realSize = realSize
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val curIndex = currentItem
        curX = ev.x
        curY = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = curX
                downY = curY
            }
            MotionEvent.ACTION_UP -> {
                if (Math.abs(downX - curX) <= CLICKED_DISTANCE && Math.abs(downY - curY) <= CLICKED_DISTANCE) {
                    onItemClick(adapter!!, curIndex)
                    return true
                }
                if (Math.abs(curX - downX) > Math.abs(curY - downY))
                    parent.requestDisallowInterceptTouchEvent(true)//true为拦截
                else
                    parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_MOVE -> if (Math.abs(curX - downX) > Math.abs(curY - downY))
                parent.requestDisallowInterceptTouchEvent(true)
            else
                parent.requestDisallowInterceptTouchEvent(false)
        }

        return super.onTouchEvent(ev)
    }

    /**
     * 解决viewpager嵌套ImageScaleView的问题
     *
     * @param ev
     * @return
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }

    /**
     * 单击
     */
    private fun onItemClick(adapter: PagerAdapter, currentItem: Int) {
        if (onItemClickListener != null) {
            if (realSize == 0)
                realSize = adapter.count
            onItemClickListener!!.onItemClick(adapter, currentItem % realSize, getChildAt(1))
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(adapter: PagerAdapter, currentItem: Int, itemView: View?)
    }

    class TransformerFactory {

        private object ClassHolder {
            val INSTANCE = TransformerFactory()
        }

        companion object {
            val instance: TransformerFactory
                get() = ClassHolder.INSTANCE
        }

        enum class TransformerType constructor(var value: Int) {
            Depth(0), ZoomOut(1), Flag(2), Flyme(3)
        }

        fun getTransformer(viewPager: ViewPager, type: TransformerType): ViewPager.PageTransformer = when (type) {
            TransformerType.Depth -> DepthPageTransformer()
            TransformerType.ZoomOut -> ZoomOutPageTransformer()
            TransformerType.Flag -> FlagPageTransformer()
            TransformerType.Flyme -> FlymePageTransformer(viewPager)
        }

        inner class DepthPageTransformer : ViewPager.PageTransformer {
            val MIN_SCALE = 0.75f
            override fun transformPage(view: View, position: Float) {
                val pageWidth = view.width

                when {
                    position < -1 -> // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        view.alpha = 0f
                    position <= 0 -> { // [-1,0]
                        // Use the default slide transition when moving to the left page
                        view.alpha = 1f
                        view.translationX = 0f
                        view.scaleX = 1f
                        view.scaleY = 1f

                    }
                    position <= 1 -> { // (0,1]
                        // Fade the page out.
                        view.alpha = 1 - position

                        // Counteract the default slide transition
                        view.translationX = pageWidth * -position

                        // Scale the page down (between MIN_SCALE and 1)
                        val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                        view.scaleX = scaleFactor
                        view.scaleY = scaleFactor

                    }
                    else -> // (1,+Infinity]
                        // This page is way off-screen to the right.
                        view.alpha = 0f
                }
            }
        }

        inner class ZoomOutPageTransformer : ViewPager.PageTransformer {
            private val MIN_SCALE = 0.85f
            private val MIN_ALPHA = 0.5f
            @SuppressLint("NewApi")
            override fun transformPage(view: View, position: Float) {
                val pageWidth = view.width
                val pageHeight = view.height

                Log.e("TAG", view.toString() + " , " + position + "")

                when {
                    position < -1 -> // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        view.alpha = 0f
                    position <= 1
                        //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
                    -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        if (position < 0) {
                            view.translationX = horzMargin - vertMargin / 2
                        } else {
                            view.translationX = -horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        view.scaleX = scaleFactor
                        view.scaleY = scaleFactor

                        // Fade the page relative to its size.
                        view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

                    }
                    else -> // (1,+Infinity]
                        // This page is way off-screen to the right.
                        view.alpha = 0f
                }
            }
        }

        inner class FlagPageTransformer : ViewPager.PageTransformer {
            private val MIN_SCALE = 0.85f
            private val MIN_ALPHA = 0.5f
            @SuppressLint("NewApi")
            override fun transformPage(view: View, position: Float) {
                val pageWidth = view.width
                val pageHeight = view.height

                Log.e("TAG", view.toString() + " , " + position + "")

                when {
                    position < -1 -> // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        view.alpha = 0f
                    position <= 1
                        //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
                    -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        if (position < 0) {
                            view.translationX = horzMargin - vertMargin / 2
                        } else {
                            view.translationX = -horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        view.scaleX = scaleFactor
                        view.scaleY = scaleFactor

                        // Fade the page relative to its size.
                        view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

                    }
                    else -> // (1,+Infinity]
                        // This page is way off-screen to the right.
                        view.alpha = 0f
                }
            }
        }

        inner class FlymePageTransformer(private val mViewPager: ViewPager) : ViewPager.PageTransformer {
            private var reduceX = 0.0f
            private var itemWidth = 0f
            private var offsetPosition = 0f
            private val mCoverWidth: Int = 0
            private val mScaleMax = 1.0f
            private val mScaleMin = 0.7f

            override fun transformPage(view: View, position: Float) {
                if (offsetPosition == 0f) {
                    val paddingLeft = mViewPager.paddingLeft.toFloat()
                    val paddingRight = mViewPager.paddingRight.toFloat()
                    val width = mViewPager.measuredWidth.toFloat()
                    offsetPosition = paddingLeft / (width - paddingLeft - paddingRight)
                }
                val currentPos = position - offsetPosition
                if (itemWidth == 0f) {
                    itemWidth = view.width.toFloat()
                    //由于左右边的缩小而减小的x的大小的一半
                    reduceX = (2.0f - mScaleMax - mScaleMin) * itemWidth / 2.0f
                }
                when {
                    currentPos <= -1.0f -> {
                        view.translationX = reduceX + mCoverWidth
                        view.scaleX = mScaleMin
                        view.scaleY = mScaleMin
                    }
                    currentPos <= 1.0 -> {
                        val scale = (mScaleMax - mScaleMin) * Math.abs(1.0f - Math.abs(currentPos))
                        val translationX = currentPos * -reduceX
                        when {
                            currentPos <= -0.5 -> //两个view中间的临界，这时两个view在同一层，左侧View需要往X轴正方向移动覆盖的值()
                                view.translationX = translationX + mCoverWidth * Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f
                            currentPos <= 0.0f -> view.translationX = translationX
                            currentPos >= 0.5 -> //两个view中间的临界，这时两个view在同一层
                                view.translationX = translationX - mCoverWidth * Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f
                            else -> view.translationX = translationX
                        }
                        view.scaleX = scale + mScaleMin
                        view.scaleY = scale + mScaleMin
                    }
                    else -> {
                        view.scaleX = mScaleMin
                        view.scaleY = mScaleMin
                        view.translationX = -reduceX - mCoverWidth
                    }
                }
            }
        }
    }

    companion object {

        val CLICKED_DISTANCE = 10
    }
}