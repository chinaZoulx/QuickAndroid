package org.quick.component.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.quick.component.Log2
import org.quick.component.http.HttpService
import org.quick.component.http.callback.OnProgressCallBack
import org.quick.component.http.ProgressResponseBody
import org.quick.component.utils.DateUtils


/**
 * 下载进度监听-拦截器
 */
class DownloadInterceptor(var builder: HttpService.Builder, var onProgressCallBack: OnProgressCallBack) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = DateUtils.getCurrentTimeInMillis()

        val originalResponse = chain.proceed(chain.request())

        Log2.d(" ")
        Log2.d("----Download----")
        Log2.d("----url        = " + request.url().toString())

        if (request.method() == "POST")
            Log2.d(String.format("----params     = %s", LoggingInterceptor.parseRequest(request)))

        Log2.d(String.format("----Response---- %d ms", DateUtils.getCurrentTimeInMillis() - startTime))
        Log2.d(" ")
        return originalResponse.newBuilder()
                .body(ProgressResponseBody(builder, originalResponse.body()!!, onProgressCallBack))
                .build()
    }
}