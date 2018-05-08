package com.chriszou.helper.tools

import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Environment
import java.io.File
import java.lang.Exception

/**
 * 录音工具类
 * Created by Neun on 2018/4/7.
 */
object MRUtils {

    val VOICE_FILE_PATH = Environment.getExternalStorageDirectory().absolutePath + File.separatorChar + "tempHelperVoice" + File.separatorChar
    val VOICE_FILE_NAME = "tempVoice.wav"

    private var mr: MediaRecorder? = null
    private var isRecorderStart = false

    private fun initMr() {
        if (mr == null) {
            mr = MediaRecorder()
            mr?.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            mr?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            val camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW)
            mr?.setAudioChannels(1)
            mr?.setAudioSamplingRate(8000)
            mr?.setAudioEncodingBitRate(64000)
            mr?.setAudioEncoder(camcorderProfile.audioCodec)
            mr?.setOutputFile(VOICE_FILE_PATH + VOICE_FILE_NAME)
        }
    }

    private fun initFile() {
        val tempFile = File(VOICE_FILE_PATH)
        if (!tempFile.exists()) {
            tempFile.mkdir()
        }
        val temp = File(VOICE_FILE_PATH + VOICE_FILE_NAME)
        if (!temp.exists())
            temp.createNewFile()
    }

    fun start(): Boolean {
        return try {
            initFile()
            initMr()
            mr?.prepare()
            mr?.start()
            isRecorderStart = true
            true
        } catch (O_O: Exception) {
            O_O.printStackTrace()
            mr?.reset()
            mr?.release()
            mr = null
            return false
        }
    }

    fun stop(): Boolean {
        return try {
            if (isRecorderStart) {
                mr?.stop()
                mr?.reset()
                mr?.release()
                mr = null
                isRecorderStart = false
            }
            true
        } catch (O_O: Exception) {
            O_O.printStackTrace()
            return false
        }
    }
}