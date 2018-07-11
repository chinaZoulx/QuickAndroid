package org.chris.quick.function

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import org.chris.quick.QuickAndroid
import org.chris.quick.R
import org.chris.quick.b.BaseRecyclerViewAdapter

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/6-10:18
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
class QuickToast {
    private val mainHandler: Handler by lazy { return@lazy Handler(Looper.getMainLooper()) }
    private lateinit var builder: Builder
    private var toast: Toast? = null
    private lateinit var holder: BaseRecyclerViewAdapter.BaseViewHolder

    private fun setupToast(builder: Builder): QuickToast {
        this.builder = builder
        return this
    }

    fun showToast(msg: String?, vararg params: Any) {
        var showMsg = msg
        if (!TextUtils.isEmpty(msg) && params.isNotEmpty())
            showMsg = String.format(msg!!, params)
        mainHandler.post { configToast(showMsg).show() }
    }

    private fun configToast(msg: String?): Toast {
        if (toast == null) {
            toast = Toast(QuickAndroid.getApplicationContext)
            holder = BaseRecyclerViewAdapter.BaseViewHolder((QuickAndroid.getApplicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(builder.layoutRes, null))
            holder.itemView.tag = builder.layoutRes
            toast?.view = holder.itemView
        }
        holder.setText(R.id.toastMsgTv, msg)
        toast?.duration = builder.duration
        toast?.setGravity(builder.gravity, builder.xOffset, builder.yOffset)
        return toast!!
    }

    private class ClassHolder {
        companion object {
            val INSTANCE = QuickToast()
        }
    }

    companion object {
        fun showToastDefault(msg: String?, vararg params: Any) {
            Builder().build().showToast(msg, params)
        }
    }

    class Builder {
        internal var gravity = Gravity.BOTTOM
        internal var duration: Int = Toast.LENGTH_SHORT
        internal var xOffset = 0
        internal var yOffset = 150
        internal var layoutRes = R.layout.transient_notification

        fun setLayoutRes(@LayoutRes res: Int): Builder {
            this.layoutRes = res
            return this
        }

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

        fun build() = QuickToast.ClassHolder.INSTANCE.setupToast(this)
    }
}