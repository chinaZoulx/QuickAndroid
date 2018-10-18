package org.quick.library.service;

import java.lang.System;

/**
 * * Created by work on 2017/7/26.
 * * 多任务下载服务
 * *
 * * @author chris zou
 * * @mail chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 /2\u00020\u0001:\u0003/01B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0003J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rH\u0003J \u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\rH\u0002J\u0010\u0010\"\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rH\u0003J\u000e\u0010#\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\u0012\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\'H\u0016J\b\u0010(\u001a\u00020\u0014H\u0016J \u0010)\u001a\u00020*2\u0006\u0010&\u001a\u00020\'2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020*H\u0016J\u000e\u0010-\u001a\u00020\u00142\u0006\u0010.\u001a\u00020\u0010R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fj\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lorg/quick/library/service/DownloadService;", "Landroid/app/Service;", "()V", "notificationManager", "Landroid/app/NotificationManager;", "getNotificationManager", "()Landroid/app/NotificationManager;", "setNotificationManager", "(Landroid/app/NotificationManager;)V", "receiver", "Lorg/quick/library/service/DownloadService$UpgradeReceiver;", "taskList", "", "Lorg/quick/library/service/DownloadService$DownloadModel;", "taskRequestCallMap", "Ljava/util/HashMap;", "", "Lcom/zhy/http/okhttp/request/RequestCall;", "Lkotlin/collections/HashMap;", "cancel", "", "model", "compat", "customLayout", "Landroid/widget/RemoteViews;", "download", "getHint", "total", "", "progress", "", "speed", "getNotiBuilder", "Landroid/support/v4/app/NotificationCompat$Builder;", "initNotification", "installAPK", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onDestroy", "onStartCommand", "", "flags", "startId", "showToast", "content", "Companion", "DownloadModel", "UpgradeReceiver", "quick-library_debug"})
public final class DownloadService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    public android.app.NotificationManager notificationManager;
    private java.util.List<org.quick.library.service.DownloadService.DownloadModel> taskList;
    private java.util.HashMap<java.lang.String, com.zhy.http.okhttp.request.RequestCall> taskRequestCallMap;
    private org.quick.library.service.DownloadService.UpgradeReceiver receiver;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DOWNLOAD_DIR = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION = "action";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_CLICK = "actionClick";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_CANCEL = "actionCancel";
    public static final org.quick.library.service.DownloadService.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.NotificationManager getNotificationManager() {
        return null;
    }
    
    public final void setNotificationManager(@org.jetbrains.annotations.NotNull()
    android.app.NotificationManager p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.os.IBinder onBind(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @android.annotation.SuppressLint(value = {"RestrictedApi"})
    private final void initNotification(org.quick.library.service.DownloadService.DownloadModel model) {
    }
    
    private final android.support.v4.app.NotificationCompat.Builder getNotiBuilder(org.quick.library.service.DownloadService.DownloadModel model) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"ResourceAsColor"})
    private final void compat(android.widget.RemoteViews customLayout) {
    }
    
    @android.annotation.SuppressLint(value = {"DefaultLocale", "RestrictedApi"})
    private final void download(org.quick.library.service.DownloadService.DownloadModel model) {
    }
    
    private final java.lang.String getHint(long total, float progress, float speed) {
        return null;
    }
    
    public final void installAPK(@org.jetbrains.annotations.NotNull()
    org.quick.library.service.DownloadService.DownloadModel model) {
    }
    
    public final void cancel(@org.jetbrains.annotations.NotNull()
    org.quick.library.service.DownloadService.DownloadModel model) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    public final void showToast(@org.jetbrains.annotations.NotNull()
    java.lang.String content) {
    }
    
    public DownloadService() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lorg/quick/library/service/DownloadService$UpgradeReceiver;", "Landroid/content/BroadcastReceiver;", "downloadService", "Lorg/quick/library/service/DownloadService;", "(Lorg/quick/library/service/DownloadService;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "quick-library_debug"})
    public static final class UpgradeReceiver extends android.content.BroadcastReceiver {
        private org.quick.library.service.DownloadService downloadService;
        
        @java.lang.Override()
        public void onReceive(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        android.content.Intent intent) {
        }
        
        public UpgradeReceiver(@org.jetbrains.annotations.NotNull()
        org.quick.library.service.DownloadService downloadService) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\bR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0007\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\n\"\u0004\b\u001a\u0010\f\u00a8\u0006\u001b"}, d2 = {"Lorg/quick/library/service/DownloadService$DownloadModel;", "Ljava/io/Serializable;", "title", "", "apkUrl", "notificationId", "", "cover", "(Ljava/lang/String;Ljava/lang/String;II)V", "getApkUrl", "()Ljava/lang/String;", "setApkUrl", "(Ljava/lang/String;)V", "getCover", "()I", "setCover", "(I)V", "getNotificationId", "setNotificationId", "tempFile", "Ljava/io/File;", "getTempFile", "()Ljava/io/File;", "setTempFile", "(Ljava/io/File;)V", "getTitle", "setTitle", "quick-library_debug"})
    public static final class DownloadModel implements java.io.Serializable {
        @org.jetbrains.annotations.Nullable()
        private java.io.File tempFile;
        @org.jetbrains.annotations.Nullable()
        private java.lang.String title;
        @org.jetbrains.annotations.Nullable()
        private java.lang.String apkUrl;
        private int notificationId;
        private int cover;
        
        @org.jetbrains.annotations.Nullable()
        public final java.io.File getTempFile() {
            return null;
        }
        
        public final void setTempFile(@org.jetbrains.annotations.Nullable()
        java.io.File p0) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getTitle() {
            return null;
        }
        
        public final void setTitle(@org.jetbrains.annotations.Nullable()
        java.lang.String p0) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getApkUrl() {
            return null;
        }
        
        public final void setApkUrl(@org.jetbrains.annotations.Nullable()
        java.lang.String p0) {
        }
        
        public final int getNotificationId() {
            return 0;
        }
        
        public final void setNotificationId(int p0) {
        }
        
        public final int getCover() {
            return 0;
        }
        
        public final void setCover(int p0) {
        }
        
        public DownloadModel(@org.jetbrains.annotations.Nullable()
        java.lang.String title, @org.jetbrains.annotations.Nullable()
        java.lang.String apkUrl, int notificationId, int cover) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0012"}, d2 = {"Lorg/quick/library/service/DownloadService$Companion;", "", "()V", "ACTION", "", "ACTION_CANCEL", "ACTION_CLICK", "DOWNLOAD_DIR", "getDOWNLOAD_DIR", "()Ljava/lang/String;", "TAG", "getTAG", "startAction", "", "context", "Landroid/content/Context;", "model", "Lorg/quick/library/service/DownloadService$DownloadModel;", "quick-library_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTAG() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDOWNLOAD_DIR() {
            return null;
        }
        
        public final void startAction(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        org.quick.library.service.DownloadService.DownloadModel model) {
        }
        
        private Companion() {
            super();
        }
    }
}