package org.chris.quick.tools.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HttpUtils {

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param bundle
     */
    public static String httpEventGetFormat(String url, Bundle bundle) {
        if (null != bundle) {
            url += "?";
            Set<String> keySet = bundle.keySet();
            for (String key : keySet) {
                String value = bundle.get(key) + "";
                url += String.format("%s=%s&", key, value);
            }
            url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param map
     */
    public static String httpEventGetFormat(String url, Map map) {
        if (null != map) {
            url += "?";
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String value = map.get(key) + "";
                url += String.format("%s=%s&", key, value);
            }
            url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * 格式化Get网络请求的URL
     *
     * @param url
     * @param t
     */
    public static <T> String httpEventGetFormat(String url, T t) {
        if (t instanceof Bundle) {
            httpEventGetFormat(url, (Bundle) t);
        } else if (t instanceof Map) {
            httpEventGetFormat(url, (Map) t);
        }

        return url;
    }

    /**
     * 图片链接是否正确
     * The image link correct or not
     *
     * @param url
     * @return
     */
    public static Boolean isPicUrlFormatRight(String url) {
        url = url.toLowerCase();
        String[] postfixs = new String[]{"png", "jpg", "jpg", "gif"};
        if (url.startsWith("http://") || url.startsWith("https://")) {
            for (String postfix : postfixs) {
                if (url.endsWith(postfix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 文件链接是否正确
     * The file link correct or not
     *
     * @param url
     * @return
     */
    public static Boolean isFileUrlFormRight(String url) {
        if (!TextUtils.isEmpty(url)) {
            url = url.toLowerCase();
            String[] postfixs = new String[]{".png", ".jpg", ".jpg", ".gif", ".mp3", ".mp4", ".zip",".apk"};
            if (url.startsWith("http://") || url.startsWith("https://")) {
                for (String postfix : postfixs) {
                    if (url.endsWith(postfix)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 网络链接是否正确
     * The http link is correct or not
     *
     * @param url
     * @return
     */
    public static Boolean isHttpUrlFormRight(String url) {
        if (!TextUtils.isEmpty(url)) {
            url = url.toLowerCase();
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return 返回是否有网
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivitymanager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivitymanager == null) {
            return false;
        }
        NetworkInfo info = connectivitymanager.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return false;
        }
        return true;
    }

    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        //============对之前的ip判断的bug在进行判断
        if (ipAddress == true) {
            String ips[] = addr.split("\\.");

            if (ips.length == 4) {
                try {
                    for (String ip : ips) {
                        if (Integer.parseInt(ip) < 0 || Integer.parseInt(ip) > 255) {
                            return false;
                        }

                    }
                } catch (Exception e) {
                    return false;
                }

                return true;
            } else {
                return false;
            }
        }

        return ipAddress;
    }

}
