package com.chriszou.helper.tools

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.chris.quick.tools.DateUtils
import org.chris.quick.tools.common.HttpUtils
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * 简单播放音乐工具
 * Created by ChrisZou on 2018/4/7.
 */
object MPUtils {

    private val OFFSET_TIME = DateUtils.SECOND * 5
    private var mp: MediaPlayer? = null
    private var onProgressListener: OnProgressListener? = null
    private var disposable: Disposable? = null
    private var lastAction = -1//-1:默认状态 0：停止 1：开始 2：暂停

    private fun initMp(activity: Activity) {
        if (mp == null) {
            mp = MediaPlayer()
            mp?.setOnPreparedListener { mp ->
                if (lastAction == 1) {
                    onProgressListener?.onStartPlay()
                    mp.start()
                    Observable.interval(1, TimeUnit.SECONDS).take(DateUtils.HOURS).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Long> {
                        override fun onSubscribe(d: Disposable?) {
                            disposable = d
                        }

                        override fun onComplete() = Unit
                        override fun onError(e: Throwable?) = Unit
                        override fun onNext(value: Long?) {
                            onProgressListener?.onProgress(mp.duration.toLong(), mp.currentPosition.toLong())
                        }
                    })
                }
            }
            mp?.setOnSeekCompleteListener { mp ->
                mp.start()
            }
            mp?.setOnCompletionListener {
                onProgressListener?.onConfirm()
                if (disposable != null && !disposable!!.isDisposed)
                    disposable?.dispose()
            }
            mp?.setOnErrorListener { mp: MediaPlayer?, var1: Int, var2: Int ->
                onProgressListener?.onPlayError(mp, var1, var2)
                true
            }
        }
    }

    private fun setPlayFile(activity: Activity, path: String): MediaPlayer = try {
        initMp(activity)
        mp?.reset()
        if (HttpUtils.isHttpUrlFormRight(path)) mp?.setDataSource(activity, Uri.parse(path)) else mp?.setDataSource(path)
        mp!!
    } catch (O_O: Exception) {
        mp!!
    }

    fun play(activity: Activity, path: String, onProgressListener: OnProgressListener): Boolean = try {
        this.onProgressListener = onProgressListener
        setPlayFile(activity, path)
        lastAction = 1
        mp?.prepareAsync()
        true
    } catch (O_O: Exception) {
        O_O.printStackTrace()
        this.onProgressListener?.onPlayError(null, -1, -1)
        showToast(activity, "播放错误")
        false
    }

    fun pause(): Boolean = try {
        lastAction = 0
        if (mp != null && mp!!.isPlaying) {
            mp?.pause()
            if (disposable != null && !disposable!!.isDisposed)
                disposable?.dispose()
        }
        true
    } catch (O_O: Exception) {
        O_O.printStackTrace()
        false
    }

    fun onDestroy() {
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

    private fun showToast(activity: Activity, content: String) {
        Toast.makeText(activity, content, Toast.LENGTH_SHORT).show()
    }

    interface OnProgressListener {
        fun onStartPlay()
        fun onProgress(total: Long, currentProgress: Long)
        fun onPlayError(mp: MediaPlayer?, var1: Int, var2: Int)
        fun onConfirm()
    }
}