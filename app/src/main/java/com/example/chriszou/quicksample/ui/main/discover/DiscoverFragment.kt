package com.example.chriszou.quicksample.ui.mycenter

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.TextView
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.test.TestListViewActivity
import com.example.chriszou.quicksample.ui.main.discover.DiscoverListFragment
import com.example.chriszou.quicksample.ui.search.InputFiltrateListActivity
import kotlinx.android.synthetic.main.fragment_discover.*
import org.quick.library.b.BaseFragment
import org.quick.library.b.activities.WebActivity
import org.chris.zxing.library.CaptureActivity
import org.chris.zxing.library.Intents
import org.chris.zxing.library.QRCodeParse
import org.quick.component.Log2
import org.quick.component.QuickBroadcast
import org.quick.component.QuickActivity
import org.quick.component.utils.HttpUtils
import org.quick.component.utils.ViewUtils

class DiscoverFragment : BaseFragment() {

    companion object {
        const val REQUEST_CODE_SCAN = 0x354
        const val REQUEST_CODE_FILTRATE = 0x355
        const val REQUEST_CODE_SELECTOR_IMG = 0x356
    }

    lateinit var discoverListFragment: DiscoverListFragment

    override fun onResultLayoutResId(): Int = R.layout.fragment_discover
    override val isShowTitle: Boolean
        get() = super.isShowTitle

    override fun onInit() {
        discoverListFragment = childFragmentManager.findFragmentById(R.id.discoverListFragment) as DiscoverListFragment
    }

    override fun onInitLayout() {
        setTitle("发现")
        ViewUtils.setupFitsSystemWindows(activity!!, appBarLayout)
    }

    override fun onBindListener() {
        QuickBroadcast.addBroadcastListener(this, { action, intent ->
            Log2.e("test", String.format("收到广播，action:%s", action))
        }, "MyCenterFragment")
        customCompatSwipeRefreshLayout.setOnRefreshListener {
            customCompatSwipeRefreshLayout.isRefreshing = true
            Handler().postDelayed({
                start()
                showToast("刷新完成")
                customCompatSwipeRefreshLayout.isRefreshing = false
            }, 1000)
        }

        scanIv.setOnClickListener {
            CaptureActivity.startActionScan(activity, REQUEST_CODE_SCAN, "请将二维码/条形码，置于框内")
        }
        filtrateIv.setOnClickListener {
            org.quick.library.function.SelectorImgActivity.startAction(activity, REQUEST_CODE_SELECTOR_IMG, "选择二维码图片", true)
        }

        for (index in 0..9) {
            getView<TextView>(ViewUtils.getViewId("searchKeyTv", index.toString())).setOnClickListener {
                when (it.id) {
                    R.id.searchKeyTv0 -> WebActivity.startAction(activity, "搜索", "http://www.taobao.com")
                    R.id.searchKeyTv1 -> QuickActivity.Builder(activity!!, TestListViewActivity::class.java).startActivity()
                    else -> WebActivity.startAction(activity, "搜索", "http://www.taobao.com")
                }
            }
        }

        filtrateTv.setOnClickListener {
            InputFiltrateListActivity.startAction(activity, REQUEST_CODE_FILTRATE, "搜索")
        }
    }

    override fun start() {
        val tempDataList = arrayListOf<String>()
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/ce/f4/a5/cef4a5cd12d4dbdc29f85bc4631c5c35.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/73/bd/10/73bd109d7301546b19dab0e2de593ecb.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/f5/9b/62/f59b627e3aaaa494ca8f248a81a861df.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/73/84/a8/7384a8726b81802b441e416f8c5fb578.jpg")
        tempDataList.add("错误链接")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/57/3e/4d/573e4d966410f05e89e399c9e7f50ff7.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/72/cb/f8/72cbf87a181df269b95d25c652cd16b3.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/49/84/2e/49842ec241511e2949a0b49111025997.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/9a/1b/82/9a1b823144f817d0014f52a467bd9e5d.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/93/28/be/9328bea7d0ce1783546e39978572fca3.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/28/69/2c/28692c85fe23a25f7109ceeb45b7ae82.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/29/db/6e/29db6e8f5a9c87438953284940762877.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/67/ef/01/67ef015cda467054c50f46f6325a213b.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/81/5b/bd/815bbd993860f47a8bff166445adc85d.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/40/1d/82/401d82455e2d2d83b0e891301b57d55a.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/ef/99/7d/ef997d7b1ab4774c2f6795cabb0d6996.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/e8/6c/1b/e86c1b74ec5d3cd9e32bb833c42bd920.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/e6/da/9f/e6da9f74915963c84ee7d5b0fa666c9f.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/06/1c/3a/061c3ac42f0ab4f699612057926d330b.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/24/9d/b9/249db9853430f0954e53ecb3f29d858d.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/c5/22/c4/c522c49496a315e7408439902887dbc4.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/33/52/95/335295727ee98f2158c3a810ca4e1d2f.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/49/54/af/4954af624f05e1e01cb93272904fddda.jpg")
        tempDataList.add("https://up.enterdesk.com/edpic_360_360/f4/f8/4f/f4f84ff78e2b01b2a466b4b721c81114.jpg")
        discoverListFragment.getAdapter<DiscoverListFragment.Adapter>()?.setDataList(tempDataList)
    }

    private fun parseQrCodeContent(content: String?) {
        when {
            HttpUtils.isHttpUrlFormRight(content!!) -> WebActivity.startAction(activity, "链接", content)
            else ->
                isOkDialog.alertIsOkDialog("二维码包含内容", content)
        }
    }

    override fun onResume() {
        super.onResume()
        customCompatSwipeRefreshLayout.setupAppBarLayout(appBarLayout)
    }

    override fun onStop() {
        super.onStop()
        customCompatSwipeRefreshLayout.removeAppBarLayout(appBarLayout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SCAN -> {
                if (resultCode == CaptureActivity.RESULT_OK) parseQrCodeContent(data?.getStringExtra(Intents.Scan.RESULT))
            }
            REQUEST_CODE_SELECTOR_IMG -> {
                if (resultCode == org.quick.library.function.SelectorImgActivity.RESULT_CODE) parseQrCodeContent(QRCodeParse.parseQRCode(BitmapFactory.decodeFile(data!!.getStringArrayListExtra(org.quick.library.function.SelectorImgActivity.ALREADY_PATHS)[0])))
            }
        }
    }
}