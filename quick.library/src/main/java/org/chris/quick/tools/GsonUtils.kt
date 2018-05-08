package org.chris.quick.tools

import android.util.Log

import com.google.gson.Gson

import org.json.JSONArray

import java.util.ArrayList


/**
 * 解析joson
 *
 * @author chirs
 */
object GsonUtils {

    val gson = Gson()
    /**
     * 将json解析成java对象
     *
     * @param json
     * @param cls
     * @return
     */
    fun <T> parseFromJson(json: String?, cls: Class<T>): T? = try {
        gson.fromJson(json, cls)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.e("Gson", "json or class error , from  " + cls.simpleName + " error json :" + json)
        null
    }

    /**
     * 将json解析为java对象列表
     *
     * @param json
     * @param cls
     * @return
     */
    fun <T> parseFromJsons(json: String?, cls: Class<T>): List<T> {
        val listT = ArrayList<T>()
        try {
            val ja = JSONArray(json)
            (0 until ja.length()).mapTo(listT) { parseFromJson(ja.getString(it), cls)!! }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("Gson", "json or class error , from  " + cls.simpleName + " error json :" + json)
        }

        return listT
    }

    /**
     * 将java对象解析为json
     *
     * @param cls
     * @return
     */
    fun <T> parseToJson(cls: Class<T>) = try {
        gson.toJson(cls)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.e("Gson", "class error , from " + cls.simpleName)
        ""
    }

    /**
     * 将java对象列表解析为json
     *
     * @param clsList
     * @return
     */
    fun <T> parseToJsons(clsList: List<T>) = try {
        gson.toJson(clsList)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.e("Gson", "class list error , please check")
        ""
    }
}
