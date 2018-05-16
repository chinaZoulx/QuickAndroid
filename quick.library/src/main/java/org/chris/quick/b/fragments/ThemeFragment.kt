package org.chris.quick.b.fragments

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast

import org.chris.quick.R
import org.chris.quick.b.activities.ThemeActivity
import org.chris.quick.tools.common.CommonUtils
import org.chris.quick.tools.common.DevicesUtils


/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/11
 * @modifyInfo1 chriszou-16/10/11
 * @modifyContent
 */
abstract class ThemeFragment : Fragment() {

    var appBaseLayoutContainer: View? = null/*根布局，内容*/
    var isInit: Boolean = false//是否初始化

    lateinit var appContentContainer: FrameLayout
    var appBaseToolbar: Toolbar? = null
    private var isDefaultToolbar = false
    private var onMenuItemClickListener: ((menu: MenuItem?) -> Boolean)? = null
    private var resMenu = -1

    /**
     * 是否引用基本布局
     *
     * @return
     */
    open val isUsingBaseLayout get() = true
    open val isShowTitle get() = false
    open val isFitsSystemWindows get() = true

    open fun onResultToolbar(): Toolbar? {
        isDefaultToolbar = true
        if (isUsingBaseLayout && appBaseToolbar == null)
            appBaseToolbar = getView(R.id.appToolbar) as Toolbar
        return appBaseToolbar
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (appBaseLayoutContainer == null) {
            if (isUsingBaseLayout) {
                appBaseLayoutContainer = inflater.inflate(R.layout.app_content, container, false)
                appContentContainer = appBaseLayoutContainer?.findViewById(R.id.appContent)!!
                inflater.inflate(onResultLayoutResId(), appContentContainer)
            } else appBaseLayoutContainer = inflater.inflate(onResultLayoutResId(), container, false)
            setHasOptionsMenu(true)//允许包含菜单
            setupTitle()
        } else {
            val parent = appBaseLayoutContainer?.parent as ViewGroup
            parent.removeView(appBaseLayoutContainer)
        }
        init()
        return appBaseLayoutContainer
    }

    private fun setupTitle() {
        appBaseToolbar = onResultToolbar()
        if (appBaseToolbar != null) {
            appBaseToolbar?.visibility = if (isShowTitle) View.VISIBLE else View.GONE
            appBaseToolbar?.fitsSystemWindows = isShowTitle

            if (isFitsSystemWindows) {
//                appBaseToolbar?.setPadding(0, CommonUtils.getStatusHeight(activity), 0, 0)
//                appBaseToolbar?.layoutParams?.height = (CommonUtils.getSystemAttrValue(activity, R.attr.actionBarSize) + CommonUtils.getStatusHeight(activity)).toInt()
                CommonUtils.setupFitsSystemWindowsFromToolbar(activity,appBaseToolbar)
            }

            if (!isDefaultToolbar && isUsingBaseLayout) {//不是默认的布局并且引用父布局
                val viewGroup = appBaseLayoutContainer as ViewGroup
                for (i in 0 until viewGroup.childCount) {
                    if (viewGroup.getChildAt(i) is Toolbar) {
                        viewGroup.removeViewAt(i)
                        break
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (resMenu != -1)
            inflater?.inflate(resMenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (onMenuItemClickListener != null) onMenuItemClickListener!!.invoke(item) else super.onOptionsItemSelected(item)
    }

    fun setParentMenu(@MenuRes resMenu: Int, onMenuItemClickListener: ((menu: MenuItem?) -> Boolean)) {
        this.resMenu = resMenu
        this.onMenuItemClickListener = onMenuItemClickListener
    }

    fun setTitle(title: String) {
        appBaseToolbar?.title = title
    }

    protected fun setRightView(@LayoutRes resId: Int) {
        setRightView(LayoutInflater.from(activity).inflate(resId, null), null)
    }

    @JvmOverloads
    protected fun setRightView(view: View, onClickListener: View.OnClickListener? = null) {
        if (appBaseToolbar == null) return
        val layoutParams = Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        if (onClickListener != null)
            view.setOnClickListener(onClickListener)
        appBaseToolbar!!.addView(view, layoutParams)
    }

    protected fun setBackInvalid() {
        setBackValid(-1, false, null)
    }

    protected fun setBackValid(onClickListener: View.OnClickListener) {
        setBackValid(-1, false, onClickListener)
    }

    /**
     * @param backIcon        -1:默认按钮   其他为自定义按钮
     * @param isValid         单击按钮是否有效
     * @param onClickListener
     */
    @JvmOverloads
    protected fun setBackValid(backIcon: Int = -1, isValid: Boolean = false, onClickListener: View.OnClickListener? = null) {
        var tempListener = onClickListener
        if (appBaseToolbar == null) return
        if (isValid) {
            if (backIcon == -1)
                appBaseToolbar?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            else
                appBaseToolbar?.setNavigationIcon(backIcon)
            if (tempListener == null)
                tempListener = View.OnClickListener { activity?.onBackPressed() }
            appBaseToolbar?.setNavigationOnClickListener(tempListener)
        } else appBaseToolbar?.navigationIcon = null
    }

    fun setOnClickListener(onClickListener: View.OnClickListener, @IdRes vararg resIds: Int) {
        for (resId in resIds) {
            setOnClickListener(onClickListener, getView<View>(resId))
        }
    }

    fun setOnClickListener(onClickListener: View.OnClickListener, vararg views: View) {
        for (view in views)
            view.setOnClickListener(onClickListener)
    }

    /**
     * 获取View
     *
     * @param resId
     * @param <T>
     * @return
    </T> */
    fun <T : View> getView(@IdRes resId: Int): T = getView(resId, appBaseLayoutContainer!!)

    /**
     * 获取View
     *
     * @param parent
     * @param resId
     * @param <T>
     * @return
    </T> */
    fun <T : View> getView(@IdRes resId: Int, parent: View): T = parent.findViewById<View>(resId) as T

    /**
     * 获取常规类型数值
     *
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
    </T> */
    fun <T> getTransmitValue(key: String, defaultValue: T): T? {
        return CommonUtils.getIntentValue(activity!!.intent, key, defaultValue)
    }

    protected fun showToast(content: CharSequence?) {
        showToast(content, 0, Toast.LENGTH_SHORT)
    }

    protected fun showToast(content: CharSequence?, duration: Int) {
        showToast(content, 0, duration)
    }

    protected fun showToast(content: CharSequence?, gravity: Int = 0, duration: Int = Toast.LENGTH_SHORT) {
        if (activity == null) return
        val toast = Toast.makeText(activity, content, duration)
        if (gravity != 0)
            toast.setGravity(gravity, 0, 0)
        DevicesUtils.closeSoftInput(activity)
        toast.show()
    }

    protected fun showSnackbar(content: CharSequence, onClickListener: View.OnClickListener) {

        showSnackbar(content, getString(R.string.sure), onClickListener)
    }

    protected fun showSnackbar(view: View, content: CharSequence, onClickListener: View.OnClickListener) {

        showSnackbar(view, content, getString(R.string.sure), onClickListener)
    }

    @JvmOverloads
    protected fun showSnackbar(content: CharSequence, actionTxt: CharSequence? = null, onClickListener: View.OnClickListener? = null) {
        showSnackbar(null, content, actionTxt, onClickListener)
    }

    @JvmOverloads
    protected fun showSnackbar(view: View?, content: CharSequence, actionTxt: CharSequence? = null, onClickListener: View.OnClickListener? = null) {
        if (activity == null) return
        var tempView = view
        DevicesUtils.closeSoftInput(activity)
        if (tempView == null) {
            tempView = appBaseLayoutContainer
        }
        Snackbar.make(tempView!!, content, Snackbar.LENGTH_SHORT).setAction(actionTxt, onClickListener).setActionTextColor(ContextCompat.getColor(activity!!, R.color.colorBlueShallow)).show()
    }

    /**
     * 返回资源文件ID
     *
     * @return
     */
    @LayoutRes
    protected abstract fun onResultLayoutResId(): Int

    /**
     * 初始化操作
     */
    abstract fun init()
}
