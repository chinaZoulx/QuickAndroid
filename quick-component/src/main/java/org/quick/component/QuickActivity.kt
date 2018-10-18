package org.quick.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Size
import android.util.Log
import android.util.SparseArray
import java.io.Serializable

/**
 * @describe 快速简洁的返回startActivityForResult值，以回调的方式使用
 * @author ChrisZou
 * @date 2018/6/14-14:33
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
object QuickActivity {

    private val requestParamsList = SparseArray<((resultCode: Int, data: Intent?) -> Unit)>()

    private fun startActivity(builder: Builder, onActivityResultListener: ((resultCode: Int, data: Intent?) -> Unit)? = null) {
        if (onActivityResultListener == null)
            builder.activity?.startActivity(builder.build())
        else {
            val requestCode = createRequestCode(builder.build().component.className)
            requestParamsList.put(requestCode, onActivityResultListener)/*这里是以目的地存储的*/
            builder.activity?.startActivityForResult(builder.build(), requestCode)
        }
    }

    fun createRequestCode(className: String): Int {
        val hasCodeStr = className.hashCode().toString()
        var tempCode = ""
        for (index in hasCodeStr.length - 1 downTo 0)
            if (index % 2 != 0)
                tempCode += hasCodeStr[index]
        val requestCode = tempCode.toInt()
        return if (requestCode > 65536) requestCode / 2 else requestCode
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        requestParamsList.get(requestCode)?.invoke(resultCode, data)
    }

    fun remove(activity: Activity?) {
        if (activity == null) return
        requestParamsList.remove(createRequestCode(String.format("%s.%s", activity.packageName, activity.localClassName)))
    }

    fun resetInternal() {
        requestParamsList.clear()
    }

    class Builder(var activity: Activity?, clazz: Class<*>) {
        var intent: Intent = Intent(activity, clazz)

        fun setupIntent(data: Intent): Builder {
            intent = data
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: String): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Float): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Int): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Double): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Byte): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: CharSequence): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Boolean): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Long): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, @Size(min =1)vararg value: Short): Builder {
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

        fun startActivity(onActivityResultListener: ((resultCode: Int, data: Intent?) -> Unit)? = null) {
            QuickActivity.startActivity(this, onActivityResultListener)
        }

        fun build() = intent
    }

    /**
     * 获取intent中的内容
     *
     * @param intent
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
    </T> */
    fun <T> getIntentValue(intent: Intent, key: String, defaultValue: T): T {
        var value: Any? = defaultValue
        try {
            when (defaultValue) {
                is String -> value = intent.getStringExtra(key)
                is Int -> value = intent.getIntExtra(key, defaultValue)
                is Boolean -> value = intent.getBooleanExtra(key, defaultValue)
                is Serializable -> value = intent.getSerializableExtra(key)
                is Long -> value = intent.getLongExtra(key, defaultValue)
                is Float -> value = intent.getFloatExtra(key, defaultValue)
                is Double -> value = intent.getDoubleExtra(key, defaultValue)
                is java.util.ArrayList<*> -> value = intent.getStringArrayListExtra(key)
                is Bundle -> value = intent.getBundleExtra(key)
            }
        } catch (o_O: Exception) {
            value = defaultValue
            Log.e("转换错误", "获取intent内容失败：或许是因为Key不存在,若需要解决请手动添加类型转换")
            o_O.printStackTrace()
        }

        if (value == null) {
            value = defaultValue
        }
        return value as T
    }
}
