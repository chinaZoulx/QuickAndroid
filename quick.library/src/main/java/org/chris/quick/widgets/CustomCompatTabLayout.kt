package org.chris.quick.widgets

import android.content.Context
import android.content.res.TypedArray
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.zhy.autolayout.utils.AutoUtils
import org.chris.quick.R

/**
 * Created by zoulx on 2017/12/22.
 */

class CustomCompatTabLayout : TabLayout {
    private val TT: IntArray = intArrayOf(
            org.chris.quick.R.attr.tabIndicatorHeight,
            org.chris.quick.R.attr.tabIndicatorColor,
            org.chris.quick.R.attr.tabTextColor,
            org.chris.quick.R.attr.tabSelectedTextColor,
            org.chris.quick.R.attr.tabMode)

    init {
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context.obtainStyledAttributes(attrs, TT))
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun init(ta: TypedArray) {
        if (ta.getBoolean(R.styleable.CustomCompatTabLayout_isDefaultStyle, true)) {
//        if (ta.getDimension(R.styleable.TabLayout_tabIndicatorHeight, -1F) == -1F)
//            setSelectedTabIndicatorHeight(AutoUtils.getPercentHeightSize(2))
//        if (ta.getColor(R.styleable.TabLayout_tabIndicatorColor, -1) == -1)
//            setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary))
//        if (ta.getInt(R.styleable.TabLayout_tabMode, -1) == -1)
//            tabMode = MODE_FIXED
//
//        var tabColor = ta.getColor(R.styleable.TabLayout_tabTextColor, -1)
//        var tabSelectedColor = ta.getColor(R.styleable.TabLayout_tabSelectedTextColor, -1)
//
//        if (tabColor == -1)
//            tabColor = ContextCompat.getColor(context, R.color.colorBlack)
//        if (tabSelectedColor == -1)
//            tabSelectedColor = ContextCompat.getColor(context, R.color.colorPrimary)
//
//        setTabTextColors(tabColor, tabSelectedColor)
            setSelectedTabIndicatorHeight(AutoUtils.getPercentHeightSize(2))
            setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary))
            tabMode = MODE_FIXED
            setTabTextColors(ContextCompat.getColor(context, R.color.colorBlack), ContextCompat.getColor(context, R.color.colorPrimary))
        }
    }

    fun setupData(vararg strings: String) {
        if (childCount > 0) removeAllTabs()
        for (index in strings) addTab(newTab().setText(index))
    }

    fun setupData(onTabSelectedListener: OnTabSelectedListener, selectorPosition: Int, vararg strings: String) {
        if (childCount > 0) removeAllTabs()
        for (index in strings) addTab(newTab().setText(index))
        addOnTabSelectedListener(onTabSelectedListener)
        if (selectorPosition != 0) getTabAt(selectorPosition)!!.select()
    }
}
