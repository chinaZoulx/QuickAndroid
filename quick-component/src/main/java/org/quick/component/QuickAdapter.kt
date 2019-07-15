package org.quick.component

import android.content.Context
import android.os.Build
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.CallSuper
import androidx.annotation.Size
import androidx.core.content.ContextCompat
import org.quick.component.utils.ViewUtils

/**
 * Created by chris Zou on 2016/6/12.
 * 基础适配器,继承便能快速生成adapter
 * 如需分割线，请参照quick-library中的XRecyclerViewLine
 * @author chris Zou
 * @Date 2016/6/12
 */
abstract class QuickAdapter<M> : androidx.recyclerview.widget.RecyclerView.Adapter<QuickViewHolder>() {
    lateinit var context: Context
    var parent: androidx.recyclerview.widget.RecyclerView? = null

    private val dataList = mutableListOf<M>()

    val mHeaderViews = SparseArray<View>()/*头部*/
    val mFooterViews = SparseArray<View>()/*底部*/

    var mOnItemClickListener: ((view: View, viewHolder: QuickViewHolder, position: Int, itemData: M) -> Unit)? = null
    var mOnItemLongClickListener: ((view: View, viewHolder: QuickViewHolder, position: Int, itemData: M) -> Boolean)? =
        null
    var mOnClickListener: ((view: View, viewHolder: QuickViewHolder, position: Int, itemData: M) -> Unit)? = null
    var mOnCheckedChangedListener: ((view: View, viewHolder: QuickViewHolder, isChecked: Boolean, position: Int, itemData: M) -> Unit)? =
        null
    var clickResId: IntArray = intArrayOf()
    var checkedChangedResId = intArrayOf()

    /**
     * 布局文件
     *
     * @return
     */
    abstract fun onResultItemResId(viewType: Int): Int

    abstract fun onBindData(holder: QuickViewHolder, position: Int, itemData: M, viewType: Int)

    override fun getItemCount(): Int = mHeaderViews.size() + mFooterViews.size() + dataList.size

    @CallSuper
    override fun getItemViewType(position: Int): Int = when {
        isHeaderView(position) -> mHeaderViews.keyAt(position)
        isFooterView(position) -> mFooterViews.keyAt(position - dataList.size - mHeaderViews.size())
        else -> -1
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMargin(position: Int): Float {
        return 0.0f
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginTop(position: Int): Float {
        return if (isVertically()) {
            when (position) {
                0 -> {
                    onResultItemMargin(position)
                }
                else -> onResultItemMargin(position) / 2
            }
        } else onResultItemMargin(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginBottom(position: Int): Float {
        return if (isVertically()) {
            when (position) {
                itemCount - 1 -> {
                    onResultItemMargin(position)
                }
                else -> onResultItemMargin(position) / 2
            }
        } else onResultItemMargin(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginLeft(position: Int): Float {
        return if (!isVertically()) {
            when (position) {
                0 -> {
                    onResultItemMargin(position)
                }
                else -> onResultItemMargin(position) / 2
            }
        } else onResultItemMargin(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginRight(position: Int): Float {
        return if (!isVertically()) {
            when (position) {
                itemCount - 1 -> {
                    onResultItemMargin(position)
                }
                else -> onResultItemMargin(position) / 2
            }
        } else onResultItemMargin(position)
    }

    open fun onResultItemPadding(position: Int): Float {
        return 0.0f
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingTop(position: Int): Float {
        return if (isVertically()) {
            when (position) {
                0 -> {
                    onResultItemPadding(position)
                }
                else -> onResultItemPadding(position) / 2
            }
        } else onResultItemPadding(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingBottom(position: Int): Float {
        return if (isVertically()) {
            when (position) {
                itemCount - 1 -> {
                    onResultItemPadding(position)
                }
                else -> onResultItemPadding(position) / 2
            }
        } else onResultItemPadding(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingLeft(position: Int): Float {
        return if (!isVertically()) {
            when (position) {
                0 -> {
                    onResultItemPadding(position)
                }
                else -> onResultItemPadding(position) / 2
            }
        } else onResultItemPadding(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingRight(position: Int): Float {
        return if (!isVertically()) {
            when (position) {
                itemCount - 1 -> {
                    onResultItemPadding(position)
                }
                else -> onResultItemPadding(position) / 2
            }
        } else onResultItemPadding(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickViewHolder {
        context = parent.context
        return when {
            mHeaderViews.get(viewType) != null -> QuickViewHolder(mHeaderViews.get(viewType))
            mFooterViews.get(viewType) != null -> QuickViewHolder(mFooterViews.get(viewType))
            else -> setupLayout(LayoutInflater.from(context).inflate(onResultItemResId(viewType), parent, false))
        }
    }

    open fun setupLayout(itemView: View): QuickViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (itemView.foreground == null)
                itemView.foreground = ContextCompat.getDrawable(
                    context,
                    ViewUtils.getSystemAttrTypeValue(context, R.attr.selectableItemBackground).resourceId
                )
        } else if (itemView.background == null) {
            itemView.setBackgroundResource(
                ViewUtils.getSystemAttrTypeValue(
                    context,
                    R.attr.selectableItemBackground
                ).resourceId
            )
        }
        return onResultViewHolder(itemView)
    }

    open fun onResultViewHolder(itemView: View): QuickViewHolder = QuickViewHolder(itemView)

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int) {
        if (!(isHeaderView(position) || isFooterView(position))) {
            val realPosition = getOriginalPosition(position)
            setupListener(holder)
            setupLayout(holder, realPosition)
            onBindData(holder, realPosition, getDataList()[realPosition], getItemViewType(realPosition))
        }
    }

    /**
     * 设置各种监听
     *
     * @paramholder
     */
    private fun setupListener(holder: QuickViewHolder) {
        /*单击事件*/
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(object : org.quick.component.callback.OnClickListener2() {
                override fun onClick2(view: View) {
                    val dataIndex = getOriginalPosition(holder.adapterPosition)
                    mOnItemClickListener?.invoke(view, holder, dataIndex, getItem(dataIndex))
                }
            })
        }
        /*长按事件*/
        if (mOnItemLongClickListener != null) holder.itemView.setOnLongClickListener { v ->
            val dataIndex = getOriginalPosition(holder.adapterPosition)
            mOnItemLongClickListener!!.invoke(v, holder, dataIndex, getItem(dataIndex))
        }
        /*选择事件*/
        if (mOnCheckedChangedListener != null && checkedChangedResId.isNotEmpty()) {
            for (resId in checkedChangedResId) {
                val compoundButton = holder.getView<View>(resId)
                if (compoundButton is CompoundButton)
                    compoundButton.setOnCheckedChangeListener { buttonView, isChecked ->
                        val dataIndex = getOriginalPosition(holder.adapterPosition)
                        mOnCheckedChangedListener?.invoke(buttonView, holder, isChecked, dataIndex, getItem(dataIndex))
                    }
                else
                    Log2.e(
                        "列表选择事件错误：",
                        String.format(
                            "from%s id:%d类型不正确，无法设置OnCheckedChangedListener",
                            context.javaClass.simpleName,
                            resId
                        )
                    )
            }
        }
        /*item项内View的独立点击事件，与OnItemClickListner不冲突*/
        if (mOnClickListener != null && clickResId.isNotEmpty()) {
            holder.setOnClickListener({ view, viewHolder ->
                val dataIndex = getOriginalPosition(holder.adapterPosition)
                mOnClickListener?.invoke(view, holder, dataIndex, getItem(dataIndex))
            }, *clickResId)
        }
    }

    /**
     * 设置布局
     *
     * @paramholder
     * @param position
     */
    private fun setupLayout(holder: QuickViewHolder, position: Int) {
        if (onResultItemMargin(position) > 0) {
            val left = onResultItemMarginLeft(position).toInt()
            val top = onResultItemMarginTop(position).toInt()
            val right = onResultItemMarginRight(position).toInt()
            val bottom = onResultItemMarginBottom(position).toInt()

            val itemLayoutParams =
                holder.itemView.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams
            itemLayoutParams.setMargins(left, top, right, bottom)
        }
        if (onResultItemPadding(position) > 0) {
            val left = onResultItemPaddingLeft(position).toInt()
            val top = onResultItemPaddingTop(position).toInt()
            val right = onResultItemPaddingRight(position).toInt()
            val bottom = onResultItemPaddingBottom(position).toInt()


            holder.itemView.setPadding(left, top, right, bottom)
        }
    }

    fun setDataList(dataList: MutableList<M>) {
        if (dataList.isNotEmpty()) {
            removeAll()
            addDataList(dataList)
        }
    }

    fun getDataList(): MutableList<M> {
        return dataList
    }

    fun addDataList(dataList: MutableList<M>) {
        if (dataList.isNotEmpty()) {
            val lastSize = getDataList().size
            getDataList().addAll(dataList)
            notifyItemRangeInserted(lastSize, getDataList().size)
        }
    }

    open fun remove(position: Int) {
        getDataList().removeAt(position)
        notifyItemRemoved(position + mHeaderViews.size())
    }

    open fun remove(m: M) {
        val lastPosition = getDataList().indexOf(m)
        getDataList().remove(m)
        notifyItemRemoved(lastPosition + mHeaderViews.size())
    }

    fun removeAll() {
        notifyItemRangeRemoved(mHeaderViews.size(), getDataList().size)
        getDataList().clear()
    }

    fun addData(m: M) {
        getDataList().add(m)
        notifyItemInserted(getLastPosition())
    }

    fun getItem(position: Int): M {
        return getDataList()[position]
    }

    fun getLastItem(): M = getItem(getLastPosition())

    fun getLastPosition(): Int = getDataList().size - 1

    fun setOnClickListener(
        onClickListener: ((view: View, viewHolder: QuickViewHolder, position: Int, itemData: M) -> Unit), @Size(
            min = 1
        ) vararg resId: Int
    ) {
        this.clickResId = resId
        this.mOnClickListener = onClickListener
    }

    fun setOnCheckedChangedListener(
        onCheckedChangedListener: ((view: View, viewHolder: QuickViewHolder, isChecked: Boolean, position: Int, itemData: M) -> Unit), @Size(
            min = 1
        ) vararg resId: Int
    ) {
        this.checkedChangedResId = resId
        this.mOnCheckedChangedListener = onCheckedChangedListener
    }

    fun setOnItemClickListener(onItemClickListener: ((view: View, viewHolder: QuickViewHolder, position: Int, itemData: M) -> Unit)) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: ((view: View, viewHolder: QuickViewHolder, position: Int, itemData: M) -> Boolean)) {
        this.mOnItemLongClickListener = onItemLongClickListener
    }

    /*head footer相关*/

    /**
     * 添加头部View
     */
    fun addHeaderView(@Size(min = 1) vararg views: View) {
        for (view in views) {
            mHeaderViews.put(mHeaderViews.size() + Int.MAX_VALUE / 100, view)
        }
        notifyItemRangeInserted(mHeaderViews.size() - views.size, mHeaderViews.size())
    }

    /**
     * 添加底部View
     */
    fun addFooterView(@Size(min = 1) vararg views: View) {
        for (view in views) {
            mFooterViews.put(mFooterViews.size() + Int.MAX_VALUE / 100, view)
        }
        notifyItemRangeInserted(itemCount - views.size, itemCount)
    }

    fun removeHeaderView(view: View) {
        val index = mHeaderViews.indexOfValue(view)
        if (index != -1) {
            mHeaderViews.remove(mHeaderViews.keyAt(index))
            notifyItemRemoved(index)
        }
    }

    fun removeFooterView(view: View) {
        val index = mFooterViews.indexOfValue(view)
        if (index != -1) {
            mFooterViews.remove(mFooterViews.keyAt(index))
            notifyItemRemoved(index + mHeaderViews.size() + getDataList().size)
        }
    }

    /**
     * 获取实际坐标
     */
    private fun getOriginalPosition(position: Int): Int = position - mHeaderViews.size()

    /**
     * 根据flag判断是否是头部
     * * @param flag 下标位置或者是Key
     */
    fun isHeaderView(flag: Int): Boolean =
        itemCount > dataList.size && flag - mHeaderViews.size() < 0 || mHeaderViews.get(flag) != null

    /**
     * 根据flag判断是否是尾总
     * @param flag 下标位置或者是Key
     */
    fun isFooterView(flag: Int): Boolean =
        itemCount > dataList.size && flag - mHeaderViews.size() >= dataList.size || mFooterViews.get(flag) != null

    fun isHeaderView(view: View) = mHeaderViews.size() > 0 && mHeaderViews.indexOfValue(view) != -1

    fun isFooterView(view: View) = mFooterViews.size() > 0 && mFooterViews.indexOfValue(view) != -1

    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        parent = recyclerView
        when {
            parent!!.layoutManager is androidx.recyclerview.widget.GridLayoutManager -> (parent!!.layoutManager as androidx.recyclerview.widget.GridLayoutManager).spanSizeLookup =
                object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (isHeaderView(position) || isFooterView(position)) (parent!!.layoutManager as androidx.recyclerview.widget.GridLayoutManager).spanCount else 1
                    }
                }
        }
    }

    override fun onViewAttachedToWindow(holder: QuickViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (parent?.layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager && (isHeaderView(holder.itemView) || isFooterView(
                holder.itemView
            ))
        ) (holder.itemView.layoutParams as androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams).isFullSpan =
            true
    }

    /**
     * 是否垂直滚动
     */
    fun isVertically(): Boolean {
        return if (parent != null) {
            parent!!.layoutManager!!.canScrollVertically()
        } else true
    }
}
