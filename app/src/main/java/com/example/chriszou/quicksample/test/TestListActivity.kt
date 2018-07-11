package com.example.chriszou.quicksample.test

import android.support.v7.widget.RecyclerView
import org.chris.quick.b.BaseListActivity
import org.chris.quick.b.BaseRecyclerViewAdapter

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/4-15:02
 * @email chrisSpringSmell@gmail.com
 */
class TestListActivity : BaseListActivity() {

    override fun onResultLayoutResId(): Int {
        return super.onResultLayoutResId()
    }
    /**
     * 流程：先初始化基类本身需要的属性，比如isPullRefreshEnable、isLoadMoreEnable、onResultAdapter、onResultLayoutManager之后，再走到onInit()
     * 此处应当初始化业务逻辑的变量，
     */
    override fun onInit() {
        getAdapter<Adapter>().setOnItemClickListener { v, position ->
            showToast(String.format("点击了第%d项", position))
        }
//        getAdapter<Adapter>().setOnClickListener(BaseRecyclerViewAdapter.OnClickListener{ view, holder, position ->
//
//        },R.id.你的ID1,R.id.你的ID2)
    }

    override fun start() {
        onRefresh()
    }

    /*是否开启下拉刷新*/
    override fun isPullRefreshEnable(): Boolean = true

    /*是否开启上拉加载*/
    override fun isLoadMoreEnable(): Boolean = true

    /*返回适配器*/
    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultLayoutManager(): RecyclerView.LayoutManager {
        return super.onResultLayoutManager()
    }

    override fun onResultUrl(): String = "http://www.baidu.com"

    override fun onResultParams(params: MutableMap<String, String>) {
        params["userName"] = "张三"
    }

    override fun onRequestDataSuccess(jsonData: String?, isPullRefresh: Boolean) {
        if (isPullRefresh) {//下拉刷新

        } else {//上拉加载

        }
    }

    inner class Adapter : BaseRecyclerViewAdapter<String>() {
        override fun onResultLayoutResId(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}