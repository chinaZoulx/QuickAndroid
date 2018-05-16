package com.example.chriszou.quicksample.ui.main

import android.content.Intent
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.main.index.IndexFragment
import com.example.chriszou.quicksample.ui.mycenter.DiscoverFragment
import com.example.chriszou.quicksample.ui.main.mycenter.MyCenterFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.chris.quick.b.BaseActivity

class MainActivity : BaseActivity() {
    override val isShowTitle: Boolean get() = false

    override fun onResultLayoutResId(): Int = R.layout.activity_main
    override fun onInit() {
        setBackInvalid()
    }

    override fun onInitLayout() {
        tabFragmentViewPager.setupData(supportFragmentManager, IndexFragment(), DiscoverFragment(), MyCenterFragment())
    }

    override fun onBindListener() {
        tabFragmentViewPager.setupBottomNavigationView(bottomNavigationView)
    }

    override fun start() {
        onRefresh()
    }

    private fun onRefresh() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (0 until tabFragmentViewPager.aAdapter?.getDataList()!!.size).map {
            tabFragmentViewPager.aAdapter?.getDataList()!![it]
        }.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }
}
