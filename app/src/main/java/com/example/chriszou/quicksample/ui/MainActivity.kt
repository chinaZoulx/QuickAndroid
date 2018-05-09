package com.example.chriszou.quicksample.ui

import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.index.IndexFragment
import com.example.chriszou.quicksample.ui.mycenter.DiscoverFragment
import com.example.chriszou.quicksample.ui.mycenter.MyCenterFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.chris.quick.b.BaseActivity

class MainActivity : BaseActivity() {
    override fun hasTitle(): Boolean = true
    override fun onResultLayoutResId(): Int = R.layout.activity_main
    override fun onInit() {

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
}
