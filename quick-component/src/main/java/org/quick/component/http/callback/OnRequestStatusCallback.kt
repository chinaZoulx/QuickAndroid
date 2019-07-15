package org.quick.component.http.callback

interface OnRequestStatusCallback {
    fun onFailure(e: Throwable, isNetworkError: Boolean)
    fun onErrorParse(data:String)
}