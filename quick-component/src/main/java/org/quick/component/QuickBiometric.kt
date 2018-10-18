package org.quick.component

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.KeyguardManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.util.Base64
import android.view.View
import org.quick.component.utils.DevicesUtils
import org.quick.component.widget.CheckAnimView
import java.io.IOException
import java.io.Serializable
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

/**
 * 快速验证生物特征
 * @desc FingerprintManager此类将在API 28中被废弃，将使用新的启用新的API BiometricPrompt
 * @desc BiometricPrompt 生物特征已经在Android设备中普及，BiometricPrompt将提供统一的Dialog验证，该类不仅仅支持指纹，并且人脸等生物特征也支持，具体请查看官方文档：https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt
 */
@TargetApi(Build.VERSION_CODES.M)
@RequiresApi(Build.VERSION_CODES.M)
object QuickBiometric {
    val keyguardManager = QuickAndroid.applicationContext.getSystemService<KeyguardManager>(KeyguardManager::class.java)
    val fingerprintManager = QuickAndroid.applicationContext.getSystemService<FingerprintManager>(FingerprintManager::class.java)
    val AndroidKeyStore = "AndroidKeyStore"
    val defaultCipher: Cipher
    var mKeyStore: KeyStore? = null
    var mKeyGenerator: KeyGenerator? = null
    var cancellationSignal: CancellationSignal? = null
    @SuppressLint("StaticFieldLeak")
    lateinit var holder: QuickViewHolder
    private lateinit var mCryptoObject: FingerprintManager.CryptoObject/*加密对象*/

    enum class TYPE constructor(var type: Int) : Serializable {
        /**
         * 指纹验证通过
         */
        AuthenticationSucceeded(1),
        /**
         * 指纹验证中
         */
        Authenticating(2),
        /**
         * 指纹验证失败-不同系统失败次数不一样，并且失败后短时间内无法再次发起验证
         */
        AuthenticationFailed(0),
        /**
         * 指纹验证失败-此设备未设置锁屏密码
         */
        AuthenticationErrorKeyguardSecure(-1),
        /**
         * 指纹验证失败-此设备不支持指纹功能
         */
        AuthenticationErrorHardwareDetected(-2),
        /**
         * 指纹验证失败-此设备未录入指纹
         */
        AuthenticationErrorEnrolledFingerprints(-3),
    }

    init {
        try {
            mKeyStore = KeyStore.getInstance(AndroidKeyStore)
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to get an instance of KeyStore", e)
        }

        try {
            mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get an instance of KeyGenerator", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get an instance of KeyGenerator", e)
        }

        try {
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get an instance of Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get an instance of Cipher", e)
        }
        createKey(QuickAndroid.appBaseName, true)
    }

    private fun createKey(keyName: String, invalidatedByBiometricEnrollment: Boolean) {
        try {
            mKeyStore!!.load(null)
            val builder = KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)
            }
            mKeyGenerator!!.init(builder.build())
            mKeyGenerator!!.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initCipher(cipher: Cipher, keyName: String): Boolean {
        try {
            mKeyStore!!.load(null)
            val key = mKeyStore!!.getKey(keyName, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }

    fun startFingerprintListener(onFingerprintListener: (type: TYPE, resultMsg: String?) -> Unit) {
        if (checkDevicesSupport(onFingerprintListener)) {
            if (initCipher(defaultCipher, QuickAndroid.appBaseName)) {
                cancellationSignal = CancellationSignal()
                mCryptoObject = FingerprintManager.CryptoObject(defaultCipher)
                fingerprintManager.authenticate(mCryptoObject, cancellationSignal, 0, object : FingerprintManager.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {/*识别错误-操作完成后调用*/
                        onFingerprintListener.invoke(TYPE.AuthenticationFailed, "验证失败")
                    }

                    override fun onAuthenticationFailed() {/*指纹有效，但是不能正确*/
                        onFingerprintListener.invoke(TYPE.Authenticating, "指纹有效，但是不正确")
                    }

                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {/*验证错误，但是此错误可以恢复，比如，传感器脏了*/
                        onFingerprintListener.invoke(TYPE.Authenticating, helpString as @kotlin.ParameterName(name = "resultMsg") String?)
                    }

                    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {/*识别成功*/
                        tryEncrypt(onFingerprintListener)
                        stopFingerprintListener(null)
                    }
                }, null)
            } else {/*两种情况：
            1、指纹无法使用（被禁用）
            2、指纹已验证过（刚验证失败了）
            */
                onFingerprintListener.invoke(TYPE.AuthenticationFailed, "验证失败")
            }
        }
    }

    /**
     * 停止监听
     */
    fun stopFingerprintListener(onCancelListener: CancellationSignal.OnCancelListener?) {
        cancellationSignal?.cancel()
        cancellationSignal?.setOnCancelListener(onCancelListener)
        cancellationSignal = null
    }

    /**
     * 验证设备是否支持指纹功能
     */
    fun checkDevicesSupport(onFingerprintListener: ((type: TYPE, resultMsg: String) -> Unit)?): Boolean = when {
        !keyguardManager.isKeyguardSecure -> {
            onFingerprintListener?.invoke(TYPE.AuthenticationErrorKeyguardSecure, "验证失败，此设备未设置锁屏密码")
            false
        }
        !fingerprintManager.isHardwareDetected -> {
            onFingerprintListener?.invoke(TYPE.AuthenticationErrorHardwareDetected, "验证失败，此设备不支持指纹功能")
            false
        }
        !fingerprintManager.hasEnrolledFingerprints() -> {
            onFingerprintListener?.invoke(TYPE.AuthenticationErrorEnrolledFingerprints, "验证失败，此设备未录入指纹")
            false
        }
        else -> true
    }

    /**
     * 解密-用于验证手机是否被劫持了
     */
    private fun tryEncrypt(onFingerprintListener: (type: TYPE, resultMsg: String?) -> Unit) {
        try {
            val encrypted = mCryptoObject.cipher.doFinal(QuickAndroid.appBaseName.toByteArray())
            onFingerprintListener.invoke(TYPE.AuthenticationSucceeded, Base64.encodeToString(encrypted, 0))
        } catch (O_O: Exception) {/*如果异常了，表示该设备被劫持了或者不兼容*/
            onFingerprintListener.invoke(TYPE.AuthenticationFailed, "解密失败，该设备或许被劫持")
        }
    }

    /**
     * 自带的指纹验证码弹框
     * @param onFingerprintListener type:值为{@link #TYPE.AuthenticationSucceeded}时表示成功,{@link TYPE.AuthenticationFailed}是表示失败
     */
    fun showFingerprintDialog(activity: Activity, onFingerprintListener: (type: TYPE, resultMsg: String?) -> Unit) {
        val size: Int = (DevicesUtils.getScreenWidth() / 2.6).toInt()
        holder = QuickDialog.Builder(activity, R.layout.app_dialog_fingerprint).setSize(size, size).show()
        startFingerprintListener { type, resultMsg ->
            onFingerprintListener.invoke(type, resultMsg)

            when (type) {
                TYPE.AuthenticationSucceeded -> {
                    holder.setVisibility(View.VISIBLE, R.id.checkAnimView)
                    holder.getView<CheckAnimView>(R.id.checkAnimView)?.setCheck(true)
                    holder.setText(R.id.fingerprintHintTv, "恭喜，验证通过！")
                    QuickAsync.asyncDelay({
                        holder.getView<CheckAnimView>(R.id.checkAnimView)?.setCheck(false)
                        holder.setVisibility(View.GONE, R.id.checkAnimView)
                        QuickDialog.dismiss()
                    }, 1500)

                }
                TYPE.AuthenticationFailed -> holder.setText(R.id.fingerprintHintTv, "验证失败")
                else -> holder.setText(R.id.fingerprintHintTv, "按下手指验证")
            }
        }
    }
}