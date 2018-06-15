package org.chris.quick.function

import android.app.Activity
import android.content.Intent
import android.util.SparseArray

/**
 * @describe 快速简洁的返回startActivityForResult值
 *  每个目的地只支持一个回调，比如A跳转至B，A应该将所有跳转写在一起，而不是分散开来
 * @author ChrisZou
 * @date 2018/6/14-14:33
 * @email chrisSpringSmell@gmail.com
 */
object QuickStartActivity {

    private val requestParamsList = SparseArray<((resultCode: Int, data: Intent?) -> Unit)>()

    fun startActivity(activity: Activity?, intent: Intent) {
        startActivity(activity, intent, null)
    }

    fun startActivity(activity: Activity?, intent: Intent, onActivityResultListener: ((resultCode: Int, data: Intent?) -> Unit)?) {
        if (activity == null) return
        if (onActivityResultListener == null)
            activity.startActivity(intent)
        else {
            val requestCode = createRequestCode(intent.component.className)
            if (requestParamsList[requestCode] == null)
                requestParamsList.put(requestCode, onActivityResultListener)/*这里是以目的地存储的*/
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun createRequestCode(className: String): Int {
        val hasCodeStr = className.hashCode().toString()
        var tempCode = ""
        for (index in hasCodeStr.length - 1 downTo 0)
            if (index % 2 != 0)
                tempCode += hasCodeStr[index]
        val requestCode = tempCode.toInt()
        return if (requestCode > 65536) requestCode / 2 else requestCode
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        requestParamsList.get(requestCode)?.invoke(resultCode, data)
    }

    fun remove(activity: Activity?) {
        if (activity == null) return
        requestParamsList.remove(createRequestCode(String.format("%s.%s", activity.packageName, activity.localClassName)))
    }

    fun destory() {
        requestParamsList.clear()
    }
}
