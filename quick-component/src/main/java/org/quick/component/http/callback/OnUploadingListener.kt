package org.quick.component.http.callback

/**
 * 上传监听
 * @param T 泛型，解析为指定的类型
 * @from https://blog.csdn.net/Fy993912_chris/article/details/84765483
 */
abstract class OnUploadingListener<T> : OnProgressCallback, Callback<T>() {

}