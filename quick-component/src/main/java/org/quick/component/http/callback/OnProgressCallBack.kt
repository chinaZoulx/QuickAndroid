package org.quick.component.http.callback

interface OnProgressCallBack {
    fun onLoading(key: String, bytesRead: Long, totalCount: Long, isDone: Boolean)
}