package com.example.chriszou.quicksample.ui.main

import android.content.Intent
import android.os.Handler
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.main.index.IndexFragment
import com.example.chriszou.quicksample.ui.main.index.IndexListFragment
import com.example.chriszou.quicksample.ui.main.mycenter.MyCenterFragment
import com.example.chriszou.quicksample.ui.mycenter.DiscoverFragment
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import org.quick.component.Constant
import org.quick.component.QuickBroadcast
import org.quick.library.service.DownloadService

class MainActivity : org.quick.library.b.BaseActivity() {
    private var isExit = false
    override val isShowTitle: Boolean get() = false
    override fun onResultLayoutResId(): Int = R.layout.activity_main
    override fun onInit() {
        QuickBroadcast.addBroadcastListener(this,{action, intent ->
            when (Beta.getUpgradeInfo().upgradeType) {
            //强制
                2 -> isOkDialog.setTitle(String.format("发现新版本%s", Beta.getUpgradeInfo().versionName)).setContent(Beta.getUpgradeInfo().newFeature).setBtnRight("马上更新").show { _, isRight -> if (isRight) DownloadService.startAction(this@MainActivity, DownloadService.DownloadModel(getString(R.string.app_name) + "新版下载", Beta.getUpgradeInfo().apkUrl, 11, R.mipmap.ic_launcher)) }

            //建议
                else -> isOkDialog.setTitle(String.format("发现新版本%s", Beta.getUpgradeInfo().versionName)).setContent(Beta.getUpgradeInfo().newFeature).setBtnLeft("暂不更新").setBtnRight("马上更新").show { _, isRight -> if (isRight) DownloadService.startAction(this@MainActivity, DownloadService.DownloadModel(getString(R.string.app_name) + "新版下载", Beta.getUpgradeInfo().apkUrl, 11, R.mipmap.ic_launcher)) }

//            //手工
//                3 -> isOkDialog.alertIsOkDialog(String.format("发现新版本%s", Beta.getUpgradeInfo().versionName), Beta.getUpgradeInfo().newFeature, "暂不更新", "马上更新", { view, isRight -> Beta.startDownload()})
            }
            true
        }, Constant.APP_UPGRADE)

        setBackInvalid()
    }

    override fun onInitLayout() {
        tabFragmentViewPager.setupData(supportFragmentManager, IndexFragment(), DiscoverFragment(), RecyclerListFragment(),IndexListFragment(),MyCenterFragment())
    }

    override fun onBindListener() {
        tabFragmentViewPager.setupBottomNavigationView(bottomNavigationView)
        tabFragmentViewPager.offscreenPageLimit=3
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
            org.quick.library.b.application.ExitApplication.Companion.instance.exit()
        } else {
            showToast("再按一次退出程序")
            isExit = true
            Handler().postDelayed({
                isExit = false
            }, 2000)
        }
    }
}
