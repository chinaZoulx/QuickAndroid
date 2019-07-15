package com.example.chriszou.quicksample.ui.material

import com.example.chriszou.quicksample.R
import org.quick.component.QuickViewHolder

/**
 * @Author ChrisZou
 * @Date 2018/6/7-14:58
 * @Email chrisSpringSmell@gmail.com
 */
class CommodityBannerFragment : org.quick.library.b.BannerFragment<String>() {
    override val isAutoScroll: Boolean
        get() = true

    override fun onResultItemLayout(viewType: Int): Int = R.layout.item_banner

    override fun onBindData(position: Int, holder: QuickViewHolder, itemData: String) {
        holder.setImg(R.id.img, itemData)
    }

    override fun onInit() {

    }

    override fun start() {
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
        setDataList(dataList)
    }
}