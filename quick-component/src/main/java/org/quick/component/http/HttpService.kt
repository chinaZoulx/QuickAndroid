package org.quick.component.http

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import okhttp3.*
import org.quick.component.QuickAsync
import org.quick.component.QuickInstance
import org.quick.component.callback.OnWriteListener
import org.quick.component.http.callback.OnDownloadListener
import org.quick.component.http.callback.OnRequestStatusCallback
import org.quick.component.http.callback.OnUploadingListener
import org.quick.component.http.interceptor.DownloadInterceptor
import org.quick.component.http.interceptor.LoggingInterceptor
import org.quick.component.http.interceptor.UploadingInterceptor
import org.quick.component.utils.FileUtils
import org.quick.component.utils.GsonUtils
import org.quick.component.utils.HttpUtils
import org.quick.component.utils.check.CheckUtils
import java.io.File
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeUnit

/**
 * 网络服务
 * 使用okHttp
 */
object HttpService : QuickInstance() {
    /**
     * json类型
     */
    val mediaTypeJson: MediaType?
        get() {
            return MediaType.parse("application/json; charset=" + Config.encoding)
        }
    /**
     * File类型
     */
    val mediaTypeFile: MediaType?
        get() {
            return MediaType.parse("application/octet-stream; charset=" + Config.encoding)
        }

    private val taskCalls = HashMap<String, Call>()
    /**
     * Cookie管理
     */
    private val localCookieJar = LocalCookieJar()

    private val normalClient by lazy {
        return@lazy OkHttpClient.Builder()
            .connectTimeout(Config.connectTimeout, TimeUnit.SECONDS)
            .readTimeout(Config.readTimeout, TimeUnit.SECONDS)
            .writeTimeout(Config.writeTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(Config.isRetryConnection)
            .addInterceptor(LoggingInterceptor())
            .cookieJar(localCookieJar)
            .followRedirects(true)
            .build()
    }

    private val downloadClient by lazy {
        return@lazy OkHttpClient.Builder()
            .connectTimeout(Config.connectTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(Config.isRetryConnection)
            .followRedirects(true)
            .cookieJar(localCookieJar)
    }

    private val uploadingClient by lazy {
        return@lazy OkHttpClient.Builder()
            .connectTimeout(Config.connectTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(Config.isRetryConnection)
            .followRedirects(true)
            .cookieJar(localCookieJar)
            .addInterceptor(UploadingInterceptor())
            .build()
    }

    /**
     * 构建保存请求体的Key
     */
    private fun buildKey(builder: Builder, request: Request): String {
        var key =
            if (request.method() == "GET")
                request.url().toString()
            else
                LoggingInterceptor.parseRequest(request)
        if (builder.activity != null)
            key = builder.activity?.javaClass?.canonicalName + key
        return key
    }

    /**
     * 构建通用的Call
     */
    private fun getCall(client: OkHttpClient, request: Request, builder: Builder): Call {
        val call = client.newCall(request)
        taskCalls[buildKey(builder, request)] = call
        return call
    }

    /**
     * 移除请求队列
     */
    private fun removeTask(builder: Builder, request: Request) {
        taskCalls.remove(buildKey(builder, request))
    }

    /**
     * 根据参数获取requestBody
     */
    private fun getRequestBody(builder: Builder): RequestBody {
//        return when {
//            builder.fileBundle.size() > 0 -> {/*表单与多个文件*/
//                val multipartBody = MultipartBody.Builder().setType(MultipartBody.MIXED)
//                builder.requestBodyBundle.keySet().forEach {
//                    multipartBody.addFormDataPart(it, builder.requestBodyBundle.get(it).toString())
//                }
//                builder.fileBundle.keySet().forEach {
//                    val file = builder.fileBundle.getSerializable(it) as File
//                    if (file.exists())
//                        multipartBody.addFormDataPart(it, file.name, RequestBody.create(mediaTypeFile, file))
//                }
//
//                Config.params.keySet().forEach {
//                    multipartBody.addFormDataPart(it, Config.params.get(it).toString())
//                }
//                multipartBody.build()
//            }
//            else -> {/*只有表单*/
//                val formBody = FormBody.Builder()
//                builder.requestBodyBundle.keySet().forEach {
//                    formBody.add(it, builder.requestBodyBundle.get(it).toString())
//                }
//                Config.params.keySet().forEach {
//                    formBody.add(it, Config.params.get(it).toString())
//                }
//                formBody.build()
//            }
//        }
        /*表单上传*/
        return run {
            val formBody = FormBody.Builder()
            builder.requestBodyBundle.keySet().forEach {
                formBody.add(it, builder.requestBodyBundle.get(it).toString())
            }
            Config.params.keySet().forEach {
                formBody.add(it, Config.params.get(it).toString())
            }
            formBody.build()
        }
    }

    /**
     * 构建Get请求，并添加默认参数
     */
    private fun getRequest(builder: Builder): Request.Builder {
        val url =
            HttpUtils.formatGet(configUrl(builder.url), builder.requestBodyBundle) + '&' + HttpUtils.formatParamsGet(
                Config.params
            )

        val request = Request.Builder().url(url).tag(builder.tag)
        builder.header.keySet().forEach { request.addHeader(it, builder.header.get(it).toString()) }
        Config.header.keySet().forEach { request.addHeader(it, Config.header.get(it).toString()) }

        if (builder.isDownloadBreakpoint) request.addHeader(
            "RANGE",
            String.format("bytes=%d-%d", builder.downloadStartIndex, builder.downloadEndIndex)
        )

        return request
    }

    /**
     * 构建Post请求，并添加默认参数
     */
    private fun postRequest(builder: Builder): Request.Builder {
        val request = Request.Builder().url(configUrl(builder.url)).tag(builder.tag).post(getRequestBody(builder))
        builder.header.keySet().forEach { request.addHeader(it, builder.header.get(it).toString()) }
        Config.header.keySet().forEach { request.addHeader(it, Config.header.get(it).toString()) }

        if (builder.isDownloadBreakpoint) request.addHeader(
            "RANGE",
            String.format("bytes=%d-%d", builder.downloadStartIndex, builder.downloadEndIndex)
        )

        return request
    }

    /**
     * 配置URL
     */
    private fun configUrl(postfix: String): String {
        return if (HttpUtils.isHttpUrlFormRight(postfix))
            postfix
        else
            Config.baseUrl + postfix
    }

    /**
     * 异常检测
     */
    private fun checkOOM(response: Response): String {
        return try {
            response.body()!!.string()
        } catch (O_O: OutOfMemoryError) {/*服务器返回数据太大，会造成OOM，比如返回APK，高清图片*/
            "内存溢出\n" + O_O.message
        }
    }

    /**
     * 绑定者是否生存
     */
    private fun checkBinderIsExist(builder: Builder): Boolean = when {
        builder.activity != null -> CheckUtils.checkActivityIsRunning(builder.activity)
        builder.fragment != null -> !builder.fragment!!.isDetached
        else -> true
    }

    /**
     * 兼容java
     * 返回json使用GSON解析，若 T = String 则不进行解析
     */
    fun <T> getWithJava(builder: Builder, callback: org.quick.component.http.callback.Callback<T>) {
        val request = getRequest(builder).build()

        callback.onStart()
        getCall(normalClient, request, builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(builder, call.request())
                if (checkBinderIsExist(builder)) {
                    QuickAsync.runOnUiThread {
                        callback.onFailure(e, e.javaClass == ConnectException::class.java)
                        callback.onEnd()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(builder, call.request())
                val data = checkOOM(response)
                if (checkBinderIsExist(builder)) {
                    QuickAsync.runOnUiThread {
                        if (callback.tClass == String::class.java) callback.onResponse(data as T)
                        else callback.onResponse(GsonUtils.parseFromJson(data, callback.tClass))
                        callback.onEnd()
                    }
                }
            }
        })

    }

    /**
     * 兼容java
     * 返回值json使用GSON解析，若 T = String 则不进行解析
     */
    fun <T> postWithJava(builder: Builder, callback: org.quick.component.http.callback.Callback<T>) {
        val request = postRequest(builder).build()

        callback.onStart()
        getCall(normalClient, request, builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                removeTask(builder, call.request())
                if (checkBinderIsExist(builder)) {
                    QuickAsync.runOnUiThread {
                        callback.onFailure(e, e.javaClass == ConnectException::class.java)
                        Config.onRequestCallback?.onFailure(e, e.javaClass == ConnectException::class.java)
                        callback.onEnd()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(builder, call.request())
                val data = checkOOM(response)
                if (checkBinderIsExist(builder)) {
                    QuickAsync.runOnUiThread {
                        if (callback.tClass == String::class.java) callback.onResponse(data as T)
                        else {
                            val model = GsonUtils.parseFromJson(data, callback.tClass)
                            if (model == null) Config.onRequestCallback?.onErrorParse(data)
                            callback.onResponse(model)
                        }
                        callback.onEnd()
                    }
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
            val obj = builder.fileBundle.getSerializable(it)
            when (obj) {
                is File ->
                    if (obj.exists())
                        multipartBody.addFormDataPart(
                            it,
                            obj.name,
                            UploadingRequestBody(mediaTypeFile!!, it, obj, onUploadingListener)
                        )
                is ArrayList<*> -> {
                    obj.forEach { temp ->
                        val file = temp as File
                        if (file.exists())
                            multipartBody.addFormDataPart(
                                it,
                                file.name,
                                UploadingRequestBody(mediaTypeFile!!, it, file, onUploadingListener)
                            )
                    }
                }
            }
        }

        val request = Request.Builder().url(configUrl(builder.url)).tag(builder.tag).post(multipartBody.build()).build()

        onUploadingListener.onStart()
        getCall(uploadingClient, request, builder).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                removeTask(builder, call.request())
                if (checkBinderIsExist(builder)) {
                    QuickAsync.runOnUiThread {
                        onUploadingListener.onFailure(e, e.javaClass == ConnectException::class.java)
                        onUploadingListener.onEnd()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                removeTask(builder, call.request())
                val data = checkOOM(response)
                if (checkBinderIsExist(builder)) {
                    QuickAsync.runOnUiThread {
                        if (onUploadingListener.tClass == String::class.java) onUploadingListener.onResponse(data as T)
                        else onUploadingListener.onResponse(GsonUtils.parseFromJson(data, onUploadingListener.tClass))
                        onUploadingListener.onEnd()
                    }
                }
            }
        })
    }

    /**
     * 使用GET方式下载文件
     */
    fun downloadGet(builder: Builder, onDownloadListener: OnDownloadListener) {
        val request = getRequest(builder).build()

        onDownloadListener.onStart()
        getCall(
            downloadClient.addNetworkInterceptor(DownloadInterceptor(builder, onDownloadListener)).build(),
            request,
            builder
        )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    removeTask(builder, call.request())
                    if (checkBinderIsExist(builder)) {
                        QuickAsync.runOnUiThread {
                            onDownloadListener.onFailure(e, e.javaClass == ConnectException::class.java)
                            onDownloadListener.onEnd()
                        }
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (checkBinderIsExist(builder))
                        FileUtils.writeFile(
                            response.body()?.byteStream(),
                            Config.cachePath,
                            HttpUtils.getFileName(configUrl(builder.url)),
                            true,
                            object : OnWriteListener {
                                override fun onLoading(
                                    key: String,
                                    bytesRead: Long,
                                    totalCount: Long,
                                    isDone: Boolean
                                ) = Unit

                                override fun onResponse(file: File) {
                                    removeTask(builder, call.request())
                                    if (checkBinderIsExist(builder)) {
                                        onDownloadListener.onResponse(file)
                                        onDownloadListener.onEnd()
                                    }
                                }

                                override fun onFailure(O_O: IOException) {
                                    removeTask(builder, call.request())
                                    if (checkBinderIsExist(builder)) {
                                        onDownloadListener.onFailure(O_O, false)
                                        onDownloadListener.onEnd()
                                    }
                                }
                            })
                }
            })
    }

    /**
     * 使用POST方式下载文件
     */
    fun downloadPost(builder: Builder, onDownloadListener: OnDownloadListener) {
        if (!builder.isDownloadBreakpoint)/*非断点，未调用onStart方法*/
            onDownloadListener.onStart()
        val request = postRequest(builder).build()
        getCall(
            downloadClient.addNetworkInterceptor(DownloadInterceptor(builder, onDownloadListener)).build(),
            request,
            builder
        )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    removeTask(builder, call.request())
                    if (checkBinderIsExist(builder)) {
                        QuickAsync.runOnUiThread {
                            onDownloadListener.onFailure(e, e.javaClass == ConnectException::class.java)
                            onDownloadListener.onEnd()
                        }
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (checkBinderIsExist(builder))
                        FileUtils.writeFile(
                            response.body()?.byteStream(),
                            Config.cachePath,
                            HttpUtils.getFileName(configUrl(builder.url)),
                            builder.isDownloadBreakpoint,
                            object : OnWriteListener {
                                override fun onLoading(
                                    key: String,
                                    bytesRead: Long,
                                    totalCount: Long,
                                    isDone: Boolean
                                ) = Unit

                                override fun onResponse(file: File) {
                                    removeTask(builder, call.request())
                                    if (checkBinderIsExist(builder)) {
                                        onDownloadListener.onResponse(file)
                                        onDownloadListener.onEnd()
                                    }
                                }

                                override fun onFailure(O_O: IOException) {
                                    removeTask(builder, call.request())
                                    if (checkBinderIsExist(builder)) {
                                        onDownloadListener.onFailure(O_O, false)
                                        onDownloadListener.onEnd()
                                    }
                                }
                            })
                }
            })
    }

    /**
     * 获取本地文件的总长度
     */
    fun getLocalDownloadLength(builder: Builder): Long {
        val file = File(Config.cachePath + File.separatorChar + HttpUtils.getFileName(configUrl(builder.url)))
        return if (file.exists()) file.length() else 0
    }

    /**
     * 断点下载文件
     */
    fun downloadBreakpoint(builder: Builder, onDownloadListener: OnDownloadListener) {
        onDownloadListener.onStart()
        if (builder.downloadEndIndex == 0L)/*没有指定下载结束*/
            downloadClient.build().newCall(postRequest(builder).build()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    QuickAsync.runOnUiThread {
                        onDownloadListener.onFailure(e, e.javaClass == ConnectException::class.java)
                        onDownloadListener.onEnd()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    builder.downloadEndIndex = response.body()!!.contentLength()
                    response.body()?.close()
                    if (builder.downloadEndIndex == builder.downloadStartIndex) {/*本地与线上一致*/
                        QuickAsync.runOnUiThread {
                            onDownloadListener.onResponse(
                                File(
                                    Config.cachePath + File.separatorChar + HttpUtils.getFileName(
                                        configUrl(builder.url)
                                    )
                                )
                            )
                            onDownloadListener.onEnd()
                        }
                    } else
                        downloadPost(builder, onDownloadListener)
                }
            })
        else downloadPost(builder, onDownloadListener)
    }

    /**
     * 取消指定正在运行的任务
     */
    fun cancelTask(tag: String?) {
        if (TextUtils.isEmpty(tag)) return
        for (call in taskCalls) {
            if (tag == call.value.request().tag() && !call.value.isCanceled) {
                if (!call.value.isCanceled) call.value.cancel()
                taskCalls.remove(call.key)
                break
            }
        }
    }

    /**
     * 取消指定正在运行的任务
     */
    fun cancelTask(activity: Activity?) {
        if (activity != null)
            for (call in taskCalls) {
                if (call.key.startsWith(activity.javaClass.canonicalName)) {
                    if (!call.value.isCanceled) call.value.cancel()
                }
            }

    }

    /**
     * 取消所有正在运行的任务
     */
    fun cancelAllTask() {
        for (call in taskCalls) {
            if (!call.value.isCanceled)
                call.value.cancel()
        }
        taskCalls.clear()
    }

    /**
     * 构造器
     */
    class Builder(val url: String) {
        val requestBodyBundle = Bundle()
        val fileBundle = Bundle()
        val header = Bundle()

        var method: String = Config.defaultMethod
        var tag: String? = null
        var downloadStartIndex = 0L
        var downloadEndIndex = 0L
        var isDownloadBreakpoint = false/*是否断点下载*/

        var fragment: androidx.fragment.app.Fragment? = null
        var activity: Activity? = null

        fun get(): Builder {
            this.method = "GET"
            return this
        }

        fun post(): Builder {
            this.method = "POST"
            return this
        }

        /**
         * 与fragment生命周期绑定，若fragment销毁或分离，请求将不会返回
         */
        fun binder(fragment: androidx.fragment.app.Fragment?): Builder {
            this.fragment = fragment
            return this
        }

        /**
         * 与activity生命周期绑定，若activity销毁，请求将不会返回
         */
        fun binder(activity: Activity?): Builder {
            this.activity = activity
            return this
        }

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

        fun addParams(key: String, value: Long): Builder {
            requestBodyBundle.putLong(key, value)
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

        fun addParams(key: String, value: ArrayList<File>): Builder {
            fileBundle.putSerializable(key, value)
            return this
        }

        /**
         * 断点下载索引
         * @param startIndex 为0时：自动获取本地路径文件大小
         * @param endIndex 为0时：自动获取总大小
         */
        fun downloadBreakpointIndex(startIndex: Long, endIndex: Long): Builder {
            this.isDownloadBreakpoint = true
            this.downloadStartIndex = startIndex
            this.downloadEndIndex = endIndex
            return this
        }

        /**
         * 断点下载
         */
        fun downloadBreakpoint(): Builder {
            this.isDownloadBreakpoint = true
            return this
        }

        /**
         * 开始执行
         * @param callback
         * from package org.quick.component.http.callback
         * 1、{@link OnDownloadListener} 下载文件
         * 2、{@link OnUploadingListener}上传文件
         * 3、{@link Callback} 普通请求
         */
        fun <T> enqueue(callback: org.quick.component.http.callback.Callback<T>): org.quick.component.http.callback.Call {
            val call = build()
            call.enqueue(callback)
            return call
        }

        fun build(): org.quick.component.http.callback.Call {
            return HttpServiceProxy(this).build()
        }
    }

    /**
     * 公共参数配置
     */
    object Config {
        /*公共Header*/
        val header = Bundle()
        /*公共参数*/
        val params = Bundle()
        internal var baseUrl = ""
        internal var defaultMethod = "GET"
        internal var encoding = "utf-8"
        internal var readTimeout = 30L
        internal var writeTimeout = 30L
        internal var connectTimeout = 30L
        /*如果遇到连接问题，是否重试*/
        internal var isRetryConnection = true
        internal var cachePath: String = FileUtils.sdCardPath
        internal var onRequestCallback: OnRequestStatusCallback? = null

        fun baseUrl(url: String): Config {
            this.baseUrl = url
            return this
        }

        /**
         * @param isGet true:GET   false:POST
         */
        fun method(isGet: Boolean): Config {
            defaultMethod = if (isGet) "GET" else "POST"
            return this
        }

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

        fun setOnRequestStatusCallback(onRequestCallback: OnRequestStatusCallback) {
            this.onRequestCallback = onRequestCallback
        }
    }
}