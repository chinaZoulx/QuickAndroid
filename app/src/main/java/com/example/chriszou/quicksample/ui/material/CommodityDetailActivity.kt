package com.example.chriszou.quicksample.ui.material

import android.support.v4.view.ViewPager
import com.example.chriszou.quicksample.R
import com.leochuan.CenterSnapHelper
import com.leochuan.CircleLayoutManager
import com.leochuan.ViewPagerLayoutManager
import kotlinx.android.synthetic.main.activity_commodity_detail.*

/**
 * material design 示例
 * @Author ChrisZou
 * @Date 2018/6/7-14:52
 * @Email chrisSpringSmell@gmail.com
 */
class CommodityDetailActivity : org.quick.library.b.BaseActivity() {

    private lateinit var commodityBannerFragment: CommodityBannerFragment
    override val isShowTitle: Boolean get() = false

    override fun onResultLayoutResId(): Int = R.layout.activity_commodity_detail

    override fun onInit() {
        commodityBannerFragment = supportFragmentManager.findFragmentById(R.id.commodityBannerFragment) as CommodityBannerFragment
    }

    override fun onInitLayout() {
        val layoutManager = CircleLayoutManager(this)
        layoutManager.angleInterval = 60
        layoutManager.infinite = true
        layoutManager.setOnPageChangeListener(object : ViewPagerLayoutManager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                commodityBannerFragment.viewPager.currentItem = position % commodityListFragment.adapter.itemCount
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        commodityListFragment.layoutManager = layoutManager
        val adapter = CommodityListFragment.Adapter()
        val dataList = mutableListOf<Int>()
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        adapter.setDataList(dataList)
        commodityListFragment.adapter = adapter
        CenterSnapHelper().attachToRecyclerView(commodityListFragment)
    }

    override fun onBindListener() {
        commodityBannerFragment.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                commodityListFragment.scrollToPosition(position)
            }
        })
    }

    override fun start() {

    }
}