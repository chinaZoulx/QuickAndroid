package org.quick.library.function

import android.content.Context
import android.text.TextUtils
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import org.quick.component.QuickDialog
import org.quick.library.R

class IsOkDialog(var context: Context?) {

    private var title = ""
    private var content = ""
    private var leftTxt = ""
    private var rightTxt = ""
    /**
     * 是否阻塞返回键
     */
    private var isBlockBack = false
    private var customView: View? = null
    private var isCancelTouchOutSide: Boolean = true

    private var listener: ((v: View, isRight: Boolean) -> Unit)? = null

    fun setTitle(title: String): IsOkDialog {
        this.title = title
        return this
    }

    fun setContent(content: String): IsOkDialog {
        this.content = content
        return this
    }

    fun setBtnLeft(value: String): IsOkDialog {
        this.leftTxt = value
        return this
    }

    fun setBtnRight(value: String): IsOkDialog {
        this.rightTxt = value
        return this
    }

    fun setCustomView(value: View): IsOkDialog {
        this.customView = value
        return this
    }

    /**
     * 是否阻塞返回键
     */
    fun setBlockBack(isBlockBack: Boolean): IsOkDialog {
        this.isBlockBack = isBlockBack
        return this
    }

    fun setCancelTouchOutSide(isCancelTouchOutSide: Boolean): IsOkDialog {
        this.isCancelTouchOutSide = isCancelTouchOutSide
        return this
    }

    /**
     * 常规问答，不带标题
     */
    fun defaultQuestions(content: String): IsOkDialog {
        this.content = content
        this.leftTxt = "取消"
        this.rightTxt = "确定"
        return this
    }

    fun show(listener: ((v: View, isRight: Boolean) -> Unit)?=null) {
        this.listener = listener
        val holder = QuickDialog.Builder(context, R.layout.app_dialog_is_ok)
            .setBlockBackKey(isBlockBack)
            .setCanceledOnTouchOutside(isCancelTouchOutSide)
            .show()

        holder.setVisibility(
            if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
            , R.id.titleTv
        )

        holder.setVisibility(
            if (TextUtils.isEmpty(leftTxt)) View.GONE else View.VISIBLE
            , R.id.leftBtn
        )

        holder.setVisibility(
            if (TextUtils.isEmpty(rightTxt)) View.GONE else View.VISIBLE
            , R.id.rightBtn
        )

        if (customView != null) {
            holder.getView<ConstraintLayout>(R.id.contentContainer)?.removeAllViews()
            holder.getView<ConstraintLayout>(R.id.contentContainer)?.addView(customView)
        }

        holder.setText(R.id.leftBtn, leftTxt) { view, _ ->
            QuickDialog.dismiss()
            listener?.invoke(view, false)
        }
            .setText(R.id.rightBtn, rightTxt) { view, _ ->
                QuickDialog.dismiss()
                listener?.invoke(view, true)
            }
            .setText(R.id.contentTv, content)
            .setText(R.id.titleTv, title)

        resetInternal()
    }

    private fun resetInternal() {
        leftTxt = ""
        rightTxt = ""
        content = ""
        customView = null
        title = ""
        isBlockBack = false
        isCancelTouchOutSide = false
    }

//    class Builder(var context: Context) {
//        internal var title = ""
//        internal var content = ""
//        internal var leftTxt = ""
//        internal var rightTxt = ""
//        /**
//         * 是否阻塞返回键
//         */
//        internal var isBlockBack = false
//        internal var customView: View? = null
//        internal var isCancelTouchOutSide: Boolean = true
//
//        internal lateinit var onAfterListener: (v: View, isRight: Boolean) -> Unit
//        fun setTitle(title: String): Builder {
//            this.title = title
//            return this
//        }
//
//        fun setContent(content: String): Builder {
//            this.content = content
//            return this
//        }
//
//        fun setBtnLeft(value: String): Builder {
//            this.leftTxt = value
//            return this
//        }
//
//        fun setBtnRight(value: String): Builder {
//            this.rightTxt = value
//            return this
//        }
//
//        fun setCustomView(value: View): Builder {
//            this.customView = value
//            return this
//        }
//
//        /**
//         * 是否阻塞返回键
//         */
//        fun setBlockBack(isBlockBack: Boolean): Builder {
//            this.isBlockBack = isBlockBack
//            return this
//        }
//
//        fun setCancelTouchOutSide(isCancelTouchOutSide: Boolean): Builder {
//            this.isCancelTouchOutSide = isCancelTouchOutSide
//            return this
//        }
//
//        fun show(onAfterListener: (v: View, isRight: Boolean) -> Unit) {
//            this.onAfterListener = onAfterListener
//            val holder = QuickDialog.Builder(context, R.layout.app_dialog_is_ok).show()
//
//            holder.setVisibility(
//                if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
//                , R.id.titleTv
//            )
//
//            holder.setVisibility(
//                if (TextUtils.isEmpty(leftTxt)) View.GONE else View.VISIBLE
//                , R.id.leftBtn
//            )
//
//            holder.setVisibility(
//                if (TextUtils.isEmpty(rightTxt)) View.GONE else View.VISIBLE
//                , R.id.rightBtn
//            )
//
//            if (customView != null) {
//                holder.getView<ConstraintLayout>(R.id.contentContainer)?.removeAllViews()
//                holder.getView<ConstraintLayout>(R.id.contentContainer)?.addView(customView)
//            }
//
//            holder.setText(R.id.leftBtn, leftTxt) { view, _ ->
//                onAfterListener.invoke(view, false)
//            }
//                .setText(R.id.rightBtn, rightTxt) { view, _ ->
//                    onAfterListener.invoke(view, true)
//                }
//                .setText(R.id.contentTv, content)
//                .setText(R.id.titleTv, title)
//        }
//
//    }
}