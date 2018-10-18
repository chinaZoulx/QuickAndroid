package org.quick.library.config;

import java.lang.System;

/**
 * * @describe 常量配置
 * * @author ChrisZou
 * * @date 2018/7/6-10:39
 * * @email chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lorg/quick/library/config/QuickConfigConstant;", "", "()V", "APP_ACCOUNT_NAME", "", "APP_BASE_NAME", "APP_BASE_URL_DEBUG_IP", "APP_BASE_URL_DEBUG_POINT", "APP_BASE_URL_IP", "APP_BASE_URL_POINT", "APP_BORDER_MARGIN", "", "APP_ERROR_MSG_N0", "APP_ERROR_MSG_N0_MORE", "APP_ERROR_NORMAL", "APP_ERROR_NO_LOGIN", "APP_ERROR_UNKNOWN", "APP_FIRST_LOGIN_DATE", "APP_NETWORK_HINT", "APP_SUCCESS_TAG", "APP_TOKEN", "APP_UPGRADE", "APP_USER_MOBILE", "quick-library_debug"})
public final class QuickConfigConstant {
    
    /**
     * * 本地存储名称
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_BASE_NAME = "baseQuickAndroidApp";
    
    /**
     * * 账户名称
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_ACCOUNT_NAME = "appAccountName";
    
    /**
     * * 用户手机号
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_USER_MOBILE = "appUserMobile";
    
    /**
     * * 没数据网络提示
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_NETWORK_HINT = "\u7f51\u7edc\u5f00\u5c0f\u5dee\u5566";
    
    /**
     * * app更新
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_UPGRADE = "appUpgrade";
    
    /**
     * * 第一次登陆时间
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_FIRST_LOGIN_DATE = "appFirstLoginDate";
    
    /**
     * * 基础URL
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_BASE_URL_IP = "appBaseUrlIp";
    
    /**
     * * 基础URL
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_BASE_URL_POINT = "appBaseUrlPoint";
    
    /**
     * * 基础URL
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_BASE_URL_DEBUG_IP = "appBaseUrlDebugIp";
    
    /**
     * * 基础URL
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_BASE_URL_DEBUG_POINT = "appBaseUrlDebugPoint";
    
    /**
     * * 未知异常
     */
    public static final int APP_ERROR_UNKNOWN = -1;
    
    /**
     * * 常规异常
     */
    public static final int APP_ERROR_NORMAL = 1;
    
    /**
     * * 成功
     */
    public static final int APP_SUCCESS_TAG = 0;
    
    /**
     * * 没消息
     */
    public static final int APP_ERROR_MSG_N0 = 2;
    
    /**
     * * 没有更多消息
     */
    public static final int APP_ERROR_MSG_N0_MORE = 3;
    
    /**
     * * 未登录
     */
    public static final int APP_ERROR_NO_LOGIN = 4;
    public static final int APP_BORDER_MARGIN = 40;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_TOKEN = "token";
    public static final org.quick.library.config.QuickConfigConstant INSTANCE = null;
    
    private QuickConfigConstant() {
        super();
    }
}