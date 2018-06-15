package com.example.chriszou.quicksample.ui.material

import android.graphics.Color
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.chriszou.quicksample.R
import com.leochuan.CircleLayoutManager
import kotlinx.android.synthetic.main.fragment_my_center.view.*
import org.chris.quick.b.BaseListFragment
import org.chris.quick.b.BaseRecyclerViewAdapter
import org.chris.quick.b.BaseViewPagerFragment
import org.chris.quick.m.ImageManager

/**
 * @Author ChrisZou
 * @Date 2018/6/7-14:58
 * @Email chrisSpringSmell@gmail.com
 */
class CommodityListFragment : BaseListFragment() {
    override fun onInit() {
        appContentContainer.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun start() {
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
        getAdapter<Adapter>().dataList = dataList
    }

    override fun isPullRefreshEnable(): Boolean = false

    override fun isLoadMoreEnable(): Boolean = false


    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) = Unit

    override fun onRequestDataSuccess(jsonData: String, isPullRefresh: Boolean) = Unit

    class Adapter : BaseRecyclerViewAdapter<Int>() {
        override fun onResultLayoutResId(): Int = R.layout.item_commodity_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: Int?) {
//            holder.setImg(R.id.coverIv, itemData!!)
            holder.getImageView(R.id.coverIv).setImageResource(itemData!!)
        }
    }

}