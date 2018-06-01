package com.example.chriszou.quicksample.ui.bluetooth

import android.support.v7.widget.RecyclerView
import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseListFragment
import org.chris.quick.b.BaseRecyclerViewAdapter

/**
 * @Author ChrisZou
 * @Date 2018/6/1-11:32
 * @Email chrisSpringSmell@gmail.com
 */
class BluetoothCommandListFragment : BaseListFragment() {

    lateinit var parent: BluetoothActivity

    override fun onInit() {
        parent = activity as BluetoothActivity
    }

    override fun start() {

    }

    override fun isPullRefreshEnable(): Boolean = false

    override fun isLoadMoreEnable(): Boolean = false

    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultUrl(): String = ""
    override fun onResultParams(params: MutableMap<String, String>) = Unit
    override fun onRequestDataSuccess(jsonData: String, isPullRefresh: Boolean) = Unit

    class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int = R.layout.item_bluetooth

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String) {
            holder.setText(R.id.contentTv, itemData)
        }

        override fun onResultItemMargin(): Int =40
/*        override fun onResultItemPaddingTop(position: Int): Int =20
        override fun onResultItemPaddingBottom(position: Int): Int =20*/
    }
}