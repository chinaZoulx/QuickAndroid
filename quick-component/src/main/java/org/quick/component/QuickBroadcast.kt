package org.quick.component

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.SparseArray
import org.quick.component.utils.check.CheckUtils
import java.io.Serializable

/**
 * @describe 方便的使用动态广播
 * @author ChrisZou
 * @date 2018/7/10-15:59
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
object QuickBroadcast {

    private const val ACTION = "org.quick.library.function#QuickBroadcastReceiverAction"
    private val onBroadcastListeners = SparseArray<(action: String, intent: Intent) -> Unit>()
    private val onBroadcastListenerActions = SparseArray<Array<String>>()

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val actions = intent.getStringArrayExtra(ACTION)
            var action = ""
            for (index in 0 until onBroadcastListenerActions.size())
                if (onBroadcastListenerActions.valueAt(index).any { tempAction ->
                            actions.any {
                                if (it == tempAction) {
                                    action = tempAction
                                    true
                                } else false
                            }
                        })
                    onBroadcastListeners[onBroadcastListenerActions.keyAt(index)]?.invoke(action, intent)
        }
    }

    init {
        QuickAndroid.applicationContext.registerReceiver(broadcastReceiver, IntentFilter(ACTION))
    }

    fun sendBroadcast(@NonNull vararg action: String) {
        sendBroadcast(null, *action)
    }

    /**
     * @param intent 协带参数
     * @param action 发送目标
     */
    fun sendBroadcast(intent: Intent?, @NonNull vararg action: String) {
        val tempIntent = CheckUtils.checkNull(intent, Intent())
        tempIntent.action = ACTION
        tempIntent.putExtra(ACTION, action)
        QuickAndroid.applicationContext.sendBroadcast(tempIntent)
    }

    /**
     * @param binder 绑定者，消息回调依赖此目标。若此目标重复将最后一个注册的有效
     * @param onMsgListener 消息回调
     * @param action 接收目标
     */
    fun addBroadcastListener(binder: Any, onMsgListener: (action: String, intent: Intent) -> Unit, vararg action: String) {
        onBroadcastListeners.put(binder.hashCode(), onMsgListener)
        onBroadcastListenerActions.put(binder.hashCode(), action as Array<String>?)
    }

    /**
     * 移除消息回调
     * @param binder 绑定者
     */
    fun removeBroadcastListener(binder: Any) {
        onBroadcastListeners.remove(binder.hashCode())
        onBroadcastListenerActions.remove(binder.hashCode())
    }

    /**
     * 使广播无效
     */
    fun resetInternal() {
        this.onBroadcastListenerActions.clear()
        this.onBroadcastListeners.clear()
    }

    class Builder {
        var intent: Intent = Intent()

        fun addParams(key: String, vararg value: String): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Float): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Int): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Double): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Byte): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: CharSequence): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Boolean): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Long): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, vararg value: Short): Builder {
            if (value.size == 1) intent.putExtra(key, value[0]) else intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: ArrayList<String>): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: Bundle): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun addParams(key: String, value: Serializable): Builder {
            intent.putExtra(key, value)
            return this
        }

        fun build() = intent
    }
}