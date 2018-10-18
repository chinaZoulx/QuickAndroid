package org.quick.component

import android.annotation.SuppressLint
import android.content.Context
import org.quick.component.utils.DevicesUtils

@SuppressLint("StaticFieldLeak")
/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/6-10:30
 * @email chrisSpringSmell@gmail.com
 */
object QuickAndroid {

    /**
     * app的基础名称，用于配置SharedPreferences
     */
    var appBaseName = QuickConfigConstant.APP_BASE_NAME
    var defenseClickTime = 300L
    var screenWidth = 0
    var screenHeight = 0
    lateinit var applicationContext: Context

    fun init(applicationContext: Context) {
        this.applicationContext = applicationContext.applicationContext
        appBaseName = applicationContext.packageName
        screenWidth = DevicesUtils.getScreenWidth()
        screenHeight = DevicesUtils.getScreenHeight()
    }

    /**
     * 设置组件是否输出日志
     */
    fun setDebug(isDebug: Boolean) {
        Log2.isDebug = isDebug
    }

    fun resetInternal() {
        QuickSPHelper.clearAll()
        QuickBroadcast.resetInternal()
        QuickActivity.resetInternal()
        QuickDialog.resetInternal()
    }
}