package org.quick.library.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

/**
 * 快速构建ViewPager
 * Created by zoulx on 2017/11/14.
 */

class TabFragmentViewPager : androidx.viewpager.widget.ViewPager {
    var aAdapter: Adapter? = null

    init {
        offscreenPageLimit = 5
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setupData(fm: androidx.fragment.app.FragmentManager, vararg fragments: androidx.fragment.app.Fragment): Unit =
        if (aAdapter == null) {
            aAdapter = Adapter(fm, *fragments)
            adapter = aAdapter
        } else {
            aAdapter!!.setDataList(fragments.toMutableList())
        }

    fun setupBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
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

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }

    fun destroy(fm: androidx.fragment.app.FragmentManager) {
        aAdapter?.getDataList()?.forEach { fm.beginTransaction().remove(it).commitAllowingStateLoss() }
    }

    fun getDataList():MutableList<Fragment>?{
        return aAdapter?.getDataList()
    }

    fun getItem(position: Int): Fragment? {
        return aAdapter?.getItem(position)
    }

    class Adapter(fm: androidx.fragment.app.FragmentManager, vararg fragments: androidx.fragment.app.Fragment) :
        androidx.fragment.app.FragmentPagerAdapter(fm) {
        private var dataList: MutableList<androidx.fragment.app.Fragment>? = null

        init {
            this.dataList = fragments.toMutableList()
        }

        fun setDataList(dataList: MutableList<androidx.fragment.app.Fragment>) {
            this.dataList = dataList
            notifyDataSetChanged()
        }

        fun getDataList() = dataList

        override fun getItem(position: Int): androidx.fragment.app.Fragment = dataList!![position]

        override fun getCount(): Int = if (dataList == null) 0 else dataList!!.size
    }
}