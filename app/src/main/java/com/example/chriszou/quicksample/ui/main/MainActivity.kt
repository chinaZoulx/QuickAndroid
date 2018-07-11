package com.example.chriszou.quicksample.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import com.example.chriszou.quicksample.MainApplication
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.main.index.IndexFragment
import com.example.chriszou.quicksample.ui.mycenter.DiscoverFragment
import com.example.chriszou.quicksample.ui.main.mycenter.MyCenterFragment
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import org.chris.quick.b.BaseActivity
import org.chris.quick.b.BaseApplication
import org.chris.quick.b.application.ExitApplication
import org.chris.quick.config.QuickConfigConstant
import org.chris.quick.service.DownloadService

class MainActivity : BaseActivity() {
    private var isExit = false
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
        registerReceiver(upgradeReceiver, IntentFilter(QuickConfigConstant.APP_UPGRADE))
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

    override fun onBackPressed() {
        if (isExit) {
            super.onBackPressed()
            ExitApplication.getInstance().exit()
        } else {
            showToast("再按一次退出程序")
            isExit = true
            Handler().postDelayed({
                isExit = false
            }, 2000)
        }
    }
}
