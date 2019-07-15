package org.quick.component.http.callback

/**
 * 请求接口
 */
abstract class Callback<T> : ClassCallback<T>() {
    open fun onStart() {}
    abstract fun onFailure(e: Throwable, isNetworkError: Boolean)
    abstract fun onResponse(value: T?)
    open fun onEnd() {}
}