/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package org.chris.quick.tools.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕工具
 */
public class ScreenUtils {

    /**
     * get window manager
     *
     * @param context
     * @return 返回窗口管理信息类，通过其可获得设备信息，example:屏幕高宽
     */
    public static Display getScreenDisplay ( Context context ) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display mDisplay = wm.getDefaultDisplay();
        return mDisplay;
    }

    /**
     * 获取屏幕宽度,单位：PX
     *
     * @param context
     * @return The screen width
     */
    public static int getScreenWidth(Context context) {
        int width = getScreenDisplay(context).getWidth();
        return width;
    }

    /**
     * 获取屏幕高度，单位：px
     *
     * @param context
     * @return The screen height
     */
    public static int getScreenHeight(Context context) {
        int height = getScreenDisplay(context).getHeight();
        return height;
    }

    /**
     * 获取屏幕密度（DPI）
     *
     * @return 屏幕密度
     */
    public static int getScreenDensityDPI() {
        DisplayMetrics dm = new DisplayMetrics();
        int density = dm.densityDpi;
        return density;
    }

    /**
     * 获取状态栏高度,单位：PX
     *
     * @param activity
     * @return 状态栏高度
     */
    public static int getStatusHeight(Activity activity) {
        int  statusHeight = 0;
        Rect localRect    = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                                                  .getField("status_bar_height").get(localObject)
                                                  .toString());
                statusHeight = activity.getResources()
                                       .getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
