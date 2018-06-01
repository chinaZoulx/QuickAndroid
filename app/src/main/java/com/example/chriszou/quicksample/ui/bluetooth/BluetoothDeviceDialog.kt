package com.example.chriszou.quicksample.ui.bluetooth

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast

import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView

import org.chris.quick.b.BaseRecyclerViewAdapter

/**
 * Created by work on 2017/6/28.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

class BluetoothDeviceDialog(var context: Context, internal var onItemClickedListener: BaseRecyclerViewAdapter.OnItemClickListener?) {

    lateinit var dialog: Dialog
    lateinit var holder: ViewHolder

    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    when (device.bondState) {
                        BluetoothDevice.BOND_BONDED -> {
                        }
                        else -> {
                            if (!holder.adapter.dataList.contains(device)) {
                                holder.adapter.dataList.add(device)
                                holder.adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    holder.recyclerView.refreshComplete()
                    if (holder.adapter.itemCount <= 0)
                        showToast("未找到设备")
                }
            }
        }
    }

    init {
        initDialog(context)
    }

    private fun initDialog(context: Context) {
        this.context = context
        val builder = AlertDialog.Builder(context, org.chris.quick.R.style.AppTheme_SexDialog)
        val view = LayoutInflater.from(context).inflate(org.chris.quick.R.layout.dialog_bluetooth_device, null)
        builder.setView(view)
        builder.setCancelable(true)
        dialog = builder.create()
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setWindowAnimations(org.chris.quick.R.style.SelectSexAnimation)
        holder = ViewHolder(view)
        initBluetooth()
    }

    private fun initBluetooth() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        context.registerReceiver(receiver, intentFilter)
    }

    fun show() {
        if (!dialog.isShowing) dialog.show()
        holder.recyclerView.refresh()
        val pairedDevices = BluetoothAdapter.getDefaultAdapter().bondedDevices
        for (device in pairedDevices) {
            if (device != null && !holder.adapter.dataList.contains(device))
                holder.adapter.add(device)
        }
    }

    fun dismiss() {
        if (dialog.isShowing) dialog.dismiss()
    }

    fun showToast(content: String) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

    fun unregisterReceiver() {
        context.unregisterReceiver(receiver)
    }

    inner class ViewHolder(view: View) {

        var recyclerView: XRecyclerView
        var confirmBtn: Button
        var adapter: Adapter

        init {
            confirmBtn = view.findViewById<View>(org.chris.quick.R.id.confirmBtn) as Button
            recyclerView = view.findViewById<View>(org.chris.quick.R.id.recyclerView) as XRecyclerView

            recyclerView.setPullRefreshEnabled(true)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
            recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallScale)
            recyclerView.setArrowImageView(org.chris.quick.R.drawable.ic_refresh_downward_gray)
            adapter = Adapter()
            recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onRefresh() {
                    if (!BluetoothAdapter.getDefaultAdapter().isDiscovering) BluetoothAdapter.getDefaultAdapter().startDiscovery()
                    else showToast("已开始搜索")
                }

                override fun onLoadMore() {

                }
            })
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(onItemClickedListener)
            confirmBtn.setOnClickListener { dismiss() }
        }
    }

    class Adapter : BaseRecyclerViewAdapter<BluetoothDevice>() {

        override fun onBindData(holder: BaseRecyclerViewAdapter.BaseViewHolder, position: Int, itemData: BluetoothDevice) {
            holder.setText(org.chris.quick.R.id.bluetoothNameTv, if (!TextUtils.isEmpty(itemData.name)) itemData.name else itemData.address)
            holder.setText(org.chris.quick.R.id.macTv, itemData.address)
        }

        override fun onResultItemPadding(): Int = 40
        override fun onResultLayoutResId(): Int {
            return org.chris.quick.R.layout.item_bluetooth_device_dialog
        }
    }
}
