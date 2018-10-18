package org.quick.component.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.support.annotation.AttrRes
import android.support.annotation.LayoutRes
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import org.quick.component.QuickAndroid
import java.io.Serializable
import java.util.ArrayList


/**
 * Created by chris on 2016/5/9.
 */
object ViewUtils {

    /**
     * @param context
     * @param postfix 后缀
     * @return
     * @throws Resources.NotFoundException
     */
    @Throws(Resources.NotFoundException::class)
    fun getMipmapResId(prefix: String, postfix: String): Int = QuickAndroid.applicationContext.resources.getIdentifier(prefix + postfix, "mipmap", QuickAndroid.applicationContext.packageName)

    /**
     * @param context
     * @param postfix 后缀
     * @return
     * @throws Resources.NotFoundException
     */
    @Throws(Resources.NotFoundException::class)
    fun getDrawableResId(prefix: String, postfix: String): Int = QuickAndroid.applicationContext.resources.getIdentifier(prefix + postfix, "drawable", QuickAndroid.applicationContext.packageName)

    /**
     * @param context
     * @param postfix 后缀
     * @return
     * @throws Resources.NotFoundException
     */
    @Throws(Resources.NotFoundException::class)
    fun getViewId(prefix: String, postfix: String): Int = QuickAndroid.applicationContext.resources.getIdentifier(prefix + postfix, "id", QuickAndroid.applicationContext.packageName)

    /**
     * @param context
     * @param imageView
     * @param postfix   资源名称后缀
     */
    fun setImageViewForDrawable(imageView: ImageView, prefix: String, postfix: String) {
        imageView.setImageResource(getDrawableResId(prefix, postfix))
    }

    /**
     * 设置imageview的资源
     *
     * @param context
     * @param imageView
     * @param postfix   资源名称的后缀
     */
    fun setImageViewForMipmap(imageView: ImageView, prefix: String, postfix: String) {
        imageView.setImageResource(getMipmapResId(prefix, postfix))
    }

    /**
     * 属性筛选
     */
    fun viewAttrHasValue(typedArray: TypedArray, @AttrRes attr: Int): Boolean {
        for (i in 0 until typedArray.indexCount)
            if (typedArray.getIndex(i) == attr)
                return true
        return false
    }

    fun getSystemAttrValue(context: Context, attrResId: Int): Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return getSystemAttrTypeValue(context, attrResId).getDimension(outMetrics)
    }

    fun getSystemAttrTypeValue(context: Context, attrResId: Int): TypedValue {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrResId, typedValue, true)
        return typedValue
    }

    /**
     * 获取intent中的内容
     *
     * @param intent
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
    </T> */
    fun <T> getIntentValue(intent: Intent, key: String, defaultValue: T): T {
        var value: Any? = defaultValue
        try {
            when (defaultValue) {
                is String -> value = intent.getStringExtra(key)
                is Int -> value = intent.getIntExtra(key, 0)
                is Boolean -> value = intent.getBooleanExtra(key, false)
                is Serializable -> value = intent.getSerializableExtra(key)
                is Long -> value = intent.getLongExtra(key, 0)
                is Float -> value = intent.getFloatExtra(key, 0f)
                is Double -> value = intent.getDoubleExtra(key, 0.0)
                is ArrayList<*> -> value = intent.getStringArrayListExtra(key)
                is Bundle -> value = intent.getBundleExtra(key)
            }
        } catch (o_O: Exception) {
            value = defaultValue
            Log.e("转换错误", "获取intent内容失败：或许是因为Key不存在,若需要解决请手动添加类型转换")
            o_O.printStackTrace()
        }

        if (value == null) {
            value = defaultValue
        }
        return value as T
    }

    fun getView(activity: Activity, @LayoutRes resId: Int): View {
        return getView(activity, null, resId)
    }

    fun getView(activity: Activity, parent: ViewGroup?, @LayoutRes resId: Int): View {
        return if (parent == null)
            LayoutInflater.from(activity).inflate(resId, null)
        else
            LayoutInflater.from(activity).inflate(resId, parent, false)
    }

    /**
     * 获得字体高度
     */
    fun getFontHeight(paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds("正", 0, 1, rect)
        return rect.height()
    }

    /**
     * 获得字体宽
     */
    fun getFontWidth(paint: Paint, str: String?): Int {
        if (str == null || str == "")
            return 0
        val rect = Rect()
        val length = str.length
        paint.getTextBounds(str, 0, length, rect)
        return rect.width()
    }

    fun setupFitsSystemWindows(activity: Activity, view: View) {
        view.setPadding(view.paddingLeft, DevicesUtils.getStatusHeight(activity) + view.paddingTop, view.paddingRight, view.paddingBottom)
    }

    fun setupFitsSystemWindowsFromToolbar(activity: Activity, view: View) {
        view.layoutParams.height = (getSystemAttrValue(activity, android.R.attr.actionBarSize) + DevicesUtils.getStatusHeight(activity)).toInt()
        view.setPadding(view.paddingLeft, DevicesUtils.getStatusHeight(activity) + view.paddingTop, view.paddingRight, view.paddingBottom)
    }
}
