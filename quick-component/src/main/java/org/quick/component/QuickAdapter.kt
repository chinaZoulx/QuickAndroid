package org.quick.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import org.quick.component.utils.ViewUtils

/**
 * Created by chris Zou on 2016/6/12.
 * 基础适配器,继承便能快速生成adapter
 * 如需分割线，请参照quick-library中的XRecyclerViewLine
 * @author chris Zou
 * @Date 2016/6/12
 */
abstract class QuickAdapter<M, H : QuickViewHolder> : RecyclerView.Adapter<H>() {
    private val dataList = mutableListOf<M>()
    lateinit var context: Context

    var mOnItemClickListener: ((view: View, viewHolder: H, position: Int, itemData: M) -> Unit)? = null
    var mOnItemLongClickListener: ((view: View, viewHolder: H, position: Int, itemData: M) -> Boolean)? = null
    var mOnClickListener: ((view: View, viewHolder: H, position: Int, itemData: M) -> Unit)? = null
    var mOnCheckedChangedListener: ((view: View, viewHolder: H, isChecked: Boolean, position: Int, itemData: M) -> Unit)? = null
    var clickResId: IntArray = intArrayOf()
    var checkedChangedResId = intArrayOf()

    /**
     * 布局文件
     *
     * @return
     */
    abstract fun onResultLayoutResId(viewType: Int): Int

    abstract fun onBindData(holder: H, position: Int, itemData: M, viewType: Int)

    override fun getItemCount(): Int = dataList.size

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
        return if (onResultItemMargin(position) > 0) onResultItemMargin(position) / 2 else onResultItemMargin(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginBottom(position: Int): Float {
        return if (onResultItemMargin(position) > 0) onResultItemMargin(position) / 2 else onResultItemMargin(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginLeft(position: Int): Float {
        return onResultItemMargin(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemMarginRight(position: Int): Float {
        return onResultItemMargin(position)
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
        return if (onResultItemPadding(position) > 0) onResultItemPadding(position) / 2 else onResultItemPadding(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingBottom(position: Int): Float {
        return if (onResultItemPadding(position) > 0) onResultItemPadding(position) / 2 else onResultItemPadding(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingLeft(position: Int): Float {
        return onResultItemPadding(position)
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    open fun onResultItemPaddingRight(position: Int): Float {
        return onResultItemPadding(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        context = parent.context
        return setupLayout(LayoutInflater.from(context).inflate(onResultLayoutResId(viewType), parent, false))
    }

    fun setupLayout(itemView: View): H {
//        if (itemView is CardView) {
//            if (itemView.foreground == null)
//                itemView.foreground = ContextCompat.getDrawable(activity, ViewUtils.getSystemAttrTypeValue(activity, R.attr.selectableItemBackgroundBorderless).resourceId)
//        } else {
        if (itemView.background == null)
            itemView.setBackgroundResource(ViewUtils.getSystemAttrTypeValue(context, R.attr.selectableItemBackground).resourceId)
//        }
        return onResultViewHolder(itemView)
    }

    open fun onResultViewHolder(itemView: View): H = QuickViewHolder(itemView) as H

    override fun onBindViewHolder(holder: H, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        setupListener(holder)
        setupLayout(holder, position)
        onBindData(holder, position, getDataList()[position], getItemViewType(position))
    }

    /**
     * 设置各种监听
     *
     * @param holder
     */
    private fun setupListener(holder: H) {
        /*单击事件*/
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(object : org.quick.component.callback.OnClickListener2() {
                override fun onClick2(view: View) {
                    mOnItemClickListener?.invoke(view, holder, holder.adapterPosition, getItem(holder.adapterPosition))
                }
            })
        }
        /*长按事件*/
        if (mOnItemLongClickListener != null) holder.itemView.setOnLongClickListener { v -> mOnItemLongClickListener!!.invoke(v, holder, holder.adapterPosition, getItem(holder.adapterPosition)) }
        /*选择事件*/
        if (mOnCheckedChangedListener != null && checkedChangedResId.isNotEmpty()) {
            for (resId in checkedChangedResId) {
                val compoundButton = holder.getView<View>(resId)
                if (compoundButton is CompoundButton)
                    compoundButton.setOnCheckedChangeListener { buttonView, isChecked -> mOnCheckedChangedListener?.invoke(buttonView, holder, isChecked, holder.adapterPosition, getItem(holder.adapterPosition)) }
                else
                    Log2.e("列表选择事件错误：", String.format("from%s id:%d类型不正确，无法设置OnCheckedChangedListener", context.javaClass.simpleName, resId))
            }
        }
        /*item项内View的独立点击事件，与OnItemClickListner不冲突*/
        if (mOnClickListener != null && clickResId.isNotEmpty()) {
            holder.setOnClickListener(object : org.quick.component.callback.OnClickListener2() {
                override fun onClick2(view: View) {
                    mOnClickListener?.invoke(view, holder, holder.adapterPosition, getItem(holder.adapterPosition))
                }
            }, *clickResId)
        }
    }

    /**
     * 设置布局
     *
     * @param holder
     * @param position
     */
    private fun setupLayout(holder: H, position: Int) {
        if (onResultItemMargin(position) > 0) {
            val left = onResultItemMarginLeft(position).toInt()
            val top = onResultItemMarginTop(position).toInt()
            val right = onResultItemMarginRight(position).toInt()
            var bottom = onResultItemMarginBottom(position).toInt()/2

            if (position == itemCount - 1)
                bottom = onResultItemMarginBottom(position).toInt()
            val itemLayoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            itemLayoutParams.setMargins(left, top, right, bottom)
        }
        if (onResultItemPadding(position) > 0) {
            val left = onResultItemPaddingLeft(position).toInt()
            val top = onResultItemPaddingTop(position).toInt()
            val right = onResultItemPaddingRight(position).toInt()
            var bottom = onResultItemPaddingBottom(position).toInt()/2

            if (position == itemCount - 1)
                bottom = onResultItemPaddingBottom(position).toInt()
            holder.itemView.setPadding(left, top, right, bottom)
        }
    }

    fun setDataList(dataList: MutableList<M>) {
        if (dataList.isNotEmpty()) {
            notifyItemRangeRemoved(0, getDataList().size)
            getDataList().clear()
            getDataList().addAll(dataList)
            notifyItemRangeInserted(0, getDataList().size)
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
        notifyItemRemoved(position)
    }

    open fun remove(m: M) {
        getDataList().remove(m)
        notifyItemRemoved(getDataList().indexOf(m))
    }

    fun removeAll() {
        notifyItemRangeRemoved(0, getDataList().size)
        getDataList().clear()
    }

    fun add(m: M) {
        getDataList().add(m)
        notifyItemInserted(getDataList().size)
    }

    fun getItem(position: Int): M {
        return getDataList()[position]
    }

    fun setOnClickListener(onClickListener: ((view: View, viewHolder: H, position: Int, itemData: M) -> Unit), vararg params: Int) {
        this.clickResId = params
        this.mOnClickListener = onClickListener
    }

    fun setOnCheckedChangedListener(onCheckedChangedListener: ((view: View, viewHolder: H, isChecked: Boolean, position: Int, itemData: M) -> Unit), vararg checkedChangedResId: Int) {
        this.checkedChangedResId = checkedChangedResId
        this.mOnCheckedChangedListener = onCheckedChangedListener
    }

    fun setOnItemClickListener(onItemClickListener: ((view: View, viewHolder: H, position: Int, itemData: M) -> Unit)) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: ((view: View, viewHolder: H, position: Int, itemData: M) -> Boolean)) {
        this.mOnItemLongClickListener = onItemLongClickListener
    }

    /* fun notifyDataSetChanged2() {
         notifyDataSetChanged()
         mDataSetObservable.notifyChanged()
     }

     *//*ListView以及Group兼容*//*

    private val mDataSetObservable = DataSetObservable()
    private var mAutofillOptions: Array<CharSequence>? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: H
        if (convertView == null) {
            holder = onCreateViewHolder(parent!!, getItemViewType(position))
            holder.itemView.tag = holder
        } else {
            holder = convertView.tag as H
        }
        onBindData(holder, position, getItem(position), getItemViewType(position))
        return holder.itemView
    }

    override fun getCount(): Int = itemCount

    override fun registerDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.registerObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.unregisterObserver(observer)
    }

    override fun getAutofillOptions(): Array<CharSequence>? {
        return mAutofillOptions
    }

    fun setAutofillOptions(@Nullable vararg options: CharSequence) {
        mAutofillOptions = options as Array<CharSequence>
    }

    override fun isEnabled(position: Int): Boolean = true

    override fun areAllItemsEnabled(): Boolean = true

    override fun isEmpty(): Boolean = itemCount == 0

    override fun getViewTypeCount(): Int = 1*/
}
