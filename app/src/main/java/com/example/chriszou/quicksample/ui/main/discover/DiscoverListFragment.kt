package com.example.chriszou.quicksample.ui.main.discover

import android.graphics.Color
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.library.b.BaseViewHolder

class DiscoverListFragment : org.quick.library.b.QuickListFragment<String>() {

    override fun start() {
        getAdapter<Adapter>()?.setOnItemClickListener { view, viewHolder, position, itemData ->
            if (activity != null)
                org.quick.library.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorActivity.startAction(activity!!, view, getAdapter<Adapter>()!!.getDataList(), position)
        }
        getAdapter<Adapter>()?.setOnClickListener({ view, viewHolder, position, itemData ->
            when (view.id) {
                R.id.delBtn -> {
                    viewHolder.getButton(R.id.delBtn)?.setBackgroundResource(R.drawable.shape_cir_prim)
                    viewHolder.getTextView(R.id.delBtn)?.setTextColor(Color.WHITE)
                    getAdapter<Adapter>()?.remove(position)
                }
            }
        }, R.id.delBtn)
/*onRefresh()*/
    }

    override val isPullRefreshEnable: Boolean
        get() = false

    override val isLoadMoreEnable: Boolean
        get() = true

    override fun onResultAdapter(): QuickAdapter<*,*> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) {

    }

    override fun onResultLayoutManager(): RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)

    override fun onRequestSuccess(jsonData: String, isPullRefresh: Boolean) {
        if (isPullRefresh) {//下拉刷新
            dataHas(false)//没有数据
        } else {//上拉加载
            dataHas(true)//没有更多
        }
    }

    class Adapter : org.quick.library.b.BaseAdapter<String>() {

        override fun onResultLayoutResId(viewType: Int): Int = R.layout.item_discover_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String, viewType: Int) {
            holder.setImg(R.id.contentIv, itemData)
        }

        //----------margin 一般用于CardView
        override fun onResultItemMargin(position: Int): Float = 40f
//
////        override fun onResultItemMarginLeft(position: Int): Float = when {
////            position % 2 == 0 -> 20f
////            else -> 10f
////        }
////
////        override fun onResultItemMarginRight(position: Int): Float = when {
////            position % 2 != 0 -> 20f
////            else -> 10f
////        }
//
//        override fun onResultItemMarginTop(position: Int): Float {
//            return super.onResultItemMarginTop(position)
//        }
//
//        override fun onResultItemMarginBottom(position: Int): Float {
//            return super.onResultItemMarginBottom(position)
//        }
//
//        //------------Padding，一般用于常规列表
//        override fun onResultItemPadding(position: Int): Float {
//            return super.onResultItemPadding(position)
//        }
//
//        override fun onResultItemPaddingLeft(position: Int): Float {
//            return super.onResultItemPaddingLeft(position)
//        }
    }
}