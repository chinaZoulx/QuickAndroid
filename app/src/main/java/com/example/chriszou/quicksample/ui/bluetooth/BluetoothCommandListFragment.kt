package com.example.chriszou.quicksample.ui.bluetooth

import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.component.QuickViewHolder
import org.quick.library.b.BaseViewHolder

/**
 * @Author ChrisZou
 * @Date 2018/6/1-11:32
 * @Email chrisSpringSmell@gmail.com
 */
class BluetoothCommandListFragment : org.quick.library.b.QuickListFragment<String,String>() {
    override fun onResultItemResId(viewType: Int): Int = R.layout.item_bluetooth

    override fun onBindData(holder: QuickViewHolder, position: Int, itemData: String, viewType: Int) {
        holder.setText(R.id.contentTv, itemData)
    }

    lateinit var parent: BluetoothActivity

    override fun start() {
        parent = activity as BluetoothActivity
    }

    override val isPullRefreshEnable: Boolean
        get() = false

    override val isLoadMoreEnable: Boolean
        get() = false

    override fun onResultUrl(): String = ""
    override fun onResultParams(params: MutableMap<String, String>) = Unit

    override fun onLoadMoreSuccess(model: String) {

    }

    override fun onPullRefreshSuccess(model: String) {

    }
}