package org.quick.component.callback

import android.view.View

import org.quick.component.QuickAndroid

import java.util.Calendar

/**
 * Created by work on 2017/6/16.
 * 防止用户快速点击
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

abstract class OnClickListener2 : View.OnClickListener {

    private var lastTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        val temp = currentTime - lastTime
        if (temp > QuickAndroid.defenseClickTime) {//间隔时间，成功触发
            onClick2(v)
            lastTime = currentTime
        }
    }

    abstract fun onClick2(view: View)
}
