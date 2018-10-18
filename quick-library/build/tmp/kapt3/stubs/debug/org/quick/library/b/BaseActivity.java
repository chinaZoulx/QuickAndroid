package org.quick.library.b;

import java.lang.System;

/**
 * * Created by zoulx on 2017/11/13.
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\"\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0010H&J\b\u0010\u0018\u001a\u00020\u0010H&J\b\u0010\u0019\u001a\u00020\u0010H&J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0016J\u001e\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0016J-\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u000e\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0!2\u0006\u0010\"\u001a\u00020#H\u0016\u00a2\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0013H\'J\b\u0010&\u001a\u00020\u0010H&R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\'"}, d2 = {"Lorg/quick/library/b/BaseActivity;", "Lorg/quick/library/b/activities/ThemeActivity;", "Lpub/devrel/easypermissions/EasyPermissions$PermissionCallbacks;", "()V", "isOkDialog", "Lorg/quick/library/function/IsOkDialog;", "()Lorg/quick/library/function/IsOkDialog;", "setOkDialog", "(Lorg/quick/library/function/IsOkDialog;)V", "loadingDialog", "Lorg/quick/library/function/LoadingDialog;", "getLoadingDialog", "()Lorg/quick/library/function/LoadingDialog;", "setLoadingDialog", "(Lorg/quick/library/function/LoadingDialog;)V", "init", "", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onBindListener", "onInit", "onInitLayout", "onPermissionsDenied", "perms", "", "", "onPermissionsGranted", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResultLayoutResId", "start", "quick-library_debug"})
public abstract class BaseActivity extends org.quick.library.b.activities.ThemeActivity implements pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks {
    @org.jetbrains.annotations.NotNull()
    public org.quick.library.function.IsOkDialog isOkDialog;
    @org.jetbrains.annotations.NotNull()
    public org.quick.library.function.LoadingDialog loadingDialog;
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
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @java.lang.Override()
    public void onPermissionsDenied(int requestCode, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> perms) {
    }
    
    @java.lang.Override()
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @java.lang.Override()
    public void onPermissionsGranted(int requestCode, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> perms) {
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
    
    public BaseActivity() {
        super();
    }
}