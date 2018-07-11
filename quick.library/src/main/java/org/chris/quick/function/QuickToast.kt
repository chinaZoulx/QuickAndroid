package org.chris.quick.function

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import org.chris.quick.QuickAndroid
import org.chris.quick.R
import org.chris.quick.b.BaseRecyclerViewAdapter
import kotlin.coroutines.experimental.coroutineContext

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/6-10:18
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
open class QuickToast {
    private val mainHandler: Handler by lazy { return@lazy Handler(Looper.getMainLooper()) }
    private lateinit var builder: Builder
    private var toast: Toast? = null
    private lateinit var holder: BaseRecyclerViewAdapter.BaseViewHolder

    open val context get() = QuickAndroid.getApplicationContext

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
        if (toast == null || (builder.layoutView != null && toast!!.view != builder.layoutView)) {/*布局发生变化将重新初始化*/
            toast = Toast(context)
            holder = if (builder.layoutView != null) BaseRecyclerViewAdapter.BaseViewHolder(builder.layoutView) else BaseRecyclerViewAdapter.BaseViewHolder((context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.transient_notification, null))
            toast?.view = holder.itemView
        }
        if (builder.layoutView == null) holder.setText(R.id.toastMsgTv, msg)/*自定义View将不会设置msg*/
        toast!!.duration = builder.duration
        toast!!.setGravity(builder.gravity, builder.xOffset, builder.yOffset)
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
        internal var layoutView: View? = null

        fun setLayoutView(res: View): Builder {
            this.layoutView = res
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

        fun create(msg: String?) = QuickToast.ClassHolder.INSTANCE.setupToast(this).configToast(msg)
    }
}