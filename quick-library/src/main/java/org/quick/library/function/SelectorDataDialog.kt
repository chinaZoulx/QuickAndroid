package org.quick.library.function

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import org.quick.component.QuickDialog
import org.quick.library.R
import org.quick.library.widgets.RollView

object SelectorDataDialog {

    /**
     * 选择数据
     */
    fun show(context: Context, title: String, listener: (item: String) -> Unit, vararg datas: String) {
        QuickDialog.Builder(context, R.layout.dialog_selector_data)
            .setCanceledOnTouchOutside(true)
            .setSize(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            .setGravity(Gravity.BOTTOM)
            .setAnimStyle(R.style.dialogAnimBottom2Up)
            .setOnInitListener { _, holder ->
                holder.getView<RollView>(R.id.contentRv)?.setData(datas.toMutableList())
                holder.setText(R.id.titleTv, title)
                holder.setOnClickListener({ view, _ ->
                    QuickDialog.dismiss()
                    if (view.id == R.id.confirmTv)
                        listener.invoke(holder.getView<RollView>(R.id.contentRv)!!.selected)
                }, R.id.cancelTv, R.id.confirmTv)
            }
            .show()
    }
}