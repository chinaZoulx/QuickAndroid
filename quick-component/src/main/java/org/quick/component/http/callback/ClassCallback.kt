package org.quick.component.http.callback

import java.lang.Exception
import java.lang.reflect.ParameterizedType

/**
 * 泛型Class获取
 * 解决泛型擦除的问题
 * @from https://blog.csdn.net/Fy993912_chris/article/details/84765483
 */
abstract class ClassCallback<T> {

    /**
     * 泛型Class
     * TestBean<String,Int> TestBean的Class，若无，返回Object.Class
     */
    val tClass: Class<T>
        get() = getTClass(javaClass) as Class<T>

    /**
     * 泛型中的泛型列表
     * TestBean<String,Int> String,Int的Class,若无，返回列表为空
     */
    val tAgainTClzList: ArrayList<Class<T>>
        get() = getTAgainTClzList(javaClass) as ArrayList<Class<T>>

    companion object {
        /**
         * 获取泛型的类型
         */
        fun getTClass(clz: Class<*>): Class<*> {
            val type = clz.genericSuperclass as? ParameterizedType ?: return Any::class.java
            val params = type.actualTypeArguments
            return if (params[0] is ParameterizedType) {
                /*
                ((params[0] as ParameterizedType).actualTypeArguments[0] as Class<*>).canonicalName*//*TestBean<T> T的类型*//*
            ((params[0] as ParameterizedType).rawType as Class<*>).canonicalName/*TestBean<T> TestBean的类型*/
            */
                (params[0] as ParameterizedType).rawType as Class<*>
            } else
                try {
                    params[0] as Class<*>
                } catch (O_O: Exception) {
                    Any::class.java
                }
        }

        /**
         * 获取泛型中的泛型
         */
        fun getTAgainTClzList(clz: Class<*>): ArrayList<Class<*>> {
            val clzArray = arrayListOf<Class<*>>()
            val type = clz.genericSuperclass as? ParameterizedType ?: return clzArray
            val params = type.actualTypeArguments
            if (params[0] is ParameterizedType) {
                (params[0] as ParameterizedType).actualTypeArguments.forEach {
                    clzArray.add(it as Class<*>)
                }
            }

            return clzArray
        }
    }
}