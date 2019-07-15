package org.quick.library.b

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.graphics.PointF
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import org.quick.component.QuickViewHolder
import org.quick.component.utils.CommonUtils
import org.quick.component.utils.FormatUtils
import org.quick.library.R
import org.quick.library.widgets.CompatViewPager

/**
 * Created by work on 2017/9/6.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

abstract class BannerFragment<M> : BaseFragment() {

    private var onTouchListener: OnTouchListener? = null
    private var onPageChangeListener: ViewPager.OnPageChangeListener
    private var onIndicatorListener: ((position: Int, totalCount: Int, itemData: M) -> Unit)? = null
    lateinit var dotsContainer: LinearLayout
    lateinit var tabLayout: TabLayout
    var dataSetObserver: DataSetObserver

    lateinit var viewPager: CompatViewPager
    private lateinit var mAdapter: PagerAdapter

    var tabs: Array<String>? = null
        private set
    internal var dataList = mutableListOf<M>()

    lateinit var lastView: View
    private var viewPagerScrollTime = -1

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var isPullRefresh: Boolean = false

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                AUTO_SCROLL_WHAT -> if (activity != null) {
                    viewPager!!.currentItem = lastSelectItemPosition
                    startAutoScroll()
                }
            }
        }
    }

    init {
        onPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                val tempPosition = position % dataList.size
                setScrollTimeType(if (isAutoScroll) onResultScrollChangeTimeFocus() else onResultScrollChangeTimeNormal())
                if (isShowHintDots && adapter!!.count > 1) {
                    lastView.setBackgroundResource(onResultIndicatorDotBgNormal())
                    if (dotsContainer.visibility != View.VISIBLE)
                        dotsContainer.visibility = View.VISIBLE
                    dotsContainer.getChildAt(tempPosition).setBackgroundResource(onResultIndicatorDotBgFocus())
                    lastView = dotsContainer.getChildAt(tempPosition)
                }
                onIndicatorListener?.invoke(tempPosition,realCount,getItem(tempPosition))
            }

            override fun onPageScrollStateChanged(state: Int) {
                //                switch (state) {
                //                    case ViewPager.SCROLL_STATE_IDLE://停止了
                //
                //                        break;
                //                    case ViewPager.SCROLL_STATE_DRAGGING://拖动中
                //
                //                        break;
                //                    case ViewPager.SCROLL_STATE_SETTLING://滑动停止了
                //                        setScrollTimeType(onResultScrollChangeTimeFocus());
                //                        break;
                //                }
            }
        }
        dataSetObserver = object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                setupDots()
                startAutoScroll()
            }
        }
//        onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                setScrollTimeType(onResultScrollChangeTimeNormal())
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        }
    }

    /**
     * 是否显示提示点
     *
     * @return
     */
    val isShowHintDots: Boolean
        get() = false

    val adapter: PagerAdapter?
        @CallSuper
        get() = viewPager.adapter

    private val lastSelectItemPosition: Int
        get() {
            var tempPosition = viewPager.currentItem
            if (isAutoScroll) {
                when {
                    tempPosition >= adapter!!.count - 1 -> tempPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE /
                            2 % dataList.size
                    tempPosition == 0 -> tempPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 %
                            dataList.size + 1
                    else -> tempPosition++
                }
            } else {
                if (tempPosition >= adapter!!.count - 1) {
                    tempPosition = 0
                } else
                    tempPosition++
            }
            return tempPosition
        }

    val realCount: Int
        get() = getDataList().size

    open val isAutoScroll: Boolean = false

    val isResolveSwipeRefreshLayout: Boolean
        get() = swipeRefreshLayout != null

    override fun onResultLayoutResId(): Int {
        return R.layout.app_base_view_pager
    }

    override fun onInit() {

    }

    override fun onInitLayout() {
        viewPager = getView(R.id.customCompatViewPager)
        tabLayout = getView(R.id.tabLayout)
        dotsContainer = getView(R.id.dotsContainer)

        mAdapter = onResultAdapter()
        viewPager.adapter = mAdapter
        val transformer = onResultPageTransformer()
        if (transformer != null) viewPager.setPageTransformer(true, transformer)
        setScrollTimeType(if (isAutoScroll) onResultScrollChangeTimeFocus() else onResultScrollChangeTimeNormal())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindListener() {
        viewPager.addOnPageChangeListener(onPageChangeListener)
        adapter!!.registerDataSetObserver(dataSetObserver)

        viewPager.setOnTouchListener(object : View.OnTouchListener {
            var downPoint = PointF()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (onTouchListener != null) {
                    onTouchListener!!.onTouch(v, event)
                }
                if (isAutoScroll)
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            downPoint.x = event.x
                            downPoint.y = event.y
                            stopAutoScroll()
                            if (isResolveSwipeRefreshLayout)
                                swipeRefreshLayout!!.isEnabled = false
                            setScrollTimeType(onResultScrollChangeTimeNormal())
                            stopAutoScroll()
                            if (isResolveSwipeRefreshLayout && Math.abs(event.x - downPoint.x) < Math.abs(event.y - downPoint.y))
                            //Y轴距离大于X轴
                                if (isPullRefresh)
                                    swipeRefreshLayout!!.isEnabled = true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            setScrollTimeType(onResultScrollChangeTimeNormal())
                            stopAutoScroll()
                            if (isResolveSwipeRefreshLayout && Math.abs(event.x - downPoint.x) < Math.abs(event.y - downPoint.y))
                                if (isPullRefresh)
                                    swipeRefreshLayout!!.isEnabled = true
                        }
                        MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                            startAutoScroll()
                            setScrollTimeType(onResultScrollChangeTimeNormal())
                            if (isResolveSwipeRefreshLayout && isPullRefresh)
                                swipeRefreshLayout!!.isEnabled = true
                        }
                    }
                return false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        startAutoScroll()
    }


    private fun setupDots() {
        if (activity == null) {
            return
        }
        dotsContainer.removeAllViews()
        if (isShowHintDots && adapter!!.count > 1) {
            if (dotsContainer.visibility != View.VISIBLE)
                dotsContainer.visibility = View.VISIBLE
            for (i in dataList.indices) {
                dotsContainer.addView(onResultIndicatorDot())
            }
            lastView = dotsContainer.getChildAt(0)
            lastView.setBackgroundResource(onResultIndicatorDotBgFocus())
        }
    }

    @JvmOverloads
    fun setupTab(tabs: Array<String>, onTabSelectedListener: TabLayout.OnTabSelectedListener? = null) {
        setupTab(tabs, 0, onTabSelectedListener)
    }

    /**
     * 安装顶部TabLayout
     *
     * @param tabs
     */
    fun setupTab(tabs: Array<String>, selectorPosition: Int, onTabSelectedListener: TabLayout.OnTabSelectedListener?) {
        this.tabs = tabs
        tabLayout.visibility = View.VISIBLE
        tabLayout.setupWithViewPager(viewPager)
        if (onTabSelectedListener != null)
            tabLayout.addOnTabSelectedListener(onTabSelectedListener)

        if (tabLayout.tabCount > 0) {
            var i = 0
            while (i < tabLayout.tabCount && i < tabs.size) {
                tabLayout.getTabAt(i)?.text = tabs[i]
                i++
            }
        } else
            tabs.forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }

        if (selectorPosition != 0) tabLayout.getTabAt(selectorPosition)?.select()
    }

    /**
     * 返回适配器
     *
     * @return
     */
    open fun onResultAdapter(): PagerAdapter {
        return BaseBannerVpAdapter()
    }

    /**
     * 返回提示点
     *
     * @return
     */
    open fun onResultIndicatorDot(): View {
        val view = View(activity)
        val size = FormatUtils.dip2px(onResultIndicatorDotSize().toFloat()).toInt()
        val margin = FormatUtils.dip2px(onResultIndicatorDotMargin().toFloat()).toInt()
        val layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
        view.setBackgroundResource(onResultIndicatorDotBgNormal())
        view.layoutParams = layoutParams
        return view
    }

    /**
     * 返回提示点背景（选中）
     *
     * @return
     */
    @DrawableRes
    open fun onResultIndicatorDotBgFocus(): Int {
        return R.drawable.shape_oval_black
    }

    /**
     * 返回提示点背景（未选中）
     *
     * @return
     */
    @DrawableRes
    open fun onResultIndicatorDotBgNormal(): Int {
        return R.drawable.shape_oval_black80
    }

    /**
     * 返回提示大小
     *
     * @return
     */
    open fun onResultIndicatorDotSize(): Int {
        return 15
    }

    /**
     * 返回提示点之间距离
     *
     * @return
     */
    open fun onResultIndicatorDotMargin(): Int {
        return 6
    }

    /**
     * 切换Item之间切换时间（正常）
     *
     * @return
     */
    open fun onResultScrollChangeTimeNormal(): Int {
        return 200
    }

    /**
     * 切换Item之间切换时间（自动滚动）
     *
     * @return
     */
    open fun onResultScrollChangeTimeFocus(): Int {
        return 1000
    }

    /**
     * 返回自动滚动延迟时间
     *
     * @return
     */
    open fun onResultAutoScrollDelayTime(): Long {
        return 5000L + onResultScrollChangeTimeFocus()
    }

    /**
     * @return should using [CompatViewPager.TransformerFactory.Companion.getInstance] and [org.quick.library.widgets.CompatViewPager.TransformerFactory.TransformerType.Depth] or [org.quick.library.widgets.CompatViewPager.TransformerFactory.TransformerType.ZoomOut] [org.quick.library.widgets.CompatViewPager.TransformerFactory.TransformerType.Flag] [org.quick.library.widgets.CompatViewPager.TransformerFactory.TransformerType.Flyme]
     */
    open fun onResultPageTransformer(): ViewPager.PageTransformer? {
        return null
    }

    /**
     * 开始自动滚动
     */
    fun startAutoScroll() {
        if (isAutoScroll && adapter!!.count > 1) {
            handler.sendEmptyMessageDelayed(AUTO_SCROLL_WHAT, onResultAutoScrollDelayTime())
        }
    }

    /**
     * 停止自动滚动
     */
    fun stopAutoScroll() {
        if (handler.hasMessages(AUTO_SCROLL_WHAT)) {
            handler.removeMessages(AUTO_SCROLL_WHAT)
        }
    }

    /**
     * 设置滚动时的时间类型
     *
     * @param type [.onResultScrollChangeTimeFocus][.onResultScrollChangeTimeNormal]
     */
    fun setScrollTimeType(type: Int) {
        if (viewPagerScrollTime != type) {
            viewPagerScrollTime = type
            CommonUtils.controlViewPagerSpeed(viewPager!!, type)
        }
    }

    /**
     * 添加触摸事件监听
     * 若需额外设置：[.getViewPager]
     *
     * @param onTouchListener
     */
    fun addOnTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    fun setOnItemClickListener(onItemClickListener: (position: Int, itemData: M, itemView: View) -> Unit) {
        viewPager!!.setOnItemClickListener(object : CompatViewPager.OnItemClickListener {
            override fun onItemClick(adapter: PagerAdapter, currentItem: Int, itemView: View) {
                onItemClickListener.invoke(currentItem, getItem(currentItem), itemView)
            }
        })
    }

    fun setOnIndicatorListener(onIndicatorListener: ((position: Int, totalCount: Int, itemData: M) -> Unit)) {
        this.onIndicatorListener = onIndicatorListener
    }

    fun setDataList(dataList: MutableList<M>) {
        this.dataList = dataList
        adapter!!.notifyDataSetChanged()
        val zeroItem = 0
        viewPager.currentItem = zeroItem
        viewPager.setRealSize(dataList.size)
    }

    fun getDataList(): MutableList<M> {
        return dataList
    }

    fun getItem(position: Int) = getDataList()[position]

    open fun viewType(position: Int, itemData: M): Int {
        return 0
    }

    abstract fun onResultItemLayout(viewType: Int): Int

    abstract fun onBindData(position: Int, holder: QuickViewHolder, itemData: M)


    fun setResolveSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout, isPullRefresh: Boolean) {
        this.swipeRefreshLayout = swipeRefreshLayout
        this.isPullRefresh = isPullRefresh
    }

    override fun onStop() {
        super.onStop()
        stopAutoScroll()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter!!.unregisterDataSetObserver(dataSetObserver)
    }

    inner class BaseBannerVpAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return if (dataList.isNotEmpty()) if (isAutoScroll) Integer.MAX_VALUE else dataList.size else 0
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val tempPosition = position % dataList.size
            val viewHolder = QuickViewHolder(
                LayoutInflater.from(activity).inflate(
                    onResultItemLayout(viewType(tempPosition, getItem(tempPosition))),
                    container,
                    false
                )
            )
            val view = viewHolder.itemView
            if (view.parent != null) (view.parent as ViewGroup).removeView(view)

            container.addView(view)

            onBindData(tempPosition, viewHolder, getItem(tempPosition))
            return view
        }
    }

    interface OnTouchListener {
        fun onTouch(v: View, event: MotionEvent)
    }

    companion object {

        val AUTO_SCROLL_WHAT = 0x1
    }
}