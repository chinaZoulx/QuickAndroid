package org.chris.quick

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.zhy.autolayout.config.AutoLayoutConifg
import com.zhy.http.okhttp.OkHttpUtils
import org.chris.quick.config.QuickConfigConstant
import org.chris.quick.helper.QuickSharedPreferencesHelper
import org.chris.quick.m.Log
import org.chris.quick.tools.common.DevicesUtils
import org.litepal.LitePal

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/6-10:30
 * @email chrisSpringSmell@gmail.com
 */
@SuppressLint("StaticFieldLeak")
object QuickAndroid {

    var width: Int = 0
    var height: Int = 0
    var maxMemory: Int = 0
    var appVersionCode: Int = 0
    private lateinit var applicationContext: Context
    lateinit var SHA1: String
    lateinit var deviceName: String
    lateinit var deviceBrand: String
    lateinit var appVersionName: String

    val getApplicationContext get() = applicationContext

    fun init(applicationContext: Context) {
        Log.setDebug(true)
        this.applicationContext = applicationContext
        QuickSharedPreferencesHelper.init(applicationContext, QuickConfigConstant.APP_BASE_NAME)
        LitePal.initialize(applicationContext)/* 数据库初始化 */
        AutoLayoutConifg.getInstance().useDeviceSize()/* 自动适配 */
        OkHttpUtils.getInstance()//初始化okHttp/* 网络 */
        initDeviceInfo()
        printDeviceInfo()
    }

    private fun initDeviceInfo() {
        width = DevicesUtils.getScreenWidth(applicationContext)
        height = DevicesUtils.getScreenHeight(applicationContext)
        maxMemory = DevicesUtils.getMaxMemory()
        SHA1 = DevicesUtils.getSHA1(applicationContext)
        deviceName = DevicesUtils.getDeviceModel()
        deviceBrand = Build.BRAND
        try {
            val packageInfo = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, PackageManager.GET_CONFIGURATIONS)
            appVersionName = packageInfo.versionName
            appVersionCode = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    /**
     * 输出手机基本信息
     */
    private fun printDeviceInfo() {
        Log.v(javaClass.simpleName, "--------------------------------设备信息 Input Start----------------------------")
        Log.v(javaClass.simpleName, "----------------------------width:$width height:$height")
        Log.v(javaClass.simpleName, "----------------------------maxMemory:$maxMemory")
        Log.v(javaClass.simpleName, "----------------------------DPI:" + DevicesUtils.getScreenDensityDPI())
        Log.v(javaClass.simpleName, "----------------------------level:" + DevicesUtils.getDeviceLevel())
        Log.v(javaClass.simpleName, "--------------------------------设备信息 Input End----------------------------")
    }

    fun clearQuickSharedPreferences() {
        QuickSharedPreferencesHelper.clearAll()
    }
}