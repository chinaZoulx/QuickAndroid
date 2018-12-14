package org.quick.component.http.callback

import java.io.File
import java.io.IOException

/**
 * 请求进度监听
 */
interface OnDownloadListener : OnProgressCallBack {
    fun onStart() {}
    fun onFailure(e: Exception, isNetworkError: Boolean)
    fun onResponse(file: File)
    fun onEnd() {}
}