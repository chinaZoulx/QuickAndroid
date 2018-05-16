package com.example.chriszou.quicksample.ui.main.discover

import android.graphics.Color
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseListFragment
import org.chris.quick.b.BaseRecyclerViewAdapter
import org.chris.quick.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorActivity

class DiscoverListFragment : BaseListFragment() {
    override fun onInit() {
        getAdapter<Adapter>().setOnItemClickListener { v, position ->
            if (activity != null)
                PhotoShowAndSelectorActivity.startAction(activity!!, v, getAdapter<Adapter>().dataList, position)
        }
        getAdapter<Adapter>().setOnClickListener(BaseRecyclerViewAdapter.OnClickListener { view, holder, _ ->
            when (view.id) {
                R.id.delBtn -> {
                    holder.getButton(R.id.delBtn).setBackgroundResource(R.drawable.shape_cir_prim)
                    holder.getTextView(R.id.delBtn).setTextColor(Color.WHITE)
                    showToast("关注")
                }
            }
        }, R.id.delBtn)
    }

    override fun start() {
/*onRefresh()*/
    }

    override fun isPullRefreshEnable(): Boolean = false

    override fun isLoadMoreEnable(): Boolean = false

    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) {
        params["token"] = ""
    }

    override fun onResultLayoutManager(): RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)

    override fun isShowCuttingLine(): Boolean {
        return super.isShowCuttingLine()
    }

    override fun onResultCuttingLine(): RecyclerView.ItemDecoration {
        return super.onResultCuttingLine()
    }

    override fun onResultPullRefreshIcon(): Int {
        return super.onResultPullRefreshIcon()
    }


    override fun onRequestDataSuccess(jsonData: String, isPullRefresh: Boolean) {
        if (isPullRefresh) {//下拉刷新
            setHasData(false)//没有数据
        } else {//上拉加载
            setNoMore(true)//没有更多
        }
    }

    inner class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int = R.layout.item_discover_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String?) {
            holder.setImg(R.id.contentIv, itemData)
        }

        //----------margin 一般用于CardView
        override fun onResultItemMargin(): Int = 40

        override fun onResultItemMarginLeft(position: Int): Int = when {
            position % 2 == 0 -> 40
            else -> 20
        }

        override fun onResultItemMarginRight(position: Int): Int = when {
            position % 2 != 0 -> 40
            else -> 20
        }

        override fun onResultItemMarginTop(position: Int): Int {
            return super.onResultItemMarginTop(position)
        }

        override fun onResultItemMarginBottom(position: Int): Int {
            return super.onResultItemMarginBottom(position)
        }

        //------------Padding，一般用于常规列表
        override fun onResultItemPadding(): Int {
            return super.onResultItemPadding()
        }

        override fun onResultItemPaddingLeft(position: Int): Int {
            return super.onResultItemPaddingLeft(position)
        }
    }
}