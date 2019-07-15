package com.example.chriszou.quicksample.ui.material

import android.graphics.Color
import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.component.QuickViewHolder
import org.quick.library.b.BaseViewHolder

/**
 * @Author ChrisZou
 * @Date 2018/6/7-14:58
 * @Email chrisSpringSmell@gmail.com
 */
class CommodityListFragment : org.quick.library.b.QuickListFragment<Int,Int>() {
    override fun onResultItemResId(viewType: Int): Int =R.layout.item_commodity_list

    override fun onBindData(holder: QuickViewHolder, position: Int, itemData: Int, viewType: Int) {
        holder.getImageView(R.id.coverIv)?.setImageResource(itemData)
    }

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
        setDataList(dataList)
    }

    override val isPullRefreshEnable: Boolean
        get() = false

    override val isLoadMoreEnable: Boolean
        get() = false

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) = Unit

    override fun onPullRefreshSuccess(model: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMoreSuccess(model: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}