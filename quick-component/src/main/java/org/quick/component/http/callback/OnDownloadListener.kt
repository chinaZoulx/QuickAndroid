package org.quick.component.http.callback

import java.io.File

/**
 * 请求进度监听
 */
abstract class OnDownloadListener : OnProgressCallback, Callback<File>() {
}