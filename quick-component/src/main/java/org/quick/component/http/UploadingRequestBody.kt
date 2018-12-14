package org.quick.component.http

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.Okio
import org.quick.component.QuickAsync
import org.quick.component.http.callback.OnUploadingListener
import java.io.File

/**
 * 上传文件-请求体
 */
class UploadingRequestBody(var mediaType: MediaType, var key: String, var file: File, var onUploadingListener: OnUploadingListener<*>?) : RequestBody() {

    override fun contentType(): MediaType? = mediaType

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
        val source = Okio.source(file)
        val buffer = Buffer()
        var redBytesCount: Long
        var redBytesTotalCount = 0L

        var lastTime = 0L
        var tempTime: Long
        var isDone = false
        val byteCount = 2048L
        redBytesCount = source.read(buffer, byteCount)
        while (redBytesCount != -1L) {
            redBytesTotalCount += redBytesCount
            sink.write(buffer, redBytesCount)

            redBytesCount = source.read(buffer, byteCount)

            tempTime = System.currentTimeMillis() - lastTime
            if (tempTime > 100L) {/*间隔80毫秒触发一次，否则队列阻塞*/
                lastTime = System.currentTimeMillis()

                if (!isDone) {/*完成后只触发一次*/
                    isDone = redBytesCount == -1L
                    QuickAsync.runOnUiThread { onUploadingListener?.onLoading(key, redBytesTotalCount, contentLength(), isDone) }
                }
            }
        }
        if (!isDone) QuickAsync.runOnUiThread { onUploadingListener?.onLoading(key, redBytesTotalCount, contentLength(), true) }
    }

}