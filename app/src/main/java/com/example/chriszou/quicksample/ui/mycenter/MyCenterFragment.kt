package com.example.chriszou.quicksample.ui.mycenter

import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseFragment

class MyCenterFragment : BaseFragment() {
    override fun onResultLayoutResId(): Int = R.layout.fragment_my_center
    override fun hasTitle(): Boolean = true
    override fun onInit() {

    }

    override fun onInitLayout() {
        setTitle("个人中心")
    }

    override fun onBindListener() {

    }

    override fun onBindData() {

    }
}