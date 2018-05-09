package com.example.chriszou.quicksample.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.main.MainActivity
import org.chris.quick.b.BaseActivity
import org.chris.quick.tools.common.PermissionUtils
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class LauncherActivity : BaseActivity() {
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

    @AfterPermissionGranted(REQUEST_PERMISSIONS_CODE)
    override fun start() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) launcher() else EasyPermissions.requestPermissions(this, "App需要请求一些权限后，才能正常工作", REQUEST_PERMISSIONS_CODE, *perms)
    }

    private fun launcher() {
//        val firstLoginDate = SharedPreferencesHelper.getValue(BaseApplication.APP_FIRST_LOGIN_DATE, -1L)
        when {
//            firstLoginDate == -1L -> startAction(this, LoginActivity::class.java, "")
//            DateUtils.getCurrentTimeInMillis() - firstLoginDate > DateUtils.DAY * 7 -> startAction(this, LoginActivity::class.java, "")
            else -> startAction(this, MainActivity::class.java, getString(R.string.app_name))
        }
        finish()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            var permissions = ""
            for (index in 0 until perms.size) {
                permissions += PermissionUtils.getPermissionChineseName(perms[index]) + ";"
            }
            isOkDialog.alertIsOkDialog(false, "请开启权限", String.format("%s", permissions.substring(0, permissions.length - 1)), "退出APP", "马上设置", { _, isRight -> if (isRight) startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", packageName, null)), APP_SETTINGS_RC) else finish() })
        } else super.onPermissionsDenied(requestCode, perms)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == APP_SETTINGS_RC) {
            start()
        }
    }
}