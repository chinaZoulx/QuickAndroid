package org.chris.quick.function

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import org.chris.quick.R

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/9-9:36
 * @from https://github.com/SpringSmell/quick.library
 * @email chrisSpringSmell@gmail.com
 */
class QuickDialog(var builder: Builder) {

    private var dialog: Dialog? = null

    fun createDialog(): Dialog {
        if (dialog == null || dialog?.context != builder.context || builder.isRewrite) {
            dialog = Dialog(builder.context, builder.style)
            dialog?.setContentView(builder.layoutView)
            dialog?.window?.setGravity(builder.gravity)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setCanceledOnTouchOutside(builder.canceledOnTouchOutside)
            dialog?.window?.setLayout(builder.width, builder.height)
        }
        return dialog!!
    }

    class Builder(var context: Context) {
        internal var width = WindowManager.LayoutParams.MATCH_PARENT
        internal var height = WindowManager.LayoutParams.WRAP_CONTENT
        internal var style = R.style.AppTheme_Dialog
        internal lateinit var layoutView: View
        internal var gravity = Gravity.CENTER
        internal var canceledOnTouchOutside = false
        internal var isRewrite = false/*是否每次都重新创建dialog*/

        fun setStyle(@StyleRes style: Int): Builder {
            this.style = style
            return this
        }

        fun setLayout(@LayoutRes res: Int): Builder {
            return setLayout(LayoutInflater.from(context).inflate(res, null))
        }

        fun setLayout(@LayoutRes res: View): Builder {
            this.layoutView = res
            return this
        }

        fun setSize(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setRewrite(isRewrite: Boolean): Builder {
            this.isRewrite = isRewrite
            return this
        }

        fun create(): Dialog = QuickDialog(this).createDialog()

        fun show(): Dialog {
            val dialog = QuickDialog(this).createDialog()
            dialog.show()
            return dialog
        }
    }
}