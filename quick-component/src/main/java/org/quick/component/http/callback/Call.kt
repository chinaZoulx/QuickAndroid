package org.quick.component.http.callback

import org.quick.component.http.HttpService

interface Call {

    fun <T> enqueue(callback: Callback<T>) {}
    fun cancel() {}
    fun builder(): HttpService.Builder
}