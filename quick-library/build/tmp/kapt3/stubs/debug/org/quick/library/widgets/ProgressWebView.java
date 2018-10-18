package org.quick.library.widgets;

import java.lang.System;

/**
 * * 带进度条的WebView
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0003\u0010\u0011\u0012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\fH\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lorg/quick/library/widgets/ProgressWebView;", "Landroid/webkit/WebView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "progressbar", "Landroid/widget/ProgressBar;", "onScrollChanged", "", "l", "", "t", "oldl", "oldt", "Companion", "MyWebChromeClient", "MyWebViewClient", "quick-library_debug"})
public final class ProgressWebView extends android.webkit.WebView {
    private final android.widget.ProgressBar progressbar = null;
    public static final org.quick.library.widgets.ProgressWebView.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    }
    
    public ProgressWebView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0092\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2 = {"Lorg/quick/library/widgets/ProgressWebView$MyWebChromeClient;", "Landroid/webkit/WebChromeClient;", "(Lorg/quick/library/widgets/ProgressWebView;)V", "onProgressChanged", "", "view", "Landroid/webkit/WebView;", "newProgress", "", "quick-library_debug"})
    class MyWebChromeClient extends android.webkit.WebChromeClient {
        
        @java.lang.Override()
        public void onProgressChanged(@org.jetbrains.annotations.NotNull()
        android.webkit.WebView view, int newProgress) {
        }
        
        public MyWebChromeClient() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, d2 = {"Lorg/quick/library/widgets/ProgressWebView$MyWebViewClient;", "Landroid/webkit/WebViewClient;", "(Lorg/quick/library/widgets/ProgressWebView;)V", "onReceivedSslError", "", "view", "Landroid/webkit/WebView;", "handler", "Landroid/webkit/SslErrorHandler;", "error", "Landroid/net/http/SslError;", "quick-library_debug"})
    final class MyWebViewClient extends android.webkit.WebViewClient {
        
        @java.lang.Override()
        public void onReceivedSslError(@org.jetbrains.annotations.NotNull()
        android.webkit.WebView view, @org.jetbrains.annotations.NotNull()
        android.webkit.SslErrorHandler handler, @org.jetbrains.annotations.NotNull()
        android.net.http.SslError error) {
        }
        
        public MyWebViewClient() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u001a\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a8\u0006\n"}, d2 = {"Lorg/quick/library/widgets/ProgressWebView$Companion;", "", "()V", "isScheme", "", "url", "", "supportIntentAndScheme", "activity", "Landroid/app/Activity;", "quick-library_debug"})
    public static final class Companion {
        
        /**
         * * 打开第三方应用
         *         *
         *         * @param url
         *         * @return
         */
        public final boolean supportIntentAndScheme(@org.jetbrains.annotations.Nullable()
        android.app.Activity activity, @org.jetbrains.annotations.Nullable()
        java.lang.String url) {
            return false;
        }
        
        public final boolean isScheme(@org.jetbrains.annotations.Nullable()
        java.lang.String url) {
            return false;
        }
        
        private Companion() {
            super();
        }
    }
}