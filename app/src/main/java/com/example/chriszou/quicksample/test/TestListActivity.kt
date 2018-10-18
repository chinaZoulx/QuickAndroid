package com.example.chriszou.quicksample.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.Toast
import org.quick.library.b.BaseViewHolder
import org.quick.component.QuickToast

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/4-15:02
 * @email chrisSpringSmell@gmail.com
 */
class TestListActivity : org.quick.library.b.BaseListActivity() {
    override val isPullRefreshEnable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val isLoadMoreEnable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onResultLayoutResId(): Int {
        return super.onResultLayoutResId()
    }

    override fun start() {
        sendBroadcast(Intent("action"))
        getAdapter<Adapter>().setOnItemClickListener { view, viewHolder, position, itemData ->

        }
//        getAdapter<Adapter>().setOnClickListener(BaseRecyclerViewAdapter.OnClickListener{ view, holder, position ->
//
//        },R.id.你的ID1,R.id.你的ID2)

        val toast = Toast.makeText(activity, "这是一个Toast", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        QuickToast.showToastDefault("这是一个Toast")
        QuickToast.Builder().setGravity(Gravity.CENTER).setDuration(Toast.LENGTH_SHORT).build().showToast("这是一个Toast")
        onRefresh()
    }


    /*返回适配器*/
    override fun onResultAdapter(): RecyclerView.Adapter<*> = Adapter()

    override fun onResultLayoutManager(): RecyclerView.LayoutManager {
        return super.onResultLayoutManager()
    }

    override fun onResultUrl(): String = "http://www.baidu.com"

    override fun onResultParams(params: MutableMap<String, String>) {
        params["userName"] = "张三"
    }

    override fun onRequestSuccess(jsonData: String, isPullRefresh: Boolean) {
        if (isPullRefresh) {//下拉刷新

        } else {//上拉加载

        }
    }

    inner class Adapter : org.quick.library.b.BaseAdapter<String>() {

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: String, viewType: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onResultLayoutResId(viewType: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}