package com.chriszou.helper.tools

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.chris.quick.m.Log
import java.io.*


/**
 * 录音工具类
 * Created by chrisZou on 2018/4/7.
 */
object MRUtils2 {
    val VOICE_FILE_PATH = Environment.getExternalStorageDirectory().absolutePath + File.separatorChar + "tempHelperVoice" + File.separatorChar
    val VOICE_FILE_NAME_WAV = "tempVoice.wav"
    val VOICE_FILE_NAME_PCM= "tempVoice.pcm"
    private var tempFile: File? = null
    private var audioRecord: AudioRecord? = null
    private var mAudioSessionId: Int = 0
    private var type: TYPE = TYPE.STOP
    private var minBufferSize: Int = -1

    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioEncoding = AudioFormat.ENCODING_PCM_16BIT
    val mSampleRate = 8000//44100，22050,16000，11025，8000

    enum class TYPE(var value: Int) {
        RECORDING(1), STOP(2);
    }

    private fun initMr() {
        minBufferSize = AudioRecord.getMinBufferSize(mSampleRate, channelConfig, audioEncoding)
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, mSampleRate, channelConfig, audioEncoding, minBufferSize * 2)
        mAudioSessionId = audioRecord!!.audioSessionId
    }

    private fun initFile() {
        val tempFile = File(VOICE_FILE_PATH)
        if (!tempFile.exists()) {
            tempFile.mkdirs()
        }
        if (this.tempFile == null) {
            this.tempFile = File.createTempFile("tempVoice", ".pcm", tempFile)
        }
        if (this.tempFile!!.exists()) {
            this.tempFile!!.delete()
        }
    }

    fun startRecording() {
        initFile()
        initMr()
        type = TYPE.RECORDING
        val pcmData = ByteArray(minBufferSize * 2)
        Observable.create<Boolean> { subscriber ->
            var fos: FileOutputStream? = null
            try {
                audioRecord?.startRecording()//开始录制
                fos = FileOutputStream(tempFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            while (type == TYPE.RECORDING) {
                try {
                    val bufferReadResult = audioRecord?.read(pcmData, 0, minBufferSize * 2 - 512)
                    if (bufferReadResult != AudioRecord.ERROR_INVALID_OPERATION) {
                        fos!!.write(pcmData)
                    }
                } catch (O_O: Exception) {
                    O_O.printStackTrace()
                }
            }
            try {
                fos!!.close()
            } catch (O_O: Exception) {
                O_O.printStackTrace()
            }
            subscriber.onComplete()
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation()).subscribe {
            type = TYPE.STOP
        }
    }

    fun stop() {
        if (type == TYPE.RECORDING) {
            type = TYPE.STOP
            try {
                audioRecord?.stop()
                release()
            } catch (O_O: Exception) {
                O_O.printStackTrace()
            }
        }
    }

    fun release() {
        pamToWav(tempFile!!.absolutePath, VOICE_FILE_PATH + VOICE_FILE_NAME_WAV)
        audioRecord?.release()
        audioRecord = null
    }

    private fun pamToWav(pcmPath: String, outputPath: String): Boolean {
        val buffer: ByteArray?
        val TOTAL_SIZE: Int
        val pcmFile = File(pcmPath)
        if (!pcmFile.exists()) {
            return false
        }
        TOTAL_SIZE = pcmFile.length().toInt()
        val header = WavHeader()
        header.fileLength = TOTAL_SIZE + (44 - 8)
        header.FmtHdrLeth = 16
        header.BitsPerSample = 16
        header.Channels = 1
        header.FormatTag = 0x0001
        header.SamplesPerSec = mSampleRate
        header.BlockAlign = (header.Channels * header.BitsPerSample / 8).toShort()
        header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec
        header.DataHdrLeth = TOTAL_SIZE
        val headerBuffer: ByteArray?
        try {
            headerBuffer = header.getHeader()
        } catch (O_O: Exception) {
            O_O.printStackTrace()
            return false
        }
        if (headerBuffer.size != 44) return false
        val outputFile = File(outputPath)
        if (outputFile.exists()) outputFile.delete()
        //合成所有的pcm文件的数据，写到目标文件
        try {
            buffer = ByteArray(1024 * 4) // Length of All Files, Total Size
            val inStream: InputStream?
            val ouStream: OutputStream?

            ouStream = BufferedOutputStream(FileOutputStream(outputPath))
            ouStream.write(headerBuffer, 0, headerBuffer.size)
//            writeWaveFileHeader(ouStream, header.fileLength.toLong(),header.DataHdrLeth.toLong(), header.SamplesPerSec.toLong(), header.Channels.toInt(),header.BlockAlign.toLong())
            inStream = BufferedInputStream(FileInputStream(pcmFile))
            var size = inStream.read(buffer)
            while (size != -1) {
                ouStream.write(buffer)
                size = inStream.read(buffer)
            }
            inStream.close()
            ouStream.close()
        } catch (e: FileNotFoundException) {
            Log.e("PcmToWav", e.message)
            return false
        } catch (ioe: IOException) {
            Log.e("PcmToWav", ioe.message)
            return false
        }
        pcmFile.delete()
        return true
    }

    /**
     * 加入wav文件头
     */
    @Throws(IOException::class)
    private fun writeWaveFileHeader(out: BufferedOutputStream, totalAudioLen: Long, totalDataLen: Long, longSampleRate: Long, channels: Int, byteRate: Long) {
        val header = ByteArray(44)
        header[0] = 'R'.toByte() // RIFF/WAVE header
        header[1] = 'I'.toByte()
        header[2] = 'F'.toByte()
        header[3] = 'F'.toByte()
        header[4] = (totalDataLen and 0xff).toByte()
        header[5] = (totalDataLen shr 8 and 0xff).toByte()
        header[6] = (totalDataLen shr 16 and 0xff).toByte()
        header[7] = (totalDataLen shr 24 and 0xff).toByte()
        header[8] = 'W'.toByte()  //WAVE
        header[9] = 'A'.toByte()
        header[10] = 'V'.toByte()
        header[11] = 'E'.toByte()
        header[12] = 'f'.toByte() // 'fmt ' chunk
        header[13] = 'm'.toByte()
        header[14] = 't'.toByte()
        header[15] = ' '.toByte()
        header[16] = 16  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0
        header[18] = 0
        header[19] = 0
        header[20] = 1   // format = 1
        header[21] = 0
        header[22] = channels.toByte()
        header[23] = 0
        header[24] = (longSampleRate and 0xff).toByte()
        header[25] = (longSampleRate shr 8 and 0xff).toByte()
        header[26] = (longSampleRate shr 16 and 0xff).toByte()
        header[27] = (longSampleRate shr 24 and 0xff).toByte()
        header[28] = (byteRate and 0xff).toByte()
        header[29] = (byteRate shr 8 and 0xff).toByte()
        header[30] = (byteRate shr 16 and 0xff).toByte()
        header[31] = (byteRate shr 24 and 0xff).toByte()
        header[32] = (2 * 16 / 8).toByte() // block align
        header[33] = 0
        header[34] = 16  // bits per sample
        header[35] = 0
        header[36] = 'd'.toByte() //data
        header[37] = 'a'.toByte()
        header[38] = 't'.toByte()
        header[39] = 'a'.toByte()
        header[40] = (totalAudioLen and 0xff).toByte()
        header[41] = (totalAudioLen shr 8 and 0xff).toByte()
        header[42] = (totalAudioLen shr 16 and 0xff).toByte()
        header[43] = (totalAudioLen shr 24 and 0xff).toByte()
        out.write(header, 0, 44)
    }

    class WavHeader {
        val fileID = charArrayOf('R', 'I', 'F', 'F')
        var fileLength: Int = 0
        var wavTag = charArrayOf('W', 'A', 'V', 'E')
        var FmtHdrID = charArrayOf('f', 'm', 't', ' ')
        var FmtHdrLeth: Int = 0
        var FormatTag: Short = 0
        var Channels: Short = 0
        var SamplesPerSec: Int = 0
        var AvgBytesPerSec: Int = 0
        var BlockAlign: Short = 0
        var BitsPerSample: Short = 0
        var DataHdrID = charArrayOf('d', 'a', 't', 'a')
        var DataHdrLeth: Int = 0

        @Throws(IOException::class)
        fun getHeader(): ByteArray {
            val bos = ByteArrayOutputStream()
            WriteChar(bos, fileID)
            WriteInt(bos, fileLength)
            WriteChar(bos, wavTag)
            WriteChar(bos, FmtHdrID)
            WriteInt(bos, FmtHdrLeth)
            WriteShort(bos, FormatTag.toInt())
            WriteShort(bos, Channels.toInt())
            WriteInt(bos, SamplesPerSec)
            WriteInt(bos, AvgBytesPerSec)
            WriteShort(bos, BlockAlign.toInt())
            WriteShort(bos, BitsPerSample.toInt())
            WriteChar(bos, DataHdrID)
            WriteInt(bos, DataHdrLeth)
            bos.flush()
            val r = bos.toByteArray()
            bos.close()
            return r
        }

        @Throws(IOException::class)
        private fun WriteShort(bos: ByteArrayOutputStream, s: Int) {
            val mybyte = ByteArray(2)
            mybyte[1] = (s shl 16 shr 24).toByte()
            mybyte[0] = (s shl 24 shr 24).toByte()
            bos.write(mybyte)
        }


        @Throws(IOException::class)
        private fun WriteInt(bos: ByteArrayOutputStream, n: Int) {
            val buf = ByteArray(4)
            buf[3] = (n shr 24).toByte()
            buf[2] = (n shl 8 shr 24).toByte()
            buf[1] = (n shl 16 shr 24).toByte()
            buf[0] = (n shl 24 shr 24).toByte()
            bos.write(buf)
        }

        private fun WriteChar(bos: ByteArrayOutputStream, id: CharArray) {
            id.indices.map { id[it] }.forEach { bos.write(it.toInt()) }
        }
    }
}