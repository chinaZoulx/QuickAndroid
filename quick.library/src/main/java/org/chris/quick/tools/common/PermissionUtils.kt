package org.chris.quick.tools.common

import android.Manifest
import android.os.Bundle

object PermissionUtils {

    private val permissionsChinese: Bundle = Bundle()

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

    fun getPermissionChineseName(perm: String): String = try {
        permissionsChinese.getString(perm)
    } catch (O_O: Exception) {
        ""
    }
}