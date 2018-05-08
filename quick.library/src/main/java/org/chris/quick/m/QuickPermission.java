package org.chris.quick.m;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by work on 2017/6/23.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class QuickPermission {
    public static final String TAG = "QuickPermission";

    public static boolean hasPermissions(Context context, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        }
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
//        boolean flag = false;
//        for (String perm : permissions) {
//            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
//                    flag = true;
//                    break;
//                }
//            }
//        }
//        if (flag) {//曾被拒绝
//
//        } else {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
//        }
    }

    public static boolean somePermissionPermanentlyDenied(Activity activity, String... permissions) {
        for (String perm : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
                return true;
            }
        }
        return false;
    }
}
