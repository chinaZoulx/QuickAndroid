package org.quick.library.config

import org.quick.component.Log2

/**
 * 接口地址
 */
object UrlPath {

    private const val baseUrlOnLine = "https://www.maotanlvxing.com/"
    private const val baseUrlOffLine = "http://csapi.maotanlvxing.com/"

    val baseUrl: String
        get() = if (Log2.isDebug) baseUrlOffLine else baseUrlOnLine

    /**
     * 修改个人信息
     */
    const val modifyUserInfo="index.php/v1/user/setUserInfo"
    /**
     * 发送验证码
     */
    const val sendShortMsg = "index.php/v1/user/sendCode"
    /**
     * 圈子
     */
    const val cirDetail="index.php/v1/circle/getTopicInfo"
    /**
     * 打赏
     */
    const val reward = "index.php/v2/circle/setReward"
    /**
     * 多文件上传
     */
    const val uploadingImgList = "index.php/v1/upload/batchUpload"
    /**
     * 单文件上传
     */
    const val uploadingImg = "index.php/v1/upload/qiniuFile"
    /**
     * 话题列表
     */
    const val themeList = "index.php/v1/circle/getCircleList"
    /**
     * 创建圈子
     */
    const val createCir = "index.php/v1/circle/setCircleAdd"
    /**
     * 立即成团
     */
    const val groupAtOnceConfirm = "index.php/v2/group/setAtonceTem"
    /**
     * 拼单折扣信息
     */
    const val scalesBooking = "index.php/v1/group/setGoodsTeamList"
    /**
     * 发送短信-通用接口
     */
    const val sendMsgOrderContent = "index.php/v3/common/setSms"
    /**
     * 我的常用联系人
     */
    const val commonUserList = "index.php/v3/user/getLinkmanList"
    /**
     * 我的拼团订单
     */
    const val groupOrderList = "index.php/v1/user/getGroupList"
    /*商店*/
    /**
     * 商品列表
     */
    const val storeList = "index.php/v1/goods/getWhereGoods"
    /**
     * 商品详情
     */
    const val storeDetail = "index.php/v1/goods/getInfo"
    /**
     * 商品详情-评论
     */
    const val storeComment = "index.php/v1/goods/getCommentList"
    /**
     * 商品详情-收藏
     */
    const val storeCollectAdd = "index.php/v2/personal/getCollectAdd"
    /**
     * 商品详情-取消收藏
     */
    const val storeCollectCancel = "index.php/v2/personal/setCollectDel"
    /**
     * 商品详情-优惠券
     */
    const val storeCouponList = "index.php/v1/goods/getCouponList"
    /**
     * 创建组团
     */
    const val createGroup = "index.php/v1/group/setupGroup"

    /**
     * 商品详情-优惠券-领取
     */
    const val storeGetCoupon = "index.php/v1/goods/getCoupon"

    /*登陆注册*/

    /**
     * 注册
     */
    const val register = "index.php/v1/user/register"
    /**
     * 修改登陆密码
     */
    const val modifyPassword = "index.php/v3/user/setUserPwd"
    /**
     * 登陆
     */
    const val login = "index.php/v1/user/login"
    /**
     * 订单列表
     */
    const val orderList = "index.php/v1/order/getOrderList"
    /**
     * 订单详情
     */
    const val orderDetail = "index.php/v1/user/getUserOrderInfo"
    /**
     * 立即下单-普通商品
     */
    const val applyOrder = "index.php/v1/order/setOrder"
    /**
     * 立即下单-套餐订单
     */
    const val applyOrderGroup = "index.php/v2/order/setMealOrder"
    /**
     * 实名认证
     */
    const val realNameAuth = "index.php/v1/user/setCardAuth"

    /**
     * 申请订单，普通商品
     */
    const val applyStoreNormal = "index.php/v1/order/setOrder"
    /**
     * 用户自己的优惠券-对应商品
     */
    const val ownCouponList = "index.php/v1/order/getCouponList"
    /**
     * 我的优惠券
     */
    const val myCouponList = "index.php/v1/user/getCoupon"

    /**
     * 我的订单-取消
     */
    const val orderCancel = "index.php/v1/order/setCancel"
    /**
     * 我的订单-取消申请退款
     */
    const val orderCancelCheckout = "index.php/v2/order/setRetreatApply"
    /**
     * 删除订单
     */
    const val orderDel="index.php/v1/order/setDel"
    /**
     * 评价商品
     */
    const val orderComment="index.php/v1/order/setOrderCmtAdd"
    /**
     * 微信支付
     * orderid 订单ID
     * paytype:支付类型 1余额,2消费券,3积分,4支付宝,5微信(小程序),6微信(app)
     */
    const val pay = "index.php/v1/pay/getPayInfo"
    /**
     * 获取已有的组团商品
     */
    const val groupStore = "index.php/v1/group/getList"

    /**
     * 组团详情
     */
    const val groupDetail = "index.php/v1/group/getGroupInfo"
    /**
     * 获取已有的组团
     */
    const val groupAlready = "index.php/v2/group/getStartGroup"
    /**
     * 商品标签
     */
    const val tagList = "index.php/v3/common/getGoodsTag"
    /**
     * 用户信息
     */
    const val userInfo = "index.php/v1/user/getInfo"
    /**
     * 保险列表
     */
    const val insurance = "index.php/v3/safe/getGoodsSafeList"
    /**
     * 支付完成-加入组团
     */
    const val addToGroup = "index.php/v1/group/setGroupMember"
    /**
     * 保险详情
     */
    const val insuranceDetail = "index.php/v3/safe/getInfoSafePro"
    /**
     * 创建保单
     */
    const val insuranceCreate = "index.php/v3/safe/addOrderSafe"
    /**
     * 投保
     */
    const val insuranceCommit = "index.php/v3/safe/setInsure"
    /**
     * 加入组团
     */
    const val joinGroup = "index.php/v1/group/setJoinGroup"
}