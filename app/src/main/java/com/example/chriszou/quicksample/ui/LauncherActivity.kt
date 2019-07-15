package com.example.chriszou.quicksample.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.main.MainActivity
import com.yanzhenjie.permission.AndPermission
import org.quick.component.utils.PermissionUtils

class LauncherActivity : org.quick.library.b.BaseActivity() {
    companion object {
        const val REQUEST_PERMISSIONS_CODE = 0x23
        const val APP_SETTINGS_RC = 0x863
    }

    override fun onResultLayoutResId(): Int = R.layout.activity_launcher

    override fun onInit() {

    }

    override fun onInitLayout() {

    }

    override fun onBindListener() {

    }

    override fun start() {
        val perms = if (Build.VERSION.SDK_INT >=0)
            arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)//, Manifest.permission.INSTALL_SHORTCUT, Manifest.permission.UNINSTALL_SHORTCUT 发现请求这俩权限没卵用，设置开启之后这俩货还是一直返回未通过
        else arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
        AndPermission.with(this).runtime().permission(perms).onDenied {
            var permissions = ""
            for (index in 0 until it.size) {
                permissions += PermissionUtils.getPermissionChineseName(it[index]) + ";"
            }
            isOkDialog.setBlockBack(true)
                    .setTitle("请开启权限")
                    .setContent(String.format("%s", permissions.substring(0, permissions.length - 1)))
                    .setBtnLeft("退出APP")
                    .setBtnRight("马上设置")
                    .show { _, isRight ->
                        if (isRight)
                            startActivityForResult(
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                            .setData(Uri.fromParts("package", packageName, null)), APP_SETTINGS_RC)
                        else
                            finish()
                    }
        }.onGranted { launcher() }
                .start()
    }

    private fun launcher() {
//        val firstLoginDate = QuickSPHelper.getValue(BaseApplication.APP_FIRST_LOGIN_DATE, -1L)
        when {
//            firstLoginDate == -1L -> startAction(this, LoginActivity::class.java, "")
//            DateUtils.getCurrentTimeInMillis() - firstLoginDate > DateUtils.DAY * 7 -> startAction(this, LoginActivity::class.java, "")
            else -> startAction(this, MainActivity::class.java, getString(R.string.app_name))
        }
        finish()
    }
}