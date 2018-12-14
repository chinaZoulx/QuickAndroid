package org.quick.library.b

import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.design.widget.TabLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.app_base_list.*

import org.quick.library.R
import org.quick.library.config.QuickConfigConstant
import org.quick.library.mvp.BaseModel
import org.quick.library.widgets.CustomCompatSwipeRefreshLayout
import org.quick.library.widgets.XStickyListHeadersListView
import org.quick.component.utils.GsonUtils
import org.quick.component.utils.check.CheckUtils
import java.util.HashMap
import org.quick.component.http.HttpService
import org.quick.component.http.callback.ClassCallback
import org.quick.component.http.callback.OnRequestListener
import org.quick.library.function.IsOkDialog
import org.quick.library.function.LoadingDialog
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter
import java.io.IOException
import java.util.ArrayList

/**
 * Created by work on 2017/8/2.
 * 带Head的列表
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

abstract class QuickListFragment2<M> : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, XStickyListHeadersListView.OnRefreshListener {

    private var onRequestListener: QuickListActivity.OnRequestListener? = null
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null

    lateinit var noMsgContainer: View
    var hintErrorIv: ImageView? = null
    var hintErrorTv: TextView? = null
    var refreshBtn: Button? = null

    private lateinit var swipeRefreshLayout: CustomCompatSwipeRefreshLayout
    private lateinit var headerContainer: FrameLayout
    private lateinit var footerContainer: FrameLayout
    private lateinit var listContainer: FrameLayout
    private lateinit var tabLayout: TabLayout
    private var tabs: Array<String>? = null

    var stickyListHeadersListView: XStickyListHeadersListView? = null
        private set
    private var adapter: Adapter? = null

    var isDefaultNoMsgLayout = true
    var pageNumber = 1
    var errorType: QuickListActivity.ErrorType = QuickListActivity.ErrorType.NORMAL

    private val params = HashMap<String, String>()

    private val isPullRefresh: Boolean
        get() = pageNumber <= 1

    abstract val isPullRefreshEnable: Boolean

    abstract val isLoadMoreEnable: Boolean

    override fun init() {
        setupLayout()
        super.init()
        setBackValid()
    }

    override fun onResultLayoutResId(): Int {
        return R.layout.app_base_list2
    }

    private fun setupLayout() {
        swipeRefreshLayout = getView(R.id.swipeRefreshLayout)
        listContainer = getView(R.id.listContainer)
        stickyListHeadersListView = getView(R.id.stickyListHeadersListView)
        headerContainer = getView(R.id.headerContainer)
        footerContainer = getView(R.id.footerContainer)
        tabLayout = getView(R.id.tabLayout)

        noMsgContainer = onResultNoMsgLayout()
        listContainer.addView(noMsgContainer)
        noMsgContainer.visibility = View.GONE
        if (isDefaultNoMsgLayout) {
            refreshBtn = getView(R.id.refreshBtn, noMsgContainer)
            hintErrorIv = getView(R.id.hintErrorIv, noMsgContainer)
            hintErrorTv = getView(R.id.hintErrorTv, noMsgContainer)
            refreshBtn!!.setOnClickListener { onRefreshClick(errorType) }
        }

        swipeRefreshLayout.isEnabled = isPullRefreshEnable
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayout.setResolveListView(stickyListHeadersListView!!.wrappedList)
        swipeRefreshLayout.setOnRefreshListener(this)
        stickyListHeadersListView!!.setLoadMore(isLoadMoreEnable)
        stickyListHeadersListView!!.setOnRefreshListener(this)
        stickyListHeadersListView!!.adapter = getAdapter()
    }

    fun setupTab(vararg tabs: String) {
        setupTab(null, *tabs)
    }

    fun setupTab(onTabSelectedListener: TabLayout.OnTabSelectedListener?, vararg tabs: String) {
        setupTab(onTabSelectedListener, 0, *tabs)
    }

    /**
     * 安装顶部TabLayout
     *
     * @param tabs
     */
    fun setupTab(onTabSelectedListener: TabLayout.OnTabSelectedListener?, selectorPosition: Int, vararg tabs: String): TabLayout {
        this.tabs = tabs as Array<String>
        tabLayout.visibility = View.VISIBLE
        if (this.onTabSelectedListener != null) {
            tabLayout.removeOnTabSelectedListener(this.onTabSelectedListener!!)
        }
        if (onTabSelectedListener != null) {
            this.onTabSelectedListener = onTabSelectedListener
            tabLayout.addOnTabSelectedListener(onTabSelectedListener)
        }

        if (tabLayout.tabCount > 0) {
            var i = 0
            while (i < tabLayout.tabCount && i < tabs.size) {
                tabLayout.getTabAt(i)?.text = tabs[i]
                i++
            }
        } else {
            for (i in tabs.indices) {
                tabLayout.addTab(tabLayout.newTab().setText(tabs[i]))
            }
        }
        if (selectorPosition != 0)
            tabLayout.getTabAt(selectorPosition)?.select()
        return tabLayout
    }

    fun setHeaderContainer(view: View) {
        headerContainer.removeAllViews()
        headerContainer.addView(view)
    }

    fun setFooterContainer(view: View) {
        footerContainer.removeAllViews()
        footerContainer.addView(view)
    }

    fun onRefreshClick(errorType: QuickListActivity.ErrorType) {
        onRefresh()
    }

    override fun onRefresh() {
        if (!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = true
        }
        requestData(pageNumber = QuickListActivity.PAGER_FIRST_NUMBER)
    }

    override fun onLoadMore() {
        requestData(++pageNumber)
    }

    fun requestData(pageNumber: Int) {
        if (!CheckUtils.isNetWorkAvailable()) {
            dataHas(false, QuickListActivity.ErrorType.NET_WORK)
            return
        }
        params.clear()
        onResultParams(params)
        checkNotNull()
        if (!params.containsKey(QuickListActivity.PAGER_NUMBER_KEY))
            params[QuickListActivity.PAGER_NUMBER_KEY] = pageNumber.toString() + ""
        HttpService.Builder(onResultUrl()).addParams(params).post(object : org.quick.component.http.callback.OnRequestListener<String>() {

            override fun onFailure(e: IOException, isNetworkError: Boolean) {
                dataHas(false, QuickListActivity.ErrorType.SERVICE)
                onRequestListener?.onError("", isPullRefresh, errorType)
            }

            override fun onResponse(value: String?) {
                val model = GsonUtils.parseFromJson(value, BaseModel::class.java)
                val realModel = GsonUtils.parseFromJson(value, ClassCallback.getTClass(this@QuickListFragment2::class.java))
                if (model != null && realModel != null) {
                    if (model.code == QuickConfigConstant.APP_SUCCESS_TAG) {//成功了返回
                        recyclerView.isNoMore = false
                        dataHas(true)
                        onRequestDataSuccess(realModel as M, isPullRefresh)
                    } else {
                        if (isPullRefresh) {//下拉刷新
                            dataHas(false)
                            showToast(model.msg)
                        } else {//上拉加载
                            recyclerView.isNoMore = true
                            dataHas(true)
                        }
                        onRequestListener?.onError(value!!, isPullRefresh, errorType)
                    }
                } else {
                    onRequestListener?.onError(value!!, isPullRefresh, errorType)
                    dataHas(false, QuickListActivity.ErrorType.SERVICE)
                }
            }

            override fun onEnd() {
                if (isPullRefresh && isPullRefreshEnable && swipeRefreshLayout.isRefreshing)
                    swipeRefreshLayout.isRefreshing = false
                else if (isLoadMoreEnable)
                    stickyListHeadersListView!!.loadMoreConfirm()
                onRequestListener?.onEnd()
                onRequestEnd()
            }
        })
    }

    /**
     * 设置是否有数据
     *
     * @param isHasData
     */
    @JvmOverloads
    fun dataHas(isHasData: Boolean, type: QuickListActivity.ErrorType = QuickListActivity.ErrorType.NO_MSG) {
        if (isHasData) {//有
            setNoMore(false)
            if (noMsgContainer.visibility == View.VISIBLE)
                noMsgContainer.visibility = View.GONE
            if (stickyListHeadersListView!!.visibility == View.GONE)
                stickyListHeadersListView!!.visibility = View.VISIBLE
        } else {//无
            setNoMore(true)
            if (noMsgContainer.visibility == View.GONE)
                noMsgContainer.visibility = View.VISIBLE
            if (stickyListHeadersListView!!.visibility == View.VISIBLE)
                stickyListHeadersListView!!.visibility = View.GONE
            setHintErrorStyle(type)
        }
    }

    private fun setHintErrorStyle(type: QuickListActivity.ErrorType) {
        this.errorType = type
        if (isDefaultNoMsgLayout) {
            when (type) {
                QuickListActivity.ErrorType.NO_MSG -> {
                    hintErrorIv?.setImageResource(onResultErrorNoMsgIcon())
                    hintErrorTv?.text = onResultErrorNoMsgTxt()
                    refreshBtn?.visibility = View.GONE
                    refreshBtn?.text = onResultErrorBtnTxt()
                }
                QuickListActivity.ErrorType.NET_WORK -> {
                    hintErrorIv?.setImageResource(onResultErrorNetWorkIcon())
                    hintErrorTv?.text = onResultErrorNetWorkTxt()
                    refreshBtn?.visibility = View.VISIBLE
                    refreshBtn?.text = onResultErrorBtnTxt()
                }
                QuickListActivity.ErrorType.SERVICE -> {
                    hintErrorIv?.setImageResource(onResultErrorServiceIcon())
                    hintErrorTv?.text = onResultErrorServiceTxt()
                    refreshBtn?.visibility = View.VISIBLE
                    refreshBtn?.text = onResultErrorBtnTxt()
                }
                QuickListActivity.ErrorType.OTHER -> {
                    hintErrorIv?.setImageResource(onResultErrorOtherIcon())
                    hintErrorTv?.text = onResultErrorOtherTxt()
                    refreshBtn?.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    fun setNoMore(isNoMore: Boolean) {
        stickyListHeadersListView!!.setNoMore(isNoMore)
    }

    /**
     * 返回网络错误图片
     *
     * @return
     */
    @DrawableRes
    fun onResultErrorNetWorkIcon(): Int {
        return R.drawable.ic_broken_image_gray_24dp
    }

    fun onResultErrorNetWorkTxt(): String {
        return getString(R.string.errorNetWorkHint)
    }

    /**
     * 返回没有数据的图片
     *
     * @return
     */
    @DrawableRes
    fun onResultErrorNoMsgIcon(): Int {
        return R.drawable.ic_broken_image_gray_24dp
    }

    fun onResultErrorNoMsgTxt(): String {
        return getString(R.string.errorNoMsgHint)
    }

    /**
     * 返回服务器错误的图片
     *
     * @return
     */
    @DrawableRes
    fun onResultErrorServiceIcon(): Int {
        return R.drawable.ic_broken_image_gray_24dp
    }

    fun onResultErrorServiceTxt(): String {
        return getString(R.string.errorServiceHint)
    }

    /**
     * 返回服务器错误的图片
     *
     * @return
     */
    @DrawableRes
    fun onResultErrorOtherIcon(): Int {
        return R.drawable.ic_broken_image_gray_24dp
    }

    fun onResultErrorOtherTxt(): String {
        return getString(R.string.errorOtherHint)
    }

    /**
     * 网络出错时的文字
     *
     * @return
     */
    fun onResultErrorBtnTxt(): String {
        return getString(R.string.refresh)
    }

    @LayoutRes
    fun onResultNoMsgLayoutRes(): Int {
        return R.layout.app_include_no_msg
    }

    fun onResultNoMsgLayout(): View {
        isDefaultNoMsgLayout = true
        return LayoutInflater.from(activity).inflate(onResultNoMsgLayoutRes(), listContainer, false)
    }

    fun checkNotNull() {
        if (!TextUtils.isEmpty(onResultUrl())) {
            val keySet = params.keys
            for (key in keySet) {
                if (TextUtils.isEmpty(key)) {
                    throw NullPointerException("key cannot be empty,please check")
                } else if (params[key] == null) {
                    throw NullPointerException(String.format("The key ： %s value cannot be empty,please check", key))
                }
            }
        } else {
            throw NullPointerException(String.format("url or params cannot be empty,please check"))
        }
    }

    fun getAdapter(): Adapter {
        if (adapter == null)
            adapter = Adapter()
        return adapter!!
    }

    fun onRequestEnd() {}

    fun onRequestError() {}

    fun setOnRequestListener(onRequestListener: QuickListActivity.OnRequestListener) {
        this.onRequestListener = onRequestListener
    }

    abstract fun onResultUrl(): String

    abstract fun onResultParams(params: Map<String, String>)

    abstract fun onRequestDataSuccess(model: M, isPullRefresh: Boolean)

    @LayoutRes
    abstract fun onResultItemLayout(): Int

    @LayoutRes
    abstract fun onResultHeaderLayout(): Int

    abstract fun onBindHeaderId(itemData: M, position: Int): Long

    abstract fun onBindDataItemView(holder: BaseViewHolder, itemData: M, position: Int)

    abstract fun onBindDataHeaderView(holder: BaseViewHolder, itemData: M, position: Int)

    inner class Adapter : BaseAdapter(), StickyListHeadersAdapter {
        private var dataList: MutableList<M>? = ArrayList()

        override fun getCount(): Int {
            return if (dataList == null) 0 else dataList!!.size
        }

        override fun getItem(position: Int): M {
            return dataList!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val holder: BaseViewHolder
            if (convertView == null) {
                holder = BaseViewHolder(LayoutInflater.from(parent.context).inflate(onResultItemLayout(), parent, false))
                holder.itemView.tag = holder
            } else
                holder = convertView.tag as BaseViewHolder
            onBindDataItemView(holder, getItem(position), position)
            return holder.itemView
        }

        override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup): View {
            val holder: BaseViewHolder
            if (convertView == null) {
                holder = BaseViewHolder(LayoutInflater.from(parent.context).inflate(onResultHeaderLayout(), parent, false))
                holder.itemView.tag = holder
            } else
                holder = convertView.tag as BaseViewHolder
            onBindDataHeaderView(holder, getItem(position), position)
            return holder.itemView
        }

        override fun getHeaderId(position: Int): Long {
            return onBindHeaderId(getItem(position), position)
        }

        fun setDataList(dataList: MutableList<M>) {
            this.dataList = dataList
            notifyDataSetChanged()
        }

        fun getDataList(): List<M>? {
            return dataList
        }

        fun addDataList(dataList: List<M>) {
            this.dataList!!.addAll(dataList)
            notifyDataSetChanged()
        }
    }
}