package org.quick.component

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.annotation.LayoutRes
import android.view.*
import org.quick.component.utils.check.CheckUtils

/**
 * @describe 快速使用自定义Dialog
 * @author ChrisZou
 * @date 2018/7/9-9:36
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
open class QuickDialog private constructor() {

    lateinit var builder: Builder
    private var dialog: Dialog? = null
    private var holder: QuickViewHolder? = null

    private fun setupQuickDialog(builder: Builder): QuickDialog {
        this.builder = builder
        return this
    }

    @SuppressLint("ResourceType")
    private fun createViewHolder(): QuickViewHolder {
        assert(builder.resId != -1 || builder.layoutView != null)
        when {
            builder.layoutView != null ->
                if (holder?.itemView != builder.layoutView)
                    holder = QuickViewHolder(builder.layoutView!!)

            builder.resId != -1 -> {
                if (holder?.itemView?.id != builder.resId) {
                    val tempView = LayoutInflater.from(builder.context).inflate(builder.resId, null)
                    tempView.id = builder.resId
                    holder = QuickViewHolder(tempView)
                }
            }
        }
        return holder!!
    }

    private fun getDialog(): Dialog {
        if (tryCreateDialog()) {
            dialog = Dialog(builder.context, builder.style)
            dialog?.setContentView(createViewHolder().itemView)
            dialog?.window?.setGravity(builder.gravity)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.setLayout(builder.width, builder.height)
            dialog?.window?.decorView?.setPadding(builder.paddingLeft, builder.paddingTop, builder.paddingRight, builder.paddingBottom)
            dialog?.setCanceledOnTouchOutside(builder.canceledOnTouchOutside)
            dialog!!.setOnKeyListener { dialog, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!builder.isBlockBackKey) dialog!!.dismiss()
                    else return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        return dialog!!
    }

    /**
     * 是否需要重新创建dialog
     */
    private fun tryCreateDialog() = when {
        builder.isRewrite -> true/*每次都重构*/
        dialog == null -> true/*dialog为空*/
        builder.layoutView != null -> holder?.itemView != builder.layoutView/*有自定义的View并且与上一个不同*/
        builder.resId != -1 -> holder?.itemView?.id != builder.resId /*layout id不同*/
        !CheckUtils.checkActivityIsRunning(builder.context as Activity) -> true/*当前dialog依赖的Activity已经被销毁*/
        else -> false
    }

    fun dismiss() {
        getDialog().dismiss()
    }

    fun show(): QuickViewHolder {
        getDialog().show()
        return createViewHolder()
    }

    fun resetInternal() {
        dialog = null
        holder = null
    }

    companion object {

        fun dismiss() {
            ClassHolder.INSTANCE.dismiss()
        }

        fun resetInternal() {
            ClassHolder.INSTANCE.resetInternal()
        }
    }

    private object ClassHolder {
        val INSTANCE = QuickDialog()
    }

    /**
     * @param resId 资源ID
     * @param style 弹框主题
     */
    class Builder constructor(val context: Context, @LayoutRes var resId: Int = -1, var style: Int = 0) {

        internal var layoutView: View? = null
        internal var width = WindowManager.LayoutParams.MATCH_PARENT
        internal var height = WindowManager.LayoutParams.WRAP_CONTENT
        internal var gravity = Gravity.CENTER
        internal var canceledOnTouchOutside = false
        internal var isRewrite = false/*是否每次都重新创建dialog*/
        internal var paddingLeft = 100
        internal var paddingRight = 100
        internal var paddingTop = 0
        internal var paddingBottom = 0
        internal var isBlockBackKey = false/*屏蔽返回键*/

        /**
         * 屏蔽返回键
         */
        fun setBlockBackKey(isBlockBackKey: Boolean): Builder {
            this.isBlockBackKey = isBlockBackKey
            return this
        }

        fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): Builder {
            this.canceledOnTouchOutside = canceledOnTouchOutside
            return this
        }

        fun setWindowPadding(left: Int, top: Int, right: Int, bottom: Int): Builder {
            this.paddingLeft = left
            this.paddingTop = top
            this.paddingRight = right
            this.paddingBottom = bottom
            return this
        }

        fun setSize(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            if (paddingLeft == 100) paddingLeft = 0
            if (paddingRight == 100) paddingRight = 0
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setLayoutView(view: View?, style: Int = 0): Builder {
            this.layoutView = view
            this.style = style
            return this
        }

        fun setRewrite(isRewrite: Boolean): Builder {
            this.isRewrite = isRewrite
            return this
        }

        fun build() = ClassHolder.INSTANCE.setupQuickDialog(this)

        fun createDialog(): Dialog = ClassHolder.INSTANCE.setupQuickDialog(this).getDialog()

        fun createViewHolder() = build().createViewHolder()

        fun show(): QuickViewHolder = ClassHolder.INSTANCE.setupQuickDialog(this).show()

        fun dismiss() {
            QuickDialog.dismiss()
        }
    }
}