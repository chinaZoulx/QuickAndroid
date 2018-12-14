package com.example.chriszou.quicksample.ui

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.example.chriszou.quicksample.R
import org.quick.component.QuickAdapter
import org.quick.component.QuickAsync
import org.quick.library.b.BaseAdapter
import org.quick.library.b.BaseViewHolder
import org.quick.library.b.QuickListActivity

class AppListActivity : QuickListActivity<PackageInfo>() {


    override val isPullRefreshEnable: Boolean
        get() = false
    override val isLoadMoreEnable: Boolean
        get() = false

    override fun onResultAdapter(): QuickAdapter<*, *> =Adapter()

    override fun onResultUrl(): String = ""

    override fun onRefresh() {

    }

    override fun onResultParams(params: MutableMap<String, String>) {

    }

    override fun onRequestSuccess(model: PackageInfo, isPullRefresh: Boolean) {

    }
    override fun start() {
        QuickAsync.async(object : QuickAsync.OnASyncListener<MutableList<PackageInfo>> {
            override fun onASync(): MutableList<PackageInfo> {
                return packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
            }

            override fun onAccept(value: MutableList<PackageInfo>) {
                setDataList(value)
            }
        })
        getAdapter<Adapter>()?.setOnItemClickListener { view, viewHolder, position, itemData ->
            val intent = activity.packageManager.getLaunchIntentForPackage(itemData.applicationInfo.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            activity.startActivity(intent)
        }
    }

    class Adapter : BaseAdapter<PackageInfo>() {
        override fun onResultLayoutResId(viewType: Int): Int = R.layout.item_app_list

        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: PackageInfo, viewType: Int) {
            holder.getImageView(R.id.appIconIv)?.setImageDrawable(itemData.applicationInfo.loadIcon(context.packageManager))
            holder.setText(R.id.titleTv, itemData.applicationInfo.loadLabel(context.packageManager))
        }

    }

}