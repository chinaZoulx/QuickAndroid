package org.quick.component.utils

import com.google.gson.Gson

import org.json.JSONArray
import org.quick.component.Log2

import java.util.ArrayList


/**
 * 解析joson
 *
 * @author chirs
 */
object GsonUtils {

    val gson = Gson()

    inline fun <reified T> parseFromJson(json: String?): T? = try {
        gson.fromJson(json, T::class.java)
    } catch (O_O: Exception) {
        Log2.e("Gson", "json or class error , from  " + T::class.java.simpleName + " error json :" + json)
        null
    }

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
        Log2.e("Gson", "json or class error , from  " + cls.simpleName + " error json :" + json)
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
            Log2.e("Gson", "json or class error , from  " + cls.simpleName + " error json :" + json)
        }

        return listT
    }

    /**
     * 将json解析为java对象列表
     *
     * @param json
     * @param cls
     * @return
     */
    inline fun <reified T> parseFromJsons(json: String?): List<T> {
        val listT = ArrayList<T>()
        try {
            val ja = JSONArray(json)
            (0 until ja.length()).mapTo(listT) { parseFromJson(ja.getString(it), T::class.java)!! }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log2.e("Gson", "json or class error , from  " + T::class.java.simpleName + " error json :" + json)
        }

        return listT
    }

    /**
     * 将java对象解析为json
     *
     * @param cls
     * @return
     */
    fun parseToJson(obj:Any) = try {
        gson.toJson(obj)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log2.e("Gson", "class error , from " + obj::class.java.simpleName)
        ""
    }
}
