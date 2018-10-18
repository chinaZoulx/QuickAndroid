package org.quick.component

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by chris zou on 2016/7/29.
 *
 * @author ChrisZou
 * @describe 快速使用SharedPreferences
 * @date 2016/7/29
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
object QuickSPHelper {

    val mSharedPreferences: SharedPreferences by lazy { return@lazy QuickAndroid.applicationContext.getSharedPreferences(QuickAndroid.appBaseName, Context.MODE_PRIVATE) }
    val mEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    fun putValue(key: String, value: String): QuickSPHelper {
        mEditor.putString(key, value).commit()
        return this
    }

    fun putValue(key: String, value: Boolean): QuickSPHelper {
        mEditor.putBoolean(key, value).commit()
        return this
    }

    fun putValue(key: String, value: Float): QuickSPHelper {
        mEditor.putFloat(key, value).commit()
        return this
    }

    fun putValue(key: String, value: Double): QuickSPHelper {
        mEditor.putFloat(key, value.toFloat()).commit()
        return this
    }

    fun putValue(key: String, value: Long): QuickSPHelper {
        mEditor.putLong(key, value).commit()
        return this
    }

    fun putValue(key: String, value: Int): QuickSPHelper {
        mEditor.putInt(key, value).commit()
        return this
    }

    fun putValue(key: String, value: Set<String>): QuickSPHelper {
        mEditor.putStringSet(key, value).commit()
        return this
    }

    fun <T> getValue(key: String, defaultValue: T): T = try {
        if (all[key] == null) defaultValue else all[key] as T
    } catch (O_o: Exception) {
        Log2.e(QuickSPHelper::class.java.simpleName, "Cannot convert with defaultValue type")
        defaultValue
    }

    fun clearAll() {
        mEditor.clear().commit()
    }

    fun removeValue(key: String): Boolean {
        return mEditor.remove(key).commit()
    }

    fun getSet(key: String, defValues: Set<String>): Set<String>? {
        return mSharedPreferences.getStringSet(key, defValues)
    }

    val all: Map<String, *> get() = mSharedPreferences.all
}
