package com.example.chriszou.quicksample.ui.search

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.model.FiltrateModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.include_filtrate_toolbar.*
import org.chris.quick.b.BaseListActivity
import org.chris.quick.b.BaseRecyclerViewAdapter
import org.chris.quick.m.DBManager
import org.chris.quick.tools.ViewUtils
import org.chris.quick.widgets.XStickyListHeadersListView

class InputFiltrateListActivity : BaseListActivity() {

    companion object {
        fun startAction(activity: Activity?, requestCode: Int, title: String) {
            val intent = Intent(activity, InputFiltrateListActivity::class.java)
            intent.putExtra(TITLE, title)
            activity?.startActivityForResult(intent, requestCode)
        }
    }

    lateinit var headerView: View
    override fun onResultToolbar(): Toolbar? {
        headerView = ViewUtils.getView(activity, R.layout.include_filtrate_toolbar)
        return headerView.findViewById(R.id.filtrateToolbar)
    }

    override fun onInit() {
        setHeaderContainer(headerView)
        filtrateEtc.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.keyCode == 66) {
                setResult()
            }
            true
        }
        filtrateTv.setOnClickListener {
            setResult()
        }
        getAdapter<Adapter>().setOnItemClickListener { v, position ->

        }
    }

    override fun start() {
        onRefresh()
    }

    private fun setResult() {
        if (filtrateEtc.text.isEmpty()) {
            showToast("请输入内容")
            return
        }
        val intent = Intent()
        intent.putExtra(DATA, filtrateEtc.textStr)
        setResult(Activity.RESULT_OK, intent)
        FiltrateModel(filtrateEtc.textStr, "").save()
        finish()
    }

    override fun onRefresh() {
        pageNumber = 1
        Observable.create<MutableList<FiltrateModel>> { subscriber ->
            subscriber.onNext(DBManager.limit(XStickyListHeadersListView.PAGE_ITEM_COUNT).order("id desc").find(FiltrateModel::class.java))
        }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { actionModels ->
            recyclerView.refreshComplete()
            if (actionModels.size > 0) {
                getAdapter<Adapter>().dataList = actionModels
                setHasData(true)
                recyclerView.setNoMore(false)
            } else
                setHasData(false)
        }
    }

    override fun onLoadMore() {
        Observable.create<MutableList<FiltrateModel>> { subscriber ->
            subscriber.onNext(DBManager.limit(XStickyListHeadersListView.PAGE_ITEM_COUNT).offset(pageNumber * XStickyListHeadersListView.PAGE_ITEM_COUNT).order("id desc").find(FiltrateModel::class.java))
        }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { actionModels ->
            if (actionModels.size > 0) {
                getAdapter<Adapter>().addDataAll(actionModels)
                pageNumber++
            } else recyclerView.setNoMore(true)
            recyclerView.loadMoreComplete()
        }
    }

    override fun isPullRefreshEnable(): Boolean = true

    override fun isLoadMoreEnable(): Boolean = true
    override fun onResultLayoutManager(): RecyclerView.LayoutManager = StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL)
    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>?) = Unit

    override fun onRequestDataSuccess(jsonData: String?, isPullRefresh: Boolean) = Unit

    class Adapter : BaseRecyclerViewAdapter<FiltrateModel>() {
        override fun onResultLayoutResId(): Int = R.layout.item_filtrate

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: FiltrateModel?) {
            holder.setText(R.id.filtrateBtn, itemData?.filtrateKey)
        }

        override fun onResultItemMargin(): Int = 40
        override fun onResultItemMarginLeft(position: Int): Int = when {
            position % 4 == 0 -> 40
            else -> 20
        }

        override fun onResultItemMarginRight(position: Int): Int = when {
            (position + 1) % 4 == 0 -> 40
            else -> 20
        }
    }
}