package org.quick.library.b;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.multidex.MultiDex;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

import org.quick.component.QuickBroadcast;
import org.quick.component.Constant;
import org.quick.component.Log2;
import org.quick.component.QuickToast;
import org.quick.component.utils.DateUtils;

import java.util.Locale;

/**
 * Created by zoulx on 2017/11/20.
 */

public class BaseTinkerApplication extends BaseApplication {
    private static final String TAG = "BaseTinkerApplication";

    @Override
    public void onInit() {
        setStrictMode();
    }

    @Override
    public void initBuggly() {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
//        Beta.upgradeCheckPeriod = DateUtils.getInstance.getMINUTE();
        Beta.initDelay = DateUtils.INSTANCE.getSECOND();
        Beta.autoDownloadOnWifi = true;

        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int i, UpgradeInfo upgradeInfo, boolean b, boolean b1) {
                if (upgradeInfo != null) {
                    QuickBroadcast.INSTANCE.sendBroadcast(Constant.APP_UPGRADE);
                }
            }
        };
        /*
           全量升级状态回调
         */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {

            }

            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                QuickToast.Companion.showToastDefault("最新版本");
            }

            @Override
            public void onUpgrading(boolean b) {
                QuickToast.Companion.showToastDefault("开始检查更新");
            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }
        };
        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
                Toast.makeText(getApplicationContext(), patchFileUrl, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(),
                        "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                Toast.makeText(getApplicationContext(), patchFilePath, Toast.LENGTH_SHORT).show();
//                Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {
                Toast.makeText(getApplicationContext(), "onPatchRollback", Toast.LENGTH_SHORT).show();
            }
        };

        long start = System.currentTimeMillis();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
        Bugly.init(this, onResultBugglyAppId(), true);
        long end = System.currentTimeMillis();
        Log2.INSTANCE.e("init time--->", end - start + "ms");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);//此处需要初始化分包，一定要在Bugly之前
        // 安装tinker
//        Beta.installTinker();
    }


    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
}
