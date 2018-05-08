/*
 *
 *  * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 *
 */

package org.chris.quick.m;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/14
 * @modifyInfo1 chriszou-16/10/14
 * @modifyContent
 */
public final class Log {
    private static boolean isDebug = true;

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Exception o_o) {
        if (isDebug) {
            android.util.Log.e(tag, msg, o_o);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Exception o_o) {
        if (isDebug) {
            android.util.Log.d(tag, msg, o_o);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            android.util.Log.w(tag, msg);
        }
    }
}
