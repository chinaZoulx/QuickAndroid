package com.example.chriszou.quicksample.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.main.index.IndexFragment
import com.example.chriszou.quicksample.ui.mycenter.DiscoverFragment
import com.example.chriszou.quicksample.ui.main.mycenter.MyCenterFragment
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import org.chris.quick.b.BaseActivity
import org.chris.quick.b.BaseApplication
import org.chris.quick.service.DownloadService

class MainActivity : BaseActivity() {

    private var upgradeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (Beta.getUpgradeInfo().upgradeType) {
            //强制
                2 -> isOkDialog.alertIsOkDialog(String.format("发现新版本%s", Beta.getUpgradeInfo().versionName), Beta.getUpgradeInfo().newFeature, "", "马上更新", { _, isRight -> if (isRight) DownloadService.startAction(this@MainActivity, DownloadService.DownloadModel(getString(R.string.app_name) + "新版下载", Beta.getUpgradeInfo().apkUrl, 11, R.mipmap.ic_launcher)) })
            //建议
                else -> isOkDialog.alertIsOkDialog(String.format("发现新版本%s", Beta.getUpgradeInfo().versionName), Beta.getUpgradeInfo().newFeature, "暂不更新", "马上更新", { _, isRight -> if (isRight) DownloadService.startAction(this@MainActivity, DownloadService.DownloadModel(getString(R.string.app_name) + "新版下载", Beta.getUpgradeInfo().apkUrl, 11, R.mipmap.ic_launcher)) })
//            //手工
//                3 -> isOkDialog.alertIsOkDialog(String.format("发现新版本%s", Beta.getUpgradeInfo().versionName), Beta.getUpgradeInfo().newFeature, "暂不更新", "马上更新", { view, isRight -> Beta.startDownload()})
            }
            abortBroadcast()
        }
    }

    override val isShowTitle: Boolean get() = false
    override fun onResultLayoutResId(): Int = R.layout.activity_main
    override fun onInit() {
        registerReceiver(upgradeReceiver, IntentFilter(BaseApplication.APP_UPGRADE))
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
