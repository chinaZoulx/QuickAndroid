package org.quick.component.http.callback

import java.io.IOException

/**
 * 上传监听
 * @param T 泛型，解析为指定的类型
 * @from https://blog.csdn.net/Fy993912_chris/article/details/84765483
 */
abstract class OnUploadingListener<T> : OnProgressCallBack, ClassCallback<T>() {

    open fun onStart() {}

    abstract fun onFailure(e: IOException, isNetworkError: Boolean)
    abstract fun onResponse(value: T?)

    open fun onEnd() {}
}