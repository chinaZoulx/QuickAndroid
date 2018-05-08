package org.chris.quick.tools

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.support.annotation.AttrRes
import android.view.inputmethod.InputMethodManager
import org.chris.quick.b.BaseApplication
import org.chris.quick.tools.common.BankCardUtils
import org.chris.quick.tools.common.HttpUtils
import org.chris.quick.tools.common.IDCardUtils
import org.chris.quick.tools.common.MobileCheckUtils

/**
 * Created by zoulx on 2018/1/3.
 * @email chrisSpringSmell@gmail.com
 */
object CheckUtils {

    private var connectivityManager: ConnectivityManager? = null

    private fun getConnectivityManager(): ConnectivityManager = if (connectivityManager == null) {
        connectivityManager = BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager!!
    } else connectivityManager!!

    /**
     * 是否是邮箱
     * @return true：正确 false：错误
     */
    fun isEmail(email: String?): Boolean = if (isEmpty(email)) false else email!!.matches("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$".toRegex())

    /**
     * 是否为空
     */
    fun isEmpty(str: String?): Boolean = when {
        str == null || "" == str || "null".equals(str, ignoreCase = true) -> true
        else -> (0 until str.length).map { str[it] }.none { it != ' ' && it != '\t' && it != '\r' && it != '\n' }
    }

    /**
     * 是否包含属性
     */
    fun isHasAttrValue(typedArray: TypedArray, @AttrRes attr: Int): Boolean = (0 until typedArray.indexCount).any { typedArray.getIndex(it) == attr }

    /**
     * 是否是手机
     */
    fun isMobileNo(mobileNo: String) = MobileCheckUtils.isMobileNo(mobileNo)

    /**
     * 是否是正确的图片链接
     */
    fun isImgUrl(url: String?) = !isEmpty(url) && (url!!.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg") || url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".gif"))

    /**
     *是否是http链接
     */
    fun isHttpUrl(url: String?) = !isEmpty(url) && (url!!.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))

    fun isIp(ip: String?) = HttpUtils.isIP(ip)
    fun isIpPoint(point: String?) = try {
        !isEmpty(point) && point!!.toInt() <= 65535
    } catch (O_O: Exception) {
        O_O.printStackTrace()
        false
    }

    /**
     * 判断SD卡是否可用
     */
    fun isSDcardOK(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 是否是wifi
     *
     * @return
     */
    fun isWifi() = try {
        getConnectivityManager().activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    } catch (O_o: Exception) {
        false
    }

    /**
     * @return 返回是否有网
     */
    fun isNetWorkAvailable() = try {
        getConnectivityManager().activeNetworkInfo.isAvailable
    } catch (O_O: Exception) {
        false
    }

    fun isShowSoftInput() = try {
        (BaseApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive
    } catch (O_O: Exception) {
        false
    }

    /**
     * 是否是身份证
     * @return 有效：返回"" 无效：返回String信息
     */

    fun checkIdCard(idCard: String?) = IDCardUtils.IDCardValidate(idCard)!!

    /**
     * 检查是身份证
     * @return 有效：返回"" 无效：返回String信息
     */
    fun checkBankCard(binNum: Long) = BankCardUtils.getNameOfBank(BaseApplication.getInstance(), binNum)!!

    /**
     * 检查Activity是否运行，true:正在运行 false : 反之
     */
    fun checkActivityIsRunning(activity: Activity?): Boolean = !(activity == null || activity.isFinishing || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)
}