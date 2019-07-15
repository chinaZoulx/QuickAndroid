package com.example.chriszou.quicksample.ui.main.mycenter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.model.TestModel
import com.example.chriszou.quicksample.ui.bluetooth.BluetoothActivity
import com.example.chriszou.quicksample.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_my_center.*
import org.quick.library.b.BaseFragment
import org.quick.library.b.activities.ThemeActivity.Companion.TITLE
import org.chris.zxing.library.QRCodeParse
import org.quick.component.*
import org.quick.component.img.GlideApp
import org.quick.component.utils.DevicesUtils
import org.quick.component.utils.FormatUtils
import org.quick.component.utils.ImageUtils
import org.quick.library.callback.AppBarStateChangeListener
import java.io.File
import java.text.DecimalFormat

class MyCenterFragment : BaseFragment() {
    companion object {
        const val REQUEST_SELECTOR_COVER = 0x324
        const val COVER_PATH = "coverPath"
    }

    lateinit var qrCodeView: View

    override fun onResultLayoutResId(): Int = R.layout.fragment_my_center

    override fun onInit() = Unit

    override fun onInitLayout() {
        QuickBroadcast.addBroadcastListener(this, { action, _ ->
            Log2.e("test", String.format("收到广播，action:%s", action))
            true
        }, "MyCenterFragment")
        setTitle("个人中心")
        GlideApp.with(context!!).load(File(QuickSPHelper.getValue(COVER_PATH, ""))).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).transform(CircleCrop()).into(coverIv)
        customCompatSwipeRefreshLayout.setOnRefreshListener {
            QuickAsync.asyncDelay({ customCompatSwipeRefreshLayout.isRefreshing = false }, 500)
        }
        qrCodeView = LayoutInflater.from(activity).inflate(R.layout.include_qr_code, null)
        appBarLayout.setPadding(0, DevicesUtils.getStatusHeight(activity!!), 0, 0)
    }


    override fun onBindListener() {
        coverIv.setOnClickListener { org.quick.library.function.SelectorImgActivity.startAction(activity!!, REQUEST_SELECTOR_COVER, "选择头像", true) }
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
            isOkDialog.setCustomView(qrCodeView).defaultQuestions("二维码").show()
        }
        settingIv.setOnClickListener {
            //            ThemeActivity.startAction(activity, SettingActivity::class.java, "设置")
            startActivity(QuickActivity.Builder(activity, SettingActivity::class.java).addParams(TITLE, "设置")) { resultCode, data ->
                //do something...
            }
        }
        bluetoothTv.setOnClickListener {
            QuickActivity.Builder(activity, BluetoothActivity::class.java).addParams(TITLE, "蓝牙管理").startActivity { resultCode, data ->
                showToast("蓝牙返回了哟")
            }
        }
        broadcastTv.setOnClickListener {
            val intent = Intent()
            intent.putExtra("test", "广播1")
            QuickBroadcast.sendBroadcast(intent)
        }
        broadcastTv2.setOnClickListener {
            val intent = Intent()
            intent.putExtra("test", "广播2")
            QuickBroadcast.sendBroadcast(intent, "test2")
        }
        quickDialogTv.setOnClickListener {
            QuickDialog.Builder(activity!!, R.layout.dialog_is_ok).show()
                    .setText(R.id.msgTitleTv, "这是标题").setText(R.id.msgContentTv, "这是内容").setOnClickListener({view,viewHolder->
                        when (view.id) {
                            R.id.msgCancelBtn -> showToast("点击了取消按钮")
                            else -> showToast("点击了确定按钮")
                        }
                        QuickDialog.dismiss()
                    }, R.id.msgCancelBtn, R.id.msgOkBtn)
        }
        quickDialogTv2.setOnClickListener {
            QuickDialog.Builder(activity!!, R.layout.include_qr_code).show()
                    .setImg(R.id.qrCodeIv,R.mipmap.ic_launcher_round)
        }
        logTv.setOnClickListener {
            val testMode = TestModel()
            testMode.anInt = 10
            testMode.isMan = true
            testMode.setaDouble(12.0)
            testMode.setaFloat(11.0f)
            testMode.setaShort(12)
            testMode.string = "这是String"
            Log2.e(testMode)
        }
        quickRecyclerViewTv.setOnClickListener {
            QuickActivity.Builder(activity, QuickRecyclerViewActivity::class.java).startActivity()
        }
        QSwipeRefreshLayout2Tv.setOnClickListener {
            QuickActivity.Builder(activity, QSwipeRefreshLayoutActivity::class.java).startActivity()
        }
        screenShotTv.setOnClickListener {
            QuickAsync.asyncDelay({
                val tempBitmap = ImageUtils.screenshot(activity)
                QuickDialog.Builder(activity!!, R.layout.include_qr_code).setPadding(0, 0, 0, 0).setSize(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT).show().getImageView(R.id.qrCodeIv)?.setImageBitmap(tempBitmap)
            }, 1000)
        }

        fingerprintTv.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null) {
                QuickBiometric.showFingerprintDialog(activity!!) { type, resultMsg ->
                    when (type) {
                        QuickBiometric.TYPE.AuthenticationSucceeded -> {/*成功*/

                        }
                        QuickBiometric.TYPE.AuthenticationFailed -> {/*失败*/

                        }
                        else -> {/*其他提示消息*/
                        }
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null) {
            QuickBiometric.startFingerprintListener { type, resultMsg ->
                when (type) {
                    QuickBiometric.TYPE.AuthenticationSucceeded -> {/*成功*/

                    }
                    QuickBiometric.TYPE.AuthenticationFailed -> {/*失败*/

                    }
                    else -> {/*其他提示消息*/
                    }
                }
            }
        }

        checkAnimViewTv.setOnClickListener {
            QuickActivity.Builder(activity, CheckAnimActivity::class.java).startActivity()
        }

        bottomSheetDialogTv.setOnClickListener {
            val dialog = BottomSheetDialog(activity!!)
            dialog.setContentView(LayoutInflater.from(activity!!).inflate(R.layout.dialog_bottom_sheet, null))
            dialog.show()
        }
    }

    override fun start() {
        FormatUtils.numberSplit(1212123.45601245,6)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECTOR_COVER && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra(org.quick.library.function.SelectorImgActivity.ALREADY_PATHS)) {
                val imgList = data.getStringArrayListExtra(org.quick.library.function.SelectorImgActivity.ALREADY_PATHS)
                if (imgList != null && imgList.size > 0) {
                    QuickSPHelper.putValue(COVER_PATH, imgList[0])
                    GlideApp.with(context!!).load(File(imgList[0])).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).transform(CircleCrop()).dontAnimate().into(coverIv)
                }
            }
        }
    }
}