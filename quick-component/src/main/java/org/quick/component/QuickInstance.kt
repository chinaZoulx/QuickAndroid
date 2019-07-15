package org.quick.component

import android.util.SparseArray

open class QuickInstance {

    companion object {
        private val instances = SparseArray<QuickInstance>()

        @Synchronized
        @Throws(Exception::class)
        fun <T : QuickInstance> instance(clz: Class<T>): T? {
            val id = (clz.`package`.name + clz.simpleName).hashCode()
            var instance = instances.get(id)
            return if (instance != null) instance as T
            else {
                instance = clz.newInstance()
                instances.put(id, instance)
                return instance
            }

        }
    }
}