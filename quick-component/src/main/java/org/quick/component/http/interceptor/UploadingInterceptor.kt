package org.quick.component.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.quick.component.Log2
import org.quick.component.http.HttpService
import org.quick.component.utils.DateUtils

/**
 * 上传拦截器
 */
class UploadingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = DateUtils.getCurrentTimeInMillis()

        val response = chain.proceed(request)

        Log2.d(" ")
        Log2.d("----Uploading---")
        Log2.d("----url        = " + request.url().toString())

        val resultStr = try {
            String(response.body()!!.bytes())
        } catch (O_O: OutOfMemoryError) {
            "内存溢出"
        }

        if (request.method() == "POST")
            Log2.d(String.format("----params     = %s", LoggingInterceptor.parseRequest(request)))

        Log2.d(String.format("----result     = %s", resultStr))
        Log2.d(String.format("----Response---- %d ms", DateUtils.getCurrentTimeInMillis() - startTime))
        Log2.d(" ")

        return response.newBuilder()
                .body(ResponseBody.create(HttpService.mediaTypeJson, resultStr))
                .build()
    }
}