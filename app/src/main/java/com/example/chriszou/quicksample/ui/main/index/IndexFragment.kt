package com.example.chriszou.quicksample.ui.main.index

import android.support.v7.widget.Toolbar
import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseFragment

class IndexFragment : BaseFragment() {
    override fun onResultLayoutResId(): Int = R.layout.fragment_index

    override fun hasTitle(): Boolean = true
    override fun onResultToolbar(): Toolbar? = getView(R.id.toolbar)
    override fun onInit() {
        setParentMenu(R.menu.navigation, { menu ->
            when(menu?.itemId){
                R.id.navigation0->showToast("首页")
                R.id.navigation1->showToast("发现")
                R.id.navigation3->showToast("我的")
            }
            true
        })
    }

    override fun onInitLayout() {
        setTitle("Index")
    }

    override fun onBindListener() {

    }

    override fun onBindData() {

    }
}