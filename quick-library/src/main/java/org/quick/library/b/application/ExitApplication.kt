package org.quick.library.b.application

import android.app.Activity
import android.app.Application
import android.content.Context
import org.quick.component.Log2
import java.util.*

/**
 * Created by chris on 2015/12/13.
 */
open class ExitApplication : Application() {

    private val Tag = "ExitApplication"
    private val activityList = LinkedList<Activity>()

    val context: Context
        get() = applicationContext

    override fun onCreate() {
        instance = this
        Log2.e("走了这里")
        super.onCreate()
    }

    fun addActivity(activity: Activity?) {
        if (activity != null)
            this.activityList.add(activity)
    }

    fun removeActivity(activity: Activity?) {

        if (activity != null) {
            for (mActivity in this.activityList) {
                if (mActivity === activity) {
                    this.activityList.remove(activity)
                    if (!mActivity.isFinishing)
                        mActivity.finish()
                    break
                }
            }
        }
    }

    @JvmOverloads
    fun clearAllActivity(exceptionActivity: Class<*>? = null) {
        for (mActivity in activityList) {
            if (!mActivity.isFinishing && mActivity.javaClass != exceptionActivity) {
                mActivity.finish()
            }
        }
    }


    fun exit() {
        try {
            for (activity in activityList) {
                activity.finish()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            //            System.exit ( 0 );
        }
    }

    companion object {
        lateinit var instance: ExitApplication
            private set
    }
}
