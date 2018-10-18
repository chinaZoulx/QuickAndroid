package org.quick.library.widgets

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * 快速构建ViewPager
 * Created by zoulx on 2017/11/14.
 */

class ViewPagerTabFragment : ViewPager {
    var aAdapter: Adapter? = null

    init {
        offscreenPageLimit = 5
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setupData(fm: FragmentManager, vararg fragments: Fragment): Unit = if (aAdapter == null) {
        aAdapter = Adapter(fm, *fragments)
        adapter = aAdapter
    } else {
        aAdapter!!.setDataList(fragments.toMutableList())
    }

    fun setupBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = bottomNavigationView.menu.getItem(position).itemId
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        bottomNavigationView.setOnNavigationItemSelectedListener { menu ->
            for (i in 0..bottomNavigationView.menu.size()) {
                if (bottomNavigationView.menu.getItem(i).itemId == menu.itemId) {
                    currentItem = i
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    fun setupTabLayout(tabLayout: TabLayout) {
        setupTabLayout(tabLayout, 0)
    }

    fun setupTabLayout(tabLayout: TabLayout, selectorPosition: Int, vararg strings: String) {
        tabLayout.setupWithViewPager(this)
        if (tabLayout.childCount > 0) tabLayout.removeAllTabs()
        for (index in strings) tabLayout.addTab(tabLayout.newTab().setText(index))
        if (selectorPosition != 0) tabLayout.getTabAt(selectorPosition)!!.select()
    }

    fun destroy(fm: FragmentManager) {
        aAdapter?.getDataList()?.forEach { fm.beginTransaction().remove(it).commitAllowingStateLoss() }
    }

    class Adapter(fm: FragmentManager, vararg fragments: Fragment) : FragmentPagerAdapter(fm) {
        private var dataList: MutableList<Fragment>? = null

        init {
            this.dataList = fragments.toMutableList()
        }

        fun setDataList(dataList: MutableList<Fragment>) {
            this.dataList = dataList
            notifyDataSetChanged()
        }

        fun getDataList() = dataList

        override fun getItem(position: Int): Fragment = dataList!![position]

        override fun getCount(): Int = if (dataList == null) 0 else dataList!!.size
    }
}