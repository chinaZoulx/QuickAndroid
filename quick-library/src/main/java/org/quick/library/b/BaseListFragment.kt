package org.quick.library.b

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.Size
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.zhy.http.okhttp.callback.StringCallback
import kotlinx.android.synthetic.main.app_base_list.*
import kotlinx.android.synthetic.main.app_include_no_msg.*
import okhttp3.Call
import org.quick.component.callback.OnClickListener2
import org.quick.component.utils.GsonUtils
import org.quick.component.utils.check.CheckUtils
import org.quick.component.widget.QRecyclerView
import org.quick.library.R
import org.quick.library.config.QuickConfigConstant
import org.quick.library.m.HttpManager
import org.quick.library.mvp.BaseModel
import java.util.*

/**
 * Created by work on 2017/8/2.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

abstract class BaseListFragment : BaseFragment(), QRecyclerView.OnRefreshListener {

    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var isDefaultNoMsgLayout = true
    private var tabs: Array<String>? = null
    private val params = HashMap<String, String>()

    var pageNumber = 1
    var onRequestListener: BaseListActivity.OnRequestListener? = null
    var errorType = BaseListActivity.ErrorType.NORMAL
    override val isUsingBaseLayout: Boolean
        get() = true

    open val noMsgLayoutRes: Int
        @LayoutRes
        get() = R.layout.app_include_no_msg

    open val noMsgLayout: View
        get() {
            isDefaultNoMsgLayout = true
            return LayoutInflater.from(activity).inflate(noMsgLayoutRes, null)
        }

    private val isPullRefresh: Boolean
        get() = pageNumber <= 1

    abstract val isPullRefreshEnable: Boolean

    abstract val isLoadMoreEnable: Boolean

    override fun onInit() {

    }

    override fun onInitLayout() {
        recyclerView.setLayoutManager(onResultLayoutManager())
        recyclerView.setAdapter(onResultAdapter())
        recyclerView.setColorSchemeColors(*onResultRefreshColors())
        recyclerView.setRefreshListener(isPullRefreshEnable, isLoadMoreEnable, this)
    }

    override fun onBindListener() {
        if (isDefaultNoMsgLayout)
            refreshBtn.setOnClickListener(object : OnClickListener2() {
                override fun onClick2(view: View) {
                    onRefreshClick(errorType)
                }
            })
    }


    override fun onResultLayoutResId(): Int = R.layout.app_base_list

    fun setupTab(@Size(min =1)vararg tabs: String) {
        setupTab(null, *tabs)
    }

    fun setupTab(onTabSelectedListener: TabLayout.OnTabSelectedListener?, @Size(min =1)vararg tabs: String) {
        setupTab(onTabSelectedListener, 0, *tabs)
    }

    /**
     * 安装顶部TabLayout
     *
     * @param tabs
     */
    fun setupTab(onTabSelectedListener: TabLayout.OnTabSelectedListener?, selectorPosition: Int, @Size(min =1)vararg tabs: String): TabLayout {
        this.tabs = tabs as Array<String>
        tabLayout.visibility = View.VISIBLE
        tabLayout.removeOnTabSelectedListener(this.onTabSelectedListener!!)
        if (onTabSelectedListener != null) {
            this.onTabSelectedListener = onTabSelectedListener
            tabLayout.addOnTabSelectedListener(this.onTabSelectedListener!!)
        }

        if (tabLayout.tabCount > 0) {
            var i = 0
            while (i < tabLayout.tabCount && i < tabs.size) {
                tabLayout.getTabAt(i)!!.text = tabs[i]
                i++
            }
        } else tabs.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        Objects.requireNonNull<TabLayout.Tab>(tabLayout!!.getTabAt(selectorPosition)).select()
        return tabLayout
    }

    fun setupDividerLine(drawable: Drawable, padding: Float = -1f) {
        recyclerView.setupDividerLine(drawable, padding)
    }

    fun setupDividerLine(color: Int, padding: Float = -1f) {
        recyclerView.setupDividerLine(color, padding)
    }

    fun refreshComplete() {
        recyclerView.refreshComplete()
    }

    fun loadMoreComplete() {
        recyclerView.loadMoreComplete()
    }

    fun addFooterView(view: View) {
        footerContainer.addView(view)
    }

    fun addHeadeViewRv(@Size(min =1)vararg views: View) {
        recyclerView.addHeaderView(*views)
    }

    fun addFooterView(@Size(min =1)vararg views: View) {
        recyclerView.addFooterView(*views)
    }

    fun getRecyclerView() = recyclerView

    override fun onRefresh() {
        if (!CheckUtils.isNetWorkAvailable())
            dataHas(false, BaseListActivity.ErrorType.NET_WORK)
        else if (!TextUtils.isEmpty(onResultUrl()))
            requestData(BaseListActivity.PAGER_FIRST_NUMBER)
    }

    override fun onLoading() {
        if (!TextUtils.isEmpty(onResultUrl()))
            requestData(++pageNumber)
    }

    open fun requestData(pageNumber: Int) {
        params.clear()
        onResultParams(params)
        if (!params.containsKey(BaseListActivity.PAGER_NUMBER_KEY)) params[BaseListActivity.PAGER_NUMBER_KEY] = pageNumber.toString()

        HttpManager.Post(activity, onResultUrl(), params, object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                dataHas(false, BaseListActivity.ErrorType.SERVICE)
                onRequestListener?.onError("", isPullRefresh, errorType)
            }

            override fun onResponse(response: String, id: Int) {
                val model = GsonUtils.parseFromJson(response, BaseModel::class.java)
                if (model != null) {
                    if (model.code == QuickConfigConstant.APP_SUCCESS_TAG) {//成功了返回
                        recyclerView.isNoMore = false
                        dataHas(true)
                        onRequestSuccess(response, isPullRefresh)
                    } else {
                        if (isPullRefresh) {//下拉刷新
                            dataHas(false)
                            showToast(model.msg)
                        } else {//上拉加载
                            recyclerView.isNoMore = true
                            dataHas(true)
                        }
                        onRequestListener?.onError(response, isPullRefresh, errorType)
                    }
                } else {
                    onRequestListener?.onError(response, isPullRefresh, errorType)
                    dataHas(false, BaseListActivity.ErrorType.SERVICE)
                }
            }

            override fun onAfter(id: Int) {
                super.onAfter(id)
                if (isPullRefresh && isPullRefreshEnable)
                    recyclerView.refreshComplete()
                else if (isLoadMoreEnable)
                    recyclerView.loadMoreComplete()
                onRequestListener?.onEnd()
            }
        })
    }

    fun dataNoMore(isNoMore: Boolean) {
        recyclerView.isNoMore = isNoMore
    }

    /**
     * 设置是否有数据
     *
     * @param isHasData
     */
    fun dataHas(isHasData: Boolean) {
        dataHas(isHasData, BaseListActivity.ErrorType.NO_MSG)
    }

    /**
     * 设置是否有数据
     *
     * @param isHasData
     */
    @Synchronized
    fun dataHas(isHasData: Boolean, type: BaseListActivity.ErrorType) {
        if (isHasData) {//有
            errorType = BaseListActivity.ErrorType.NORMAL
            if (noMsgContainer.visibility == View.VISIBLE) noMsgContainer.visibility = View.GONE
            if (recyclerView.visibility == View.GONE) recyclerView.visibility = View.VISIBLE
        } else {//无
            if (noMsgContainer.visibility == View.GONE) noMsgContainer.visibility = View.VISIBLE
            if (recyclerView.visibility == View.VISIBLE) recyclerView.visibility = View.GONE
            setHintErrorStyle(type)
        }
    }

    private fun setHintErrorStyle(type: BaseListActivity.ErrorType) {
        this.errorType = type
        if (isDefaultNoMsgLayout) {
            when (type) {
                BaseListActivity.ErrorType.NO_MSG -> {
                    hintErrorIv.setImageResource(onResultErrorNoMsgIcon())
                    hintErrorTv.text = onResultErrorNoMsgTxt()
                    refreshBtn.visibility = View.GONE
                    refreshBtn.text = onResultErrorBtnTxt()
                }
                BaseListActivity.ErrorType.NET_WORK -> {
                    hintErrorIv.setImageResource(onResultErrorNetWorkIcon())
                    hintErrorTv.text = onResultErrorNetWorkTxt()
                    refreshBtn.visibility = View.VISIBLE
                    refreshBtn.text = onResultErrorBtnTxt()
                }
                BaseListActivity.ErrorType.SERVICE -> {
                    hintErrorIv.setImageResource(onResultErrorServiceIcon())
                    hintErrorTv.text = onResultErrorServiceTxt()
                    refreshBtn.visibility = View.VISIBLE
                    refreshBtn.text = onResultErrorBtnTxt()
                }
                BaseListActivity.ErrorType.OTHER -> {
                    hintErrorIv.setImageResource(onResultErrorOtherIcon())
                    hintErrorTv.text = onResultErrorOtherTxt()
                    refreshBtn.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    /**
     * 刷新按钮的点击事件
     */
    fun onRefreshClick(errorType: BaseListActivity.ErrorType) {
        onRefresh()
    }

    fun <T : BaseAdapter<*>> getAdapter(): T? = recyclerView.getAdapter<BaseAdapter<*>>() as T?

    fun setDataList(dataList: MutableList<*>) {
        getAdapter<BaseAdapter<Any>>()?.setDataList(dataList as MutableList<Any>)
    }

    fun addDataList(dataList: MutableList<*>) {
        getAdapter<BaseAdapter<Any>>()?.addDataList(dataList as MutableList<Any>)
    }

    /**
     * 返回网络错误图片
     *
     * @return
     */
    @DrawableRes
    open fun onResultErrorNetWorkIcon(): Int = R.drawable.ic_broken_image_gray_24dp

    open fun onResultErrorNetWorkTxt(): String = getString(R.string.errorNetWorkHint)

    /**
     * 返回没有数据的图片
     *
     * @return
     */
    @DrawableRes
    open fun onResultErrorNoMsgIcon(): Int = R.drawable.ic_broken_image_gray_24dp

    open fun onResultErrorNoMsgTxt(): String = getString(R.string.errorNoMsgHint)

    /**
     * 返回服务器错误的图片
     *
     * @return
     */
    @DrawableRes
    open fun onResultErrorServiceIcon(): Int = R.drawable.ic_broken_image_gray_24dp

    open fun onResultErrorServiceTxt(): String = getString(R.string.errorServiceHint)

    /**
     * 返回服务器错误的图片
     *
     * @return
     */
    @DrawableRes
    open fun onResultErrorOtherIcon(): Int = R.drawable.ic_broken_image_gray_24dp

    open fun onResultErrorOtherTxt(): String = getString(R.string.errorOtherHint)

    /**
     * 网络出错时的文字
     *
     * @return
     */
    open fun onResultErrorBtnTxt(): String = getString(R.string.refresh)

    open fun onResultLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(activity)

    open fun onResultRefreshColors(): IntArray = intArrayOf(Color.BLACK)

    abstract fun onResultAdapter(): RecyclerView.Adapter<*>

    abstract fun onResultUrl(): String

    abstract fun onResultParams(params: MutableMap<String, String>)

    /**
     * 数据请求成功
     *
     * @param jsonData
     * @return
     */
    abstract fun onRequestSuccess(jsonData: String, isPullRefresh: Boolean)
}