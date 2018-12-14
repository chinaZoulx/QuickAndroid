package org.quick.component.callback

import java.io.File
import java.io.IOException

interface OnWriteListener {
    fun onLoading(key: String, bytesRead: Long, totalCount: Long, isDone: Boolean)
    fun onResponse(file: File)
    fun onFailure(O_O: IOException)
}