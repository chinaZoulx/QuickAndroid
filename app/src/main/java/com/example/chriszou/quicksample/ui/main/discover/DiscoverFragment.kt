package com.example.chriszou.quicksample.ui.mycenter

import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseFragment

class DiscoverFragment : BaseFragment() {
    override fun onResultLayoutResId(): Int = R.layout.fragment_discover
    override fun hasTitle(): Boolean = true
    override fun onInit() {

    }

    override fun onInitLayout() {
        setTitle("发现")
    }

    override fun onBindListener() {

    }

    override fun onBindData() {

    }
}