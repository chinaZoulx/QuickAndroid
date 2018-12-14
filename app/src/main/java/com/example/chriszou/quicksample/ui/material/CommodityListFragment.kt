package com.example.chriszou.quicksample.ui.material

import android.graphics.Color
import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.library.b.BaseViewHolder

/**
 * @Author ChrisZou
 * @Date 2018/6/7-14:58
 * @Email chrisSpringSmell@gmail.com
 */
class CommodityListFragment : org.quick.library.b.QuickListFragment<Int>() {

    override fun start() {
        appContentContainer.setBackgroundColor(Color.TRANSPARENT)
        val dataList = mutableListOf<Int>()
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        dataList.add(R.drawable.ic_ring)
        getAdapter<Adapter>()?.setDataList(dataList)
    }

    override val isPullRefreshEnable: Boolean
        get() = false

    override val isLoadMoreEnable: Boolean
        get() = false


    override fun onResultAdapter(): QuickAdapter<*, *> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) = Unit

    override fun onRequestSuccess(jsonData: Int, isPullRefresh: Boolean) = Unit

    class Adapter : org.quick.library.b.BaseAdapter<Int>() {
        override fun onResultLayoutResId(viewType: Int): Int =R.layout.item_commodity_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: Int, viewType: Int) {
            holder.getImageView(R.id.coverIv)?.setImageResource(itemData)
        }
    }

}