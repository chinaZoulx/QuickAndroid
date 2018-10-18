package org.quick.library.b.fragments;

import java.lang.System;

/**
 * * Created by work on 2017/9/22.
 * *
 * * @author chris zou
 * * @mail chrisSpringSmell@gmail.com
 */
@android.annotation.SuppressLint(value = {"ValidFragment"})
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\b\u0017\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0013\b\u0007\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0002J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0002J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0003H\u0002J\u0006\u0010\u0014\u001a\u00020\u000bJ\b\u0010\u0015\u001a\u00020\u0010H\u0016J\b\u0010\u0016\u001a\u00020\u0010H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0017J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0010H\u0002J\b\u0010\u001c\u001a\u00020\u0010H\u0016J\u0010\u0010\u001c\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lorg/quick/library/b/fragments/WebFragment;", "Lorg/quick/library/b/BaseFragment;", "baseUrl", "", "(Ljava/lang/String;)V", "errorView", "Landroid/view/View;", "lastErrorUrl", "webViewClient", "Landroid/webkit/WebViewClient;", "compat", "", "view", "Landroid/webkit/WebView;", "url", "downloadApk", "", "apkUrl", "goDownload", "isAuthorizationUrl", "onBackPressed", "onBindListener", "onDestroy", "onInit", "onInitLayout", "onResultLayoutResId", "", "setError", "start", "Companion", "quick-library_debug"})
public class WebFragment extends org.quick.library.b.BaseFragment {
    private android.webkit.WebViewClient webViewClient;
    private java.lang.String lastErrorUrl;
    private android.view.View errorView;
    private java.lang.String baseUrl;
    public static final org.quick.library.b.fragments.WebFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public int onResultLayoutResId() {
        return 0;
    }
    
    @java.lang.Override()
    public void onInit() {
    }
    
    @android.annotation.SuppressLint(value = {"SetJavaScriptEnabled"})
    @java.lang.Override()
    public void onInitLayout() {
    }
    
    @java.lang.Override()
    public void start() {
    }
    
    @java.lang.Override()
    public void onBindListener() {
    }
    
    public final void start(@org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    private final boolean compat(android.webkit.WebView view, java.lang.String url) {
        return false;
    }
    
    private final void setError() {
    }
    
    /**
     * * 是否是支付链接
     *     *
     *     * @param url
     *     * @return
     */
    private final boolean isAuthorizationUrl(java.lang.String url) {
        return false;
    }
    
    private final void downloadApk(java.lang.String apkUrl) {
    }
    
    private final void goDownload(java.lang.String apkUrl) {
    }
    
    public final boolean onBackPressed() {
        return false;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    public WebFragment(@org.jetbrains.annotations.Nullable()
    java.lang.String baseUrl) {
        super();
    }
    
    public WebFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lorg/quick/library/b/fragments/WebFragment$Companion;", "", "()V", "instance", "Lorg/quick/library/b/fragments/WebFragment;", "getInstance", "()Lorg/quick/library/b/fragments/WebFragment;", "baseUrl", "", "quick-library_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final org.quick.library.b.fragments.WebFragment getInstance() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final org.quick.library.b.fragments.WebFragment getInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String baseUrl) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}