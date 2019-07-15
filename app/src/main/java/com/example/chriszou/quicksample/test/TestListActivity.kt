package com.example.chriszou.quicksample.test

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.widget.Toast
import org.quick.component.QuickAdapter
import org.quick.library.b.BaseViewHolder
import org.quick.component.QuickToast
import org.quick.component.QuickViewHolder

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/4-15:02
 * @email chrisSpringSmell@gmail.com
 */
class TestListActivity : org.quick.library.b.QuickListActivity<String,String>() {
    override fun onResultItemResId(viewType: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindData(holder: QuickViewHolder, position: Int, itemData: String, viewType: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override val isPullRefreshEnable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val isLoadMoreEnable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun start() {
        sendBroadcast(Intent("action"))
        setOnItemClickListener { view, viewHolder, position, itemData ->

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


    override fun onResultLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager {
        return super.onResultLayoutManager()
    }

    override fun onResultUrl(): String = "http://www.baidu.com"

    override fun onResultParams(params: MutableMap<String, String>) {
        params["userName"] = "张三"
    }

    override fun onLoadMoreSuccess(model: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPullRefreshSuccess(model: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}