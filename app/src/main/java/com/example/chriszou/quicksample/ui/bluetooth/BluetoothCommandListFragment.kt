package com.example.chriszou.quicksample.ui.bluetooth

import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.library.b.BaseViewHolder

/**
 * @Author ChrisZou
 * @Date 2018/6/1-11:32
 * @Email chrisSpringSmell@gmail.com
 */
class BluetoothCommandListFragment : org.quick.library.b.QuickListFragment<String>() {

    lateinit var parent: BluetoothActivity

    override fun start() {
        parent = activity as BluetoothActivity
    }

    override val isPullRefreshEnable: Boolean
        get() = false

    override val isLoadMoreEnable: Boolean
        get() = false
    override fun onResultAdapter(): QuickAdapter<*, *> = Adapter()

    override fun onResultUrl(): String = ""
    override fun onResultParams(params: MutableMap<String, String>) = Unit

    override fun onRequestSuccess(jsonData: String, isPullRefresh: Boolean) = Unit

    class Adapter : org.quick.library.b.BaseAdapter<String>() {
        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String, viewType: Int) {
            holder.setText(R.id.contentTv, itemData)
        }

        override fun onResultLayoutResId(viewType: Int): Int = R.layout.item_bluetooth
    }
}