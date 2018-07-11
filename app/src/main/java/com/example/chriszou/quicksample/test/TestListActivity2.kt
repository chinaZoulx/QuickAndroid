package com.example.chriszou.quicksample.test

import org.chris.quick.b.BaseListActivity2
import org.chris.quick.b.BaseRecyclerViewAdapter

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/4-15:39
 * @email chrisSpringSmell@gmail.com
 */
class TestListActivity2 : BaseListActivity2<String>() {
    override fun onInit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPullRefreshEnable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isLoadMoreEnable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultUrl(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultParams(params: MutableMap<String, String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestDataSuccess(jsonData: String?, isPullRefresh: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultItemLayout(): Int {/*返回列表LayoutId*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultHeaderLayout(): Int {/*返回头部LayoutId*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindHeaderId(itemData: String?, position: Int): Long {/*返回用于分类的ID，例如：根据月份分类，应当返回月月份，*/
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*绑定item数据*/
    override fun onBindDataItemView(holder: BaseRecyclerViewAdapter.BaseViewHolder?, itemData: String?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*绑定header数据*/
    override fun onBindDataHeaderView(holder: BaseRecyclerViewAdapter.BaseViewHolder?, itemData: String?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}