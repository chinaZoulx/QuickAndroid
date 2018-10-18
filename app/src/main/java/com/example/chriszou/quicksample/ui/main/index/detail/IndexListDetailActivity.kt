package com.example.chriszou.quicksample.ui.main.index.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.Toolbar
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.setting.OtherActivity
import kotlinx.android.synthetic.main.activity_index_list_detail.*
import org.quick.component.QuickActivity
import org.quick.component.utils.ViewUtils

class IndexListDetailActivity : org.quick.library.b.BaseActivity() {
    companion object {
        fun startAction(context: Context, title: String, coverUrl: String) {
            val intent = Intent(context, IndexListDetailActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra("coverUrl", coverUrl)
            context.startActivity(intent)
        }
    }

    override fun onResultLayoutResId(): Int = R.layout.activity_index_list_detail

    override val isUsingBaseLayout: Boolean
        get() = true
    override val isShowTitle: Boolean
        get() = true

    override fun onInit() {

    }

    override fun onResultToolbar(): Toolbar? = filtrateToolbar

    override fun onInitLayout() {
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE)

        ViewUtils.setupFitsSystemWindowsFromToolbar(activity,appBaseToolbar!!)
    }

    override fun onBindListener() {
        coverIv.setOnClickListener { startActivity(QuickActivity.Builder(activity,OtherActivity::class.java)) { resultCode, data -> showToast("dfdfdf") } }
    }

    override fun start() {
        org.quick.library.m.ImageManager.loadImage(this, getTransmitValue("coverIv", ""), coverIv)
    }

    override fun onResume() {
        super.onResume()
        collapsingToolbarLayout.setExpandedTitleMargin(200,0,0,0)
    }
}