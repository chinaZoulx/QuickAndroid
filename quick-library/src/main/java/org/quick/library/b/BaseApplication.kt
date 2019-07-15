package org.quick.library.b

import android.content.Context
import android.widget.Toast
import androidx.multidex.MultiDex
import com.tencent.bugly.crashreport.CrashReport
import com.zzhoujay.richtext.RichText
import org.litepal.LitePal
import org.quick.component.*
import org.quick.component.http.HttpService
import org.quick.component.http.callback.OnRequestStatusCallback
import org.quick.component.utils.DevicesUtils
import org.quick.component.utils.GsonUtils
import org.quick.library.b.application.ExitApplication
import org.quick.library.config.UrlPath
import org.quick.library.model.BaseModel

/**
 * @author chris
 * @date 16/9/23
 * @from chris-16/9/23
 */

abstract class BaseApplication : ExitApplication() {

    override fun onCreate() {
        QuickAndroid.init(this)
        super.onCreate()

        initBuggly()
        initRoute()
        initHttpService()
        initRichText()
        initDB()

        onInit()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    private fun initRoute() {

    }

    private fun initHttpService() {
        HttpService.Config.addParams("source", "android")
            .addParams("token", QuickSPHelper.getValue(Constant.APP_TOKEN, ""))
            .method(false)
            .baseUrl(UrlPath.baseUrl)
            .setOnRequestStatusCallback(object : OnRequestStatusCallback {
                override fun onFailure(e: Throwable, isNetworkError: Boolean) {
                    if (isNetworkError) QuickToast.showToastDefault("网络异常")
                    else QuickToast.showToastDefault("服务器异常")
                }

                override fun onErrorParse(data: String) {
                    val baseModel = GsonUtils.parseFromJson<BaseModel>(data)
                    if (baseModel != null) {
                        when (baseModel.status) {
                            Constant.APP_ERROR_NO_LOGIN -> {
                                QuickToast.Builder().setDuration(Toast.LENGTH_LONG).showToast("请登录后再操作")
                            }
//                            else ->
//                                QuickToast.showToastDefault("服务器异常")
                        }
                    } else QuickToast.showToastDefault("数据异常")
                }
            })
    }

    /**
     * 初始化Bugly
     */
    open fun initBuggly() {
        val context = applicationContext
        // 获取当前包名
        val packageName = context.packageName
        // 获取当前进程名
        val processName = DevicesUtils.getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        // 初始化Bugly
        CrashReport.initCrashReport(context, onResultBugglyAppId(), Log2.isDebug, strategy)
        //如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        //        CrashReport.initCrashReport(activity, strategy);
    }

    open fun onResultBugglyAppId(): String {
        return "c4f6d9153c"
    }

    fun initRichText() {
        RichText.initCacheDir(this)
    }

    fun initDB() {
        LitePal.initialize(this)
    }

    abstract fun onInit()
}
