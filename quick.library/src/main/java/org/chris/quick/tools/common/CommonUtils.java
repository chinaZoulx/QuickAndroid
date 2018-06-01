package org.chris.quick.tools.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

import org.chris.quick.m.Log;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CommonUtils {

    private static FixedSpeedScroller mScroller;

    /**
     * 设置ViewPager的滑动时间
     *
     * @param context
     * @param viewpager      ViewPager控件
     * @param DurationSwitch 滑动延时
     */
    public static void controlViewPagerSpeed(Context context, ViewPager viewpager, int DurationSwitch) {
        if (context == null) {
            return;
        }
        try {
            Field mField;
            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            if (mScroller == null) {
                mScroller = new FixedSpeedScroller(context, new AccelerateInterpolator());
            }
            mScroller.setmDuration(DurationSwitch);
            mField.set(viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SHA1码
     *
     * @param context
     * @return
     */
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得字体宽
     */
    public int getFontWidth(Paint paint, String str) {
        if (str == null || str.equals(""))
            return 0;
        Rect rect = new Rect();
        int length = str.length();
        paint.getTextBounds(str, 0, length, rect);
        return rect.width();
    }

    /**
     * 获得字体高度
     */
    public static int getFontHeight(Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds("正", 0, 1, rect);
        return rect.height();
    }

    /**
     * 获得字体高度
     */
    public static int getFontHeight(Paint paint, String txt) {
        Rect rect = new Rect();
        paint.getTextBounds(txt, 0, 1, rect);
        return rect.height();
    }

    /**
     * @param listView
     * @scene 使用场景：当与其他父控件冲突不能正常计算大小时。example:ScrollView嵌套ListView
     * @description 设置指定listView的高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() <= 0) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * get window manager
     *
     * @param context
     * @return 返回窗口管理信息类，通过其可获得设备信息，example:屏幕高宽
     */
    public static Display getScreenDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
        int statusHeight = 0;
        Rect localRect = new Rect();
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

    /**
     * 获取设备最大内存,单位为字节(B)
     *
     * @return
     */
    public static int getMaxMemory() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        return maxMemory;
    }

    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDcardOK() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取两点间距离,单位：px
     *
     * @param x1 第一个点
     * @param x2 第二个点
     * @return
     * @formula |AB| = sqrt((X1-X2)^2 + (Y1-Y2)^2)
     */
    public static float getDistance(Point x1, Point x2) {
        return getDistance(x1.x, x1.y, x2.x, x2.y);
    }

    public static float getDistance(float x1, float y1, float x2, float y2) {

        return getDistance(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * The distance between two points
     *
     * @param x |x1-x2|
     * @param y |y1-y2|
     * @return
     */
    public static float getDistance(float x, float y) {
        float distance = (float) Math.sqrt(x * x + y * y);
        return distance;
    }

    public static float getSystemAttrValue(Context context, int attrResId) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return getSystemAttrTypeValue(context, attrResId).getDimension(outMetrics);
    }

    public static TypedValue getSystemAttrTypeValue(Context context, int attrResId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrResId, typedValue, true);
        return typedValue;
    }

    /**
     * 获取intent中的内容
     *
     * @param intent
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getIntentValue(Intent intent, String key, T defaultValue) {
        Object value = defaultValue;
        try {
            if (defaultValue instanceof String) {
                value = intent.getStringExtra(key);
            } else if (defaultValue instanceof Integer) {
                value = intent.getIntExtra(key, 0);
            } else if (defaultValue instanceof Boolean) {
                value = intent.getBooleanExtra(key, false);
            } else if (defaultValue instanceof Serializable) {
                value = intent.getSerializableExtra(key);
            } else if (defaultValue instanceof Long) {
                value = intent.getLongExtra(key, 0);
            } else if (defaultValue instanceof Float) {
                value = intent.getFloatExtra(key, 0);
            } else if (defaultValue instanceof Double) {
                value = intent.getDoubleExtra(key, 0);
            } else if (defaultValue instanceof ArrayList) {
                value = intent.getStringArrayListExtra(key);
            } else if (defaultValue instanceof Bundle) {
                value = intent.getBundleExtra(key);
            }
        } catch (Exception o_O) {
            value = defaultValue;
            Log.e("转换错误", "获取intent内容失败：或许是因为Key不存在,若需要解决请手动添加类型转换");
            o_O.printStackTrace();
        }
        if (value == null) {
            value = defaultValue;
        }
        return (T) value;
    }

    public static View getView(Activity activity, @LayoutRes int resId) {
        return getView(activity, null, resId);
    }

    public static View getView(Activity activity, ViewGroup parent, @LayoutRes int resId) {
        View view;
        if (parent == null)
            view = LayoutInflater.from(activity).inflate(resId, null);
        else
            view = LayoutInflater.from(activity).inflate(resId, parent, false);
        return view;
    }

    /**
     * 设置ViewPager切换Item速度
     */
    public static class FixedSpeedScroller extends Scroller {

        private int mDuration = 1500; // 默认滑动速度 1500ms

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        /**
         * set animation time
         *
         * @param time
         */
        public void setmDuration(int time) {
            mDuration = time;
        }

        /**
         * get current animation time
         *
         * @return
         */
        public int getmDuration() {
            return mDuration;
        }
    }

    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else if (email.matches("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$")) {
            return true;
        } else {
            return false;
        }
    }

    public static void setupFitsSystemWindows(Activity activity, View view) {
        view.setPadding(view.getPaddingLeft(), CommonUtils.getStatusHeight(activity) + view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void setupFitsSystemWindowsFromToolbar(Activity activity, View view) {
        view.getLayoutParams().height = (int) (CommonUtils.getSystemAttrValue(activity, android.R.attr.actionBarSize) + CommonUtils.getStatusHeight(activity));
        view.setPadding(view.getPaddingLeft(), CommonUtils.getStatusHeight(activity) + view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 将字节转换为十六进制的字符串（）
     *
     * @param bytesCommand
     * @return
     * @from 忘了
     */
    public static String bytesToHexString(byte[] bytesCommand) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytesCommand == null || bytesCommand.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytesCommand.length; i++) {
            int v = bytesCommand[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将十六进制的字符串转换成字节
     *
     * @param commandStr 7E 18 00 07 00 04 01 02 03 04 00 05 00 1A 7E
     * @return
     * @throws NumberFormatException
     */
    public static byte[] parseCommand(String commandStr) throws NumberFormatException {
        String[] tempStr = commandStr.split(" ");
        byte[] commands = new byte[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            try {
                commands[i] = (byte) Integer.parseInt(tempStr[i], 16);
            } catch (Exception o_o) {
                commands[i] = 00;
                Log.e("命令转换出错", tempStr[i]);
            }
        }
        return commands;
    }
}
