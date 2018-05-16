package com.example.chriszou.quicksample.ui.main.index

import android.support.v7.widget.Toolbar
import com.example.chriszou.quicksample.R
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.android.synthetic.main.include_btn_index.*
import org.chris.quick.b.BaseFragment

class IndexFragment : BaseFragment() {

    override fun onResultLayoutResId(): Int = R.layout.fragment_index
    override val isShowTitle: Boolean get() = true
//    override fun onResultToolbar(): Toolbar? = getView(R.id.toolbar)
    override fun onInit() {
        setParentMenu(R.menu.navigation, { menu ->
            when (menu?.itemId) {
                R.id.navigation0 -> showToast("首页")
                R.id.navigation1 -> showToast("发现")
                R.id.navigation3 -> showToast("我的")
            }
            true
        })
    }

    override fun onInitLayout() {
        setTitle("Index")
        customCompatSwipeRefreshLayout.isEnabled = false
    }

    override fun onBindListener() {
        fabContainer.setOnItemClickListener { tagView, position ->

        }
    }

    override fun onBindData() {

    }
}