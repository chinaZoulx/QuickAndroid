package com.example.chriszou.quicksample.ui.main.mycenter

import android.content.Intent
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.bluetooth.BluetoothActivity
import com.example.chriszou.quicksample.ui.setting.SettingActivity
import com.jcodecraeer.xrecyclerview.AppBarStateChangeListener
import kotlinx.android.synthetic.main.fragment_my_center.*
import org.chris.quick.b.BaseFragment
import org.chris.quick.b.activities.ThemeActivity.Companion.TITLE
import org.chris.quick.function.QuickBroadcast
import org.chris.quick.function.QuickStartActivity
import org.chris.quick.function.SelectorImgActivity
import org.chris.quick.helper.QuickSharedPreferencesHelper
import org.chris.quick.m.glide.GlideCircleTransform
import org.chris.quick.tools.common.CommonUtils
import org.chris.quick.tools.common.ImageUtils
import org.chris.zxing.library.QRCodeParse
import java.io.File

class MyCenterFragment : BaseFragment() {
    companion object {
        const val REQUEST_SELECTOR_COVER = 0x324
        const val COVER_PATH = "coverPath"
    }

    lateinit var qrCodeView: View

    override fun onResultLayoutResId(): Int = R.layout.fragment_my_center

    override fun onInit() = Unit

    override fun onInitLayout() {
        QuickBroadcast.addBroadcastListener(this, { action, intent ->
            showToast(String.format("收到广播，action:%s", action))
        }, "MyCenterFragment")
        setTitle("个人中心")
        Glide.with(context).load(File(QuickSharedPreferencesHelper.getValue(COVER_PATH, ""))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).transform(GlideCircleTransform(context)).dontAnimate().into(coverIv)
        customCompatSwipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({ customCompatSwipeRefreshLayout.isRefreshing = false }, 500)
        }
        qrCodeView = LayoutInflater.from(activity).inflate(R.layout.include_qr_code, null)
        appBarLayout.setPadding(0, CommonUtils.getStatusHeight(activity), 0, 0)
    }

    override fun onBindListener() {
        coverIv.setOnClickListener { SelectorImgActivity.startAction(activity!!, REQUEST_SELECTOR_COVER, "选择头像", true) }
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                when (state) {
                    AppBarStateChangeListener.State.EXPANDED -> {
                        customCompatSwipeRefreshLayout.isEnabled = true
                        if (!titleTv.text.isEmpty())
                            titleTv.text = ""
                    }
                    AppBarStateChangeListener.State.COLLAPSED -> {//折叠起来 AppBarStateChangeListener.State.EXPANDED:展开 IDLE:折叠中
                        titleTv.text = "标题"
                    }
                    else -> {//折叠中
                        customCompatSwipeRefreshLayout.isEnabled = false
                        if (!titleTv.text.isEmpty())
                            titleTv.text = ""
                    }
                }
            }
        })
        qrCodeIv.setOnClickListener {
            qrCodeView.findViewById<ImageView>(R.id.qrCodeIv).setImageBitmap(QRCodeParse.createQRCode("这是一个二维码", ImageUtils.decodeSampledBitmapFromResource(resources, R.mipmap.bg_aboutus, 341, 341)))//, ImageUtils.decodeSampledBitmapFromResource(resources, R.mipmap.bg_aboutus, 341, 341)
            isOkDialog.alertIsOkDialog("二维码", qrCodeView, "取消", "确定", null)
        }
        settingIv.setOnClickListener {
            //            ThemeActivity.startAction(activity, SettingActivity::class.java, "设置")
            val intent = Intent(activity, SettingActivity::class.java)
            intent.putExtra(TITLE, "设置")
            QuickStartActivity.startActivity(activity, intent) { resultCode, data ->
                //do something...
            }
        }
        bluetoothTv.setOnClickListener {
            val intent = Intent(activity, BluetoothActivity::class.java)
            intent.putExtra(TITLE, "蓝牙管理")
            QuickStartActivity.startActivity(activity, intent) { resultCode, data ->
                showToast("蓝牙返回了哟")
            }
        }
        broadcastTv.setOnClickListener {
            val intent = Intent()
            intent.putExtra("test", "广播1")
            QuickBroadcast.sendBroadcast(intent, "test")
        }
        broadcastTv2.setOnClickListener {
            val intent = Intent()
            intent.putExtra("test", "广播2")
            QuickBroadcast.sendBroadcast(intent, "test2")
        }
    }

    override fun start() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECTOR_COVER && resultCode == SelectorImgActivity.RESULT_CODE) {
            if (data != null && data.hasExtra(SelectorImgActivity.ALREADY_PATHS)) {
                val imgList = data.getStringArrayListExtra(SelectorImgActivity.ALREADY_PATHS)
                if (imgList != null && imgList.size > 0) {
                    QuickSharedPreferencesHelper.putValue(COVER_PATH, imgList[0])
                    Glide.with(context).load(File(imgList[0])).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).transform(GlideCircleTransform(context)).dontAnimate().into(coverIv)
                }
            }
        }
    }
}