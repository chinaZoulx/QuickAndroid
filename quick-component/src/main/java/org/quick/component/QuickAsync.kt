package org.quick.component

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.concurrent.Executors

/**
 * @describe 方便的使用简单异步，此类只做简单异步封装。
 *           更复杂的场景推荐使用RxAndroid：https://github.com/ReactiveX/RxAndroid
 * @author ChrisZou
 * @date 2018/08/03-10:16
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
object QuickAsync {

    private val executorService = Executors.newFixedThreadPool(20)
    val mainHandler: Handler by lazy { return@lazy Handler(Looper.getMainLooper()) }

    /**
     * 异常线程处理数据，然后返回值
     */
    fun <T> async(onASyncListener: OnASyncListener<T>) = executorService.submit {
        val value = onASyncListener.onASync()
        mainHandler.post { onASyncListener.onAccept(value) }
    }

    /**
     * 秒表计步，for example:60秒（1....60）
     * @param interval 间隔时间，单位：毫秒
     * @param maxSteps 最大步数
     */
    fun <T> asyncTime(onIntervalListener: OnIntervalListener<T>, interval: Long, maxSteps: Long, isReversal: Boolean = false) = executorService.submit {
        var steps = if (isReversal) maxSteps else 0
        if (isReversal)
            while (steps > 0) {
                steps--
                mainHandler.post { onIntervalListener.onNext(steps as T) }
                Thread.sleep(interval)
            }
        else
            while (steps < maxSteps) {
                steps++
                mainHandler.post { onIntervalListener.onNext(steps as T) }
                Thread.sleep(interval)
            }
        mainHandler.post { onIntervalListener.onAccept(steps as T) }
    }

    /**
     * 延迟执行
     * @param delayTime 延迟时间（毫秒）
     */
    fun asyncDelay(onEndListener: () -> Unit, delayTime: Long) {
        mainHandler.postDelayed({
            onEndListener.invoke()
        }, delayTime)
    }

    /**
     * 循环执行，如果不调用cancel方法永不停止
     * @param interval 间隔时间（毫秒）
     * return Long 跳动步数
     */
    fun asyncLoop(onIntervalListener: (steps: Int) -> Unit?, interval: Long) = executorService.submit {
        var steps = 0
        while (true) {
            steps++
            mainHandler.post { onIntervalListener.invoke(steps) }
            Thread.sleep(interval)
        }
    }


    interface OnIntervalListener<T> : Consumer<T> {
        fun onNext(value: T)
    }

    interface OnASyncListener<T> : Consumer<T> {
        fun onASync(): T
    }

    interface Consumer<T> {
        /**
         * Consume the given value.
         * @param value the value
         * @throws Exception on error
         */
        @Throws(Exception::class)
        fun onAccept(value: T)
    }
}