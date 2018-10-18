package org.quick.component.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import org.quick.component.QuickAndroid
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Created by chriszou on 16/9/28.
 */

object DevicesUtils {

    /**
     * 获取屏幕密度（DPI）
     *
     * @return 屏幕密度
     */
    val screenDensityDPI: Int
        get() {
            val dm = DisplayMetrics()
            return dm.densityDpi
        }

    /**
     * 获取设备最大内存,单位为字节(B)
     *
     * @return
     */
    val maxMemory: Int
        get() = Runtime.getRuntime().maxMemory().toInt()

    /**
     * 判断SD卡是否可用
     */
    val isSDcardOK: Boolean get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 获取设备版本号
     *
     * @return
     */
    val deviceLevel: String get() = Build.VERSION.RELEASE

    /**
     * 获取设备手机型号
     *
     * @return
     */
    val deviceModel: String
        get() = Build.MODEL

    /**
     * 获取设备版本号
     *
     * @return
     */
    val deviceSDK: Int
        get() = Build.VERSION.SDK_INT


    /**
     * 是否是wifi
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    fun isWifi(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * get window manager
     *
     * @param context
     * @return 返回窗口管理信息类，通过其可获得设备信息，example:屏幕高宽
     */
    fun getScreenDisplay(): Display = (QuickAndroid.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

    /**
     * 获取屏幕宽度,单位：PX
     *
     * @return The screen width
     */
    fun getScreenWidth(): Int {
        val outSize = Point()
        getScreenDisplay().getSize(outSize)
        return outSize.x
    }

    /**
     * 获取屏幕高度，单位：px
     *
     * @return The screen height
     */
    fun getScreenHeight(): Int {
        val outSize = Point()
        getScreenDisplay().getSize(outSize)
        return outSize.y
    }

    /**
     * 获取状态栏高度,单位：PX
     *
     * @param activity
     * @return 状态栏高度
     */
    fun getStatusHeight(activity: Activity): Int {
        var statusHeight = 0
        val localRect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(localRect)
        statusHeight = localRect.top
        if (0 == statusHeight) {
            val localClass: Class<*>
            try {
                localClass = Class.forName("com.android.internal.R\$dimen")
                val localObject = localClass.newInstance()
                val i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString())
                statusHeight = activity.resources.getDimensionPixelSize(i5)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return statusHeight
    }

    fun installAPK(apkFile: File): Boolean = if (apkFile.exists()) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri = FileProvider.getUriForFile(QuickAndroid.applicationContext, QuickAndroid.applicationContext.applicationInfo.processName + ".install.fileProvider", apkFile)
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (QuickAndroid.applicationContext.packageManager.queryIntentActivities(intent, 0).size > 0) {
            QuickAndroid.applicationContext.startActivity(intent)
        }
        true
    } else false

    /**
     * 获取SHA1码
     *
     * @param context
     * @return
     */
    @SuppressLint("PackageManagerGetSignatures")
    fun getSHA1(): String? {
        try {
            val info = QuickAndroid.applicationContext.packageManager.getPackageInfo(QuickAndroid.applicationContext.packageName, PackageManager.GET_SIGNATURES)
            val cert = info.signatures[0].toByteArray()
            val md = MessageDigest.getInstance("SHA1")
            val publicKey = md.digest(cert)
            val hexString = StringBuffer()
            for (i in publicKey.indices) {
                val appendString = Integer.toHexString(0xFF and publicKey[i].toInt()).toUpperCase(Locale.US)
                if (appendString.length == 1)
                    hexString.append("0")
                hexString.append(appendString)
            }
            return hexString.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }
        return null
    }

    /**
     * 关闭键盘事件
     *
     * @param activity
     */
    fun closeSoftInput(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 当前软键盘是否打开
     *
     * @return
     */
    fun isShowSoftInput(): Boolean {
        val inputMethodManager = QuickAndroid.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isActive
    }

    /**
     * 全面屏适配
     */
    fun compatMaxAspect() {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = QuickAndroid.applicationContext.packageManager.getApplicationInfo(QuickAndroid.applicationContext.packageName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        if (applicationInfo == null) {
            throw IllegalArgumentException(" get application info = null, has no meta data! ")
        }
        applicationInfo.metaData.putString("android.max_aspect", "2.1")
    }

    /**
     * 重启Launcher
     */
    fun restartSystemLauncher() {
        val am = QuickAndroid.applicationContext.getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        i.addCategory(Intent.CATEGORY_DEFAULT)
        val resolves = QuickAndroid.applicationContext.packageManager.queryIntentActivities(i, 0)
        for (res in resolves) {
            if (res.activityInfo != null) {
                am.killBackgroundProcesses(res.activityInfo.packageName)
            }
        }
    }
}
