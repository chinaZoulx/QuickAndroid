package org.quick.component

/**
 * @describe 常量配置
 * @author ChrisZou
 * @date 2018/7/6-10:39
 * @email chrisSpringSmell@gmail.com
 */
object QuickConfigConstant {

    /**
     * 本地存储名称
     */
    val APP_BASE_NAME :String by lazy { return@lazy javaClass.`package`.name }
    /**
     * 账户名称
     */
    var APP_ACCOUNT_NAME = "appAccountName"
    /**
     * 用户手机号
     */
    var APP_USER_MOBILE = "appUserMobile"
    /**
     * 没数据网络提示
     */
    var APP_NETWORK_HINT = "网络开小差啦"

    /**
     * app更新
     */
    var APP_UPGRADE = "appUpgrade"
    /**
     * 第一次登陆时间
     */
    var APP_FIRST_LOGIN_DATE = "appFirstLoginDate"
    /**
     * 基础URL
     */
    var APP_BASE_URL_IP = "appBaseUrlIp"
    /**
     * 基础URL
     */
    var APP_BASE_URL_POINT = "appBaseUrlPoint"
    /**
     * 基础URL
     */
    var APP_BASE_URL_DEBUG_IP = "appBaseUrlDebugIp"
    /**
     * 基础URL
     */
    var APP_BASE_URL_DEBUG_POINT = "appBaseUrlDebugPoint"
    /**
     * 未知异常
     */
    var APP_ERROR_UNKNOWN = -1
    /**
     * 常规异常
     */
    var APP_ERROR_NORMAL = 1
    /**
     * 成功
     */
    var APP_SUCCESS_TAG = 0
    /**
     * 没消息
     */
    var APP_ERROR_MSG_N0 = 2
    /**
     * 没有更多消息
     */
    var APP_ERROR_MSG_N0_MORE = 3
    /**
     * 未登录
     */
    var APP_ERROR_NO_LOGIN = 4

    var APP_BORDER_MARGIN = 40
    var APP_TOKEN = "token"
}