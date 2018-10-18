package org.quick.library.m;

import java.lang.System;

/**
 * * 请在这里写上用途
 * *
 * * @author chris
 * * @Date 16/9/23
 * * @modifyInfo1 chris-16/9/23
 * * @modifyContent
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u001e\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0017J.\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J.\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0017J.\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017JF\u0010\u0018\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017H\u0002J\u001e\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0017J.\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J\u001e\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J.\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J\u001e\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0017J$\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#J.\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017JF\u0010!\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017H\u0002J\u001e\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0017J6\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J\u001e\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J6\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J\u001e\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0017J&\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017J6\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017JN\u0010%\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0017H\u0002J\u000e\u0010\'\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006("}, d2 = {"Lorg/quick/library/m/ImageManager;", "", "()V", "defaultEmpty", "", "getDefaultEmpty", "()I", "setDefaultEmpty", "(I)V", "defaultError", "getDefaultError", "setDefaultError", "cancelAllTasks", "", "context", "Landroid/content/Context;", "cleanAllCache", "clearDiskCache", "clearMemory", "loadAssetImage", "name", "", "imageView", "Landroid/widget/ImageView;", "loadCircleImage", "file", "Ljava/io/File;", "iv", "errorImg", "emptyImg", "resId", "url", "errImg", "loadImage", "listener", "Lcom/bumptech/glide/request/RequestListener;", "Landroid/graphics/Bitmap;", "loadRoundImage", "radius", "resumeAllTasks", "quick-library_debug"})
public final class ImageManager {
    private static int defaultError;
    private static int defaultEmpty;
    public static final org.quick.library.m.ImageManager INSTANCE = null;
    
    public final int getDefaultError() {
        return 0;
    }
    
    public final void setDefaultError(int p0) {
    }
    
    public final int getDefaultEmpty() {
        return 0;
    }
    
    public final void setDefaultEmpty(int p0) {
    }
    
    public final synchronized void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    public final synchronized void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId, int errorImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Activity 和 Fragment
     *     * @param file    图片file
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Activity 和 Fragment
     *     * @param file    图片file
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file, int errorImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Activity 和 Fragment
     *     * @param url     图片url
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    public final void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    com.bumptech.glide.request.RequestListener<android.graphics.Bitmap> listener) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context  可以是Activity 和 Fragment
     *     * @param url      图片url
     *     * @param errImg   加载出错显示的图片
     *     * @param emptyImg 加载前的默认图
     *     * @param iv       显示图片的ImageView
     */
    public final synchronized void loadImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, int errImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * @param context
     *     * @param url      允许为空
     *     * @param file     允许为空
     *     * @param resId    允许为空
     *     * @param errImg   允许为空
     *     * @param emptyImg 允许为空
     *     * @param iv
     */
    private final synchronized void loadImage(android.content.Context context, java.lang.String url, java.io.File file, int resId, int errImg, int emptyImg, android.widget.ImageView iv) {
    }
    
    /**
     * * 加载Assets中的图片
     *     *
     *     * @param context   可以是Activity 和 Fragment
     *     * @param name      图片名称
     *     * @param imageView 显示图片的ImageView
     */
    public final synchronized void loadAssetImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView imageView) {
    }
    
    public final synchronized void loadCircleImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    public final synchronized void loadCircleImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId, int errorImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param file    图片file
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadCircleImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param file    图片file
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadCircleImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file, int errorImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param url     图片url
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadCircleImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context  可以是Context 和 Fragment
     *     * @param url      图片url
     *     * @param errImg   加载出错显示的图片
     *     * @param emptyImg 加载前的默认图
     *     * @param iv       显示图片的ImageView
     */
    public final synchronized void loadCircleImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, int errImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * @param context
     *     * @param url      允许为空
     *     * @param file     允许为空
     *     * @param resId    允许为空
     *     * @param errImg   允许为空
     *     * @param emptyImg 允许为空
     *     * @param iv
     */
    private final synchronized void loadCircleImage(android.content.Context context, java.lang.String url, java.io.File file, int resId, int errImg, int emptyImg, android.widget.ImageView iv) {
    }
    
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resId, int radius, int errorImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param file    图片file
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param file    图片file
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file, int radius, int errorImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param url     图片url
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context 可以是Context 和 Fragment
     *     * @param url     图片url
     *     * @param iv      显示图片的ImageView
     */
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, int radius, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * 加载图片为圆形图片
     *     *
     *     * @param context  可以是Context 和 Fragment
     *     * @param url      图片url
     *     * @param errImg   加载出错显示的图片
     *     * @param emptyImg 加载前的默认图
     *     * @param iv       显示图片的ImageView
     */
    public final synchronized void loadRoundImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url, int radius, int errImg, int emptyImg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView iv) {
    }
    
    /**
     * * @param context
     *     * @param url      允许为空
     *     * @param file     允许为空
     *     * @param resId    允许为空
     *     * @param errImg   允许为空
     *     * @param emptyImg 允许为空
     *     * @param radius   圆角
     *     * @param iv
     */
    private final synchronized void loadRoundImage(android.content.Context context, java.lang.String url, java.io.File file, int resId, int radius, int errImg, int emptyImg, android.widget.ImageView iv) {
    }
    
    /**
     * * 取消所有正在下载或等待下载的任务。
     */
    public final synchronized void cancelAllTasks(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * * 恢复所有任务
     */
    public final synchronized void resumeAllTasks(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * * 清除内存缓存
     */
    public final void clearMemory(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * * 清除磁盘缓存
     */
    public final void clearDiskCache(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * * 清除所有缓存
     */
    public final synchronized void cleanAllCache(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    private ImageManager() {
        super();
    }
}