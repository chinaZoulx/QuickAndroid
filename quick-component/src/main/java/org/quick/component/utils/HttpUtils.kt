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
    fun httpEventGetFormat(url: String, bundle: Bundle?): String {
        var url = url
        if (null != bundle) {
            url += "?"
            val keySet = bundle.keySet()
            for (key in keySet) {
                val value = bundle.get(key)!!.toString() + ""
                url += String.format("%s=%s&", key, value)
            }
            url.substring(0, url.length - 1)
        }
        return url
    }

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param map
     */
    fun httpEventGetFormat(url: String, map: Map<*, *>?): String {
        var url = url
        if (null != map) {
            url += "?"
            val keySet = map.keys
            for (key in keySet) {
                val value = map[key].toString() + ""
                url += String.format("%s=%s&", key, value)
            }
            url.substring(0, url.length - 1)
        }
        return url
    }

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param t
     */
    fun <T> httpEventGetFormat(url: String, t: T): String {
        if (t is Bundle) {
            httpEventGetFormat(url, t as Bundle)
        } else if (t is Map<*, *>) {
            httpEventGetFormat(url, t as Map<*, *>)
        }

        return url
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
        if (TextUtils.isEmpty(addr)||addr!!.length < 7 || addr.length > 15) {
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

}
