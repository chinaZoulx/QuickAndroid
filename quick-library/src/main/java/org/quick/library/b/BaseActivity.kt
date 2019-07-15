package org.quick.library.b

import androidx.annotation.LayoutRes
import org.quick.component.Constant
import org.quick.library.b.activities.ThemeActivity

/**
 * Created by zoulx on 2017/11/13.
 */
abstract class BaseActivity : ThemeActivity() {
    companion object {
        val SUCCESS = Constant.APP_SUCCESS_TAG
    }
    lateinit var isOkDialog: org.quick.library.function.IsOkDialog
    lateinit var loadingDialog: org.quick.library.function.LoadingDialog

    override fun init() {
        if (isUsingBaseLayout && isShowTitle) {
            setBackValid()
        }
        isOkDialog = org.quick.library.function.IsOkDialog(activity)
        loadingDialog = org.quick.library.function.LoadingDialog(activity)
        onInit()
        onInitLayout()
        onBindListener()
        start()
    }

    /**
     * 返回资源文件ID
     *
     * @return
     */
    @LayoutRes
    public abstract override fun onResultLayoutResId(): Int

    /**
     * 初始化操作
     */
    abstract fun onInit()

    /**
     * 初始化布局
     */
    abstract fun onInitLayout()

    /**
     * 绑定监听
     */
    abstract fun onBindListener()

    /**
     * 绑定数据
     */
    abstract fun start()


}