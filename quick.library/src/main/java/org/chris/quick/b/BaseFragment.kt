package org.chris.quick.b

import android.os.Bundle
import android.support.annotation.LayoutRes
import org.chris.quick.b.fragments.ThemeFragment
import org.chris.quick.function.IsOkDialog
import org.chris.quick.function.LoadingDialog

/**
 * Created by chris Zou on 2016/6/12.
 *
 * @author chris Zou
 * @date 2016/6/12
 */
abstract class BaseFragment : ThemeFragment() {

    var isOkDialog = IsOkDialog(activity)
    var loadDialog = LoadingDialog(activity)
    //    private var onInitListener: ((isFirst:Boolean) -> Unit)? = null
    private var onInitListener: (() -> Unit)? = null

    override fun init() {
        isOkDialog = IsOkDialog(activity)
        loadDialog = LoadingDialog(activity)
        if (hasTitle()) {
            setBackValid()
        }
        onInit()
    }

    fun setOnInitListener(onInitListener: () -> Unit) {
        this.onInitListener = onInitListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isInit) {
            onInitLayout()
            onBindListener()
            onBindData()
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
    abstract fun onBindData()
}
