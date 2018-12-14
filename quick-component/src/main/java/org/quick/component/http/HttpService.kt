package org.quick.component.http

import android.os.Bundle
import android.text.TextUtils
import android.util.SparseArray
import okhttp3.*
import org.quick.component.QuickAsync
import org.quick.component.callback.OnWriteListener
import org.quick.component.http.callback.OnDownloadListener
import org.quick.component.http.callback.OnRequestListener
import org.quick.component.http.callback.OnUploadingListener
import org.quick.component.http.interceptor.LoggingInterceptor
import org.quick.component.http.interceptor.DownloadInterceptor
import org.quick.component.http.interceptor.UploadingInterceptor
import org.quick.component.utils.FileUtils
import org.quick.component.utils.GsonUtils
import org.quick.component.utils.HttpUtils
import java.io.File
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeUnit

/**
 * 网络服务
 * 使用okHttp
 */
object HttpService {

    var config = Config()
    /**
     * json类型
     */
    val mediaTypeJson: MediaType?
        get() {
            return MediaType.parse("application/json; charset=" + config.encoding)
        }
    /**
     * File类型
     */
    val mediaTypeFile: MediaType?
        get() {
            return MediaType.parse("application/octet-stream; charset=" + config.encoding)
        }

    val taskCalls = SparseArray<Call>()
    /**
     * Cookie管理
     */
    val localCookieJar = LocalCookieJar()

    val normalClient by lazy {
        return@lazy OkHttpClient.Builder()
                .connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
                .readTimeout(config.readTimeout, TimeUnit.SECONDS)
                .writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(config.isRetryConnection)
                .addInterceptor(LoggingInterceptor())
                .cookieJar(localCookieJar)
                .followRedirects(true)
                .build()
    }

    val downloadClient by lazy {
        return@lazy OkHttpClient.Builder()
                .connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(config.isRetryConnection)
                .followRedirects(true)
                .cookieJar(localCookieJar)
    }

    val uploadingClient by lazy {
        return@lazy OkHttpClient.Builder()
                .connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(config.isRetryConnection)
                .followRedirects(true)
                .cookieJar(localCookieJar)
                .addInterceptor(UploadingInterceptor())
                .build()
    }

    /**
     * 安装配置
     */
    private fun setupConfig(config: Config) {
        this.config = config
    }

    /**
     * 获取本地文件的总长度
     */
    fun getLocalDownloadLength(builder: Builder): Long {
        val file = File(config.cachePath + File.separatorChar + HttpUtils.getFileName(builder.url))
        return if (file.exists()) file.length() else 0
    }

    /**
     * 断点下载文件
     */
    private fun downloadBreakpoint(builder: Builder, onDownloadListener: OnDownloadListener) {
        onDownloadListener.onStart()
        if (builder.downloadEndIndex == 0L)/*没有指定下载结束*/
            downloadClient.build().newCall(postRequest(builder).build()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onDownloadListener.onFailure(e, e.javaClass == ConnectException::class.java)
                    onDownloadListener.onEnd()
                }

                override fun onResponse(call: Call, response: Response) {
                    builder.downloadEndIndex = response.body()!!.contentLength()
                    response.body()?.close()
                    if (builder.downloadEndIndex == builder.downloadStartIndex) {/*本地与线上一致*/
                        QuickAsync.runOnUiThread {
                            onDownloadListener.onResponse(File(config.cachePath + File.separatorChar + HttpUtils.getFileName(builder.url)))
                            onDownloadListener.onEnd()
                        }
                    } else
                        downloadPost(builder, onDownloadListener)
                }
            })
        else downloadPost(builder, onDownloadListener)
    }

    /**
     * 构建通用的Call
     */
    fun getCall(client: OkHttpClient, request: Request): Call {
        val call = client.newCall(request)
        val key = if (request.method() == "GET") request.url().hashCode() else LoggingInterceptor.parseRequest(request).hashCode()
        taskCalls.put(key, call)
        return call
    }

    /**
     * 取消指定正在运行的任务
     */
    fun cancelTask(tag: String?) {
        if (TextUtils.isEmpty(tag)) return
        var index = -1
        for (tempIndex in 0 until taskCalls.size()) {
            val tempCall = taskCalls.valueAt(tempIndex)
            if (tag == tempCall.request().tag() && !tempCall.isCanceled) {
                index = tempIndex
                tempCall.cancel()
                break
            }
        }
        if (index != -1) taskCalls.removeAt(index)
    }

    /**
     * 取消所有正在运行的任务
     */
    fun cancelAllTask() {
        for (index in 0 until taskCalls.size()) {
            if (!taskCalls.valueAt(index).isCanceled)
                taskCalls.valueAt(index).cancel()
        }
        taskCalls.clear()
    }

    /**
     * 根据参数获取requestBody
     */
    fun getRequestBody(builder: Builder): RequestBody {
        return when {
            builder.fileBundle.size() > 0 -> {/*表单与多个文件*/
                val multipartBody = MultipartBody.Builder().setType(MultipartBody.MIXED)
                builder.requestBodyBundle.keySet().forEach {
                    multipartBody.addFormDataPart(it, builder.requestBodyBundle.get(it).toString())
                }
                builder.fileBundle.keySet().forEach {
                    val file = builder.fileBundle.getSerializable(it) as File
                    if (file.exists())
                        multipartBody.addFormDataPart(it, file.name, RequestBody.create(mediaTypeFile, file))
                }

                config.params.keySet().forEach {
                    multipartBody.addFormDataPart(it, config.params.get(it).toString())
                }
                multipartBody.build()
            }
            else -> {/*只有表单*/
                val formBody = FormBody.Builder()
                builder.requestBodyBundle.keySet().forEach {
                    formBody.add(it, builder.requestBodyBundle.get(it).toString())
                }
                config.params.keySet().forEach {
                    formBody.add(it, config.params.get(it).toString())
                }
                formBody.build()
            }
        }
    }

    /**
     * 构建Get请求，并添加默认参数
     */
    fun getRequest(builder: Builder): Request.Builder {
        val url = HttpUtils.formatGet(builder.url, builder.requestBodyBundle) + '&' + HttpUtils.formatParamsGet(config.params)

        val request = Request.Builder().url(url).tag(builder.tag)
        builder.header.keySet().forEach { request.addHeader(it, builder.header.get(it).toString()) }
        config.header.keySet().forEach { request.addHeader(it, config.header.get(it).toString()) }

        if (builder.isDownloadBreakpoint) request.addHeader("RANGE", String.format("bytes=%d-%d", builder.downloadStartIndex, builder.downloadEndIndex))

        return request
    }

    /**
     * 构建Post请求，并添加默认参数
     */
    fun postRequest(builder: Builder): Request.Builder {
        val request = Request.Builder().url(builder.url).tag(builder.tag).post(getRequestBody(builder))
        builder.header.keySet().forEach { request.addHeader(it, builder.header.get(it).toString()) }
        config.header.keySet().forEach { request.addHeader(it, config.header.get(it).toString()) }

        if (builder.isDownloadBreakpoint) request.addHeader("RANGE", String.format("bytes=%d-%d", builder.downloadStartIndex, builder.downloadEndIndex))

        return request
    }

    /**
     * 异常检测
     */
    fun checkOOM(response: Response): String {
        return try {
            response.body()!!.string()
        } catch (O_O: OutOfMemoryError) {/*服务器返回数据太大，会造成OOM，比如返回APK，高清图片*/
            "内存溢出\n" + O_O.message
        }
    }

    /**
     * 移除请求队列
     */
    fun removeTask(request: Request) {
        taskCalls.remove(if (request.method() == "GET") request.url().hashCode() else LoggingInterceptor.parseRequest(request).hashCode())
    }

    /**
     * kotlin专用内联函数
     * 返回值json使用GSON解析，若 T = String 则不进行解析
     */
    inline fun <reified T> getWithKotlin(builder: Builder, onRequestListener: OnRequestListener<T>) {
        val request = getRequest(builder).build()

        onRequestListener.onStart()
        getCall(normalClient, request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(call.request())
                onRequestListener.onFailure(e, e.javaClass == ConnectException::class.java)
                onRequestListener.onEnd()
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(call.request())
                val data = checkOOM(response)
                QuickAsync.runOnUiThread {
                    if (T::class.java == String::class.java) onRequestListener.onResponse(data as T)
                    else onRequestListener.onResponse(GsonUtils.parseFromJson(data))
                    onRequestListener.onEnd()
                }
            }
        })
    }

    /**
     * kotlin专用内联函数
     * 返回值json使用GSON解析，若 T = String 则不进行解析
     */
    inline fun <reified T> postWithKotlin(builder: Builder, onRequestListener: OnRequestListener<T>) {
        val request = postRequest(builder).build()

        onRequestListener.onStart()
        getCall(normalClient, request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(call.request())
                onRequestListener.onFailure(e, e.javaClass == ConnectException::class.java)
                onRequestListener.onEnd()
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(call.request())
                val data = checkOOM(response)
                QuickAsync.runOnUiThread {
                    if (T::class.java == String::class.java) onRequestListener.onResponse(data as T)
                    else onRequestListener.onResponse(GsonUtils.parseFromJson(data))
                    onRequestListener.onEnd()
                }
            }
        })
    }

    /**
     * 兼容java
     * 返回json使用GSON解析，若 T = String 则不进行解析
     */
    fun <T> getWithJava(builder: Builder, onRequestListener: OnRequestListener<T>) {
        val request = getRequest(builder).build()

        onRequestListener.onStart()
        getCall(normalClient, request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(call.request())
                onRequestListener.onFailure(e, e.javaClass == ConnectException::class.java)
                onRequestListener.onEnd()
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(call.request())
                val data = checkOOM(response)
                QuickAsync.runOnUiThread {
                    if (onRequestListener.tClass == String::class.java) onRequestListener.onResponse(data as T)
                    else onRequestListener.onResponse(GsonUtils.parseFromJson(data, onRequestListener.tClass))
                    onRequestListener.onEnd()
                }
            }
        })

    }

    /**
     * 兼容java
     * 返回值json使用GSON解析，若 T = String 则不进行解析
     */
    fun <T> postWithJava(builder: Builder, onRequestListener: OnRequestListener<T>) {
        val request = postRequest(builder).build()

        onRequestListener.onStart()
        getCall(normalClient, request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(call.request())
                onRequestListener.onFailure(e, e.javaClass == ConnectException::class.java)
                onRequestListener.onEnd()
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(call.request())
                val data = checkOOM(response)
                QuickAsync.runOnUiThread {
                    if (onRequestListener.tClass == String::class.java) onRequestListener.onResponse(data as T)
                    else onRequestListener.onResponse(GsonUtils.parseFromJson(data, onRequestListener.tClass))
                    onRequestListener.onEnd()
                }
            }
        })
    }

    /**
     * kotlin专用内联函数
     * 上传文件
     */
    inline fun <reified T> uploadingWithKotlin(builder: Builder, onUploadingListener: OnUploadingListener<T>) {
        val multipartBody = MultipartBody.Builder().setType(MultipartBody.MIXED)
        builder.requestBodyBundle.keySet().forEach {
            multipartBody.addFormDataPart(it, builder.requestBodyBundle.get(it).toString())
        }
        builder.fileBundle.keySet().forEach {
            val file = builder.fileBundle.getSerializable(it) as File
            if (file.exists())
                multipartBody.addFormDataPart(it, file.name, UploadingRequestBody(mediaTypeFile!!, it, file, onUploadingListener))
        }

        val request = Request.Builder().url(builder.url).tag(builder.tag).post(multipartBody.build()).build()

        onUploadingListener.onStart()
        getCall(uploadingClient, request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(call.request())
                onUploadingListener.onFailure(e, e.javaClass == ConnectException::class.java)
                onUploadingListener.onEnd()
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(call.request())
                val data = checkOOM(response)
                QuickAsync.runOnUiThread {
                    if (T::class.java == String::class.java) onUploadingListener.onResponse(data as T)
                    else onUploadingListener.onResponse(GsonUtils.parseFromJson(data))
                    onUploadingListener.onEnd()
                }
            }
        })
    }

    /**
     * 兼容java
     * 上传文件
     */
    fun <T> uploadingWithJava(builder: Builder, onUploadingListener: OnUploadingListener<T>) {
        val multipartBody = MultipartBody.Builder().setType(MultipartBody.MIXED)
        builder.requestBodyBundle.keySet().forEach {
            multipartBody.addFormDataPart(it, builder.requestBodyBundle.get(it).toString())
        }
        builder.fileBundle.keySet().forEach {
            val file = builder.fileBundle.getSerializable(it) as File
            if (file.exists())
                multipartBody.addFormDataPart(it, file.name, UploadingRequestBody(mediaTypeFile!!, it, file, onUploadingListener))
        }

        val request = Request.Builder().url(builder.url).tag(builder.tag).post(multipartBody.build()).build()

        onUploadingListener.onStart()
        getCall(uploadingClient, request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(call.request())
                onUploadingListener.onFailure(e, e.javaClass == ConnectException::class.java)
                onUploadingListener.onEnd()
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(call.request())
                val data = checkOOM(response)
                QuickAsync.runOnUiThread {
                    if (onUploadingListener.tClass == String::class.java) onUploadingListener.onResponse(data as T)
                    else onUploadingListener.onResponse(GsonUtils.parseFromJson(data, onUploadingListener.tClass))
                    onUploadingListener.onEnd()
                }
            }
        })
    }

    /**
     * 使用GET方式下载文件
     */
    private fun downloadGet(builder: Builder, onDownloadListener: OnDownloadListener) {
        val request = getRequest(builder).build()

        onDownloadListener.onStart()
        getCall(downloadClient.addNetworkInterceptor(DownloadInterceptor(builder, onDownloadListener)).build(), request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        removeTask(call.request())
                        onDownloadListener.onFailure(e, e.javaClass == ConnectException::class.java)
                        onDownloadListener.onEnd()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        FileUtils.writeFile(response.body()?.byteStream(), config.cachePath, HttpUtils.getFileName(builder.url), true, object : OnWriteListener {
                            override fun onLoading(key: String, bytesRead: Long, totalCount: Long, isDone: Boolean) = Unit

                            override fun onResponse(file: File) {
                                removeTask(call.request())
                                onDownloadListener.onResponse(file)
                                onDownloadListener.onEnd()
                            }

                            override fun onFailure(O_O: IOException) {
                                removeTask(call.request())
                                onDownloadListener.onFailure(O_O, false)
                                onDownloadListener.onEnd()
                            }
                        })
                    }
                })
    }

    /**
     * 使用POST方式下载文件
     */
    private fun downloadPost(builder: Builder, onDownloadListener: OnDownloadListener) {
        if (!builder.isDownloadBreakpoint)/*非断点，未调用onStart方法*/
            onDownloadListener.onStart()
        val request = postRequest(builder).build()
        getCall(downloadClient.addNetworkInterceptor(DownloadInterceptor(builder, onDownloadListener)).build(), request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        removeTask(call.request())
                        onDownloadListener.onFailure(e, e.javaClass == ConnectException::class.java)
                        onDownloadListener.onEnd()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        FileUtils.writeFile(response.body()?.byteStream(), config.cachePath, HttpUtils.getFileName(builder.url), builder.isDownloadBreakpoint, object : OnWriteListener {
                            override fun onLoading(key: String, bytesRead: Long, totalCount: Long, isDone: Boolean) = Unit

                            override fun onResponse(file: File) {
                                removeTask(call.request())
                                onDownloadListener.onResponse(file)
                                onDownloadListener.onEnd()
                            }

                            override fun onFailure(O_O: IOException) {
                                removeTask(call.request())
                                onDownloadListener.onFailure(O_O, false)
                                onDownloadListener.onEnd()
                            }
                        })
                    }
                })
    }

    /**
     * 构造器
     */
    class Builder(val url: String) {
        val requestBodyBundle = Bundle()
        val fileBundle = Bundle()
        val header = Bundle()
        var tag: String? = null
        var downloadStartIndex = 0L
        var downloadEndIndex = 0L
        /*是否断点下载*/
        var isDownloadBreakpoint = false

        /**
         * 添加标识，可用于取消任务
         */
        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        /**
         * 添加header
         */
        fun addHeader(key: String, value: Any): Builder {
            header.putString(key, value.toString())
            return this
        }

        /**
         * 添加参数
         */
        fun addParams(bundle: Bundle): Builder {
            requestBodyBundle.putAll(bundle)
            return this
        }

        fun addParams(map: Map<String, *>): Builder {
            map.keys.forEach { requestBodyBundle.putString(it, map[it].toString()) }
            return this
        }

        fun addParams(key: String, value: String): Builder {
            requestBodyBundle.putString(key, value)
            return this
        }

        fun addParams(key: String, value: Int): Builder {
            requestBodyBundle.putInt(key, value)
            return this
        }

        fun addParams(key: String, value: Float): Builder {
            requestBodyBundle.putFloat(key, value)
            return this
        }

        fun addParams(key: String, value: Double): Builder {
            requestBodyBundle.putDouble(key, value)
            return this
        }

        fun addParams(key: String, value: Boolean): Builder {
            requestBodyBundle.putBoolean(key, value)
            return this
        }

        fun addParams(key: String, value: Char): Builder {
            requestBodyBundle.putChar(key, value)
            return this
        }

        fun addParams(key: String, value: CharSequence): Builder {
            requestBodyBundle.putCharSequence(key, value)
            return this
        }

        fun addParams(key: String, value: Byte): Builder {
            requestBodyBundle.putByte(key, value)
            return this
        }

        fun addParams(key: String, value: File): Builder {
            fileBundle.putSerializable(key, value)
            return this
        }

        /**
         * 断点下载索引
         * @param startIndex 为0时：自动获取本地路径文件大小
         * @param endIndex 为0时：自动获取总大小
         */
        fun downloadBreakpointIndex(startIndex: Long, endIndex: Long): Builder {
            this.downloadStartIndex = startIndex
            this.downloadEndIndex = endIndex
            return this
        }

        /**
         * kotlin 专用内联函数
         * get请求数据
         */
        inline fun <reified T> get(onRequestListener: OnRequestListener<T>) {
            HttpService.getWithKotlin(this, onRequestListener)
        }

        /**
         * kotlin 专用内联函数
         * post请求数据
         */
        inline fun <reified T> post(onRequestListener: OnRequestListener<T>) {
            HttpService.postWithKotlin(this, onRequestListener)
        }

        /**
         * * kotlin 专用内联函数
         * 上传文件
         */
        inline fun <reified T> uploading(onUploadingListener: OnUploadingListener<T>) {
            HttpService.uploadingWithKotlin(this, onUploadingListener)
        }

        /**
         * 兼容JAVA
         * get请求数据
         */
        fun <T> getWithJava(onRequestListener: OnRequestListener<T>) {
            HttpService.getWithJava(this, onRequestListener)
        }

        /**
         * 兼容JAVA
         * post请求数据
         */
        fun <T> postWithJava(onRequestListener: OnRequestListener<T>) {
            HttpService.postWithJava(this, onRequestListener)
        }

        /**
         * 兼容JAVA
         * 上传文件
         */
        fun <T> uploadingWithJava(onUploadingListener: OnUploadingListener<T>) {
            HttpService.uploadingWithJava(this, onUploadingListener)
        }
//
//        /**
//         * 以GET方式，下载文件
//         */
//        fun downloadGet(onProgressListener: OnDownloadListener) {
//            HttpService.downloadGet(this, onProgressListener)
//        }

//        /**
//         * 以POST方式，下载文件
//         */
//        fun downloadPost(onProgressListener: OnDownloadListener) {
//            HttpService.downloadPost(this, onProgressListener)
//        }

        /**
         * 下载文件，可协带参数
         */
        fun download(onDownloadListener: OnDownloadListener) {
            isDownloadBreakpoint = false
            HttpService.downloadPost(this, onDownloadListener)
        }

        /**
         * 断点下载文件（需要服务器支持）
         * 需要自定义下载索引，请调用downloadIndex()
         */
        fun downloadBreakpoint(onDownloadListener: OnDownloadListener) {
            isDownloadBreakpoint = true
            if (downloadStartIndex == 0L) downloadStartIndex = getLocalDownloadLength(this)

            HttpService.downloadBreakpoint(this, onDownloadListener)/*断点下载*/
        }
    }

    /**
     * 公共参数配置
     */
    class Config {
        /*公共Header*/
        val header = Bundle()
        /*公共参数*/
        val params = Bundle()
        internal var encoding = "utf-8"
        internal var readTimeout = 30L
        internal var writeTimeout = 30L
        internal var connectTimeout = 30L
        /*如果遇到连接问题，是否重试*/
        internal var isRetryConnection = true
        internal var cachePath: String = FileUtils.sdCardPath

        /**
         * 添加公共header
         */
        fun addHeader(key: String, value: Any): Config {
            header.putString(key, value.toString())
            return this
        }

        fun removeHeader(key: String): Config {
            header.remove(key)
            return this
        }

        /**
         * 添加公共参数
         */
        fun addParams(key: String, value: Any): Config {
            params.putString(key, value.toString())
            return this
        }

        fun removeParams(key: String): Config {
            params.remove(key)
            return this
        }

        /**
         * 指定编码，默认utf-8
         */
        fun encoding(encoding: String): Config {
            this.encoding = encoding
            return this
        }

        /**
         * 读取超时时间
         */
        fun readTimeout(timeout: Long): Config {
            this.readTimeout = timeout
            return this
        }

        /**
         * 写入超时时间
         */
        fun writeTimeout(timeout: Long): Config {
            this.writeTimeout = timeout
            return this
        }

        /**
         * 连接超时时间
         */
        fun connectTimeout(timeout: Long): Config {
            this.connectTimeout = timeout
            return this
        }

        /**
         * 遇到连接问题，是否重试
         */
        fun retryConnection(isRetryConnection: Boolean): Config {
            this.isRetryConnection = isRetryConnection
            return this
        }

        /**
         * 缓存路径
         */
        fun cachePath(dirPath: String): Config {
            this.cachePath = dirPath
            return this
        }

        fun build() {
            HttpService.setupConfig(this)
        }
    }
}