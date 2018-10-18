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
import org.quick.library.b.BaseViewHolder
import org.quick.component.utils.ViewUtils

class InputFiltrateListActivity : org.quick.library.b.BaseListActivity() {

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
    override fun start() {
        filtrateEtc.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.keyCode == 66) {
                setResult()
            }
            true
        }
        filtrateTv.setOnClickListener {
            setResult()
        }
        getAdapter<Adapter>().setOnItemClickListener { view, viewHolder, position, itemData ->

        }
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
            subscriber.onNext(org.quick.library.m.DBManager.limit(org.quick.library.widgets.XStickyListHeadersListView.PAGE_ITEM_COUNT).order("id desc").find(FiltrateModel::class.java))
        }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { actionModels ->
            refreshComplete()
            if (actionModels.size > 0) {
                getAdapter<Adapter>().setDataList(actionModels)
                dataHas(true)
                dataNoMore(false)
            } else
                dataHas(false)
        }
    }

    override fun onLoading() {
        Observable.create<MutableList<FiltrateModel>> { subscriber ->
            subscriber.onNext(org.quick.library.m.DBManager.limit(org.quick.library.widgets.XStickyListHeadersListView.PAGE_ITEM_COUNT).offset(pageNumber * org.quick.library.widgets.XStickyListHeadersListView.PAGE_ITEM_COUNT).order("id desc").find(FiltrateModel::class.java))
        }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { actionModels ->
            if (actionModels.size > 0) {
                getAdapter<Adapter>().addDataList(actionModels)
                pageNumber++
            } else dataNoMore(true)
            loadMoreComplete()
        }
    }

    override val isLoadMoreEnable: Boolean
        get() = false

    override val isPullRefreshEnable: Boolean
        get() = false

    override fun onResultLayoutManager(): RecyclerView.LayoutManager = StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL)
    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultUrl(): String = ""

    override fun onResultParams(params: MutableMap<String, String>) = Unit

    override fun onRequestSuccess(jsonData: String, isPullRefresh: Boolean) = Unit

    class Adapter : org.quick.library.b.BaseAdapter<FiltrateModel>() {
        override fun onResultLayoutResId(viewType: Int): Int = R.layout.item_filtrate

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: FiltrateModel, viewType: Int) {
            holder.setText(R.id.filtrateBtn, itemData?.filtrateKey)
        }

        override fun onResultItemMargin(position: Int): Float = 40f
        override fun onResultItemMarginLeft(position: Int): Float = when {
            position % 4 == 0 -> 40f
            else -> 20f
        }

        override fun onResultItemMarginRight(position: Int): Float = when {
            (position + 1) % 4 == 0 -> 40f
            else -> 20f
        }
    }
}