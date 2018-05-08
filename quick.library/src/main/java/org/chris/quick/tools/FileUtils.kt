package org.chris.quick.tools

import android.content.Context
import android.os.Environment

/**
 * Created by zoulx on 2018/1/25.
 */
object FileUtils {

    val sdCardPath = Environment.getExternalStorageDirectory().absolutePath/*内存卡可读写的根目录*/
    val sdCardPathCache = Environment.getDownloadCacheDirectory().absolutePath/*缓存目录*/

    /**
     * 当前应用数据库存放路径
     */
    fun currentAppDBPath(context: Context,dbName:String) = context.applicationContext.getDatabasePath(dbName).absolutePath
    /**
     * 当前应用路径
     */
    fun currentAppPath(context: Context) = context.applicationContext.filesDir.absolutePath

    /**
     * 当前应用安装包路径
     */
    fun currentAppPathInstall(context: Context) = context.applicationContext.packageResourcePath
}