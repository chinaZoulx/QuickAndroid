package org.quick.component.utils

import android.os.Bundle
import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by Administrator on 2016/6/6.
 */
object HttpUtils {

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param bundle
     */
    fun formatGet(url: String, bundle: Bundle?): String {
        val params = formatParamsGet(bundle)
        return if (params.isNotEmpty()) String.format("%s?%s", url, params) else url
    }

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param map
     */
    fun formatGet(url: String, map: Map<*, *>?): String {

        var params = ""
        map?.keys?.forEach {
            params += it.toString() + '=' + map[it].toString() + '&'
        }
        if (params.isNotEmpty()) params = params.substring(0, params.length - 1)

        return if (params.isNotEmpty()) String.format("%s?%s", url, params) else url
    }

    fun formatParamsGet(bundle: Bundle?): String {
        var params = ""
        bundle?.keySet()?.forEach {
            params += it + '=' + bundle.get(it).toString() + '&'
        }

        if (params.length > 1) params = params.substring(0, params.length - 1)

        return params
    }


    /**
     * 文件链接是否正确
     * The file link correct or not
     *
     * @param url
     * @return
     */
    fun isFileUrlFormRight(url: String): Boolean {
        var url = url
        if (!TextUtils.isEmpty(url)) {
            url = url.toLowerCase()
            val postfixs = arrayOf(".png", ".jpg", ".jpg", ".gif", ".mp3", ".mp4", ".zip", ".apk")
            if (url.startsWith("http://") || url.startsWith("https://")) {
                for (postfix in postfixs) {
                    if (url.endsWith(postfix)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 网络链接是否正确
     * The http link is correct or not
     *
     * @param url
     * @return
     */
    fun isHttpUrlFormRight(url: String?): Boolean {
        var url = url
        if (!TextUtils.isEmpty(url)) {
            url = url!!.toLowerCase()
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return true
            }
        }
        return false
    }

    fun isIP(addr: String?): Boolean {
        if (TextUtils.isEmpty(addr) || addr!!.length < 7 || addr.length > 15) {
            return false
        }
        /**
         * 判断IP格式和范围
         */
        val rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}"

        val pat = Pattern.compile(rexp)

        val mat = pat.matcher(addr)

        val ipAddress = mat.find()

        //============对之前的ip判断的bug在进行判断
        if (ipAddress == true) {
            val ips = addr.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (ips.size == 4) {
                try {
                    for (ip in ips) {
                        if (Integer.parseInt(ip) < 0 || Integer.parseInt(ip) > 255) {
                            return false
                        }

                    }
                } catch (e: Exception) {
                    return false
                }

                return true
            } else {
                return false
            }
        }

        return ipAddress
    }

    fun getFileName(url: String): String {
        var fileName = System.currentTimeMillis().toString()
        val endIndex = url.lastIndexOf('/')
        if (endIndex != -1 && url.length != endIndex + 1) {
            fileName = url.substring(endIndex + 1, url.length)
            val endIndex = fileName.lastIndexOf('?')
            if (endIndex != -1) fileName = fileName.substring(0, endIndex)
        }
        return fileName
    }

}
