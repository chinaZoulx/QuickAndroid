package com.example.chriszou.quicksample.ui.main.index

import android.os.Handler
import android.support.v7.widget.RecyclerView
import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseListFragment
import org.chris.quick.b.BaseRecyclerViewAdapter
import org.chris.quick.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorActivity

class IndexListFragment : BaseListFragment() {
    override fun onInit() {
        getAdapter<Adapter>().setOnItemClickListener { v, position ->
            if (activity != null)
                PhotoShowAndSelectorActivity.startAction(activity!!, v, getAdapter<Adapter>().dataList, position)
        }
    }

    override fun start() {
        onRefresh()
    }

    override fun onRefresh() {
        Handler().postDelayed({
            val dataList = mutableListOf<String>()
            dataList.add("http://up.enterdesk.com/edpic_source/f9/05/ec/f905ecd469436fb2f2fc0211eca44b3b.jpg")
            dataList.add("http://pic72.nipic.com/file/20150720/20018550_120817338000_2.jpg")
            dataList.add("http://img.taopic.com/uploads/allimg/110901/1720-110Z110394425.jpg")
            dataList.add("http://pic38.nipic.com/20140222/2656254_095504906000_2.jpg")
            dataList.add("http://pic20.nipic.com/20120423/9448607_112237329000_2.jpg")
            dataList.add("http://www.cnhubei.com/xwzt/2015/2015snjlx/snjfj/201509/W020150930775925767248.jpg")
            dataList.add("http://pic39.nipic.com/20140325/6947145_150220631172_2.jpg")
            dataList.add("http://up.enterdesk.com/edpic_source/3d/09/c4/3d09c4f52338fbaa1ea3f6ff6fceeea0.jpg")
            dataList.add("http://pic41.nipic.com/20140602/14680244_170645522110_2.jpg")
            dataList.add("http://pic34.nipic.com/20131028/1175293_134103150121_2.jpg")
            getAdapter<Adapter>().dataList = dataList
            recyclerView.refreshComplete()
        }, 1000)
    }

    override fun onLoadMore() {
        Handler().postDelayed({
            val dataList = mutableListOf<String>()
            dataList.add("http://up.enterdesk.com/edpic_source/bf/f2/5c/bff25c52adda065475059b31c2214228.jpg")
            dataList.add("http://pic46.nipic.com/20140822/19290370_053234839000_2.jpg")
            dataList.add("http://up.enterdesk.com/edpic/bb/12/64/bb1264aae179a8a13d154563ad381d93.jpg")
            dataList.add("http://pic60.nipic.com/file/20150210/20460375_125807393000_2.jpg")
            dataList.add("http://pic.58pic.com/58pic/17/46/22/85958PICdGt_1024.jpg")
            dataList.add("http://file.mumayi.com/forum/201401/16/183515h7dre6zxsiy3hity.jpg")
            dataList.add("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1209/11/c0/13783009_1347330674323.jpg")
            dataList.add("http://up.enterdesk.com/edpic/36/e6/bc/36e6bc8653381fe8f43ac1bd720d3f72.jpg")
            dataList.add("http://p2.so.qhmsg.com/t01ba41296165d80487.jpg")
            dataList.add("http://up.enterdesk.com/edpic_source/28/b0/8e/28b08ee6d59ed9693e0b180ffdcf5e9c.jpg")
            getAdapter<Adapter>().addDataAll(dataList)
            recyclerView.loadMoreComplete()
        }, 1000)
    }

    override fun isPullRefreshEnable(): Boolean = true

    override fun isLoadMoreEnable(): Boolean = true

    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) {

    }

    override fun onRequestDataSuccess(jsonData: String, isPullRefresh: Boolean) {

    }

    class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int = R.layout.item_index_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String?) {
            holder.setImg(R.id.coverIv, itemData)
        }

        override fun onResultItemMargin(): Int = 40
    }
}