package org.quick.library.b.activities;

import java.lang.System;

/**
 * * @author Chris zou
 * * @Date 2016/11/3
 * * @modifyInfo1 Zuo-2016/11/3
 * * @modifyContent
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\b\u0010\f\u001a\u00020\nH\u0014J\b\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0013"}, d2 = {"Lorg/quick/library/b/activities/WebActivity;", "Lorg/quick/library/b/BaseActivity;", "()V", "webViewFragment", "Lorg/quick/library/b/fragments/WebFragment;", "getWebViewFragment", "()Lorg/quick/library/b/fragments/WebFragment;", "setWebViewFragment", "(Lorg/quick/library/b/fragments/WebFragment;)V", "onBackPressed", "", "onBindListener", "onDestroy", "onInit", "onInitLayout", "onResultLayoutResId", "", "start", "Companion", "quick-library_debug"})
public final class WebActivity extends org.quick.library.b.BaseActivity {
    @org.jetbrains.annotations.NotNull()
    public org.quick.library.b.fragments.WebFragment webViewFragment;
    public static final org.quick.library.b.activities.WebActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final org.quick.library.b.fragments.WebFragment getWebViewFragment() {
        return null;
    }
    
    public final void setWebViewFragment(@org.jetbrains.annotations.NotNull()
    org.quick.library.b.fragments.WebFragment p0) {
    }
    
    @java.lang.Override()
    public int onResultLayoutResId() {
        return 0;
    }
    
    @java.lang.Override()
    public void onInit() {
    }
    
    @java.lang.Override()
    public void onInitLayout() {
    }
    
    @java.lang.Override()
    public void onBindListener() {
    }
    
    @java.lang.Override()
    public void start() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    public WebActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b\u00a8\u0006\n"}, d2 = {"Lorg/quick/library/b/activities/WebActivity$Companion;", "", "()V", "startAction", "", "context", "Landroid/content/Context;", "title", "", "url", "quick-library_debug"})
    public static final class Companion {
        
        public final void startAction(@org.jetbrains.annotations.Nullable()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.Nullable()
        java.lang.String url) {
        }
        
        private Companion() {
            super();
        }
    }
}