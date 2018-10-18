package org.quick.component.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ListView
import android.widget.Scroller
import org.quick.component.QuickAndroid
import java.lang.reflect.Field
import kotlin.experimental.and

object CommonUtils {

    private var mScroller: FixedSpeedScroller? = null

    /**
     * 设置ViewPager的滑动时间
     *
     * @param context
     * @param viewpager      ViewPager控件
     * @param DurationSwitch 滑动延时
     */
    fun controlViewPagerSpeed(viewpager: ViewPager, DurationSwitch: Int) {
        try {
            val mField: Field
            mField = ViewPager::class.java.getDeclaredField("mScroller")
            mField.isAccessible = true

            if (mScroller == null) {
                mScroller = FixedSpeedScroller(QuickAndroid.applicationContext, AccelerateInterpolator())
            }
            mScroller!!.setmDuration(DurationSwitch)
            mField.set(viewpager, mScroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获得字体宽
     */
    fun getFontWidth(paint: Paint, str: String?): Int {
        if (str == null || str == "")
            return 0
        val rect = Rect()
        val length = str.length
        paint.getTextBounds(str, 0, length, rect)
        return rect.width()
    }

    /**
     * 获得字体高度
     */
    fun getFontHeight(paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds("正", 0, 1, rect)
        return rect.height()
    }

    /**
     * 获得字体高度
     */
    fun getFontHeight(paint: Paint, txt: String): Int {
        val rect = Rect()
        paint.getTextBounds(txt, 0, 1, rect)
        return rect.height()
    }

    /**
     * @param listView
     * @scene 使用场景：当与其他父控件冲突不能正常计算大小时。example:ScrollView嵌套ListView
     * @description 设置指定listView的高度
     */
    fun setListViewHeightBasedOnChildren(listView: ListView) {
        // 获取ListView对应的Adapter
        val listAdapter = listView.adapter
        if (listAdapter == null || listAdapter.count <= 0) {
            return
        }
        var totalHeight = 0
        var i = 0
        val len = listAdapter.count
        while (i < len) {
            // listAdapter.getCount()返回数据项的数目
            val listItem = listAdapter.getView(i, null, listView)
            // 计算子项View 的宽高
            listItem.measure(0, 0)
            // 统计所有子项的总高度
            totalHeight += listItem.measuredHeight
            i++
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }



    /**
     * 获取两点间距离,单位：px
     *
     * @param x1 第一个点
     * @param x2 第二个点
     * @return
     * @formula |AB| = sqrt((X1-X2)^2 + (Y1-Y2)^2)
     */
    fun getDistance(x1: Point, x2: Point): Float {
        return getDistance(x1.x.toFloat(), x1.y.toFloat(), x2.x.toFloat(), x2.y.toFloat())
    }

    fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {

        return getDistance(Math.abs(x1 - x2), Math.abs(y1 - y2))
    }

    /**
     * The distance between two points
     *
     * @param x |x1-x2|
     * @param y |y1-y2|
     * @return
     */
    fun getDistance(x: Float, y: Float): Float {
        val distance = Math.sqrt((x * x + y * y).toDouble()).toFloat()
        return distance
    }

    /**
     * 设置ViewPager切换Item速度
     */
    class FixedSpeedScroller(context: Context, interpolator: Interpolator) : Scroller(context, interpolator) {

        private var mDuration = 1500 // 默认滑动速度 1500ms

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        /**
         * set animation time
         *
         * @param time
         */
        fun setmDuration(time: Int) {
            mDuration = time
        }

        /**
         * get current animation time
         *
         * @return
         */
        fun getmDuration(): Int {
            return mDuration
        }
    }


    /**
     * 将字节转换为十六进制的字符串（）
     *
     * @param bytesCommand
     * @return
     * @from 忘了
     */
    fun bytesToHexString(bytesCommand: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (bytesCommand == null || bytesCommand.isEmpty()) {
            return null
        }
        for (i in bytesCommand.indices) {
            val v = bytesCommand[i] and 0xFF.toByte()
            val hv = Integer.toHexString(v.toInt())
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }

    /**
     * 将十六进制的字符串转换成字节
     *
     * @param commandStr 7E 18 00 07 00 04 01 02 03 04 00 05 00 1A 7E
     * @return
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun parseCommand(commandStr: String): ByteArray {
        val tempStr = commandStr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val commands = ByteArray(tempStr.size)
        for (i in tempStr.indices) {
            try {
                commands[i] = Integer.parseInt(tempStr[i], 16).toByte()
            } catch (o_o: Exception) {
                commands[i] = 0
                Log.e("命令转换出错", tempStr[i])
            }

        }
        return commands
    }
}