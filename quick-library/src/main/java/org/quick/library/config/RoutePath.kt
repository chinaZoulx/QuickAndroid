package org.quick.library.config

/**
 * 路由路径管理
 */
object RoutePath {

    /*主model*/
    private const val mainTag = "mainApp"
    /**
     * 主Model
     */
    const val MainActivity = "/$mainTag/MainActivity"


    /*个人中心*/
    private const val AccountCenterTag = "AccountCenter"
    /**
     * 个人中心
     */
    const val AccountCenterFragment = "/$AccountCenterTag/AccountCenterFragment"
    const val RealNameAuthActivity="/$AccountCenterTag/RealNameAuthActivity"
    /**
     * 我的优惠券
     */
    const val MyCouponActivity = "/$AccountCenterTag/MyCouponActivity"

    /*登陆注册*/
    private const val LoginRegisterTag = "LoginRegister"
    /**
     * 登陆
     */
    const val LoginActivity = "/$LoginRegisterTag/LoginActivity"
    const val LoginIndexActivity="/$LoginRegisterTag/LoginIndexActivity"

    /*商品列表*/
    private const val storeTag = "store"
    /**
     * 商品列表
     */
    const val StoreMainListFragment = "/$storeTag/StoreMainListFragment"
    /**
     * 商品详情
     */
    const val StoreDetailActivity = "/$storeTag/StoreDetailActivity"

    /*二维码*/
    private const val scanTag = "scan"
    /**
     * 扫码主页
     */
    const val CaptureActivity = "/$scanTag/CaptureActivity"

    /*地图*/
    private const val mapTag = "map"
    const val MapActivity = "/$mapTag/MapActivity"

    /*支付*/
    private const val payTag = "pay"
    const val PayService = "/$payTag/PayService"

    /*分享*/
    private const val shareSDKTag = "pay"
    const val ShareSdkService = "/$shareSDKTag/ShareSdkService"

    /*视频*/
    private const val exoVideoTag = "exoVideo"
    const val VideoActivity = "/$exoVideoTag/ShareSdkService"

    /*社区*/
    private const val communityTag = "community"
    const val CommunityFragment = "/$communityTag/ShareSdkService"

    /*组团*/
    private const val groupTag = "groupTag"
    const val GroupMainActivity = "/$groupTag/GroupMainActivity"
    const val GroupFragment = "/$groupTag/GroupFragment"
    const val GroupOrderList="/$groupTag/GroupOrderListActivity"
    const val GroupDetailActivity = "/$groupTag/GroupDetailActivity"

    const val BookingMainActivity = "/$groupTag/BookingMainActivity"
    const val BookingDetailActivity = "/$groupTag/BookingDetailActivity"




}