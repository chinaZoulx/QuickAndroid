package org.quick.component

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.annotation.LayoutRes
import org.quick.component.utils.check.CheckUtils

/**
 * @describe 快速使用自定义Dialog
 * @author ChrisZou
 * @date 2018/7/9-9:36
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
open class QuickDialog private constructor() {

    private val defaultPadding = 100
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
                if (holder == null || holder?.itemView?.id != builder.resId || holder!!.itemView.context != builder.context) {
                    val tempView = LayoutInflater.from(builder.context).inflate(builder.resId, null)
                    tempView.id = builder.resId
                    holder = QuickViewHolder(tempView)
                }
            }
        }
        return holder!!
    }

    private fun createDialog(): Dialog {
        if (checkEqualDialog()) {
            dialog = Dialog(builder.context!!, builder.style)
            dialog?.setContentView(createViewHolder().itemView)
            dialog!!.setOnKeyListener { dialog, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!builder.isBlockBackKey) dialog!!.dismiss()
                    else return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            dialog?.setOnDismissListener {
                builder.onDismissListener?.invoke(dialog!!, it, createViewHolder())
            }
            builder.onInitListener?.invoke(dialog!!, createViewHolder())
        }

        dialog?.window?.setGravity(builder.gravity)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(builder.width, builder.height)
        dialog?.window?.setWindowAnimations(builder.animStyle)
        dialog?.window?.decorView?.setPadding(
            if (builder.paddingLeft == builder.defaultPadding) defaultPadding else builder.paddingLeft,
            builder.paddingTop,
            if (builder.paddingRight == builder.defaultPadding) defaultPadding else builder.paddingRight,
            builder.paddingBottom
        )
        dialog?.setCanceledOnTouchOutside(builder.canceledOnTouchOutside)
        return dialog!!
    }

    private fun checkEqualDialog() =
        builder.isRewrite || dialog == null || (builder.layoutView != null && holder?.itemView != builder.layoutView) || (builder.resId != -1 && holder?.itemView?.id != builder.resId) || contextChange()

    /**
     * context变化
     */
    private fun contextChange() =
        dialog != null && (if (dialog!!.context is ContextThemeWrapper) (dialog!!.context as ContextThemeWrapper).baseContext else dialog!!.context) != builder.context

    fun dismiss() {
        dialog?.dismiss()
    }

    fun show(onAfterListener: ((dialog: Dialog, holder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        if (CheckUtils.checkActivityIsRunning(builder.context as Activity)) {
            createDialog().show()
        }
        onAfterListener?.invoke(createDialog(), createViewHolder())
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

        fun show() {
            ClassHolder.INSTANCE.show()
        }
    }

    private object ClassHolder {
        val INSTANCE = QuickDialog()
    }

    class Builder constructor(val context: Context?, @LayoutRes var resId: Int = -1, var style: Int = 0) {
        internal var animStyle = -1
        internal val defaultPadding = -1
        internal var layoutView: View? = null
        internal var width = WindowManager.LayoutParams.MATCH_PARENT
        internal var height = WindowManager.LayoutParams.WRAP_CONTENT
        internal var gravity = Gravity.CENTER
        internal var canceledOnTouchOutside = true
        internal var isRewrite = false/*是否每次都重新创建dialog*/
        internal var paddingLeft = defaultPadding
        internal var paddingRight = defaultPadding
        internal var paddingTop = 0
        internal var paddingBottom = 0
        internal var isBlockBackKey = false/*屏蔽返回键*/

        internal var onInitListener: ((dialog: Dialog, holder: QuickViewHolder) -> Unit)? = null
        internal var onDismissListener: ((dialog: Dialog, iDialog: DialogInterface, holder: QuickViewHolder) -> Unit)? =
            null

        fun setAnimStyle(animStyle: Int): Builder {
            this.animStyle = animStyle
            return this
        }

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

        fun setPadding(left: Int, top: Int, right: Int, bottom: Int): Builder {
            this.paddingLeft = left
            this.paddingTop = top
            this.paddingRight = right
            this.paddingBottom = bottom
            return this
        }

        fun setSize(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            if (paddingLeft == defaultPadding) paddingLeft = 0
            if (paddingRight == defaultPadding) paddingRight = 0
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setLayoutView(view: View, style: Int = 0): Builder {
            this.layoutView = view
            this.style = style
            return this
        }

        fun setRewrite(isRewrite: Boolean): Builder {
            this.isRewrite = isRewrite
            return this
        }

        fun build() = ClassHolder.INSTANCE.setupQuickDialog(this)

        fun createDialog(): Dialog = ClassHolder.INSTANCE.setupQuickDialog(this).createDialog()

        fun createViewHolder() = build().createViewHolder()

        fun setOnInitListener(onInitListener: (dialog: Dialog, holder: QuickViewHolder) -> Unit): Builder {
            this.onInitListener = onInitListener
            return this
        }

        fun setOnDismissListener(onDismissListener: (dialog: Dialog, iDialog: DialogInterface, holder: QuickViewHolder) -> Unit): Builder {
            this.onDismissListener = onDismissListener
            return this
        }

        fun show(onAfterListener: ((dialog: Dialog, holder: QuickViewHolder) -> Unit)? = null): QuickViewHolder = ClassHolder.INSTANCE.setupQuickDialog(this).show(onAfterListener)

        fun dismiss() {
            QuickDialog.dismiss()
        }
    }
}