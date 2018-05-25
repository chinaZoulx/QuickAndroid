package com.example.chriszou.quicksample.ui.setting

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.widget.Toast
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.service.FloatService
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_setting.*
import org.chris.quick.b.BaseActivity
import org.chris.quick.b.BaseApplication


/**
 * @Author ChrisZou
 * @Date 2018/5/23-10:43
 * @Email chrisSpringSmell@gmail.com
 */
class SettingActivity : BaseActivity() {

    companion object {
        val REQUEST_CODE_SHORTCUT = 0x321
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
        versionTv.text = String.format("%s.%d", BaseApplication.appVersionName, BaseApplication.appVersionCode)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindListener() {
        addShortcutTv.setOnClickListener {
            //            setupShortcut()
            if (Build.VERSION.SDK_INT >= 26) setupShortcutO() else setupShortcutOBefore()
        }
        openFloatWindowTv.setOnClickListener {
            startService(Intent(applicationContext, FloatService::class.java))
        }
        checkUpgradeContainer.setOnClickListener { Beta.checkUpgrade() }
    }

    override fun start() {

    }

    private fun setupShortcutO() {
        val shortcutIntent = Intent(Intent.ACTION_VIEW)
//        shortcutIntent.data = Uri.parse("https://www.baidu.com")//网络，自动打开链接
        shortcutIntent.component = ComponentName(packageName, "$packageName.ui.setting.SettingActivity")
        shortcutIntent.putExtra(TITLE, "设置")
        val shortcutManagerCompat = ShortcutInfoCompat.Builder(this, "shortcutId")
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ucrop_ic_angle))
                .setShortLabel("默认快捷方式")
                .setIntent(shortcutIntent)
                .setAlwaysBadged()
                .build()

        val pendingIntentCancel = PendingIntent.getBroadcast(this, REQUEST_CODE_SHORTCUT, Intent(this, ShortcutBroadcastReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        if (ShortcutManagerCompat.isRequestPinShortcutSupported(this))
            if (ShortcutManagerCompat.requestPinShortcut(this, shortcutManagerCompat, pendingIntentCancel.intentSender))
            else showToast("添加失败")
        else showToast("设备不支持")
    }

    private fun setupShortcutOBefore() {
        //添加action
        var shortcut = Intent("com.android.launcher.action.INSTALL_SHORTCUT")
        //shortcut的名字
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        //不允许重复添加
        shortcut.putExtra("duplicate", false);
        //设置shortcut的图标
        var icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.ucrop_shadow_upside)
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //设置用来启动的intent
        //如果用以下Intent会造成从快捷方式进入和从应用集合进入 会开启两遍SplashActivity的问题
        //解决的关键在于添加Action_Main
        //Intent intent = new Intent(this, SplashActivity.class);
        var comp = ComponentName(this.getPackageName(), this.getPackageName() + "." + this.getLocalClassName());
        var intent = Intent(Intent.ACTION_MAIN).setComponent(comp);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        //发送广播让Launcher接收来创建shortcut
        sendBroadcast(shortcut);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SHORTCUT) {
            showToast("onActivityResult")
        }
    }
}