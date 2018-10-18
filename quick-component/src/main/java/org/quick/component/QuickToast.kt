package org.quick.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/6-10:18
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
class QuickToast private constructor() {
    private val mainHandler: Handler by lazy { return@lazy Handler(Looper.getMainLooper()) }
    private lateinit var builder: Builder
    private var toast: Toast? = null
    private var holder: QuickViewHolder? = null

    private fun setupToast(builder: Builder): QuickToast {
        this.builder = builder
        return this
    }

    fun showToast(msg: String?): QuickViewHolder {
        val toast = configToast(msg)
        mainHandler.post { toast.show() }
        return createViewHolder()
    }

    private fun configToast(msg: String?): Toast {
        if (toast == null || toast?.view?.id != builder.resId) {/*布局发生变化将重新初始化*/
            toast = Toast(QuickAndroid.applicationContext)
            toast?.view = createViewHolder().itemView
        }
        holder?.setText(R.id.toastMsgTv, msg)/*自定义View将不会设置msg*/
        toast?.duration = builder.duration
        toast?.setGravity(builder.gravity, builder.xOffset, builder.yOffset)
        return toast!!
    }

    @SuppressLint("ResourceType")
    private fun createViewHolder(): QuickViewHolder {
        if (holder?.itemView?.id != builder.resId) {
            val tempView = (QuickAndroid.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(builder.resId, null)
            tempView.id = builder.resId
            holder = QuickViewHolder(tempView)
        }
        return holder!!
    }

    private class ClassHolder {
        companion object {
            val INSTANCE = QuickToast()
        }
    }

    companion object {
        fun showToastDefault(msg: String?) {
            Builder().showToast(msg)
        }
    }

    class Builder(@LayoutRes var resId: Int = R.layout.app_toast) {
        internal var gravity = Gravity.BOTTOM
        internal var duration: Int = Toast.LENGTH_SHORT
        internal var xOffset = 0
        internal var yOffset = 150

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): Builder {
            this.gravity = gravity
            this.xOffset = xOffset
            this.yOffset = yOffset
            return this
        }

        /**
         * Set how long to show the view for.
         * @see Toast.LENGTH_SHORT
         * @see Toast.LENGTH_LONG
         */
        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun build() = ClassHolder.INSTANCE.setupToast(this)

        fun create(msg: String?) = ClassHolder.INSTANCE.setupToast(this).configToast(msg)

        fun showToast(msg: String?) = ClassHolder.INSTANCE.setupToast(this).showToast(msg)
    }
}