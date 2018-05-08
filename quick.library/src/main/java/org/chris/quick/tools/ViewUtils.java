package org.chris.quick.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by chris on 2016/5/9.
 */
public class ViewUtils {

    public static boolean viewAttrHasValue(TypedArray typedArray, @AttrRes int attr) {
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            if (typedArray.getIndex(i) == attr) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置本地
     *
     * @param context
     * @param imageView
     * @param prefix    前缀
     * @param postfix   后缀
     */
    public static void setImgResource(Context context, ImageView imageView, String prefix, String postfix) {
        String resName = prefix + postfix;
        try {
            int resId = context.getResources().getIdentifier(resName, "mipmap", context.getPackageName());
            imageView.setImageResource(resId);
        } catch (Exception ex) {
            Log.e("setImgResource", "set image resource aborted");
        }
    }

    public static float getSystemAttrValue(Context context, int attrResId) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
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
            org.chris.quick.m.Log.e("转换错误", "获取intent内容失败：或许是因为Key不存在,若需要解决请手动添加类型转换");
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
}
