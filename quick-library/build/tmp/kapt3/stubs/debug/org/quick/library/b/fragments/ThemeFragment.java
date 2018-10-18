package org.quick.library.b.fragments;

import java.lang.System;

/**
 * * 请填写方法内容
 * *
 * * @author Chris zou
 * * @Date 16/10/11
 * * @modifyInfo1 chriszou-16/10/11
 * * @modifyContent
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u00aa\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\r\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J#\u0010&\u001a\u0004\u0018\u0001H\'\"\u0004\b\u0000\u0010\'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u0002H\'\u00a2\u0006\u0002\u0010+J\u001f\u0010,\u001a\u0002H\'\"\b\b\u0000\u0010\'*\u00020\u00042\b\b\u0001\u0010-\u001a\u00020%\u00a2\u0006\u0002\u0010.J\'\u0010,\u001a\u0002H\'\"\b\b\u0000\u0010\'*\u00020\u00042\b\b\u0001\u0010-\u001a\u00020%2\u0006\u0010/\u001a\u00020\u0004\u00a2\u0006\u0002\u00100J\b\u00101\u001a\u000202H&J\u001c\u00103\u001a\u0002022\b\u0010#\u001a\u0004\u0018\u0001042\b\u00105\u001a\u0004\u0018\u000106H\u0016J&\u00107\u001a\u0004\u0018\u00010\u00042\u0006\u00105\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:2\b\u0010;\u001a\u0004\u0018\u00010<H\u0016J\u0012\u0010=\u001a\u00020\u00162\b\u0010>\u001a\u0004\u0018\u00010 H\u0016J\b\u0010?\u001a\u00020%H%J\n\u0010@\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010A\u001a\u000202H\u0004J\u0010\u0010B\u001a\u0002022\u0006\u0010C\u001a\u00020DH\u0004J(\u0010B\u001a\u0002022\b\b\u0002\u0010E\u001a\u00020%2\b\b\u0002\u0010F\u001a\u00020\u00162\n\b\u0002\u0010C\u001a\u0004\u0018\u00010DH\u0005J)\u0010G\u001a\u0002022\u0006\u0010C\u001a\u00020D2\u0014\b\u0001\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040I\"\u00020\u0004\u00a2\u0006\u0002\u0010JJ\u001c\u0010G\u001a\u0002022\u0006\u0010C\u001a\u00020D2\f\b\u0001\u0010K\u001a\u00020L\"\u00020%J)\u0010G\u001a\u0002022\u0006\u0010C\u001a\u00020M2\u0014\b\u0001\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040I\"\u00020\u0004\u00a2\u0006\u0002\u0010NJ\u001e\u0010G\u001a\u0002022\u0006\u0010C\u001a\u00020M2\f\b\u0001\u0010K\u001a\u00020L\"\u00020%H\u0004J5\u0010O\u001a\u0002022\b\b\u0001\u0010$\u001a\u00020%2#\u0010\u001e\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010 \u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u00160\u001fJ\u001c\u0010P\u001a\u0002022\u0006\u0010Q\u001a\u00020\u00042\n\b\u0002\u0010C\u001a\u0004\u0018\u00010DH\u0005J\u0012\u0010P\u001a\u0002022\b\b\u0001\u0010-\u001a\u00020%H\u0004J\u000e\u0010R\u001a\u0002022\u0006\u0010S\u001a\u00020)J\u0016\u0010T\u001a\u0002022\u0006\u0010U\u001a\u00020%2\u0006\u0010Q\u001a\u00020\u0004J)\u0010T\u001a\u0002022\u0006\u0010U\u001a\u00020%2\u0014\b\u0001\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040I\"\u00020\u0004\u00a2\u0006\u0002\u0010VJ\u001c\u0010T\u001a\u0002022\u0006\u0010U\u001a\u00020%2\f\b\u0001\u0010K\u001a\u00020L\"\u00020%J\b\u0010W\u001a\u000202H\u0002J \u0010X\u001a\u0002022\u0006\u0010Q\u001a\u00020\u00042\u0006\u0010Y\u001a\u00020Z2\u0006\u0010C\u001a\u00020DH\u0004J2\u0010X\u001a\u0002022\b\u0010Q\u001a\u0004\u0018\u00010\u00042\u0006\u0010Y\u001a\u00020Z2\n\b\u0002\u0010[\u001a\u0004\u0018\u00010Z2\n\b\u0002\u0010C\u001a\u0004\u0018\u00010DH\u0005J\u0018\u0010X\u001a\u0002022\u0006\u0010Y\u001a\u00020Z2\u0006\u0010C\u001a\u00020DH\u0004J(\u0010X\u001a\u0002022\u0006\u0010Y\u001a\u00020Z2\n\b\u0002\u0010[\u001a\u0004\u0018\u00010Z2\n\b\u0002\u0010C\u001a\u0004\u0018\u00010DH\u0005J\u0012\u0010\\\u001a\u0002022\b\u0010Y\u001a\u0004\u0018\u00010ZH\u0004J\u001a\u0010\\\u001a\u0002022\b\u0010Y\u001a\u0004\u0018\u00010Z2\u0006\u0010]\u001a\u00020%H\u0004J6\u0010\\\u001a\u0002022\b\u0010Y\u001a\u0004\u0018\u00010Z2\b\b\u0002\u0010^\u001a\u00020%2\u0006\u0010_\u001a\u00020%2\u0006\u0010`\u001a\u00020%2\b\b\u0002\u0010]\u001a\u00020%H\u0004JJ\u0010a\u001a\u0002022\u0006\u0010b\u001a\u00020c28\u0010d\u001a4\u0012\u0013\u0012\u00110%\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(f\u0012\u0015\u0012\u0013\u0018\u00010g\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(h\u0012\u0004\u0012\u0002020eH\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0018\"\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0018R\u0014\u0010\u001d\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u0018R-\u0010\u001e\u001a!\u0012\u0015\u0012\u0013\u0018\u00010 \u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006i"}, d2 = {"Lorg/quick/library/b/fragments/ThemeFragment;", "Landroid/support/v4/app/Fragment;", "()V", "appBaseLayoutContainer", "Landroid/view/View;", "getAppBaseLayoutContainer", "()Landroid/view/View;", "setAppBaseLayoutContainer", "(Landroid/view/View;)V", "appBaseToolbar", "Landroid/support/v7/widget/Toolbar;", "getAppBaseToolbar", "()Landroid/support/v7/widget/Toolbar;", "setAppBaseToolbar", "(Landroid/support/v7/widget/Toolbar;)V", "appContentContainer", "Landroid/widget/FrameLayout;", "getAppContentContainer", "()Landroid/widget/FrameLayout;", "setAppContentContainer", "(Landroid/widget/FrameLayout;)V", "isDefaultToolbar", "", "isFitsSystemWindows", "()Z", "isInit", "setInit", "(Z)V", "isShowTitle", "isUsingBaseLayout", "onMenuItemClickListener", "Lkotlin/Function1;", "Landroid/view/MenuItem;", "Lkotlin/ParameterName;", "name", "menu", "resMenu", "", "getTransmitValue", "T", "key", "", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "getView", "resId", "(I)Landroid/view/View;", "parent", "(ILandroid/view/View;)Landroid/view/View;", "init", "", "onCreateOptionsMenu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "onResultLayoutResId", "onResultToolbar", "setBackInvalid", "setBackValid", "onClickListener", "Landroid/view/View$OnClickListener;", "backIcon", "isValid", "setOnClickListener", "views", "", "(Landroid/view/View$OnClickListener;[Landroid/view/View;)V", "resIds", "", "Lorg/quick/component/callback/OnClickListener2;", "(Lorg/quick/component/callback/OnClickListener2;[Landroid/view/View;)V", "setParentMenu", "setRightView", "view", "setTitle", "title", "setVisibility", "visibility", "(I[Landroid/view/View;)V", "setupTitle", "showSnackbar", "content", "", "actionTxt", "showToast", "duration", "gravity", "xOffset", "yOffset", "startActivity", "builder", "Lorg/quick/component/QuickActivity$Builder;", "onActivityResultListener", "Lkotlin/Function2;", "resultCode", "Landroid/content/Intent;", "data", "quick-library_debug"})
public abstract class ThemeFragment extends android.support.v4.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private android.view.View appBaseLayoutContainer;
    private boolean isInit;
    @org.jetbrains.annotations.NotNull()
    public android.widget.FrameLayout appContentContainer;
    @org.jetbrains.annotations.Nullable()
    private android.support.v7.widget.Toolbar appBaseToolbar;
    private boolean isDefaultToolbar;
    private kotlin.jvm.functions.Function1<? super android.view.MenuItem, java.lang.Boolean> onMenuItemClickListener;
    private int resMenu;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final android.view.View getAppBaseLayoutContainer() {
        return null;
    }
    
    public final void setAppBaseLayoutContainer(@org.jetbrains.annotations.Nullable()
    android.view.View p0) {
    }
    
    public final boolean isInit() {
        return false;
    }
    
    public final void setInit(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.FrameLayout getAppContentContainer() {
        return null;
    }
    
    public final void setAppContentContainer(@org.jetbrains.annotations.NotNull()
    android.widget.FrameLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.support.v7.widget.Toolbar getAppBaseToolbar() {
        return null;
    }
    
    public final void setAppBaseToolbar(@org.jetbrains.annotations.Nullable()
    android.support.v7.widget.Toolbar p0) {
    }
    
    public boolean isUsingBaseLayout() {
        return false;
    }
    
    public boolean isShowTitle() {
        return false;
    }
    
    public boolean isFitsSystemWindows() {
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
    
    @org.jetbrains.annotations.Nullable()
    public android.support.v7.widget.Toolbar onResultToolbar() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void setupTitle() {
    }
    
    @java.lang.Override()
    public void onCreateOptionsMenu(@org.jetbrains.annotations.Nullable()
    android.view.Menu menu, @org.jetbrains.annotations.Nullable()
    android.view.MenuInflater inflater) {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.Nullable()
    android.view.MenuItem item) {
        return false;
    }
    
    public final void setParentMenu(@android.support.annotation.MenuRes()
    int resMenu, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.view.MenuItem, java.lang.Boolean> onMenuItemClickListener) {
    }
    
    public final void setTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
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
    
    public final void setOnClickListener(@org.jetbrains.annotations.NotNull()
    android.view.View.OnClickListener onClickListener, @org.jetbrains.annotations.NotNull()
    @android.support.annotation.IdRes()
    @android.support.annotation.Size(min = 1L)
    int... resIds) {
    }
    
    public final void setOnClickListener(@org.jetbrains.annotations.NotNull()
    android.view.View.OnClickListener onClickListener, @org.jetbrains.annotations.NotNull()
    @android.support.annotation.Size(min = 1L)
    android.view.View... views) {
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
     *     * @param parent
     *     * @param resId
     *     * @param <T>
     *     * @return
     *    </T> 
     */
    @org.jetbrains.annotations.NotNull()
    public final <T extends android.view.View>T getView(@android.support.annotation.IdRes()
    int resId, @org.jetbrains.annotations.NotNull()
    android.view.View parent) {
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
    
    /**
     * * 获取常规类型数值
     *     *
     *     * @param key
     *     * @param defaultValue
     *     * @param <T>
     *     * @return
     *    </T> 
     */
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>T getTransmitValue(@org.jetbrains.annotations.NotNull()
    java.lang.String key, T defaultValue) {
        return null;
    }
    
    protected final void showToast(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence content) {
    }
    
    protected final void showToast(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence content, int duration) {
    }
    
    protected final void showToast(@org.jetbrains.annotations.Nullable()
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
    
    public ThemeFragment() {
        super();
    }
}