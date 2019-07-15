package org.quick.component.http

import org.quick.component.http.callback.*

class HttpServiceProxy(var builder: HttpService.Builder) : Call {

    /**
     * 异步执行
     */
    override fun <T> enqueue(callback: Callback<T>) {
        when (callback) {
            is OnDownloadListener -> {/*下载*/
                if (builder.isDownloadBreakpoint) {
                    if (builder.downloadStartIndex == 0L)
                        builder.downloadStartIndex = HttpService.getLocalDownloadLength(builder)

                    HttpService.downloadBreakpoint(builder, callback)/*断点下载*/
                } else
                    HttpService.downloadGet(builder, callback)
            }

            is OnUploadingListener -> {/*上传*/
                HttpService.uploadingWithJava(builder, callback)
            }

            else -> {/*普通请求*/
                if (builder.method == "GET")
                    HttpService.getWithJava(builder, callback)
                else
                    HttpService.postWithJava(builder, callback)
            }
        }
    }

    override fun cancel() {
        HttpService.cancelTask(builder().tag)
    }

    override fun builder(): HttpService.Builder {
        return builder
    }

    fun build(): Call {
        return this
    }
}