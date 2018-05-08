package org.chris.quick.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import com.zhy.http.okhttp.request.RequestCall
import okhttp3.Call
import org.chris.quick.R
import org.chris.quick.b.activities.ThemeActivity.Companion.DATA
import org.chris.quick.tools.CheckUtils
import org.chris.quick.tools.DateUtils
import org.chris.quick.tools.common.DevicesUtils
import org.chris.quick.tools.common.FormatUtils
import java.io.File
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by work on 2017/7/26.
 * 多任务下载服务
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

class DownloadService : Service() {
    lateinit var notificationManager: NotificationManager
    private var taskList: MutableList<DownloadModel> = ArrayList()
    private var taskRequestCallMap: HashMap<String, RequestCall> = HashMap()
    private var receiver = UpgradeReceiver(this)

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val model = intent.getSerializableExtra(DATA) as DownloadModel
        if (!CheckUtils.isEmpty(model.apkUrl)) {
            if ((0 until taskList.size).map { taskList[it] }.any { it.apkUrl == model.apkUrl }) {
                showToast("该任务已建立，请等待")
                return super.onStartCommand(intent, flags, startId)
            }
            registerReceiver(receiver, IntentFilter(ACTION))
            initNotification(model)
            download(model)
            taskList.add(model)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("RestrictedApi")
    private fun initNotification(model: DownloadModel) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.createNotificationChannel(NotificationChannel(packageName, packageName, NotificationManager.IMPORTANCE_HIGH))
        notificationManager.notify(model.notificationId, getNotiBuilder(model).build())
    }

    private fun getNotiBuilder(model: DownloadModel): NotificationCompat.Builder {
        val intentCancel = Intent(ACTION)
        intentCancel.putExtra(ACTION, ACTION_CANCEL)
        intentCancel.putExtra(DATA, model)
        val pendingIntentCancel = PendingIntent.getBroadcast(this, model.notificationId, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT)

        val customLayout = RemoteViews(packageName, R.layout.app_download_notification)
        customLayout.setTextViewText(R.id.titleTv, model.title)
        customLayout.setImageViewResource(R.id.coverIv, model.cover)
        //        customLayout.setProgressBar(R.id.progressBar, 100, 0, true);
        customLayout.setOnClickPendingIntent(R.id.downloadStatusContainer, pendingIntentCancel)
        compat(customLayout)

        return NotificationCompat.Builder(this, packageName)
                .setContentTitle(model.title)
                .setSmallIcon(R.mipmap.ic_cloud_download_white)
                .setProgress(100, 0, true)
                .setAutoCancel(false)
                .setOngoing(true)
                .setCustomContentView(customLayout)
    }

    @SuppressLint("ResourceAsColor")
    private fun compat(customLayout: RemoteViews) {
        when (Build.BRAND.toLowerCase()) {
            "meizu", "huawei", "nubia", "oppo" -> {//黑色通知栏
                customLayout.setTextColor(R.id.titleTv, R.color.colorWhite)
                customLayout.setTextColor(R.id.hintTv, R.color.colorLineDark)
            }
            "xiaomi", "oneplus" -> {//白色通知栏
                customLayout.setTextColor(R.id.titleTv, R.color.colorBlack)
                customLayout.setTextColor(R.id.hintTv, R.color.colorGrayDarkDark)
            }
        }
    }

    @SuppressLint("DefaultLocale", "RestrictedApi")
    private fun download(model: DownloadModel) {
        showToast("已建立下载任务，请查看通知栏")
        var lastMillisecond = 0L
        var lastProgress = 0f
        val fileName = model.apkUrl!!.substring(model.apkUrl!!.lastIndexOf(File.separator) + 1, model.apkUrl!!.length)

        taskRequestCallMap[model.notificationId.toString()] = OkHttpUtils.get().tag(TAG).url(model.apkUrl).build()
        taskRequestCallMap[model.notificationId.toString()]!!.execute(object : FileCallBack(DOWNLOAD_DIR, fileName) {
            override fun onError(call: Call, e: Exception, id: Int) {
                e.printStackTrace()
                showToast("下载失败")
                cancel(model)
            }

            override fun onResponse(response: File, id: Int) {
                model.tempFile = response
                lastProgress = 0f
                val builder = getNotiBuilder(model)
                val intentClick = Intent(ACTION)
                intentClick.putExtra(ACTION, ACTION_CLICK)
                intentClick.putExtra(DATA, model)
                val pendingIntentClick = PendingIntent.getBroadcast(this@DownloadService, model.notificationId, intentClick, PendingIntent.FLAG_UPDATE_CURRENT)//PendingIntent.FLAG_ONE_SHOT点一次消失  FLAG_UPDATE_CURRENT不消失
                builder.setContentIntent(pendingIntentClick)

                //                builder.getContentView().setProgressBar(R.id.progressBar, 100, 100, false);
                builder.contentView.setViewVisibility(R.id.downloadStatusContainer, View.GONE)
                builder.contentView.setViewVisibility(R.id.confirmTimeTv, View.VISIBLE)
                builder.contentView.setTextViewText(R.id.confirmTimeTv, String.format("%d:%d", DateUtils.getCurrentHour(), DateUtils.getCurrentSecond()))
                builder.contentView.setTextViewText(R.id.hintTv, String.format("应用下载完成，点击安装"))
                notificationManager.notify(model.notificationId, builder.build())
                DevicesUtils.installAPK(this@DownloadService, model.tempFile)
            }

            override fun inProgress(progress: Float, total: Long, id: Int) {
                if (DateUtils.getCurrentTimeInMillis() - lastMillisecond > 1000) {
                    lastMillisecond = DateUtils.getCurrentTimeInMillis()
                    val speed = (progress - lastProgress) * total
//                    val tempProgress = (progress * 100).toInt()
                    //                    builder.getContentView().setProgressBar(R.id.progressBar, 100, tempProgress, false);
                    val builder = getNotiBuilder(model)
                    builder.contentView.setTextViewText(R.id.hintTv, getHint(total, progress * total, speed))
                    notificationManager.notify(model.notificationId, builder.build())
                    lastProgress = progress
                }
            }
        })
    }

    private fun getHint(total: Long, progress: Float, speed: Float): String {
        return String.format("%s/%s  %s/s", FormatUtils.formatFlow(progress * 8), FormatUtils.formatFlow((total * 8).toFloat()), FormatUtils.formatFlow(speed * 8))
    }

    fun installAPK(model: DownloadModel) {
        DevicesUtils.installAPK(this@DownloadService, model.tempFile)
        (0 until taskList.size).map { taskList[it] }.filter { it.notificationId == model.notificationId }.forEach { taskList.remove(it) }
        notificationManager.cancel(model.notificationId)
    }

    fun cancel(model: DownloadModel) {
        taskRequestCallMap[model.notificationId.toString()]!!.cancel()
        (0 until taskList.size).map { taskList[it] }.filter { it.notificationId == model.notificationId }.forEach { taskList.remove(it) }
        notificationManager.cancel(model.notificationId)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    fun showToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    class UpgradeReceiver(private var downloadService: DownloadService) : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(ACTION)) {
            //点击
                ACTION_CLICK -> downloadService.installAPK(intent.getSerializableExtra(DATA) as DownloadModel)
            //取消
                ACTION_CANCEL -> downloadService.cancel(intent.getSerializableExtra(DATA) as DownloadModel)
            }
        }
    }

    class DownloadModel(var title: String?, var apkUrl: String?, var notificationId: Int, var cover: Int) : Serializable {
        var tempFile: File? = null
    }

    companion object {

        val TAG = DownloadService::class.java.simpleName!!
        val DOWNLOAD_DIR = Environment.getExternalStorageDirectory().absolutePath + File.separator + "DownloadApk"
        const val ACTION = "action"
        const val ACTION_CLICK = "actionClick"
        const val ACTION_CANCEL = "actionCancel"

        fun startAction(context: Context, model: DownloadModel) {
            val intent = Intent(context, DownloadService::class.java)
            intent.putExtra(DATA, model)
            context.startService(intent)
        }
    }
}
