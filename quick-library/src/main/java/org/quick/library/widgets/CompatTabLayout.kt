package org.quick.library.widgets

import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.Size
import com.google.android.material.tabs.TabLayout
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import org.quick.library.R
import org.quick.component.utils.FormatUtils

/**
 * Created by zoulx on 2017/12/22.
 */

class CompatTabLayout : TabLayout {
    private val TT: IntArray = intArrayOf(
            org.quick.library.R.attr.tabIndicatorHeight,
            org.quick.library.R.attr.tabIndicatorColor,
            org.quick.library.R.attr.tabTextColor,
            org.quick.library.R.attr.tabSelectedTextColor,
            org.quick.library.R.attr.tabMode)

    init {
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context.obtainStyledAttributes(attrs, TT))
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun init(ta: TypedArray) {
//        if (ta.getBoolean(R.styleable.CustomCompatTabLayout_isDefaultStyle, true)) {
//        if (ta.getDimension(R.styleable.TabLayout_tabIndicatorHeight, -1F) == -1F)
//            setSelectedTabIndicatorHeight(AutoUtils.getPercentHeightSize(2))
//        if (ta.getColor(R.styleable.TabLayout_tabIndicatorColor, -1) == -1)
//            setSelectedTabIndicatorColor(ContextCompat.getColor(activity, R.color.colorPrimary))
//        if (ta.getInt(R.styleable.TabLayout_tabMode, -1) == -1)
//            tabMode = MODE_FIXED
//
//        var tabColor = ta.getColor(R.styleable.TabLayout_tabTextColor, -1)
//        var tabSelectedColor = ta.getColor(R.styleable.TabLayout_tabSelectedTextColor, -1)
//
//        if (tabColor == -1)
//            tabColor = ContextCompat.getColor(activity, R.color.colorBlack)
//        if (tabSelectedColor == -1)
//            tabSelectedColor = ContextCompat.getColor(activity, R.color.colorPrimary)
//
//        setTabTextColors(tabColor, tabSelectedColor)
//            setSelectedTabIndicatorHeight(FormatUtils.dip2px(2f).toInt())
//            setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.colorPrimary))
//            tabMode = MODE_FIXED
//            setTabTextColors(ContextCompat.getColor(context, R.color.black), ContextCompat.getColor(context, R.color.colorPrimary))
//        }
    }

    fun setupData(@Size(min =1)vararg strings: String) {
        if (childCount > 0) removeAllTabs()
        for (index in strings) addTab(newTab().setText(index))
    }

    fun setupData(onTabSelectedListener: OnTabSelectedListener, selectorPosition: Int,@Size(min =1) vararg strings: String) {
        if (childCount > 0) removeAllTabs()
        for (index in strings) addTab(newTab().setText(index))
        addOnTabSelectedListener(onTabSelectedListener)
        if (selectorPosition != 0) getTabAt(selectorPosition)!!.select()
    }
}
