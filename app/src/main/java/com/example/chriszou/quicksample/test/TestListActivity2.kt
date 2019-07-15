package com.example.chriszou.quicksample.test

import org.quick.library.b.BaseViewHolder

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/4-15:39
 * @email chrisSpringSmell@gmail.com
 */
class TestListActivity2 : org.quick.library.b.QuickListActivity2<String>() {
    override val isPullRefreshEnable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val isLoadMoreEnable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onResultUrl(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultParams(params: Map<String, String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestDataSuccess(model: String, isPullRefresh: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultItemLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResultHeaderLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindHeaderId(itemData: String, position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindDataItemView(holder: BaseViewHolder, itemData: String, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindDataHeaderView(holder: BaseViewHolder, itemData: String, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInitLayout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}