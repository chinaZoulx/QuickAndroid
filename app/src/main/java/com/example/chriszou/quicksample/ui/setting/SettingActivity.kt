package com.example.chriszou.quicksample.ui.setting

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import android.widget.Toast
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.service.FloatService
import com.example.chriszou.quicksample.ui.AppListActivity
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_setting.*
import org.quick.component.QuickBroadcast
import org.quick.component.QuickActivity
import org.quick.component.QuickAndroid
import org.quick.component.QuickNotify


/**
 * @Author ChrisZou
 * @Date 2018/5/23-10:43
 * @Email chrisSpringSmell@gmail.com
 */
class SettingActivity : org.quick.library.b.BaseActivity() {

    companion object {
        const val REQUEST_CODE_SHORTCUT = 0x321
    }

    class ShortcutBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "添加成功啦", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResultLayoutResId(): Int = R.layout.activity_setting

    override fun onInit() {

    }

    override fun onInitLayout() {
//        versionTv.text = String.format("%s.%d", QuickAndroid.appVersionName, QuickAndroid.appVersionCode)
    }

    override fun onBindListener() {
        addShortcutTv.setOnClickListener {
            QuickNotify.notifyDesktopShortcut(QuickNotify.ShortcutBuilder()
//                .setActivity(javaClass.`package`.name,javaClass.simpleName)
                .setActivity("com.example.chriszou.quicksample","ui.setting.SettingActivity")
                .setShortcut("桌面快捷方式",BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher_round)),{ context, intent ->
//com.example.chriszou.quicksample.ui.setting
//                SettingActivity
                showToast("添加成功")
            })
        }
        openFloatWindowTv.setOnClickListener {
            startService(Intent(applicationContext, FloatService::class.java))
        }
        otherActivityTv.setOnClickListener {
            startActivity(QuickActivity.Builder(activity, OtherActivity::class.java)) { resultCode, data ->
                showToast("返回了")
            }
        }
        detailTv.setOnClickListener {
            QuickBroadcast.sendBroadcast(Intent(), "test", "test2", "MyCenterFragment")
        }
        checkUpgradeContainer.setOnClickListener { Beta.checkUpgrade() }
        appListTv.setOnClickListener { QuickActivity.Builder(this, AppListActivity::class.java).addParams(TITLE, "已安装列表").startActivity() }
    }

    override fun start() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SHORTCUT) {
            showToast("onActivityResult")
        }
    }
}