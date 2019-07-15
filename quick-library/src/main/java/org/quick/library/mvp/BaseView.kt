package org.quick.library.mvp

import org.quick.component.QuickToast

/**
 * Created by work on 2017/6/2.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

interface BaseView<T> {

    fun setPresenter(presenter: T)
    fun showToast(content: String) {
        QuickToast.showToastDefault(content)
    }

    fun showDialog(content: String)
    fun dismiss()
}
