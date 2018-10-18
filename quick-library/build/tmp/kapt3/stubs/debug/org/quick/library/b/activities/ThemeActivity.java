package org.quick.library.b.activities;

import java.lang.System;

/**
 * * 对外开放的类，请继承该类
 * * 使用该类须隐藏title，主题城需使用兼容的风格，详情请查看Demo的mainifests
 * * Created by chris on 2016/6/8.
 * *
 * * @author chris Zou
 * * @date 2016/6/8.
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0010\r\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 j2\u00020\u0001:\u0001jB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010+\u001a\u00020,H\u0002J\u0006\u0010-\u001a\u00020\u001aJ!\u0010.\u001a\u0002H/\"\u0004\b\u0000\u0010/2\u0006\u00100\u001a\u00020\u001a2\u0006\u00101\u001a\u0002H/\u00a2\u0006\u0002\u00102J\u001f\u00103\u001a\u0002H/\"\b\b\u0000\u0010/*\u00020\b2\b\b\u0001\u00104\u001a\u00020*\u00a2\u0006\u0002\u00105J\'\u00103\u001a\u0002H/\"\b\b\u0000\u0010/*\u00020\b2\b\b\u0001\u00104\u001a\u00020*2\u0006\u00106\u001a\u00020\b\u00a2\u0006\u0002\u00107J\b\u00108\u001a\u00020,H&J\b\u00109\u001a\u00020,H\u0002J\"\u0010:\u001a\u00020,2\u0006\u0010;\u001a\u00020*2\u0006\u0010<\u001a\u00020*2\b\u0010=\u001a\u0004\u0018\u00010>H\u0014J\u0012\u0010?\u001a\u00020,2\b\u0010@\u001a\u0004\u0018\u00010AH\u0014J\u0010\u0010B\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020CH\u0016J\b\u0010D\u001a\u00020,H\u0014J\u0012\u0010E\u001a\u00020\u001c2\b\u0010F\u001a\u0004\u0018\u00010%H\u0016J\b\u0010G\u001a\u00020*H%J\n\u0010H\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010I\u001a\u00020,H\u0004J\u0010\u0010J\u001a\u00020,2\u0006\u0010K\u001a\u00020LH\u0004J\u0018\u0010J\u001a\u00020,2\u0006\u0010M\u001a\u00020*2\u0006\u0010K\u001a\u00020LH\u0004J(\u0010J\u001a\u00020,2\b\b\u0002\u0010M\u001a\u00020*2\b\b\u0002\u0010N\u001a\u00020\u001c2\n\b\u0002\u0010K\u001a\u0004\u0018\u00010LH\u0005J5\u0010O\u001a\u00020,2\b\b\u0001\u0010)\u001a\u00020*2#\u0010#\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010%\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b((\u0012\u0004\u0012\u00020\u001c0$J)\u0010P\u001a\u00020,2\u0006\u0010K\u001a\u00020Q2\u0014\b\u0001\u0010R\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0S\"\u00020\b\u00a2\u0006\u0002\u0010TJ\u001e\u0010P\u001a\u00020,2\u0006\u0010K\u001a\u00020Q2\f\b\u0001\u0010U\u001a\u00020V\"\u00020*H\u0004J\u001c\u0010W\u001a\u00020,2\u0006\u00106\u001a\u00020\b2\n\b\u0002\u0010K\u001a\u0004\u0018\u00010LH\u0005J\u0012\u0010W\u001a\u00020,2\b\b\u0001\u00104\u001a\u00020*H\u0004J\u0016\u0010X\u001a\u00020,2\u0006\u0010Y\u001a\u00020*2\u0006\u00106\u001a\u00020\bJ)\u0010X\u001a\u00020,2\u0006\u0010Y\u001a\u00020*2\u0014\b\u0001\u0010R\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0S\"\u00020\b\u00a2\u0006\u0002\u0010ZJ\u001c\u0010X\u001a\u00020,2\u0006\u0010Y\u001a\u00020*2\f\b\u0001\u0010U\u001a\u00020V\"\u00020*J\b\u0010[\u001a\u00020,H\u0002J \u0010\\\u001a\u00020,2\u0006\u00106\u001a\u00020\b2\u0006\u0010]\u001a\u00020^2\u0006\u0010K\u001a\u00020LH\u0004J2\u0010\\\u001a\u00020,2\b\u00106\u001a\u0004\u0018\u00010\b2\u0006\u0010]\u001a\u00020^2\n\b\u0002\u0010_\u001a\u0004\u0018\u00010^2\n\b\u0002\u0010K\u001a\u0004\u0018\u00010LH\u0005J\u0018\u0010\\\u001a\u00020,2\u0006\u0010]\u001a\u00020^2\u0006\u0010K\u001a\u00020LH\u0004J(\u0010\\\u001a\u00020,2\u0006\u0010]\u001a\u00020^2\n\b\u0002\u0010_\u001a\u0004\u0018\u00010^2\n\b\u0002\u0010K\u001a\u0004\u0018\u00010LH\u0005J\u0010\u0010`\u001a\u00020,2\u0006\u0010]\u001a\u00020^H\u0004J\u0018\u0010`\u001a\u00020,2\u0006\u0010]\u001a\u00020^2\u0006\u0010a\u001a\u00020*H\u0004J4\u0010`\u001a\u00020,2\u0006\u0010]\u001a\u00020^2\b\b\u0002\u0010b\u001a\u00020*2\u0006\u0010c\u001a\u00020*2\u0006\u0010d\u001a\u00020*2\b\b\u0002\u0010a\u001a\u00020*H\u0004JJ\u0010e\u001a\u00020,2\u0006\u0010f\u001a\u00020g28\u0010h\u001a4\u0012\u0013\u0012\u00110*\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b(<\u0012\u0015\u0012\u0013\u0018\u00010>\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b(=\u0012\u0004\u0012\u00020,0iH\u0004R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\u001c8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u001eR\u0014\u0010\"\u001a\u00020\u001c8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\u001eR-\u0010#\u001a!\u0012\u0015\u0012\u0013\u0018\u00010%\u00a2\u0006\f\b&\u0012\b\b\'\u0012\u0004\b\b((\u0012\u0004\u0012\u00020\u001c\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006k"}, d2 = {"Lorg/quick/library/b/activities/ThemeActivity;", "Landroid/support/v7/app/AppCompatActivity;", "()V", "activity", "Landroid/app/Activity;", "getActivity", "()Landroid/app/Activity;", "appBaseLayoutContainer", "Landroid/view/View;", "getAppBaseLayoutContainer", "()Landroid/view/View;", "setAppBaseLayoutContainer", "(Landroid/view/View;)V", "appBaseToolbar", "Landroid/support/v7/widget/Toolbar;", "getAppBaseToolbar", "()Landroid/support/v7/widget/Toolbar;", "setAppBaseToolbar", "(Landroid/support/v7/widget/Toolbar;)V", "appContentContainer", "Landroid/widget/FrameLayout;", "getAppContentContainer", "()Landroid/widget/FrameLayout;", "setAppContentContainer", "(Landroid/widget/FrameLayout;)V", "errorServiceHint", "", "isDefaultToolbar", "", "isInit", "()Z", "setInit", "(Z)V", "isShowTitle", "isUsingBaseLayout", "onMenuItemClickListener", "Lkotlin/Function1;", "Landroid/view/MenuItem;", "Lkotlin/ParameterName;", "name", "menu", "resMenu", "", "checkNotNullToolbar", "", "getErrorServiceHint", "getTransmitValue", "T", "key", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "getView", "resId", "(I)Landroid/view/View;", "view", "(ILandroid/view/View;)Landroid/view/View;", "init", "initView", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "Landroid/view/Menu;", "onDestroy", "onOptionsItemSelected", "item", "onResultLayoutResId", "onResultToolbar", "setBackInvalid", "setBackValid", "onClickListener", "Landroid/view/View$OnClickListener;", "backIcon", "isValid", "setMenu", "setOnClickListener", "Lorg/quick/component/callback/OnClickListener2;", "views", "", "(Lorg/quick/component/callback/OnClickListener2;[Landroid/view/View;)V", "resIds", "", "setRightView", "setVisibility", "visibility", "(I[Landroid/view/View;)V", "setupTitle", "showSnackbar", "content", "", "actionTxt", "showToast", "duration", "gravity", "xOffset", "yOffset", "startActivity", "builder", "Lorg/quick/component/QuickActivity$Builder;", "onActivityResultListener", "Lkotlin/Function2;", "Companion", "quick-library_debug"})
public abstract class ThemeActivity extends android.support.v7.app.AppCompatActivity {
    private boolean isInit;
    @org.jetbrains.annotations.NotNull()
    public android.view.View appBaseLayoutContainer;
    @org.jetbrains.annotations.Nullable()
    private android.support.v7.widget.Toolbar appBaseToolbar;
    @org.jetbrains.annotations.NotNull()
    public android.widget.FrameLayout appContentContainer;
    private kotlin.jvm.functions.Function1<? super android.view.MenuItem, java.lang.Boolean> onMenuItemClickListener;
    private int resMenu;
    private boolean isDefaultToolbar;
    private java.lang.String errorServiceHint;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TITLE = "title";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ID = "id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATA = "data";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE = "type";
    public static final org.quick.library.b.activities.ThemeActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    public final boolean isInit() {
        return false;
    }
    
    public final void setInit(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.View getAppBaseLayoutContainer() {
        return null;
    }
    
    public final void setAppBaseLayoutContainer(@org.jetbrains.annotations.NotNull()
    android.view.View p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.support.v7.widget.Toolbar getAppBaseToolbar() {
        return null;
    }
    
    public final void setAppBaseToolbar(@org.jetbrains.annotations.Nullable()
    android.support.v7.widget.Toolbar p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.FrameLayout getAppContentContainer() {
        return null;
    }
    
    public final void setAppContentContainer(@org.jetbrains.annotations.NotNull()
    android.widget.FrameLayout p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Activity getActivity() {
        return null;
    }
    
    public boolean isUsingBaseLayout() {
        return false;
    }
    
    public boolean isShowTitle() {
        return false;
    }
    
    /**
     * * 返回资源文件ID
     *     *
     *     * @return
     */
    @android.support.annotation.LayoutRes()
    protected abstract int onResultLayoutResId();
    
    /**
     * * 初始化操作
     */
    public abstract void init();
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initView() {
    }
    
    private final void setupTitle() {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.Nullable()
    android.view.MenuItem item) {
        return false;
    }
    
    public final void setMenu(@android.support.annotation.MenuRes()
    int resMenu, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.view.MenuItem, java.lang.Boolean> onMenuItemClickListener) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public android.support.v7.widget.Toolbar onResultToolbar() {
        return null;
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    protected final void setRightView(@android.support.annotation.LayoutRes()
    int resId) {
    }
    
    protected final void setRightView(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.view.View.OnClickListener onClickListener) {
    }
    
    protected final void setRightView(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    protected final void setBackInvalid() {
    }
    
    protected final void setBackValid(@org.jetbrains.annotations.NotNull()
    android.view.View.OnClickListener onClickListener) {
    }
    
    protected final void setBackValid(int backIcon, @org.jetbrains.annotations.NotNull()
    android.view.View.OnClickListener onClickListener) {
    }
    
    /**
     * * @param backIcon        -1:默认按钮   其他为自定义按钮
     *     * @param isValid         单击按钮是否有效
     *     * @param onClickListener
     */
    protected final void setBackValid(int backIcon, boolean isValid, @org.jetbrains.annotations.Nullable()
    android.view.View.OnClickListener onClickListener) {
    }
    
    /**
     * * @param backIcon        -1:默认按钮   其他为自定义按钮
     *     * @param isValid         单击按钮是否有效
     *     * @param onClickListener
     */
    protected final void setBackValid(int backIcon, boolean isValid) {
    }
    
    /**
     * * @param backIcon        -1:默认按钮   其他为自定义按钮
     *     * @param isValid         单击按钮是否有效
     *     * @param onClickListener
     */
    protected final void setBackValid(int backIcon) {
    }
    
    /**
     * * @param backIcon        -1:默认按钮   其他为自定义按钮
     *     * @param isValid         单击按钮是否有效
     *     * @param onClickListener
     */
    protected final void setBackValid() {
    }
    
    private final void checkNotNullToolbar() {
    }
    
    /**
     * * 获取View
     *     *
     *     * @param resId
     *     * @param <T>
     *     * @return
     *    </T> 
     */
    @org.jetbrains.annotations.NotNull()
    public final <T extends android.view.View>T getView(@android.support.annotation.IdRes()
    int resId) {
        return null;
    }
    
    /**
     * * 获取View
     *     *
     *     * @param resId
     *     * @param <T>
     *     * @return
     *    </T> 
     */
    @org.jetbrains.annotations.NotNull()
    public final <T extends android.view.View>T getView(@android.support.annotation.IdRes()
    int resId, @org.jetbrains.annotations.NotNull()
    android.view.View view) {
        return null;
    }
    
    protected final void setOnClickListener(@org.jetbrains.annotations.NotNull()
    org.quick.component.callback.OnClickListener2 onClickListener, @org.jetbrains.annotations.NotNull()
    @android.support.annotation.IdRes()
    @android.support.annotation.Size(min = 1L)
    int... resIds) {
    }
    
    public final void setOnClickListener(@org.jetbrains.annotations.NotNull()
    org.quick.component.callback.OnClickListener2 onClickListener, @org.jetbrains.annotations.NotNull()
    @android.support.annotation.Size(min = 1L)
    android.view.View... views) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getErrorServiceHint() {
        return null;
    }
    
    public final void setVisibility(int visibility, @org.jetbrains.annotations.NotNull()
    @android.support.annotation.Size(min = 1L)
    int... resIds) {
    }
    
    public final void setVisibility(int visibility, @org.jetbrains.annotations.NotNull()
    @android.support.annotation.Size(min = 1L)
    android.view.View... views) {
    }
    
    public final void setVisibility(int visibility, @org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    /**
     * * 获取常规类型数值
     *     *
     *     * @param key
     *     * @param defaultValue
     *     * @param <T>
     *     * @return
     *    </T> 
     */
    public final <T extends java.lang.Object>T getTransmitValue(@org.jetbrains.annotations.NotNull()
    java.lang.String key, T defaultValue) {
        return null;
    }
    
    protected final void showToast(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content) {
    }
    
    protected final void showToast(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, int duration) {
    }
    
    protected final void showToast(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, int gravity, int xOffset, int yOffset, int duration) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, @org.jetbrains.annotations.NotNull()
    android.view.View.OnClickListener onClickListener) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, @org.jetbrains.annotations.NotNull()
    android.view.View.OnClickListener onClickListener) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, @org.jetbrains.annotations.Nullable()
    java.lang.CharSequence actionTxt, @org.jetbrains.annotations.Nullable()
    android.view.View.OnClickListener onClickListener) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, @org.jetbrains.annotations.Nullable()
    java.lang.CharSequence actionTxt) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.Nullable()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, @org.jetbrains.annotations.Nullable()
    java.lang.CharSequence actionTxt, @org.jetbrains.annotations.Nullable()
    android.view.View.OnClickListener onClickListener) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.Nullable()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content, @org.jetbrains.annotations.Nullable()
    java.lang.CharSequence actionTxt) {
    }
    
    protected final void showSnackbar(@org.jetbrains.annotations.Nullable()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.CharSequence content) {
    }
    
    protected final void startActivity(@org.jetbrains.annotations.NotNull()
    org.quick.component.QuickActivity.Builder builder, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super android.content.Intent, kotlin.Unit> onActivityResultListener) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    public ThemeActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lorg/quick/library/b/activities/ThemeActivity$Companion;", "", "()V", "DATA", "", "ID", "TITLE", "TYPE", "startAction", "", "activity", "Landroid/app/Activity;", "activityClass", "Ljava/lang/Class;", "title", "quick-library_debug"})
    public static final class Companion {
        
        /**
         * * 这只是一个简易的跳转方法，参数多时建议重写此方法
         *         *
         *         * @param activity
         *         * @param activityClass
         *         * @param title
         */
        public final void startAction(@org.jetbrains.annotations.Nullable()
        android.app.Activity activity, @org.jetbrains.annotations.NotNull()
        java.lang.Class<?> activityClass, @org.jetbrains.annotations.NotNull()
        java.lang.String title) {
        }
        
        private Companion() {
            super();
        }
    }
}