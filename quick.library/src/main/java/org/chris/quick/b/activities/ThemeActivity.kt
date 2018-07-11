package org.chris.quick.b.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast

import com.zhy.autolayout.AutoLayoutActivity

import org.chris.quick.R
import org.chris.quick.b.application.ExitApplication
import org.chris.quick.tools.common.CommonUtils
import org.chris.quick.tools.common.DevicesUtils

import android.support.v7.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled
import org.chris.quick.function.QuickStartActivity
import org.chris.quick.function.QuickToast
import org.chris.quick.listener.OnClickListener2


/**
 * 对外开放的类，请继承该类
 * 使用该类须隐藏title，主题城需使用兼容的风格，详情请查看Demo的mainifests
 * Created by chris on 2016/6/8.
 *
 * @author chris Zou
 * @date 2016/6/8.
 */
abstract class ThemeActivity : AutoLayoutActivity() {

    var isInit = false//是否初始化

    lateinit var appBaseLayoutContainer: View/*主布局*/
    var appBaseToolbar: Toolbar? = null/*标题栏*/
    lateinit var appContentContainer: FrameLayout/*根布局，内容*/

    private var onMenuItemClickListener: ((menu: MenuItem?) -> Boolean)? = null
    private var resMenu = -1
    private var isDefaultToolbar = false
    private var errorServiceHint = ""

    val activity: Activity get() = this
    /**
     * 是否引用基本布局
     *
     * @return
     */
    open val isUsingBaseLayout get() = true
    /**
     * 是否显示标题
     */
    open val isShowTitle get() = true


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ExitApplication.getInstance() == null)
            throw NullPointerException(String.format("未初始化BaseApplication"))

        ExitApplication.getInstance().addActivity(this)
        initView()
        init()
        isInit = true
    }

    private fun initView() {
        if (isUsingBaseLayout) {
            setContentView(R.layout.app_content)
            appContentContainer = findViewById(R.id.appContent)
            appBaseLayoutContainer = findViewById(R.id.parentGroupBorder)
            LayoutInflater.from(this).inflate(onResultLayoutResId(), appContentContainer)
        } else {
            appBaseLayoutContainer = LayoutInflater.from(this).inflate(onResultLayoutResId(), null)
            setContentView(appBaseLayoutContainer)
        }
        setupTitle()
    }

    private fun setupTitle() {
        appBaseToolbar = onResultToolbar()
        if (appBaseToolbar != null) {
            appBaseToolbar?.visibility = if (isShowTitle) View.VISIBLE else View.GONE

            if (isShowTitle) {//有标题
                setSupportActionBar(appBaseToolbar)
                if (intent.hasExtra(TITLE)) {
                    title = intent.getStringExtra(TITLE)
                }
                appBaseToolbar?.fitsSystemWindows = isShowTitle
                val actionBar = supportActionBar
                actionBar?.setDisplayShowHomeEnabled(true)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (onMenuItemClickListener != null) onMenuItemClickListener!!.invoke(item) else super.onOptionsItemSelected(item)
    }

    fun setMenu(@MenuRes resMenu: Int, onMenuItemClickListener: ((menu: MenuItem?) -> Boolean)) {
        this.resMenu = resMenu
        this.onMenuItemClickListener = onMenuItemClickListener
    }

    open fun onResultToolbar(): Toolbar? {
        isDefaultToolbar = true
        if (isUsingBaseLayout && appBaseToolbar == null)
            appBaseToolbar = findViewById<View>(R.id.appToolbar) as Toolbar
        return appBaseToolbar
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (resMenu != -1) menuInflater.inflate(resMenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    protected fun setRightView(@LayoutRes resId: Int) {
        setRightView(LayoutInflater.from(this).inflate(resId, null), null)
    }

    @JvmOverloads
    protected fun setRightView(view: View, onClickListener: View.OnClickListener? = null) {
        checkNotNullToolbar()
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
        setBackValid(-1, true, onClickListener)
    }

    protected fun setBackValid(backIcon: Int, onClickListener: View.OnClickListener) {
        setBackValid(backIcon, true, onClickListener)
    }

    /**
     * @param backIcon        -1:默认按钮   其他为自定义按钮
     * @param isValid         单击按钮是否有效
     * @param onClickListener
     */
    @JvmOverloads
    protected fun setBackValid(backIcon: Int = -1, isValid: Boolean = true, onClickListener: View.OnClickListener? = null) {
        var tempListener = onClickListener
        if (appBaseToolbar == null) return
        if (isValid) {
            if (backIcon == -1)
                appBaseToolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            else
                appBaseToolbar!!.setNavigationIcon(backIcon)

            if (tempListener == null)
                tempListener = View.OnClickListener { onBackPressed() }
            appBaseToolbar!!.setNavigationOnClickListener(tempListener)
        } else appBaseToolbar?.navigationIcon = null
    }

    private fun checkNotNullToolbar() {
        if (appBaseToolbar == null) {
            return
        }
    }

    /**
     * 获取View
     *
     * @param resId
     * @param <T>
     * @return
    </T> */
    fun <T : View> getView(@IdRes resId: Int): T = appBaseLayoutContainer.findViewById(resId)

    /**
     * 获取View
     *
     * @param resId
     * @param <T>
     * @return
    </T> */
    fun <T : View> getView(@IdRes resId: Int, view: View): T = view.findViewById(resId)

    protected fun setOnClickListener(onClickListener: OnClickListener2, @IdRes vararg resIds: Int) {
        for (resId in resIds) {
            setOnClickListener(onClickListener, getView<View>(resId))
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener2, vararg views: View) {
        for (view in views)
            view.setOnClickListener(onClickListener)
    }

    fun getErrorServiceHint(): String {
        if (errorServiceHint.isEmpty()) errorServiceHint = getString(R.string.errorServiceHint)
        return errorServiceHint
    }

    fun setVisibility(visibility: Int, vararg resIds: Int) {
        for (resId in resIds) setVisibility(visibility, getView<View>(resId))
    }

    fun setVisibility(visibility: Int, vararg views: View) {
        for (view in views) setVisibility(visibility, view)
    }

    fun setVisibility(visibility: Int, view: View) {
        view.visibility = visibility
    }

    /**
     * 获取常规类型数值
     *
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
    </T> */
    fun <T> getTransmitValue(key: String, defaultValue: T): T {
        return CommonUtils.getIntentValue(intent, key, defaultValue)
    }

    protected fun showToast(content: CharSequence) {
        showToast(content, Toast.LENGTH_SHORT)
    }

    protected fun showToast(content: CharSequence, duration: Int) {
        showToast(content, 0, 0, 150, duration)
    }

    protected fun showToast(content: CharSequence, gravity: Int = 0, xOffset: Int, yOffset: Int, duration: Int = Toast.LENGTH_SHORT) {
        QuickToast.Builder().setGravity(gravity, xOffset, yOffset).setDuration(duration).build().showToast(content.toString())
        DevicesUtils.closeSoftInput(this)
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
        DevicesUtils.closeSoftInput(this)
        var tempView = view ?: window.decorView
        Snackbar.make(tempView, content, Snackbar.LENGTH_SHORT).setAction(actionTxt, onClickListener).setActionTextColor(ContextCompat.getColor(activity, R.color.colorBlueShallow)).show()
    }


    protected fun startActivity(intent: Intent, onActivityResultListener: ((resultCode: Int, data: Intent?) -> Unit)) {
        QuickStartActivity.startActivity(activity, intent, onActivityResultListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        QuickStartActivity.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        ExitApplication.getInstance().removeActivity(this)
        super.onDestroy()
    }

    companion object {
        init {//兼容vector
            setCompatVectorFromResourcesEnabled(true)
        }

        const val TITLE = "title"
        const val ID = "id"
        const val DATA = "data"
        const val TYPE = "type"

        /**
         * 这只是一个简易的跳转方法，参数多时建议重写此方法
         *
         * @param activity
         * @param activityClass
         * @param title
         */
        fun startAction(activity: Activity?, activityClass: Class<*>, title: String) {
            val intent = Intent(activity, activityClass)
            intent.putExtra(TITLE, title)
            activity?.startActivity(intent)
        }
    }
}
