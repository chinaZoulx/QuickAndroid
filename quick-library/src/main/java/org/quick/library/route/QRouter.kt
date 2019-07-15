package com.example.qrouter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import androidx.annotation.Size
import org.quick.component.QuickActivity
import org.quick.component.QuickAndroid
import org.quick.component.QuickToast
import java.io.Serializable

object QRouter {

    private val classSp = SparseArray<Class<*>>()

    fun register(pattern: String, clazz: Class<*>) {
        classSp.put(pattern.hashCode(), clazz)
    }

    private fun navigation(
        builder: Builder,
        context: Context,
        onActivityResultListener: ((resultCode: Int, data: Intent?) -> Unit)? = null
    ) {
        if (classSp.get(builder.pattern.hashCode()) == null) {
            QuickToast.showToastDefault("没有找到目标")
            return
        }
        builder.intent.setClass(context, classSp.get(builder.pattern.hashCode()))
        QuickActivity.Builder(context, classSp.get(builder.pattern.hashCode()))
            .addParams(builder.intent)
            .startActivity(onActivityResultListener)
    }

    class Builder(var pattern: String) {
        var intent = Intent()

        fun addParams(data: Intent): Builder {
            intent.putExtras(data)
            return this
        }

        fun addParams(bundle: Bundle): Builder {
            intent.putExtras(bundle)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: String): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Float): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Int): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Double): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Byte): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: CharSequence): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Boolean): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Long): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min = 1) vararg value: Short): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: ArrayList<String>): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: Bundle): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: Serializable): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: Parcelable): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun navigation(context: Context? = QuickAndroid.applicationContext) {
            if (context != null) navigation(this, context, null)
        }

        fun navigation(
            context: Context?,
            onActivityResultListener: ((resultCode: Int, data: Intent?) -> Unit)? = null
        ) {
            if (context != null) navigation(this, context, onActivityResultListener)
        }

        fun <T> navigationObj(): T {
            return if (classSp.get(pattern.hashCode()) != null)
                classSp.get(pattern.hashCode()).newInstance() as T
            else throw NullPointerException()
        }

        fun getObjClz():Class<*>? = classSp.get(pattern.hashCode())

    }
}