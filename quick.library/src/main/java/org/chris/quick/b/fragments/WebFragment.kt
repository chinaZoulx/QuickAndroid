package org.chris.quick.b.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.ViewConfiguration
import android.webkit.*
import kotlinx.android.synthetic.main.app_fragment_web.*
import org.chris.quick.R
import org.chris.quick.b.BaseFragment
import org.chris.quick.m.Log
import org.chris.quick.service.DownloadService
import org.chris.quick.tools.DateUtils
import org.chris.quick.tools.common.DevicesUtils
import org.chris.quick.widgets.ProgressWebView

/**
 * Created by work on 2017/9/22.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */
@SuppressLint("ValidFragment")
open class WebFragment @JvmOverloads constructor(url: String = "") : BaseFragment() {
    private var baseUrl: String? = url
    private var webViewClient: WebViewClient? = null

    override fun onResultLayoutResId() = R.layout.app_fragment_web
    override fun onInit() {
        webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("加载状态", "开始加载:$url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingDialog.dismiss()
                Log.e("加载状态", "加载完成:$url")
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.url.toString()
                } else
                    request.toString()
                compat(view, url)
                //                else {
                //                    return super.shouldOverrideUrlLoading(view, request);//若无法调起app，网站将跳转到app下载页面，需网站支持
                //                }
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (compat(view, url)) {
                    true
                } else
                    true
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                val url = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) request.url.toString() else request.toString()
                Log.e("加载状态", "加载错误：$url")
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                handler.proceed()
                Log.e("加载状态", "SSL加载错误")
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
    }

    override fun onBindData() {
        start(baseUrl)
    }

    override fun onBindListener() {

    }

    fun start(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            this.baseUrl = url
            if (progressWebView != null) progressWebView!!.loadUrl(url)
        }
    }

    private fun compat(view: WebView, url: String): Boolean {
        Log.e("加载状态", "页面状态-Url：$url")
        var flag = false
        if (isAuthorizationUrl(url) && ProgressWebView.Companion.supportIntentAndScheme(activity!!, url)) {
            return true
        } else if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {//正常Url
            flag = if (url.endsWith(".apk")) {//apk下载链接
                downloadApk(url)
                true
            } else {
                view.loadUrl(url)
                true
            }
        } else if (ProgressWebView.Companion.supportIntentAndScheme(activity!!, baseUrl!!)) {//是否成功打开app
            return true
        } else if (url.contains("tbopen") || url.contains("tmall")) {//拦截
            flag = true
        }
        return flag
    }

    /**
     * 是否是支付链接
     *
     * @param url
     * @return
     */
    private fun isAuthorizationUrl(url: String): Boolean =
            if (url.contains("weixin://wap/pay") || url.contains("platformapi/startapp")) {
                Log.e("授权链接", "授权链接：$url")
                true
            } else {
                Log.e("授权链接", "非授权链接：$url")
                false
            }


    private fun goDownload(apkUrl: String) {
        DownloadService.startAction(activity!!, DownloadService.DownloadModel("下载应用", apkUrl, DateUtils.getCurrentTimeInMillis().toInt(), R.mipmap.ic_cloud_download_white))
    }

    private fun downloadApk(apkUrl: String) {
        if (DevicesUtils.isWifi()) {
            isOkDialog.alertIsOkDialog("是否下载应用?", "取消", "下载") { _, isRight ->
                if (isRight) goDownload(apkUrl)
            }
        } else {
            isOkDialog.alertIsOkDialog("当前并非WIFI网络，下载会消耗您的流量，确定下载？", "取消", "土豪，不差流量") { _, isRight ->
                if (isRight) goDownload(apkUrl)
            }
        }
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

        const val URL_KEY = "url"

        val instance: WebFragment
            get() = getInstance("")

        fun getInstance(baseUrl: String): WebFragment {
            val webFragment = WebFragment(baseUrl)
            if (!TextUtils.isEmpty(baseUrl)) {
                val bundle = Bundle()
                bundle.putString(URL_KEY, baseUrl)
                webFragment.arguments = bundle
            }
            return webFragment
        }
    }
}
