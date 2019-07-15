/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package org.quick.library.m;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import org.quick.component.utils.check.CheckUtils;

import java.io.File;

/**
 * 系统组件动作管理
 *
 * @author Chris zou
 * @Date 16/9/29
 * @modifyInfo1 chriszou-16/9/29
 * @modifyContent
 */
public class SystemActionManager {
    /**
     * 请求系统相册
     */
    public static final int REQUEST_CODE_PHOTO_ALBUM = 0x1;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_CAMERA = 0x2;

    /**
     * 调用系统拨号
     *
     * @param context
     * @param telNumber 号码
     */
    public static void startActionCall(Context context, String telNumber) {
        context.startActivity(actionCall(telNumber));
    }

    public static Intent actionCall(String telNumber) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + telNumber));
        return intent;
    }

    /**
     * 调用系统相机
     *
     * @param activity
     * @param savePath 保存路径
     */
    public static void startActionCamera(Activity activity, String savePath) {
        activity.startActivityForResult(actionCamera(activity,savePath), REQUEST_CODE_CAMERA);
    }

    public static Intent actionCamera(Activity activity,String savePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (CheckUtils.INSTANCE.isSDcardOK()) {
            //设定拍照存放到自己指定的目录,可以先建好
            File file = new File(savePath);
            Uri pictureUri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                pictureUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                pictureUri = Uri.fromFile(file);
            }
            if (intent != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
            }
        }
        return intent;
    }

    /**
     * 调用系统相册
     *
     * @param activity
     */
    public static void startActionAlbum(Activity activity) {
        activity.startActivityForResult(actionAlbum(), REQUEST_CODE_PHOTO_ALBUM);
    }

    public static Intent actionAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    public static void startActionBrowser(Activity activity, String url) {
        activity.startActivity(actionBrowser(url));
    }

    public static Intent actionBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        return intent;
    }

    public static void goAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

}
