package org.quick.component

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.Size
import android.support.v4.app.ActivityCompat
import android.view.View
import org.quick.component.utils.SystemActionManager

@SuppressLint("StaticFieldLeak")
object QuickPermissions {

    const val requestCode = 0x8566
    private var context: Context? = null
    private val permissionsChinese: Bundle = Bundle()
    private var onRequestPermissionsResultCallback: OnRequestPermissionCallback? = null

    init {
        permissionsChinese.putString(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入存储空间")
        permissionsChinese.putString(Manifest.permission.READ_EXTERNAL_STORAGE, "读取存储空间")
        permissionsChinese.putString(Manifest.permission.RECORD_AUDIO, "麦克风")
        permissionsChinese.putString(Manifest.permission.ACCESS_FINE_LOCATION, "位置信息")
        permissionsChinese.putString(Manifest.permission.CAMERA, "相机")
        permissionsChinese.putString(Manifest.permission.ACCESS_WIFI_STATE, "WIFI状态")
        permissionsChinese.putString(Manifest.permission.READ_CONTACTS, "读取联系人")
        permissionsChinese.putString(Manifest.permission.WRITE_CONTACTS, "写入联系人")
        permissionsChinese.putString(Manifest.permission.BATTERY_STATS, "电池")
        permissionsChinese.putString(Manifest.permission.BROADCAST_SMS, "短信通知")
        permissionsChinese.putString(Manifest.permission.CALL_PHONE, "拔打电话")
        permissionsChinese.putString(Manifest.permission.CALL_PRIVILEGED, "通话")
        permissionsChinese.putString(Manifest.permission.DELETE_PACKAGES, "删除应用")
        permissionsChinese.putString(Manifest.permission.INSTALL_PACKAGES, "安装应用")
        permissionsChinese.putString(Manifest.permission.READ_FRAME_BUFFER, "屏幕截图")
        permissionsChinese.putString(Manifest.permission.REBOOT, "重启设备")
        permissionsChinese.putString(Manifest.permission.RECEIVE_SMS, "接收短信")
        permissionsChinese.putString(Manifest.permission.SEND_SMS, "发送短信")
        permissionsChinese.putString(Manifest.permission.SET_ALARM, "设置闹钟")
        permissionsChinese.putString(Manifest.permission.BLUETOOTH_ADMIN, "蓝牙配对")
        permissionsChinese.putString(Manifest.permission.BLUETOOTH, "蓝牙")
        permissionsChinese.putString(Manifest.permission.MASTER_CLEAR, "软格式化")
        permissionsChinese.putString(Manifest.permission.INSTALL_SHORTCUT, "创建桌面快捷键")
        permissionsChinese.putString(Manifest.permission.UNINSTALL_SHORTCUT, "删除桌面快捷键")
        permissionsChinese.putString(Manifest.permission.SYSTEM_ALERT_WINDOW, "显示悬浮窗")
    }


    fun hasPermissions(@Size(min = 1) vararg perms: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return !perms.any {
            return@any ActivityCompat.checkSelfPermission(QuickAndroid.applicationContext, it) != PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermission(context: Context, @Size(min = 1) vararg permission: String, onRequestPermissionsResultCallback: OnRequestPermissionCallback) {
        this.context = context
        this.onRequestPermissionsResultCallback = onRequestPermissionsResultCallback
        if (context is Activity) {
            when {
                hasPermissions() -> onRequestPermissionsResultCallback.onPermissionsGranted(permission)
                permission.any { return@any ActivityCompat.shouldShowRequestPermissionRationale(context, it) } -> {
                    QuickDialog.Builder(context, R.layout.app_dialog_is_ok).show()
                            .setOnClickListener(View.OnClickListener { requestPermission(context, *permission) }, R.id.msgOkBtn)
                            .setOnClickListener(View.OnClickListener { QuickDialog.dismiss() }, R.id.msgCancelBtn)
                            .setText(R.id.titleTv, context.getString(R.string.permissionTitle))
                            .setText(R.id.contentTv, getPermissionChineseName(*permission))
                }
                else -> requestPermission(context, *permission)
            }
        }
    }

    private fun requestPermission(activity: Activity, vararg permission: String) {
        ActivityCompat.requestPermissions(activity, permission, requestCode)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == this.requestCode) {
            val granted = mutableListOf<String>()
            val denied = mutableListOf<String>()
            for (i in permissions.indices) {
                val perm = permissions[i]
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    granted.add(perm)
                else if (context is Activity && ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permissions[i]))
                    QuickDialog.Builder(context!!, R.layout.app_dialog_is_ok).show()
                            .setText(R.id.msgOkBtn, "设置", View.OnClickListener {
                                SystemActionManager.goAppDetailSettingIntent(context as Activity, requestCode)
                            })
                            .setOnClickListener(View.OnClickListener { QuickDialog.dismiss() }, R.id.msgCancelBtn)
                            .setText(R.id.titleTv, context!!.getString(R.string.permissionTitle))
                            .setText(R.id.contentTv, getPermissionChineseName(*permissions))
                else denied.add(perm)
            }

            if (denied.isEmpty())
                onRequestPermissionsResultCallback?.onPermissionsGranted(permissions)
            else
                onRequestPermissionsResultCallback?.onPermissionsDenied(denied.toTypedArray())
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode) this.onRequestPermissionsResultCallback?.onSettingResult(resultCode, data)
    }

    fun getPermissionChineseName(vararg permissions: String) = try {
        var tempPerm = ""
        for (perm in permissions)
            tempPerm += permissionsChinese.getString(perm) + ","
        if (tempPerm.endsWith(",")) tempPerm = tempPerm.substring(0, tempPerm.length - 1)
        tempPerm
    } catch (O_O: java.lang.Exception) {
        ""
    }

    fun getPermissionChineseName(perm: String): String = try {
        permissionsChinese.getString(perm)
    } catch (O_O: Exception) {
        ""
    }

    interface OnRequestPermissionCallback {
        fun onPermissionsDenied(perms: Array<out String>)
        fun onPermissionsGranted(perms: Array<out String>)
        fun onSettingResult(resultCode: Int, data: Intent?)
    }
}




