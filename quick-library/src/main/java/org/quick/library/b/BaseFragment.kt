package org.quick.library.b

import android.os.Bundle
import androidx.annotation.LayoutRes
import android.view.View
import org.quick.component.Constant
import org.quick.library.b.fragments.ThemeFragment

/**
 * Created by chris Zou on 2016/6/12.
 *
 * @author chris Zou
 * @date 2016/6/12
 */
abstract class BaseFragment : ThemeFragment() {

    companion object {
        val SUCCESS = Constant.APP_SUCCESS_TAG
    }

    lateinit var isOkDialog: org.quick.library.function.IsOkDialog
    lateinit var loadingDialog: org.quick.library.function.LoadingDialog
    private var onInitListener: (() -> Unit)? = null

    override fun init() {
        isOkDialog = org.quick.library.function.IsOkDialog(activity)
        loadingDialog = org.quick.library.function.LoadingDialog(activity)
        if (isShowTitle) setBackValid()
        onInit()
    }

    fun setOnInitListener(onInitListener: () -> Unit) {
        this.onInitListener = onInitListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isInit) {
            onInitLayout()
            onBindListener()
            start()
            isInit = true
            onInitListener?.invoke()
        }
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
