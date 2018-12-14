package org.quick.component.utils

import android.os.Environment
import org.quick.component.QuickAndroid
import org.quick.component.QuickAsync
import org.quick.component.callback.OnWriteListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

/**
 * Created by zoulx on 2018/1/25.
 */
object FileUtils {
    const val DirPath = "cachePath"
    const val FileName = "FileName"
    const val MAX_SKIP_BUFFER_SIZE = 2048/*最大缓冲区大小*/

    val sdCardPath = Environment.getExternalStorageDirectory().absolutePath/*内存卡可读写的根目录*/
    val sdCardPathCache = Environment.getDownloadCacheDirectory().absolutePath/*缓存目录*/

    /**
     * 当前应用数据库存放路径
     */
    fun currentAppDBPath(dbName: String) = QuickAndroid.applicationContext.getDatabasePath(dbName).absolutePath

    /**
     * 当前应用路径
     */
    fun currentAppPath() = QuickAndroid.applicationContext.filesDir.absolutePath

    /**
     * 当前应用安装包路径
     */
    fun currentAppPathInstall() = QuickAndroid.applicationContext.packageResourcePath

    /**
     * 写入文件
     *
     * @param inputStream
     * @param filePathDir   文件路径
     * @param fileName      文件名
     * @param isAppend 是否追加文件
     */
    fun writeFile(inputStream: InputStream?, filePathDir: String, fileName: String, isAppend: Boolean, onWriteListener: OnWriteListener) {
        if (inputStream != null) {
            QuickAsync.async(object : QuickAsync.OnASyncListener<File> {
                override fun onASync(): File {
                    val dir = File(filePathDir)
                    if (!dir.exists())
                        dir.mkdirs()
                    val filePath = filePathDir + File.separatorChar + fileName
                    val file = File(filePath)

                    val buffer = ByteArray(MAX_SKIP_BUFFER_SIZE)
                    val totalCount = inputStream.available().toLong()/*如果inputStream是网络流，此处数据将不准确*/
                    var redCount = 0L
                    var redBytesCount = inputStream.read(buffer)
                    val fileOutputStream = FileOutputStream(filePath, isAppend)

                    var lastTime = 0L
                    var tempTime: Long

                    var isDone = false

                    while (redBytesCount != -1) {
                        redCount += redBytesCount.toLong()
                        fileOutputStream.write(buffer, 0, redBytesCount)
                        redBytesCount = inputStream.read(buffer)

                        tempTime = System.currentTimeMillis() - lastTime
                        if (tempTime > 100L) {/*间隔80毫秒触发一次，否则队列阻塞*/
                            if (!isDone) {/*完成后只触发一次*/
                                isDone = redBytesCount == -1
                                QuickAsync.runOnUiThread { onWriteListener.onLoading(fileName, redCount, totalCount, isDone) }
                            }
                            lastTime = System.currentTimeMillis()
                        }
                    }
                    if (!isDone) QuickAsync.runOnUiThread { onWriteListener.onLoading(fileName, redCount, totalCount, true) }/*之前有延迟100毫秒，或许会造成最后一次未返回*/
                    fileOutputStream.close()
                    inputStream.close()
                    return file
                }

                override fun onError(O_O: Exception) {
                    onWriteListener.onFailure(O_O as IOException)
                }

                override fun onAccept(value: File) {
                    onWriteListener.onResponse(value)
                }

            })

        } else {
            throw NullPointerException()
        }
    }

    /**
     * 从流读取文件
     *
     * @param ins
     * @param file
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun inputstreamToFile(ins: InputStream, file: File): File {
        val os = FileOutputStream(file)
        var bytesRead = 0
        val buffer = ByteArray(8192)
        while (bytesRead != -1) {
            bytesRead = ins.read(buffer, 0, 8192)
            os.write(buffer, 0, bytesRead)
        }
        os.close()
        ins.close()
        return file
    }
}