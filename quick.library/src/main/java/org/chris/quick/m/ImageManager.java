package org.chris.quick.m;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.Util;

import org.chris.quick.R;
import org.chris.quick.m.glide.GlideCircleTransform;
import org.chris.quick.m.glide.GlideRoundTransform;

import java.io.File;

/**
 * 请在这里写上用途
 *
 * @author chris
 * @Date 16/9/23
 * @modifyInfo1 chris-16/9/23
 * @modifyContent
 */

public class ImageManager {
    public static final int defaultError = R.drawable.ic_broken_image_gray_24dp;
    public static final int defaultEmpty = R.drawable.ic_image_gray_24dp;

    public static synchronized void loadImage(Context context, int resId, @NonNull ImageView iv) {

        loadImage(context, resId, -1, -1, iv);
    }

    public static synchronized void loadImage(Context context, int resId, int errorImg, int emptyImg, @NonNull ImageView iv) {
        loadImage(context, null, null, resId, errorImg, emptyImg, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Activity 和 Fragment
     * @param file    图片file
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadImage(Context context, File file, @NonNull ImageView iv) {
        loadImage(context, file, -1, -1, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Activity 和 Fragment
     * @param file    图片file
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadImage(Context context, File file, int errorImg, int emptyImg, @NonNull ImageView iv) {
        loadImage(context, null, file, -1, errorImg, emptyImg, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Activity 和 Fragment
     * @param url     图片url
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadImage(Context context, String url, @NonNull ImageView iv) {
        loadImage(context, url, -1, -1, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context  可以是Activity 和 Fragment
     * @param url      图片url
     * @param errImg   加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param iv       显示图片的ImageView
     */
    public static synchronized void loadImage(Context context, String url, int errImg, int emptyImg, @NonNull ImageView iv) {
        loadImage(context, url, null, -1, errImg, emptyImg, iv);
    }

    /**
     * @param context
     * @param url      允许为空
     * @param file     允许为空
     * @param resId    允许为空
     * @param errImg   允许为空
     * @param emptyImg 允许为空
     * @param iv
     */
    private static synchronized void loadImage(Context context, String url, File file, int resId, int errImg, int emptyImg, @NonNull ImageView iv) {
        if (Util.isOnMainThread() && context != null) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                    return;
                }
            }
            if (errImg == -1) {
                errImg = defaultError;
            }
            if (emptyImg == -1) {
                emptyImg = defaultEmpty;
            }
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url).placeholder(emptyImg).error(errImg).dontAnimate().into(iv);
            }
            if (file != null && file.exists()) {
                Glide.with(context).load(file).placeholder(emptyImg).error(errImg).dontAnimate().into(iv);
            }
            if (resId != -1) {
                Glide.with(context).load(resId).placeholder(emptyImg).error(errImg).dontAnimate().into(iv);
            }
        }
    }

    /**
     * 加载Assets中的图片
     *
     * @param context   可以是Activity 和 Fragment
     * @param name      图片名称
     * @param imageView 显示图片的ImageView
     */
    public static synchronized void loadAssetImage(Context context, final String name, final ImageView imageView) {

        if (Util.isOnMainThread())
            Glide.with(context).load("file:///android_asset/" + name).into(imageView);
    }

    /**
     * 加载gif图片
     *
     * @param context 可以是Activity 和 Fragment
     * @param url     图片url
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadGifImage(Context context, String url, ImageView iv) {

        if (Util.isOnMainThread())
            Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static synchronized void loadGifImage(Context context, int resId, ImageView iv) {

        if (Util.isOnMainThread())
            Glide.with(context).load(resId).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    /**
     * 加载gif图片
     *
     * @param context  可以是Activity 和 Fragment
     * @param url      图片url
     * @param errorImg 加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param iv       显示图片的ImageView
     */
    public static synchronized void loadGifImage(Context context, String url, int errorImg, int emptyImg, ImageView iv) {
        if (Util.isOnMainThread())
            Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(emptyImg).error(errorImg).into(iv);
    }

    public static synchronized void loadCircleImage(Context context, int resId, ImageView iv) {

        loadCircleImage(context, resId, -1, -1, iv);
    }

    public static synchronized void loadCircleImage(Context context, int resId, int errorImg, int emptyImg, ImageView iv) {
        loadCircleImage(context, null, null, resId, errorImg, emptyImg, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Context 和 Fragment
     * @param file    图片file
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadCircleImage(Context context, File file, ImageView iv) {
        loadCircleImage(context, file, -1, -1, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Context 和 Fragment
     * @param file    图片file
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadCircleImage(Context context, File file, int errorImg, int emptyImg, ImageView iv) {
        loadCircleImage(context, null, file, -1, errorImg, emptyImg, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Context 和 Fragment
     * @param url     图片url
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadCircleImage(Context context, String url, ImageView iv) {
        loadCircleImage(context, url, -1, -1, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context  可以是Context 和 Fragment
     * @param url      图片url
     * @param errImg   加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param iv       显示图片的ImageView
     */
    public static synchronized void loadCircleImage(Context context, String url, int errImg, int emptyImg, ImageView iv) {
        loadCircleImage(context, url, null, -1, errImg, emptyImg, iv);
    }

    /**
     * @param context
     * @param url      允许为空
     * @param file     允许为空
     * @param resId    允许为空
     * @param errImg   允许为空
     * @param emptyImg 允许为空
     * @param iv
     */
    private static synchronized void loadCircleImage(Context context, String url, File file, int resId, int errImg, int emptyImg, ImageView iv) {
        if (Util.isOnMainThread() && context != null) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                    return;
                }
            }
            if (errImg == -1) {
                errImg = defaultError;
            }
            if (emptyImg == -1) {
                emptyImg = defaultEmpty;
            }
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url).placeholder(emptyImg).error(errImg).transform(new GlideCircleTransform(context)).dontAnimate().into(iv);
            }
            if (file != null && file.exists()) {
                Glide.with(context).load(file).placeholder(emptyImg).error(errImg).transform(new GlideCircleTransform(context)).dontAnimate().into(iv);
            }
            if (resId != -1) {
                Glide.with(context).load(resId).placeholder(emptyImg).error(errImg).transform(new GlideCircleTransform(context)).dontAnimate().into(iv);
            }
        }
    }

    public static synchronized void loadRoundImage(Context context, int resId, ImageView iv) {

        loadRoundImage(context, resId, 4, -1, -1, iv);
    }

    public static synchronized void loadRoundImage(Context context, int resId, int radius, int errorImg, int emptyImg, ImageView iv) {
        loadRoundImage(context, null, null, resId, radius, errorImg, emptyImg, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Context 和 Fragment
     * @param file    图片file
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadRoundImage(Context context, File file, ImageView iv) {
        loadRoundImage(context, file, -1, 4, -1, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Context 和 Fragment
     * @param file    图片file
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadRoundImage(Context context, File file, int radius, int errorImg, int emptyImg, ImageView iv) {
        loadRoundImage(context, null, file, -1, radius, errorImg, emptyImg, iv);
    }

    /**
     * 加载图片为圆形图片
     *
     * @param context 可以是Context 和 Fragment
     * @param url     图片url
     * @param iv      显示图片的ImageView
     */
    public static synchronized void loadRoundImage(Context context, String url, ImageView iv) {
        loadRoundImage(context, url, -1, 4, -1, iv);
    }
    /**
     * 加载图片为圆形图片
     *
     * @param context  可以是Context 和 Fragment
     * @param url      图片url
     * @param iv       显示图片的ImageView
     */
    public static synchronized void loadRoundImage(Context context, String url, int radius, ImageView iv) {
        loadRoundImage(context, url, null, -1, radius, -1, -1, iv);
    }
    /**
     * 加载图片为圆形图片
     *
     * @param context  可以是Context 和 Fragment
     * @param url      图片url
     * @param errImg   加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param iv       显示图片的ImageView
     */
    public static synchronized void loadRoundImage(Context context, String url, int radius, int errImg, int emptyImg, ImageView iv) {
        loadRoundImage(context, url, null, -1, radius, errImg, emptyImg, iv);
    }

    /**
     * @param context
     * @param url      允许为空
     * @param file     允许为空
     * @param resId    允许为空
     * @param errImg   允许为空
     * @param emptyImg 允许为空
     * @param radius   圆角
     * @param iv
     */
    private static synchronized void loadRoundImage(Context context, String url, File file, int resId, int radius, int errImg, int emptyImg, ImageView iv) {
        if (Util.isOnMainThread() && context != null) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                    return;
                }
            }
            if (errImg == -1) {
                errImg = defaultError;
            }
            if (emptyImg == -1) {
                emptyImg = defaultEmpty;
            }
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url).placeholder(emptyImg).error(errImg).transform(new GlideRoundTransform(context, radius)).dontAnimate().into(iv);
            }
            if (file != null && file.exists()) {
                Glide.with(context).load(file).placeholder(emptyImg).error(errImg).transform(new GlideRoundTransform(context, radius)).dontAnimate().into(iv);
            }
            if (resId != -1) {
                Glide.with(context).load(resId).placeholder(emptyImg).error(errImg).transform(new GlideRoundTransform(context, radius)).dontAnimate().into(iv);
            }
        }
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static synchronized void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static synchronized void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }


    /*
     * 清除内存缓存
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /*
     * 清除磁盘缓存
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /*
     * 清除所有缓存
     * @param context 上下文
     */
    public static synchronized void cleanAllCache(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}
