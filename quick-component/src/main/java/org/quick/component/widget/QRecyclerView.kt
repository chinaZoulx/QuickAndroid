package org.quick.component.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.Size
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import org.quick.component.Log2

import org.quick.component.QuickAsync
import org.quick.component.QuickViewHolder
import org.quick.component.R

/**
 * RecyclerView与SwipeRefreshLayout结合的刷新控件
 */
open class QRecyclerView : SwipeRefreshLayout {

    enum class TYPE(var value: Int) {
        TYPE_REFRESH("TYPE_REFRESH".hashCode())
    }

    lateinit var mRecyclerView: RecyclerView
    lateinit var mLoadMoreView: View

    private var isMove = false/*手势是否移动*/
    private var yDown = 0f
    private val loadMoreAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 255f, 0f)
    private var isLoading = false/*是否正在加载*/
    private var mOnRefreshListener: OnRefreshListener? = null

    val mHeaderViews = SparseArray<View>()/*头部*/
    val mFooterViews = SparseArray<View>()/*底部*/
    var isRefreshEnabled = false/*是否上拉刷新*/
    var isLoadMoreEnabled = false/*是否上拉加载*/
    var isNoMore = false/*是否没有更多*/

    private var mAdapter: WarpAdapter? = null
        set(adapter) {
            field = adapter
            mRecyclerView.adapter = WarpAdapter(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        mRecyclerView = RecyclerView(context)
        mRecyclerView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mRecyclerView)
        mLoadMoreView = LayoutInflater.from(context).inflate(R.layout.app_loading_more_view, null)
        mLoadMoreView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setupListener()
        setupAnim()
    }

    open fun setupAnim() {
        loadMoreAnimator.duration = 700
        loadMoreAnimator.interpolator = LinearInterpolator()
        loadMoreAnimator.repeatCount = Animation.INFINITE
        loadMoreAnimator.addUpdateListener {
            mLoadMoreView.findViewById<View>(R.id.animView).scaleX = it.animatedValue.toString().toFloat() / 255
            mLoadMoreView.findViewById<View>(R.id.animView).scaleY = it.animatedValue.toString().toFloat() / 255
            mLoadMoreView.findViewById<View>(R.id.animView).alpha = it.animatedValue.toString().toFloat() / 255
        }
    }

    private fun setupListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, state: Int) {
                checkIsLoading()
            }
        })
        setOnRefreshListener {
            mOnRefreshListener?.onRefresh()
        }
    }

    private fun tryLoading() = when {
        !isLoadMoreEnabled -> false/*设置为不允许加载*/
        isMove -> false/*正在移动*/
        isNoMore -> false/*没有更多*/
        mOnRefreshListener == null -> false/*没有监听*/
        isLoading -> false/*正在加载*/
        isRefreshing -> false/*正在刷新*/
        mRecyclerView.layoutManager.childCount < 0 -> false/*没有数据*/
        else -> true
    }

    private fun checkIsLoading() {
        if (tryLoading()) {
            val layoutManager = mRecyclerView.layoutManager

            val lastVisibleItemPosition = when (layoutManager) {
                is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
                is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
                is StaggeredGridLayoutManager -> {
                    val into = IntArray(layoutManager.spanCount)
                    layoutManager.findLastVisibleItemPositions(into)
                    if (into.isNotEmpty()) into.max()!! else 0
                }
                else -> 0
            }

            if (lastVisibleItemPosition >= layoutManager.itemCount - 1 && layoutManager.itemCount > layoutManager.childCount)
                startLoadMore()
        }
    }

    fun refresh(isRefresh: Boolean) {
        isRefreshing = isRefresh
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                yDown = ev.y
                isMove = true
            }
            MotionEvent.ACTION_MOVE -> isMove = true
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isMove = false
                if (yDown - ev.y >= 10) checkIsLoading()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setRefreshListener(isRefreshEnabled: Boolean, isLoadMoreEnabled: Boolean, onRefreshListener: OnRefreshListener) {
        this.isRefreshEnabled = isRefreshEnabled
        isEnabled = isRefreshEnabled
        this.isLoadMoreEnabled = isLoadMoreEnabled
        this.mOnRefreshListener = onRefreshListener
    }

    fun setupDividerLine(drawable: Drawable, padding: Float = -1f) {
        mRecyclerView.addItemDecoration(DividerItemDecoration(drawable, padding))
    }

    fun setupDividerLine(color: Int, padding: Float = -1f) {
        mRecyclerView.addItemDecoration(DividerItemDecoration(ColorDrawable(color), padding))
    }

    fun startRefresh() {
        isRefreshing = true
        mOnRefreshListener?.onRefresh()
    }

    fun refreshComplete() {
        isRefreshing = false
    }

    fun startLoadMore() {
        isLoading = true
        if (!loadMoreAnimator.isStarted) loadMoreAnimator.start()
        mFooterViews.put(Int.MAX_VALUE, mLoadMoreView)
        if (mAdapter != null) {
            mAdapter?.notifyDataSetChanged()
            mRecyclerView.smoothScrollToPosition(mAdapter!!.itemCount)
        }
        mOnRefreshListener?.onLoading()
    }

    /**
     * 上拉加载完成
     */
    fun loadMoreComplete() {
        loadMoreAnimator.cancel()
        mFooterViews.remove(Int.MAX_VALUE)
        mAdapter?.notifyDataSetChanged()
        QuickAsync.asyncDelay({ isLoading = false }, 1500)
    }


    fun setLayoutManager(layout: RecyclerView.LayoutManager) {
        mRecyclerView.layoutManager = layout
        when {
            mRecyclerView.layoutManager is GridLayoutManager -> (mRecyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter != null) if (mAdapter!!.isHeaderView(position) || mAdapter!!.isFooterView(position)) (mRecyclerView.layoutManager as GridLayoutManager).spanCount else 1
                    else (mRecyclerView.layoutManager as GridLayoutManager).spanCount
                }
            }
        }
    }

    fun <T : RecyclerView.LayoutManager> getLayoutManager() = mRecyclerView.layoutManager as T?

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        this.mAdapter = WarpAdapter(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        mRecyclerView.adapter = mAdapter
    }

    fun <T : RecyclerView.Adapter<*>> getAdapter() = mAdapter?.adapter as T?

    /**
     * 调用notifyItem系列方法时，不能刷新头部与底部
     * 使用示例
     * @param positionStart quickRecyclerView.getLayoutManager<GridLayoutManager>()!!.findFirstVisibleItemPosition()
     * @param positionEnd quickRecyclerView.getLayoutManager<GridLayoutManager>()!!.findLastVisibleItemPosition()
     */
    fun notifyItemRangeChanged(positionStart: Int, positionEnd: Int) {
        if (mAdapter != null) {
            mAdapter?.notifyItemRangeChanged(
                    if (mAdapter!!.isHeaderView(positionStart)) mHeaderViews.size() else positionStart,
                    if (mAdapter!!.isFooterView(positionEnd)) mAdapter!!.adapter.itemCount - 1 else positionEnd
            )
        }
    }

    fun notifyItemChanged(position: Int) {
        if (mAdapter != null)
            mAdapter?.notifyItemChanged(when {
                mAdapter!!.isHeaderView(position) -> mHeaderViews.size()
                mAdapter!!.isFooterView(position) -> mAdapter!!.adapter.itemCount - 1
                else -> position
            })
    }


    /**
     * 添加头部View
     */
    fun addHeaderView(@Size(min = 1) vararg views: View) {
        for (view in views) {
            mHeaderViews.put(mHeaderViews.size() + Int.MAX_VALUE / 100, view)
        }
        mAdapter?.notifyDataSetChanged()
    }

    /**
     * 添加底部View
     */
    fun addFooterView(@Size(min = 1) vararg views: View) {
        for (view in views) {
            mFooterViews.put(mFooterViews.size() + Int.MAX_VALUE / 100, view)
        }
        mAdapter?.notifyDataSetChanged()
    }

    inner class WarpAdapter(val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when {
                mHeaderViews.get(viewType) != null -> QuickViewHolder(mHeaderViews.get(viewType))
                mFooterViews.get(viewType) != null -> QuickViewHolder(mFooterViews.get(viewType))
                else -> adapter.onCreateViewHolder(parent, viewType)
            }
        }

        override fun getItemCount(): Int {
            return mHeaderViews.size() + mFooterViews.size() + adapter.itemCount
        }

        override fun getItemId(position: Int): Long = if (!isHeaderView(position) && !isFooterView(position)) adapter.getItemId(getOriginalPosition(position)) else NO_ID.toLong()

        override fun getItemViewType(position: Int): Int = when {
            isHeaderView(position) -> mHeaderViews.keyAt(position)
            isFooterView(position) -> mFooterViews.keyAt(position - adapter.itemCount - mHeaderViews.size())
            else -> adapter.getItemViewType(getOriginalPosition(position))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (!isHeaderView(position) && !isFooterView(position)) adapter.onBindViewHolder(holder, getOriginalPosition(position))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
            when {
                !isHeaderView(position) && !isFooterView(position) -> adapter.onBindViewHolder(holder, getOriginalPosition(position), payloads)
                else -> super.onBindViewHolder(holder, position, payloads)
            }
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            super.onViewRecycled(holder)
            if (!(isHeaderView(holder.itemView) || isFooterView(holder.itemView))) adapter.onViewRecycled(holder)
        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
            adapter.onAttachedToRecyclerView(recyclerView)
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            super.onDetachedFromRecyclerView(recyclerView)
            adapter.onDetachedFromRecyclerView(recyclerView)
        }

        override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
            super.onViewAttachedToWindow(holder)
            if (!(isHeaderView(holder.itemView) || isFooterView(holder.itemView))) adapter.onViewAttachedToWindow(holder)
            else if (mRecyclerView.layoutManager is StaggeredGridLayoutManager) (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
        }

        override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
            super.onViewDetachedFromWindow(holder)
            if (!(isHeaderView(holder.itemView) || isFooterView(holder.itemView)))
                adapter.onViewDetachedFromWindow(holder)
        }

        override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
            if (isHeaderView(holder.itemView) || isFooterView(holder.itemView)) return super.onFailedToRecycleView(holder)
            return super.onFailedToRecycleView(holder) || adapter.onFailedToRecycleView(holder)
        }

        override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
            super.registerAdapterDataObserver(observer)
            adapter.registerAdapterDataObserver(observer)
        }

        override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
            super.unregisterAdapterDataObserver(observer)
            adapter.unregisterAdapterDataObserver(observer)
        }

        override fun setHasStableIds(hasStableIds: Boolean) {
            super.setHasStableIds(hasStableIds)
            adapter.setHasStableIds(hasStableIds)
        }

        /**
         * 获取实际坐标
         */
        fun getOriginalPosition(position: Int): Int = position - mHeaderViews.size()

        /**
         * 根据flag判断是否是头部
         * * @param flag 下标位置或者是Key
         */
        fun isHeaderView(flag: Int): Boolean = itemCount > adapter.itemCount && flag - mHeaderViews.size() < 0 || mHeaderViews.get(flag) != null

        /**
         * 根据flag判断是否是尾总
         * @param flag 下标位置或者是Key
         */
        fun isFooterView(flag: Int): Boolean = itemCount > adapter.itemCount && flag - mHeaderViews.size() >= adapter.itemCount || mFooterViews.get(flag) != null

        fun isHeaderView(view: View) = mHeaderViews.size() > 0 && mHeaderViews.indexOfValue(view) != -1

        fun isFooterView(view: View) = mFooterViews.size() > 0 && mFooterViews.indexOfValue(view) != -1
    }

    /**
     * @param drawable 分割线样式
     * @param padding 左右或者上下边距，默认分割线与item同等宽度或者高度
     */
    class DividerItemDecoration(var drawable: Drawable, var padding: Float = -1f) : RecyclerView.ItemDecoration() {

        /**
         * 获取分割线的尺寸，也就是每个Item偏移多少
         */
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            if (parent != null) {

                if (parent.layoutManager.canScrollVertically()) {
//                    when{
//                        parent.layoutManager is GridLayoutManager->(parent.layoutManager as StaggeredGridLayoutManager).spanCount
//                    }
                    outRect?.set(0, 0, 0, if (drawable.intrinsicHeight != -1) drawable.intrinsicHeight else 1)
                } else outRect?.set(0, 0, if (drawable.intrinsicWidth != -1) drawable.intrinsicWidth else 1, 0)
            } else super.getItemOffsets(outRect, view, parent, state)
        }

        override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
            if (parent != null && c != null) {
                if (parent.layoutManager.canScrollVertically()) drawableVertically(c, parent)
                else drawableHorizontally(c, parent)
            }
        }

        /**
         * 绘制垂直的
         */
        private fun drawableVertically(c: Canvas, parent: RecyclerView) {
            c.save()
            for (index in 0 until parent.childCount) {
                val realIndex = parent.getChildAdapterPosition(parent.getChildAt(index))
                if (realIndex in if (isReverseLayout(parent.layoutManager)) 1 until parent.adapter.itemCount else 0 until parent.adapter.itemCount - 1) {
                    val bound = Rect()
                    parent.getDecoratedBoundsWithMargins(parent.getChildAt(index), bound)
                    val left: Int = if (padding == -1f) parent.getChildAt(index).left else Math.round(parent.left + padding)
                    val right: Int = if (padding == -1f) parent.getChildAt(index).right else Math.round(parent.right - padding)
                    val top = bound.bottom - if (drawable.intrinsicHeight != -1) drawable.intrinsicHeight else 1
                    val bottom = bound.bottom
                    drawable.setBounds(left, top, right, bottom)
                    drawable.draw(c)
                }
            }
            c.restore()
        }

        /**
         * 绘制水平的
         */
        private fun drawableHorizontally(c: Canvas, parent: RecyclerView) {
            c.save()
            for (index in 0 until parent.childCount) {
                val realIndex = parent.getChildAdapterPosition(parent.getChildAt(index))
                if (realIndex in if (isReverseLayout(parent.layoutManager)) 1 until parent.adapter.itemCount else 0 until parent.adapter.itemCount - 1) {
                    val bound = Rect()
                    parent.getDecoratedBoundsWithMargins(parent.getChildAt(index), bound)
                    val left: Int = bound.right - if (drawable.intrinsicWidth != -1) drawable.intrinsicWidth else 1
                    val right: Int = bound.right
                    val top: Int = if (padding == -1f) parent.getChildAt(index).top else Math.round(parent.top + padding)
                    val bottom: Int = if (padding == -1f) parent.getChildAt(index).bottom else Math.round(parent.bottom - padding)
                    drawable.setBounds(left, top, right, bottom)
                    drawable.draw(c)
                }
            }
            c.restore()
        }
    }

    companion object {
        /**
         * 是否翻转布局
         */
        fun isReverseLayout(layoutManager: RecyclerView.LayoutManager): Boolean = (layoutManager as? StaggeredGridLayoutManager)?.reverseLayout
                ?: (layoutManager as LinearLayoutManager).reverseLayout
    }

    interface OnRefreshListener {
        fun onRefresh()
        fun onLoading()
    }
}