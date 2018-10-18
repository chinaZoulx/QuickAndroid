package org.quick.library.widgets

import android.content.Context
import android.support.v7.widget.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.ObjectAnimator
import org.quick.library.R
import org.quick.component.utils.AnimUtils

/**
 * @function
 * @Author ChrisZou
 * @Date 2018/6/8-9:31
 * @Email chrisSpringSmell@gmail.com
 */
class CustomPullRefreshRecyclerView : org.quick.library.widgets.CustomCompatSwipeRefreshLayout {

    private lateinit var mRecyclerView: RecyclerView
    private var mOnRefreshListener: OnRefreshListener? = null

    lateinit var mFootView: View
    lateinit var mFooterAnimView: View
    lateinit var mFooterLoadingTv: TextView

    private var isScrollEnd: Boolean = false//滚动到最后
    private var isLoadingData: Boolean = false//加载更多
    private var isNoMore = false//没有更多
    private var isLoadMoreEnabled: Boolean = false//是否可以加载更多
    private var loadMoreNoMoreHintTxt = "别扯啦，我是有底线的"
    private val loadMoreHintTxt = "使劲加载中"

    private lateinit var contentContainer: LinearLayout

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    private fun init() {
        super.setOnRefreshListener {
            isRefreshing = true
            mOnRefreshListener?.onRefresh()
            mFootView.visibility = View.GONE
        }
        contentContainer = LinearLayout(context)
        contentContainer.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentContainer.orientation = LinearLayout.VERTICAL

        setupRecyclerView()
        setupFooter()
        contentContainer.addView(mRecyclerView)
        contentContainer.addView(mFootView)
        addView(contentContainer)
    }

    private fun setupRecyclerView() {
        mRecyclerView = RecyclerView(context)
        val lp = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0)
        lp.weight = 1f
        mRecyclerView.layoutParams = lp
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, state: Int) {
                super.onScrollStateChanged(recyclerView, state)
                if (state == RecyclerView.SCROLL_STATE_IDLE && mOnRefreshListener != null && !isLoadingData && isLoadMoreEnabled) {
                    val layoutManager = getRecyclerView().layoutManager
                    val lastVisibleItemPosition: Int
                    lastVisibleItemPosition = (layoutManager as? GridLayoutManager)?.findLastVisibleItemPosition() ?: if (layoutManager is StaggeredGridLayoutManager) {
                        val into = IntArray(layoutManager.spanCount)
                        layoutManager.findLastVisibleItemPositions(into)
                        findMax(into)
                    } else {
                        (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    }
                    if (layoutManager.childCount > 0 && lastVisibleItemPosition >= layoutManager.itemCount - 1 && layoutManager.itemCount > layoutManager.childCount && !isNoMore && !isRefreshing) {
                        loadMore()
                    }
                }
            }
        })
    }

    private fun setupFooter() {
        mFootView = LayoutInflater.from(context).inflate(R.layout.app_loading_more_view, null)
        mFootView.visibility = View.GONE
        mFooterAnimView = mFootView.findViewById(R.id.animView)
        mFooterLoadingTv = mFootView.findViewById(R.id.loadTv)
        mFooterLoadingTv.text = loadMoreHintTxt
    }

    private fun findMax(lastPositions: IntArray): Int = if (lastPositions.isNotEmpty()) lastPositions.max()!! else 0

    private fun loadMore() {
        isLoadingData = true
        AnimUtils.fadeIn({}, 300, mFootView)
        AnimUtils.translationY({}, 300, 0f, 0f, mFootView)
        startLoadingAnim()
        mOnRefreshListener?.onLoadMore()
    }

    private fun startLoadingAnim() {
        if (!isLoadingData) return
        val duration = 500
        ObjectAnimator.ofFloat(mFooterAnimView, "alpha", 1f, 0f).setDuration(duration.toLong()).start()
        ObjectAnimator.ofFloat(mFooterAnimView, "scaleY", 0f, 1f).setDuration(duration.toLong()).start()
        val objectAnimatorX = ObjectAnimator.ofFloat(mFooterAnimView, "scaleX", 0f, 1f).setDuration(duration.toLong())
        objectAnimatorX.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) = Unit
            override fun onAnimationEnd(animator: Animator) {
                startLoadingAnim()
            }

            override fun onAnimationCancel(animator: Animator) = Unit
            override fun onAnimationRepeat(animator: Animator) = Unit
        })
        objectAnimatorX.start()
    }

    fun loadMoreComplete() {
        isLoadingData = false
        AnimUtils.fadeOut({
            mFootView.visibility = View.GONE
        }, 1000, mFootView)
//        AnimUtils.translationY({}, 1000, 0f, 30f, mFootView)
    }

    fun refreshComplete() {
        isRefreshing = false
    }

    fun getRecyclerView() = mRecyclerView

    fun addHeaderView(view: View) {
        contentContainer.addView(view, 0)
    }

    fun addFooterView(view: View) {
        contentContainer.addView(view, contentContainer.childCount - 1)
    }

    fun addItemDecoration(itemDecoration: DividerItemDecoration) {
        getRecyclerView().addItemDecoration(itemDecoration)
    }

    fun setRefreshPullEnabled(isEnabled: Boolean) {
        setEnabled(isEnabled)
    }

    fun setLoadMoreEnabled(isEnabled: Boolean) {
        isLoadMoreEnabled = isEnabled
    }

    fun setNoMore(isNoMore: Boolean) {
        this.isNoMore = isNoMore
        mFooterLoadingTv.text = loadMoreNoMoreHintTxt
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        getRecyclerView().adapter = adapter
    }

    fun getAdapter(): RecyclerView.Adapter<*> = getRecyclerView().adapter

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        getRecyclerView().layoutManager = layoutManager
    }

    fun setOnRefreshListener(onRefreshListener: OnRefreshListener) {
        this.mOnRefreshListener = onRefreshListener
    }

    fun setPullRefreshEnabled(isEnabled: Boolean) {
        setEnabled(isEnabled)
    }

    interface OnRefreshListener {
        fun onRefresh()
        fun onLoadMore()
    }
}