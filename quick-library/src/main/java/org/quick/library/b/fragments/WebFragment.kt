package org.quick.library.b.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.ViewConfiguration
import android.webkit.*
import android.widget.TextView
import kotlinx.android.synthetic.main.app_fragment_web.*
import org.quick.library.R
import org.quick.library.b.BaseFragment
import org.quick.library.service.DownloadService
import org.quick.library.widgets.ProgressWebView
import org.quick.component.Log2
import org.quick.component.utils.DateUtils
import org.quick.component.utils.HttpUtils
import org.quick.component.utils.check.CheckUtils

/**
 * Created by work on 2017/9/22.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */
@SuppressLint("ValidFragment")
open class WebFragment @JvmOverloads constructor(private var baseUrl: String? = "") : BaseFragment() {

    private var webViewClient: WebViewClient? = null
    private var lastErrorUrl: String = ""
    private var errorView: View? = null

    override fun onResultLayoutResId() = R.layout.app_fragment_web
    override fun onInit() {
        webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (errorView != null) errorView!!.visibility = View.GONE
                if (ProgressWebView.isScheme(url)) ProgressWebView.supportIntentAndScheme(activity, url)
                else super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingDialog.dismiss()
                Log2.e("加载状态", "加载完成:$url")
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.url.toString()
                } else
                    request.toString()
                compat(view, url)
                return true
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                lastErrorUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) request.url.toString() else request.toString()
                Log2.e("加载状态", "加载错误：$lastErrorUrl")
                setError()
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                handler.proceed()
                Log2.e("加载状态", "SSL加载错误")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressWebView!!.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onInitLayout() {
        progressWebView.settings.setSupportZoom(true)// 设置可以支持缩放
        progressWebView.settings.builtInZoomControls = true// 设置出现缩放工具
        progressWebView.settings.useWideViewPort = true//扩大比例的缩放
        progressWebView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN//自适应屏幕
        progressWebView.settings.javaScriptEnabled = true

        progressWebView.settings.loadWithOverviewMode = true//和setUseWideViewPort(true)一起解决网页自适应问题
        progressWebView.settings.setAppCacheEnabled(true)//是否使用缓存
        progressWebView.settings.domStorageEnabled = true//DOM Storage
        progressWebView.settings.allowContentAccess = true
        progressWebView.webViewClient = webViewClient

        progressWebView.settings.pluginState = WebSettings.PluginState.ON//插件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    override fun start() {
        if (!TextUtils.isEmpty(baseUrl)) start(baseUrl)
    }

    override fun onBindListener() {

    }

    fun start(url: String?) {
        this.baseUrl = url
        when {
            TextUtils.isEmpty(url) -> showToast("链接不能为空")
            ProgressWebView.isScheme(url) -> ProgressWebView.supportIntentAndScheme(activity, url)
            HttpUtils.isHttpUrlFormRight(url!!) -> progressWebView?.loadUrl(url)
            else -> showToast("链接错误")
        }
    }

    private fun compat(view: WebView, url: String): Boolean {
        Log2.e("加载状态", "页面状态-Url：$url")
        return when {
            isAuthorizationUrl(url) && ProgressWebView.supportIntentAndScheme(activity, url)//支付
            -> true
            ProgressWebView.isScheme(url) -> ProgressWebView.supportIntentAndScheme(activity, baseUrl!!)
            url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")//正常链接
            -> if (url.endsWith(".apk")) {//apk下载链接
                downloadApk(url)
                true
            } else {
                start(url)
                true
            }
            else -> false
        }
    }

    private fun setError() {
        if (errorView == null) {
            errorView = layoutInflater.inflate(R.layout.include_no_msg, appContentContainer,false)
            appContentContainer.addView(errorView)
            errorView!!.setBackgroundResource(R.color.colorBg)
            getView<View>(R.id.refreshBtn, errorView!!).visibility = View.VISIBLE
            getView<View>(R.id.refreshBtn, errorView!!).setOnClickListener { start(lastErrorUrl) }
        }
        getView<TextView>(R.id.hintErrorTv).text = if (CheckUtils.isNetWorkAvailable()) "居然加载失败了！" else "网络无法连接，请检查"
        errorView!!.visibility = View.VISIBLE
    }

    /**
     * 是否是支付链接
     *
     * @param url
     * @return
     */
    private fun isAuthorizationUrl(url: String): Boolean =
            if (url.contains("weixin://wap/pay") || url.contains("platformapi/startapp")) {
                Log2.e("授权链接", "授权链接：$url")
                true
            } else {
                Log2.e("授权链接", "非授权链接：$url")
                false
            }

    private fun downloadApk(apkUrl: String) {
        if (CheckUtils.isWifi()) {
            isOkDialog.alertIsOkDialog("是否下载应用?", "取消", "下载") { _, isRight ->
                if (isRight) goDownload(apkUrl)
            }
        } else {
            isOkDialog.alertIsOkDialog("当前并非WIFI网络，下载会消耗您的流量，确定下载？", "取消", "任性下载") { _, isRight ->
                if (isRight) goDownload(apkUrl)
            }
        }
    }

    private fun goDownload(apkUrl: String) {
        DownloadService.startAction(activity!!, DownloadService.DownloadModel("下载应用", apkUrl, DateUtils.getCurrentTimeInMillis().toInt(), R.mipmap.ic_cloud_download_white))
    }

    fun onBackPressed(): Boolean =
            if (progressWebView.canGoBack()) {
                progressWebView.goBack()
                true
            } else false


    override fun onDestroy() {
        if (progressWebView != null) {
            progressWebView.visibility = View.GONE
            val timeout = ViewConfiguration.getZoomControlsTimeout()//timeout ==3000
            Handler().postDelayed({ progressWebView!!.destroy() }, timeout)
        }
        super.onDestroy()
    }

    companion object {
        val instance: WebFragment get() = getInstance("")

        fun getInstance(baseUrl: String): WebFragment = WebFragment(baseUrl)
    }
}
