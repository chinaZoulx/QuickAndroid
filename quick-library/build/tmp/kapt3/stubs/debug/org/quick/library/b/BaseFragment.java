package org.quick.library.b;

import java.lang.System;

/**
 * * Created by chris Zou on 2016/6/12.
 * *
 * * @author chris Zou
 * * @date 2016/6/12
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0010H&J\b\u0010\u0013\u001a\u00020\u0010H&J\b\u0010\u0014\u001a\u00020\u0010H&J\b\u0010\u0015\u001a\u00020\u0016H\'J\u001a\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00102\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\b\u0010\u001d\u001a\u00020\u0010H&R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lorg/quick/library/b/BaseFragment;", "Lorg/quick/library/b/fragments/ThemeFragment;", "()V", "isOkDialog", "Lorg/quick/library/function/IsOkDialog;", "()Lorg/quick/library/function/IsOkDialog;", "setOkDialog", "(Lorg/quick/library/function/IsOkDialog;)V", "loadingDialog", "Lorg/quick/library/function/LoadingDialog;", "getLoadingDialog", "()Lorg/quick/library/function/LoadingDialog;", "setLoadingDialog", "(Lorg/quick/library/function/LoadingDialog;)V", "onInitListener", "Lkotlin/Function0;", "", "init", "onBindListener", "onInit", "onInitLayout", "onResultLayoutResId", "", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "setOnInitListener", "start", "quick-library_debug"})
public abstract class BaseFragment extends org.quick.library.b.fragments.ThemeFragment {
    @org.jetbrains.annotations.NotNull()
    public org.quick.library.function.IsOkDialog isOkDialog;
    @org.jetbrains.annotations.NotNull()
    public org.quick.library.function.LoadingDialog loadingDialog;
    private kotlin.jvm.functions.Function0<kotlin.Unit> onInitListener;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final org.quick.library.function.IsOkDialog isOkDialog() {
        return null;
    }
    
    public final void setOkDialog(@org.jetbrains.annotations.NotNull()
    org.quick.library.function.IsOkDialog p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.quick.library.function.LoadingDialog getLoadingDialog() {
        return null;
    }
    
    public final void setLoadingDialog(@org.jetbrains.annotations.NotNull()
    org.quick.library.function.LoadingDialog p0) {
    }
    
    @java.lang.Override()
    public void init() {
    }
    
    public final void setOnInitListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onInitListener) {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * * 返回资源文件ID
     *     *
     *     * @return
     */
    @android.support.annotation.LayoutRes()
    @java.lang.Override()
    public abstract int onResultLayoutResId();
    
    /**
     * * 初始化操作
     */
    public abstract void onInit();
    
    /**
     * * 初始化布局
     */
    public abstract void onInitLayout();
    
    /**
     * * 绑定监听
     */
    public abstract void onBindListener();
    
    /**
     * * 绑定数据
     */
    public abstract void start();
    
    public BaseFragment() {
        super();
    }
}