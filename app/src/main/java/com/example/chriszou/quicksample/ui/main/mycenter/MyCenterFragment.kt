package com.example.chriszou.quicksample.ui.main.mycenter

import android.content.Intent
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chris.work.zxing.android.CaptureActivity
import com.chris.work.zxing.encode.CodeCreator
import com.example.chriszou.quicksample.R
import com.jcodecraeer.xrecyclerview.AppBarStateChangeListener
import kotlinx.android.synthetic.main.fragment_my_center.*
import org.chris.quick.b.BaseFragment
import org.chris.quick.function.SelectorImgActivity
import org.chris.quick.m.glide.GlideCircleTransform
import java.io.File

class MyCenterFragment : BaseFragment() {
    companion object {
        const val REQUEST_SELECTOR_COVER = 0x324
    }

    lateinit var qrCodeView: View

    override fun onResultLayoutResId(): Int = R.layout.fragment_my_center
    override fun hasTitle(): Boolean = false
    override fun onInit() = Unit

    override fun onInitLayout() {
        setTitle("个人中心")
        customCompatSwipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({}, 500)
            customCompatSwipeRefreshLayout.isRefreshing = false
        }
        qrCodeView = LayoutInflater.from(activity).inflate(R.layout.include_qr_code, null)
    }

    override fun onBindListener() {
        coverIv.setOnClickListener { SelectorImgActivity.startAction(activity!!, REQUEST_SELECTOR_COVER, "选择头像", true) }
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                when (state) {
                    AppBarStateChangeListener.State.EXPANDED -> {
                        customCompatSwipeRefreshLayout.isEnabled = true
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
            qrCodeView.findViewById<ImageView>(R.id.qrCodeIv).setImageBitmap(CodeCreator.createQRCode("https://pro.modao.cc/app/eZPidskwaymptDBTA349hYBAA5kmXNl#screen=s827EF27EBF1522825072999"))
            isOkDialog.alertIsOkDialog("二维码", qrCodeView, "取消", "确定", { view, isRight ->

            })
        }
    }

    override fun onBindData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECTOR_COVER && resultCode == SelectorImgActivity.RESULT_CODE) {
            if (data != null && data.hasExtra(SelectorImgActivity.ALREADY_PATHS)) {
                val imgList = data.getStringArrayListExtra(SelectorImgActivity.ALREADY_PATHS)
                if (imgList != null && imgList.size > 0) {
                    Glide.with(context).load(File(imgList[0])).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).transform(GlideCircleTransform(context)).dontAnimate().into(coverIv)
                }
            }
        }
    }
}