package org.quick.component

/**
 * @describe 常量配置
 * @author ChrisZou
 * @date 2018/7/6-10:39
 * @email chrisSpringSmell@gmail.com
 */
object Constant {

    /**
     * 本地存储名称
     */
    val APP_BASE_NAME: String by lazy { return@lazy javaClass.`package`.name }
    /**
     * 没数据网络提示
     */
    const val APP_NETWORK_HINT = "网络开小差啦"

    /**
     * app更新
     */
    const val APP_UPGRADE = "appUpgrade"
    /**
     * 第一次登陆时间
     */
    const val APP_FIRST_LOGIN_DATE = "appFirstLoginDate"

    /**
     * 基础URL
     */
    const val APP_BASE_URL_IP = "appBaseUrlIp"
    /**
     * 基础URL
     */
    const val APP_BASE_URL_POINT = "appBaseUrlPoint"
    /**
     * 基础URL
     */
    const val APP_BASE_URL_DEBUG_IP = "appBaseUrlDebugIp"
    /**
     * 基础URL
     */
    const val APP_BASE_URL_DEBUG_POINT = "appBaseUrlDebugPoint"
    /**
     * 未知异常
     */
    const val APP_ERROR_UNKNOWN = -1
    /**
     * 成功
     */
    const val APP_SUCCESS_TAG = 200
    /**
     * 列表-没消息
     */
    const val APP_ERROR_MSG_N0 = 204
    /**
     * 列表-没有更多消息
     */
    const val APP_ERROR_MSG_N0_MORE = 205
    /**
     * 未登录
     */
    const val APP_ERROR_NO_LOGIN = 201

    const val APP_BORDER_MARGIN = 40
    /**
     * 用户密钥
     */
    const val APP_TOKEN = "token"

    /**
     * 账户名称
     */
    const val ACCOUNT_NAME = "appAccountName"
    /**
     * 用户手机号
     */
    const val USER_MOBILE = "appUserMobile"
    /**
     * 用户身份证-状态
     */
    const val USER_ID_CARD_STATUS = "appUserIdCard"
    /**
     * 用户身份证
     */
    const val USER_ID_CARD_NUMBER = "appUserIdCardNumber"
    /**
     * 用户真实姓名
     */
    const val USER_REAL_NAME = "appUserRealName"
    /**
     * 用户真实姓名
     */
    const val USER_ID = "appUserID"
}
