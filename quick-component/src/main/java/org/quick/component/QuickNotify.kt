package org.quick.component

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationCompat.VISIBILITY_PRIVATE
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.util.SparseArray
import org.quick.component.utils.DateUtils


/**
 * @describe 快速使用通知
 * @author ChrisZou
 * @date 2018/08/02-14:33
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
object QuickNotify {
    val actionShortcut = "com.android.launcher.action.INSTALL_SHORTCUT"
    val shortcutName = javaClass.`package`.name + "-" + javaClass.simpleName + ":shortcutName"
    val shortcutIcon = javaClass.`package`.name + "-" + javaClass.simpleName + ":shortcutIcon"
    val shortcutPackageName = javaClass.`package`.name + "-" + javaClass.simpleName + ":shortcutPackageName"
    val shortcutTargetName = javaClass.`package`.name + "-" + javaClass.simpleName + ":shortcutTargetName"

    val action = javaClass.`package`.name + "-" + javaClass.simpleName + ":action"
    val actionCancel = javaClass.`package`.name + "-" + javaClass.simpleName + ":actionCancel"
    val actionClick = javaClass.`package`.name + "-" + javaClass.simpleName + ":actionClick"
    val actionNotificationId = javaClass.`package`.name + "-" + javaClass.simpleName + ":actionNotificationId"

    val notificationListeners: SparseArray<(context: Context, intent: Intent) -> Unit> by lazy { return@lazy SparseArray<((context: Context, intent: Intent) -> Unit)>() }
    val notificationManager: NotificationManager by lazy { return@lazy QuickAndroid.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(QuickAndroid.applicationContext.packageName, "通知消息", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.lightColor = Color.RED
            notificationManager.createNotificationChannel(channel)
        }
        val intentFilter = IntentFilter(action)
        intentFilter.addAction(actionShortcut)
        QuickAndroid.applicationContext.registerReceiver(NotificationReceiver(), intentFilter)
    }

    /**
     * 临时通知，允许移除
     * 该通知只响应点击动作
     */
    fun notifyTempNormal(@DrawableRes icon: Int, title: String, content: String) {
        val notificationId = Math.abs(DateUtils.getCurrentTimeInMillis().toInt())
        notify(notificationId, NotificationCompat.Builder(QuickAndroid.applicationContext, QuickAndroid.applicationContext.packageName)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(false)
                .setVisibility(VISIBILITY_PRIVATE)
                .setAutoCancel(true), null, null, null)
    }

    /**
     * 临时通知，允许移除
     * 该通知只响应点击动作
     * @param intentClick 传递自定义内容，用于点击回调返回内容
     */
    fun notifyTempNormal(@DrawableRes icon: Int, title: String, content: String, intentClick: Intent, onNotificationListener: ((context: Context, intent: Intent) -> Unit)) {
        val notificationId = Math.abs(DateUtils.getCurrentTimeInMillis().toInt())
        notify(notificationId, NotificationCompat.Builder(QuickAndroid.applicationContext, QuickAndroid.applicationContext.packageName)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(false)
                .setVisibility(VISIBILITY_PRIVATE)
                .setAutoCancel(true), intentClick, null, onNotificationListener)
    }


    /**
     * 临时通知-通知进度准备
     */
    fun notifyTempProgress(notifyId: Int, @DrawableRes icon: Int, title: String, content: String) {
        notify(notifyId, NotificationCompat.Builder(QuickAndroid.applicationContext, QuickAndroid.applicationContext.packageName)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setProgress(100, 0, true)
                .setVisibility(VISIBILITY_PRIVATE)
                .setOngoing(true)/*是否正在通知（是否不可以取消）*/
                .setAutoCancel(false)/*是否点击时取消*/)
    }

    /**
     * 临时通知-通知进度中
     */
    fun notifyTempProgresses(notifyId: Int, @DrawableRes icon: Int, title: String, content: String, progress: Int) {
        notify(notifyId, NotificationCompat.Builder(QuickAndroid.applicationContext, QuickAndroid.applicationContext.packageName)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setVisibility(VISIBILITY_PRIVATE)
                .setProgress(100, progress, false)
                .setOngoing(true)/*是否正在通知（是否不可以取消）*/
                .setAutoCancel(false)/*是否点击时取消*/)
    }

    /**
     * 临时通知-通知进度完成-点击取消
     */
    fun notifyTempProgressEnd(notifyId: Int, @DrawableRes icon: Int, title: String, content: String, intentClick: Intent?, onNotificationListener: ((context: Context, intent: Intent) -> Unit)?) {
        notify(notifyId, NotificationCompat.Builder(QuickAndroid.applicationContext, QuickAndroid.applicationContext.packageName)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setVisibility(VISIBILITY_PRIVATE)
                .setProgress(100, 100, false)
                .setOngoing(false)/*是否正在通知（是否不可以取消）*/
                .setAutoCancel(true)/*是否点击时取消*/
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                , intentClick, null, onNotificationListener)
    }

    /**
     * 自定义通知-不接管任务操作，只显示通知
     */
    fun notify(notificationId: Int, builder: NotificationCompat.Builder) {
        notify(notificationId, builder, null, null, null)
    }

    /**
     * 自定义通知-接管回调事件
     */
    fun notify(notificationId: Int, builder: NotificationCompat.Builder, onNotificationListener: ((context: Context, intent: Intent) -> Unit)) {
        val intentCancel = Intent(action)
        intentCancel.putExtra(action, actionCancel)
        intentCancel.putExtra(actionNotificationId, notificationId)

        val intentClick = Intent(action)
        intentCancel.putExtra(action, actionClick)
        intentCancel.putExtra(actionNotificationId, notificationId)
        notify(notificationId, builder, intentClick, intentCancel, onNotificationListener)
    }

    /**
     * 自定义通知-不接管任何事件
     *
     * 注意：如果需要使用自带的点击回调，请将在intent的action指定为{@link #action }例如：Intent(QuickNotify.action)
     * 并且将通知ID添加进intent{@link #actionNotificationId},例如：intentCancel.putExtra(actionNotificationId, notificationId)
     *
     * 这里提供一个自定义通知View的写法
     * for example:
     * 1、先实例一个RemoteViews
     * val customLayout = RemoteViews(packageName, R.layout.app_download_notification)
     *     customLayout.setTextViewText(R.id.titleTv, model.title)
     *     customLayout.setImageViewResource(R.id.coverIv, model.cover)
     *     customLayout.setOnClickPendingIntent(R.id.downloadStatusContainer, pendingIntentCancel)
     * 2、将实例好的RemoteViews安装进通知
     *     NotificationCompat.Builder(this, packageName).setCustomContentView(customLayout)
     * 3、安装好的Builder进行通知即可
     *
     * @param onNotificationListener 如果自行实现点击事件可不传
     */
    fun notify(notificationId: Int, builder: NotificationCompat.Builder, intentClick: Intent?, intentCancel: Intent?, onNotificationListener: ((context: Context, intent: Intent) -> Unit)?) {
        if (onNotificationListener != null) {
            if (intentCancel == null) {
                val tempCancel = Intent(action)
                tempCancel.putExtra(action, actionCancel)
                tempCancel.putExtra(actionNotificationId, notificationId)
                builder.setDeleteIntent(PendingIntent.getBroadcast(QuickAndroid.applicationContext, notificationId, tempCancel, PendingIntent.FLAG_UPDATE_CURRENT))
            }
            if (intentClick == null) {
                val tempClick = Intent(action)
                tempClick.putExtra(action, actionClick)
                tempClick.putExtra(actionNotificationId, notificationId)
                builder.setContentIntent(PendingIntent.getBroadcast(QuickAndroid.applicationContext, notificationId, tempClick, PendingIntent.FLAG_UPDATE_CURRENT))
            }

            notificationListeners.put(notificationId, onNotificationListener)
        }
        notificationManager.notify(notificationId, builder.build())
    }

    /**
     * 创建桌面通知（快捷方式）
     * @param builder 配置信息
     * @param onNotificationListener 创建成功回调
     */
    fun notifyDesktopShortcut(builder: ShortcutBuilder, onNotificationListener: (context: Context, intent: Intent) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) setupShortcutOAfter(builder, onNotificationListener) else setupShortcutOBefore(builder, onNotificationListener)
    }

    /**
     * Android O 以后兼容
     * @param builder
     * @param onSuccessListener 创建成功回调
     */
    private fun setupShortcutOAfter(builder: ShortcutBuilder, onSuccessListener: (context: Context, intent: Intent) -> Unit) {
        notificationListeners.put(builder.targetName.hashCode(), onSuccessListener)
        val callbackIntent = shortcutCallbackBuild(Intent(QuickAndroid.applicationContext, NotificationReceiver::class.java), builder)
        val pendingIntentCancel = PendingIntent.getBroadcast(QuickAndroid.applicationContext, 0x0, callbackIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val targetIntent = shortcutCallbackBuild(Intent(Intent.ACTION_VIEW), builder).setComponent(ComponentName(builder.packageName, builder.packageName + "." + builder.targetName))
        targetIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        targetIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        val shortcutManagerCompat = ShortcutInfoCompat.Builder(QuickAndroid.applicationContext, builder.shortcutId)
                .setIcon(IconCompat.createWithAdaptiveBitmap(builder.shortcutIcon))
                .setShortLabel(builder.shortcutName)
                .setIntent(targetIntent)
                .setAlwaysBadged()
                .build()

        if (ShortcutManagerCompat.isRequestPinShortcutSupported(QuickAndroid.applicationContext))
            if (ShortcutManagerCompat.requestPinShortcut(QuickAndroid.applicationContext, shortcutManagerCompat, pendingIntentCancel.intentSender))
            else QuickToast.showToastDefault("添加失败")
        else QuickToast.showToastDefault("设备不支持")
    }

    /**
     * Android O 以前
     * @param builder 配置内容
     */
    private fun setupShortcutOBefore(builder: ShortcutBuilder, onSuccessListener: (context: Context, intent: Intent) -> Unit) {
        notificationListeners.put(builder.targetName.hashCode(), onSuccessListener)

        val targetIntent = shortcutCallbackBuild(Intent(Intent.ACTION_MAIN), builder).setComponent(ComponentName(builder.packageName, builder.packageName + "." + builder.targetName))
        targetIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        targetIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        val shortcutIntent = shortcutCallbackBuild(Intent(actionShortcut), builder)
        shortcutIntent.putExtra("duplicate", false)
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent)
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, builder.shortcutName)
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, builder.shortcutIcon)
        QuickAndroid.applicationContext.sendBroadcast(shortcutIntent)
    }

    /**
     * 快捷方式回调数据构建
     */
    private fun shortcutCallbackBuild(intent: Intent, builder: ShortcutBuilder): Intent {
        intent.putExtra(actionNotificationId, builder.targetName.hashCode())
        intent.putExtra(shortcutName, builder.shortcutName)
//        intent.putExtra(shortcutIcon, builder.shortcutIcon)/*8.0无法协带*/
        intent.putExtra(shortcutPackageName, builder.packageName)
        intent.putExtra(shortcutTargetName, builder.targetName)
        intent.putExtras(builder.dataBundle)
        return intent
    }

    fun cancelNotify(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    /**
     * @param shortcutId
     */
    class ShortcutBuilder(var shortcutId: String = "") {
        var packageName: String = ""
        var targetName: String = ""
        var shortcutName: String = ""
        lateinit var dataBundle: Bundle
        lateinit var shortcutIcon: Bitmap

        fun setActivity(packageName: String, targetName: String): ShortcutBuilder {
            setActivity(packageName, targetName, Bundle())
            return this
        }

        /**
         * example: 包路径为org.quick.component 需要跳转到ui下面的MainActivity，那此时总路径应当为org.quick.component.ui.MainActivity
         *
         * @param packageName 包路径
         * @param targetName 目标Activity路径
         * @param dataBundle 传递到目标的数据集合
         */
        fun setActivity(packageName: String, targetName: String, dataBundle: Bundle): ShortcutBuilder {
            this.packageName = packageName
            this.targetName = targetName
            this.dataBundle = dataBundle
            return this
        }

        fun setShortcut(shortcutName: String, shortcutIcon: Bitmap): ShortcutBuilder {
            this.shortcutName = shortcutName
            this.shortcutIcon = shortcutIcon
            return this
        }
    }

    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            QuickNotify.notificationListeners.get(intent.getIntExtra(actionNotificationId, 0))?.invoke(context, intent)
        }
    }
}