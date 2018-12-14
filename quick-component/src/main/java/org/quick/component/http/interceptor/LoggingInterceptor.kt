package org.quick.component.http.interceptor

import okhttp3.*
import org.quick.component.Log2
import org.quick.component.http.HttpService
import org.quick.component.utils.DateUtils

/**
 * 全局拦截器
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = DateUtils.getCurrentTimeInMillis()

        val response = chain.proceed(request)

        Log2.d(" ")
        Log2.d("----Request-----")
        Log2.d("----url        = " + request.url().toString())

        val resultStr = try {
            String(response.body()!!.bytes())
        } catch (O_O: OutOfMemoryError) {
            "内存溢出"
        }

        if (request.method() == "POST")
            Log2.d(String.format("----params     = %s", parseRequest(request)))

        Log2.d(String.format("----result     = %s", resultStr))
        Log2.d(String.format("----Response---- %d ms", DateUtils.getCurrentTimeInMillis() - startTime))
        Log2.d(" ")

        return response.newBuilder()
                .body(ResponseBody.create(HttpService.mediaTypeJson, resultStr))
                .build()
    }

    companion object {
        fun parseRequest(request: Request): String {
            var params = ""
            val requestBody = request.body()
            when (requestBody) {
                is FormBody -> {
                    params += parseFormBody(requestBody)
                }
                is MultipartBody -> {
                    requestBody.parts().forEach {
                        val body = it.body()
                        params += when (body) {
                            is FormBody -> parseFormBody(body)
                            else -> String.format("{ %s } ", it.headers()?.value(0))
                        }
                    }
                }
            }
            return params
        }

        fun parseFormBody(formBody: FormBody): String {
            var params = ""
            for (index in 0 until formBody.size())
                params += formBody.encodedName(index) + " = " + formBody.encodedValue(index) + " , "

            return String.format("{ %s }", if (params.length > 2) params.substring(0, params.length - 2) else params)
        }
    }
}