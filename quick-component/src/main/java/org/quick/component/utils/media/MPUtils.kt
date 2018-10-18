package org.quick.component.utils.media

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Message
import org.quick.component.utils.DateUtils
import org.quick.component.QuickAndroid
import org.quick.component.QuickToast
import org.quick.component.utils.HttpUtils
import java.lang.Exception

/**
 * 简单播放音乐工具
 * Created by ChrisZou on 2018/4/7.
 */
object MPUtils {

    private val OFFSET_TIME = DateUtils.SECOND * 5
    private var mp: MediaPlayer? = null
    private var onProgressListener: OnProgressListener? = null
    private var lastAction = -1//-1:默认状态 0：停止 1：开始 2：暂停


    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (mp != null)
                onProgressListener?.onProgress(mp!!.duration.toLong(), mp!!.currentPosition.toLong())
            sendMsg()
        }
    }

    private fun initMp() {
        if (mp == null) {
            mp = MediaPlayer()
            mp?.setOnPreparedListener { mp ->
                if (lastAction == 1) {
                    onProgressListener?.onStartPlay()
                    mp.start()
                    sendMsg()
                }
            }
            mp?.setOnSeekCompleteListener { mp ->
                mp.start()
            }
            mp?.setOnCompletionListener {
                onProgressListener?.onConfirm()
            }
            mp?.setOnErrorListener { mp: MediaPlayer?, var1: Int, var2: Int ->
                onProgressListener?.onPlayError(mp, var1, var2)
                true
            }
        }
    }

    private fun sendMsg() {
        handler.sendEmptyMessageDelayed(0, 1000)
    }

    private fun setPlayFile(path: String): MediaPlayer = try {
        initMp()
        mp?.reset()
        if (HttpUtils.isHttpUrlFormRight(path)) mp?.setDataSource(QuickAndroid.applicationContext, Uri.parse(path)) else mp?.setDataSource(path)
        mp!!
    } catch (O_O: Exception) {
        mp!!
    }

    fun play(path: String, onProgressListener: OnProgressListener): Boolean = try {
        MPUtils.onProgressListener = onProgressListener
        setPlayFile(path)
        lastAction = 1
        mp?.prepareAsync()
        true
    } catch (O_O: Exception) {
        O_O.printStackTrace()
        MPUtils.onProgressListener?.onPlayError(null, -1, -1)
        QuickToast.showToastDefault("播放错误")
        false
    }

    fun pause(): Boolean = try {
        lastAction = 0
        if (mp != null && mp!!.isPlaying) {
            mp?.pause()
        }
        handler.removeMessages(0)
        true
    } catch (O_O: Exception) {
        O_O.printStackTrace()
        false
    }

    fun onDestroy() {
        handler.removeMessages(0)
        lastAction = -1
        mp?.release()
        mp = null
    }

    /**
     * 快退
     */
    fun fr() {
        if (mp != null && mp!!.isPlaying) {
            var tempPosition: Int = (mp!!.currentPosition - OFFSET_TIME).toInt()
            if (tempPosition <= 0) tempPosition = 0
            mp!!.seekTo(tempPosition)
        }
    }

    /**
     * 快进
     */
    fun ff() {
        if (mp != null && mp!!.isPlaying) {
            var tempPosition: Int = (mp!!.currentPosition + OFFSET_TIME).toInt()
            if (tempPosition >= mp!!.duration) tempPosition = mp!!.duration
            mp!!.seekTo(tempPosition)
        }
    }

    interface OnProgressListener {
        fun onStartPlay()
        fun onProgress(total: Long, currentProgress: Long)
        fun onPlayError(mp: MediaPlayer?, var1: Int, var2: Int)
        fun onConfirm()
    }
}