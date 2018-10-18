package org.quick.component.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import org.quick.component.QuickAndroid


import java.io.File

/**
 * 系统组件动作管理
 *
 * @author Chris zou
 * @Date 16/9/29
 * @modifyInfo1 chriszou-16/9/29
 * @modifyContent
 */
object SystemActionManager {

    val PHOTO_ALBUM = 0x1
    val CAMERA_CODE = 0x2

    /**
     * 调用系统拨号
     *
     * @param context
     * @param telNumber 号码
     */
    fun startActionCall(telNumber: String) {
        QuickAndroid.applicationContext.startActivity(actionCall(telNumber))
    }

    fun actionCall(telNumber: String): Intent {
        val intent = Intent()
        intent.action = "android.intent.action.DIAL"
        intent.data = Uri.parse("tel:$telNumber")
        return intent
    }

    /**
     * 调用系统相机
     *
     * @param activity
     * @param savePath 保存路径
     */
    fun startActionCamera(activity: Activity, savePath: String) {
        activity.startActivityForResult(actionCamera(savePath), CAMERA_CODE)
    }

    fun actionCamera(savePath: String): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (DevicesUtils.isSDcardOK) {
            //设定拍照存放到自己指定的目录,可以先建好
            val file = File(savePath)
            val pictureUri: Uri?

            pictureUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val contentValues = ContentValues(1)
                contentValues.put(MediaStore.Images.Media.DATA, file.absolutePath)
                QuickAndroid.applicationContext.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            } else {
                Uri.fromFile(file)
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
        }
        return intent
    }

    /**
     * 调用系统相册
     *
     * @param activity
     */
    fun startActionAlbum(activity: Activity) {
        activity.startActivityForResult(actionAlbum(), PHOTO_ALBUM)
    }

    fun actionAlbum(): Intent {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        return intent
    }

    /**
     * 浏览器
     */
    fun startActionBrowser(activity: Activity, url: String) {
        activity.startActivity(actionBrowser(url))
    }

    fun actionBrowser(url: String): Intent {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val content_url = Uri.parse(url)
        intent.data = content_url
        return intent
    }

    fun goAppDetailSettingIntent(activity: Activity, requestCode: Int) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", activity.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails")
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.packageName)
        }
        activity.startActivityForResult(localIntent, requestCode)
    }

}
