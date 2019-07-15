package org.quick.component.http.callback

interface OnProgressCallback {
    fun onLoading(key: String, bytesRead: Long, totalCount: Long, isDone: Boolean)
}