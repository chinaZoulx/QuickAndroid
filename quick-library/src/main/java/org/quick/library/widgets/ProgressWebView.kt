package org.quick.library.widgets

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AbsoluteLayout
import android.widget.ProgressBar
import android.widget.Toast

import org.quick.library.R
import org.quick.component.utils.FormatUtils

import java.net.URISyntaxException

/**
 * 带进度条的WebView
 */
class ProgressWebView(context: Context, attrs: AttributeSet) : WebView(context, attrs) {
    private val progressbar: ProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)

    init {
        progressbar.layoutParams = AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, FormatUtils.formatDip2Px(2f).toInt(), 0, 0)
        progressbar.progressDrawable = ContextCompat.getDrawable(context, R.drawable.layer_list_progress_bar)
        addView(progressbar)
        webViewClient = MyWebViewClient()
        webChromeClient = object : MyWebChromeClient() {
            @SuppressLint("SetJavaScriptEnabled")
            override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
                view.settings.javaScriptEnabled = true
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }
        }
    }

    private open inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                progressbar.visibility = View.GONE
            } else {
                if (progressbar.visibility == View.GONE)
                    progressbar.visibility = View.VISIBLE
                progressbar.progress = newProgress
                progressbar.postInvalidate()
            }
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp = progressbar.layoutParams as AbsoluteLayout.LayoutParams
        lp.x = l
        lp.y = t
        progressbar.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            //handler.cancel(); 默认的处理方式，WebView变成空白页
            //handler.process();接受证书
            //handleMessage(Message msg); 其他处理
            progressbar.visibility = View.GONE
            //允许https://的访问
            handler.proceed()
        }
    }

    companion object {
        /**
         * 打开第三方应用
         *
         * @param url
         * @return
         */
        fun supportIntentAndScheme(activity: Activity?, url: String?): Boolean {
            if (TextUtils.isEmpty(url)) return false
            try {
                //处理intent协议
                if (url!!.startsWith("intent://")) {
                    val intent: Intent
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        intent.addCategory("android.intent.category.BROWSABLE")
                        intent.component = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            intent.selector = null
                        }
                        val resolves = activity?.packageManager?.queryIntentActivities(intent, 0)
                        if (resolves != null && resolves.size > 0) {
                            activity.startActivityIfNeeded(intent, -1)
                        }
                        return true
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }

                } else if (!url.startsWith("http")) {
                    try {
                        // 以下固定写法
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        activity?.startActivity(intent)
                        return true
                    } catch (e: Exception) {
                        // 防止没有安装的情况
                        e.printStackTrace()
                        Toast.makeText(activity, "您所打开的App未安装！", Toast.LENGTH_LONG).show()
                    }
                } else {// 处理自定义scheme协议
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        if (intent.resolveActivity(activity?.packageManager) == null) return false
                        else {
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                            activity?.startActivity(intent)
                        }
                        return true
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        fun isScheme(url: String?): Boolean {
            val uri = Uri.parse(url)
            return uri.pathSegments != null && uri.pathSegments.size > 0
        }
    }
}
