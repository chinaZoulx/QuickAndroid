package com.example.chriszou.quicksample.ui.main.index.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.Toolbar
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.setting.OtherActivity
import kotlinx.android.synthetic.main.activity_index_list_detail.*
import org.chris.quick.b.BaseActivity
import org.chris.quick.m.ImageManager
import org.chris.quick.tools.common.CommonUtils

class IndexListDetailActivity : BaseActivity() {
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

        CommonUtils.setupFitsSystemWindowsFromToolbar(this, appBaseToolbar)
    }

    override fun onBindListener() {
        coverIv.setOnClickListener { startActivity(Intent(activity,OtherActivity::class.java),{resultCode, data -> showToast("dfdfdf") }) }
    }

    override fun start() {
        ImageManager.loadImage(this, getTransmitValue("coverIv", ""), coverIv)
    }

    override fun onResume() {
        super.onResume()
        collapsingToolbarLayout.setExpandedTitleMargin(200,0,0,0)
    }
}