package com.example.chriszou.quicksample.ui.main.discover

import android.graphics.Color
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.component.QuickViewHolder
import org.quick.library.b.BaseViewHolder

class DiscoverListFragment : org.quick.library.b.QuickListFragment<String,String>() {


    override fun start() {
        setOnItemClickListener { view, viewHolder, position, itemData ->
            if (activity != null)
                org.quick.library.function.selectorimg.photoandselectorshow.SelectorAndShowActivity.startAction(activity!!, view, getDataList(), position)
        }
        setOnClickListener({ view, viewHolder, position, itemData ->
            when (view.id) {
                R.id.delBtn -> {
                    viewHolder.getButton(R.id.delBtn)?.setBackgroundResource(R.drawable.shape_cir_prim)
                    viewHolder.getTextView(R.id.delBtn)?.setTextColor(Color.WHITE)
                    remove(position)
                }
            }
        }, R.id.delBtn)
/*onRefresh()*/
    }

    override val isPullRefreshEnable: Boolean
        get() = false

    override val isLoadMoreEnable: Boolean
        get() = true

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) {

    }

    override fun onResultLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)

    override fun onLoadMoreSuccess(model: String) {

    }

    override fun onPullRefreshSuccess(model: String) {

    }

    override fun onResultItemResId(viewType: Int): Int = R.layout.item_discover_list

    override fun onBindData(holder: QuickViewHolder, position: Int, itemData: String, viewType: Int) {
        holder.setImg(R.id.contentIv, itemData)
    }

    override fun onResultItemMargin(position: Int): Float = 40f
}