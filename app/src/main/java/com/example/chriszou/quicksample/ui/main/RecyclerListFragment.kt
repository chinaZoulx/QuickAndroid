package com.example.chriszou.quicksample.ui.main

import com.example.chriszou.quicksample.ui.main.discover.DiscoverListFragment
import org.quick.component.QuickAdapter
import org.quick.component.QuickAsync
import org.quick.component.QuickToast
import org.quick.library.b.QuickListFragment

class RecyclerListFragment : QuickListFragment<String>() {
    override val isPullRefreshEnable: Boolean
        get() = true
    override val isLoadMoreEnable: Boolean
        get() = true

    override fun onResultAdapter(): QuickAdapter<*,*> = DiscoverListFragment.Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) {

    }

    override fun onRequestSuccess(jsonData: String, isPullRefresh: Boolean) {

    }

    override fun start() {

        onRefresh()
    }

    override fun onRefresh() {
        QuickToast.showToastDefault("开始刷新")
        QuickAsync.asyncDelay({
            val tempDataList = mutableListOf<String>()
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/ce/f4/a5/cef4a5cd12d4dbdc29f85bc4631c5c35.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/73/bd/10/73bd109d7301546b19dab0e2de593ecb.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/f5/9b/62/f59b627e3aaaa494ca8f248a81a861df.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/73/84/a8/7384a8726b81802b441e416f8c5fb578.jpg")
            tempDataList.add("错误链接")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/57/3e/4d/573e4d966410f05e89e399c9e7f50ff7.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/72/cb/f8/72cbf87a181df269b95d25c652cd16b3.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/49/84/2e/49842ec241511e2949a0b49111025997.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/9a/1b/82/9a1b823144f817d0014f52a467bd9e5d.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/93/28/be/9328bea7d0ce1783546e39978572fca3.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/28/69/2c/28692c85fe23a25f7109ceeb45b7ae82.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/29/db/6e/29db6e8f5a9c87438953284940762877.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/67/ef/01/67ef015cda467054c50f46f6325a213b.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/81/5b/bd/815bbd993860f47a8bff166445adc85d.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/40/1d/82/401d82455e2d2d83b0e891301b57d55a.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/ef/99/7d/ef997d7b1ab4774c2f6795cabb0d6996.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/e8/6c/1b/e86c1b74ec5d3cd9e32bb833c42bd920.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/e6/da/9f/e6da9f74915963c84ee7d5b0fa666c9f.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/06/1c/3a/061c3ac42f0ab4f699612057926d330b.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/24/9d/b9/249db9853430f0954e53ecb3f29d858d.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/c5/22/c4/c522c49496a315e7408439902887dbc4.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/33/52/95/335295727ee98f2158c3a810ca4e1d2f.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/49/54/af/4954af624f05e1e01cb93272904fddda.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/f4/f8/4f/f4f84ff78e2b01b2a466b4b721c81114.jpg")
            setDataList(tempDataList)
            refreshComplete()
        }, 2000)
    }


    override fun onLoading() {
        QuickToast.showToastDefault("开始加载")
        QuickAsync.asyncDelay({
            loadMoreComplete()
            val tempDataList = mutableListOf<String>()
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/ce/f4/a5/cef4a5cd12d4dbdc29f85bc4631c5c35.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/73/bd/10/73bd109d7301546b19dab0e2de593ecb.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/f5/9b/62/f59b627e3aaaa494ca8f248a81a861df.jpg")
            tempDataList.add("https://up.enterdesk.com/edpic_360_360/73/84/a8/7384a8726b81802b441e416f8c5fb578.jpg")
            tempDataList.add("错误链接")
            addDataList(tempDataList)
        }, 3000)
    }
}