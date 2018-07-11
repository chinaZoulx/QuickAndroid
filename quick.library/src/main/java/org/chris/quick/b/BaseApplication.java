package org.chris.quick.b;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.http.okhttp.OkHttpUtils;

import org.chris.quick.QuickAndroid;
import org.chris.quick.b.application.ExitApplication;
import org.chris.quick.helper.QuickSharedPreferencesHelper;
import org.chris.quick.m.Log;
import org.chris.quick.tools.common.DevicesUtils;
import org.litepal.LitePal;

/**
 * @author chris
 * @date 16/9/23
 * @from chris-16/9/23
 */

public abstract class BaseApplication extends ExitApplication {

    @Override
    public void onCreate() {
        QuickAndroid.INSTANCE.init(this);
        super.onCreate();
        onInit();
        initBuggly();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
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
        CrashReport.initCrashReport(context, onResultBugglyAppId(), Log.isDebug(), strategy);
        //如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
//        CrashReport.initCrashReport(context, strategy);
    }

    public String onResultBugglyAppId() {
        return "f886605058";
    }

    public abstract void onInit();
}
