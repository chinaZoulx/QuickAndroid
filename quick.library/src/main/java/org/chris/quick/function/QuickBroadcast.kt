package org.chris.quick.function

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.SparseArray
import org.chris.quick.QuickAndroid

/**
 * @describe 方便的使用动态广播
 * @author ChrisZou
 * @date 2018/7/10-15:59
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
object QuickBroadcast {

    private const val ACTION = "org.chris.quick.function#QuickBroadcastReceiverAction"
    private val onBroadcastListeners = SparseArray<(action: String, intent: Intent) -> Unit>()
    private val onBroadcastListenerActions = SparseArray<Array<String>>()

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val actions = intent.getStringArrayExtra(ACTION)
            var action = ""
            for (index in 0 until onBroadcastListenerActions.size()) {
                if (onBroadcastListenerActions.valueAt(index).any { tempAction ->
                            actions.any {
                                if (it == tempAction) {
                                    action = tempAction
                                    true
                                } else false
                            }
                        }) {
                    onBroadcastListeners[onBroadcastListenerActions.keyAt(index)]?.invoke(action, intent)
                }
            }
//            if (isBroadcast)
//                onBroadcastListeners[key]?.invoke(action, intent)
        }
    }

    init {
        QuickAndroid.getApplicationContext.registerReceiver(broadcastReceiver, IntentFilter(ACTION))
    }

    /**
     * @param intent 协带参数
     * @param action 发送目标
     */
    fun sendBroadcast(intent: Intent, vararg action: String) {
        intent.action = ACTION
        intent.putExtra(ACTION, action)
        QuickAndroid.getApplicationContext.sendBroadcast(intent)
    }

    /**
     * @param binder 绑定者，消息回调依赖此目标。若此目标重复将最后一个有效
     * @param onMsgListener 消息回调
     * @param action 接收目标
     */
    fun addBroadcastListener(binder: Any?, onMsgListener: (action: String, intent: Intent) -> Unit, vararg action: String) {
        if (binder != null) {
            this.onBroadcastListeners.put(binder.hashCode(), onMsgListener)
            this.onBroadcastListenerActions.put(binder.hashCode(), action as Array<String>?)
        }
    }

    /**
     * 移除消息回调
     * @param binder 绑定者
     */
    fun removeBroadcastListener(binder: Any?) {
        if (binder != null) {
            this.onBroadcastListeners.remove(binder.hashCode())
            this.onBroadcastListenerActions.remove(binder.hashCode())
        }
    }

//    /**
//     * 使广播无效
//     */
//    fun gc() {
//        this.onBroadcastListenerActions.clear()
//        this.onBroadcastListeners.clear()
//        broadcastReceiver.abortBroadcast()
//    }
}