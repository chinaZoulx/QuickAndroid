package org.chris.quick.b;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.http.okhttp.OkHttpUtils;

import org.chris.quick.b.application.ExitApplication;
import org.chris.quick.helper.SharedPreferencesHelper;
import org.chris.quick.m.Log;
import org.chris.quick.tools.common.DevicesUtils;
import org.litepal.LitePal;

/**
 * 请在这里写上用途
 *
 * @author chris
 * @Date 16/9/23
 * @modifyInfo1 chris-16/9/23
 * @modifyContent
 */

public abstract class BaseApplication extends ExitApplication {

    /**
     * 本地存储名称
     */
    public static final String APP_BASE_NAME = "baseChrisApp";
    /**
     * 账户名称
     */
    public static final String APP_ACCOUNT_NAME = "appAccountName";
    /**
     * 用户手机号
     */
    public static final String APP_USER_MOBILE = "appUserMobile";
    /**
     * 没数据网络提示
     */
    public static final String APP_NETWORK_HINT = "网络开小差啦";

    /**
     * app更新
     */
    public static final String APP_UPGRADE = "appUpgrade";
    /**
     * 第一次登陆时间
     */
    public static final String APP_FIRST_LOGIN_DATE = "appFirstLoginDate";
    /**
     * 基础URL
     */
    public static final String APP_BASE_URL_IP = "appBaseUrlIp";
    /**
     * 基础URL
     */
    public static final String APP_BASE_URL_POINT = "appBaseUrlPoint";
    /**
     * 基础URL
     */
    public static final String APP_BASE_URL_DEBUG_IP = "appBaseUrlDebugIp";
    /**
     * 基础URL
     */
    public static final String APP_BASE_URL_DEBUG_POINT = "appBaseUrlDebugPoint";
    /**
     * 未知异常
     */
    public static final int APP_ERROR_UNKNOWN = -1;
    /**
     * 常规异常
     */
    public static final int APP_ERROR_NORMAL = 1;
    /**
     * 成功
     */
    public static final int APP_SUCCESS_TAG = 0;
    /**
     * 没消息
     */
    public static final int APP_ERROR_MSG_N0 = 2;
    /**
     * 没有更多消息
     */
    public static final int APP_ERROR_MSG_N0_MORE = 3;
    /**
     * 未登录
     */
    public static final int APP_ERROR_NO_LOGIN = 4;

    public static final int APP_BORDER_MARGIN = 40;
    public static final String APP_TOKEN = "token";


    public static int width;
    public static int height;
    /*最大内存*/
    public static int maxMemory;
    public static String SHA1;
    public static String deviceName;
    public static String deviceBrand;
    public static String appVersionName;
    public static int appVersionCode;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        onInit();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void init() {
        Log.setDebug(true);
        SharedPreferencesHelper.init(this, APP_BASE_NAME);
        /* 数据库初始化 */
        LitePal.initialize(this);
        /* 自动适配 */
        AutoLayoutConifg.getInstance().useDeviceSize();
        /* 网络 */
        OkHttpUtils.getInstance();//初始化okHttp
        initDeviceInfo();
        inputDeviceInfo();
        initBuggly();
    }

    /**
     * 初始化Bugly
     */
    public void initBuggly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = DevicesUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, getBugglyAppId(), Log.isDebug(), strategy);
        //如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
//        CrashReport.initCrashReport(context, strategy);
    }

    public String getBugglyAppId() {
        return "f886605058";
    }

    private void initDeviceInfo() {
        width = DevicesUtils.getScreenWidth(this);
        height = DevicesUtils.getScreenHeight(this);
        maxMemory = DevicesUtils.getMaxMemory();
        SHA1 = DevicesUtils.getSHA1(this);
        deviceName = DevicesUtils.getDeviceModel();
        deviceBrand = Build.BRAND;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            appVersionName = packageInfo.versionName;
            appVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void inputDeviceInfo() {

        Log.v(getClass().getSimpleName(), "--------------------------------设备信息 Input Start----------------------------");
        Log.v(getClass().getSimpleName(), "----------------------------width:" + width + " height:" + height);
        Log.v(getClass().getSimpleName(), "----------------------------maxMemory:" + maxMemory);
        Log.v(getClass().getSimpleName(), "----------------------------DPI:" + DevicesUtils.getScreenDensityDPI());
        Log.v(getClass().getSimpleName(), "----------------------------level:" + DevicesUtils.getDeviceLevel());
        Log.v(getClass().getSimpleName(), "--------------------------------设备信息 Input End----------------------------");
    }

    public static void clearShareConfig() {
        SharedPreferencesHelper.clearAll();
    }

    public abstract void onInit();
}
