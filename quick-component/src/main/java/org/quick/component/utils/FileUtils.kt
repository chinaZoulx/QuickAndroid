package org.quick.component.utils

import android.os.Environment
import org.quick.component.QuickAndroid
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by zoulx on 2018/1/25.
 */
object FileUtils {

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
     * @param isRewriteFile 是否覆盖
     * @return
     */
    fun writeFile(inputStream: InputStream?, filePathDir: String?, fileName: String?, isRewriteFile: Boolean): Boolean {
        return if (inputStream != null && filePathDir != null && fileName != null) {
            try {
                val e = File(filePathDir)
                if (!e.exists()) {
                    e.mkdirs()
                }

                val filePath = filePathDir + File.separatorChar + fileName
                val file = File(filePath)
                val fileOutputStream: FileOutputStream
                val buffer: ByteArray
                var count1 = 0
                if (file.exists() && file.isFile) {
                    if (!isRewriteFile) {
                        inputStream.close()
                        false
                    } else {
                        file.delete()
                        fileOutputStream = FileOutputStream(filePath)
                        buffer = ByteArray(1024)
                        while (count1 > 0) {
                            count1 = inputStream.read(buffer)
                            fileOutputStream.write(buffer, 0, count1)
                        }

                        fileOutputStream.close()
                        inputStream.close()
                        true
                    }
                } else {
                    fileOutputStream = FileOutputStream(filePath)
                    buffer = ByteArray(1024)

                    while (count1 > 0) {
                        count1 = inputStream.read(buffer)
                        fileOutputStream.write(buffer, 0, count1)
                    }

                    fileOutputStream.close()
                    inputStream.close()
                    true
                }
            } catch (var11: Exception) {
                var11.printStackTrace()
                false
            }

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