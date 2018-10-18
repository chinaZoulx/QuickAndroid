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
import android.widget.Button
import android.widget.Toast
import com.example.chriszou.quicksample.R
import org.quick.component.widget.QRecyclerView
import org.quick.library.b.BaseViewHolder

/**
 * Created by work on 2017/6/28.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

class BluetoothDeviceDialog(var context: Context, var onItemClickListener: (view: View, viewHolder: BaseViewHolder, position: Int, itemData: BluetoothDevice) -> Unit) {

    lateinit var dialog: Dialog
    lateinit var holder: ViewHolder

    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {//找到设备
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    when (device.bondState) {
                        BluetoothDevice.BOND_BONDED -> Unit
                        else -> if (!holder.adapter.getDataList().contains(device)) holder.adapter.add(device)
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {//搜索完成
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
        val builder = AlertDialog.Builder(context, org.quick.library.R.style.AppTheme_SexDialog)
        val view = LayoutInflater.from(context).inflate(org.quick.library.R.layout.dialog_bluetooth_device, null)
        builder.setView(view)
        builder.setCancelable(true)
        dialog = builder.create()
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setWindowAnimations(org.quick.library.R.style.SelectSexAnimation)
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
        holder.recyclerView.refresh(true)
        val pairedDevices = BluetoothAdapter.getDefaultAdapter().bondedDevices
        for (device in pairedDevices) {
            if (device != null && !holder.adapter.getDataList().contains(device))
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

        var recyclerView: QRecyclerView = view.findViewById<View>(org.quick.library.R.id.recyclerView) as QRecyclerView
        var confirmBtn: Button = view.findViewById<View>(org.quick.library.R.id.confirmBtn) as Button
        var adapter: Adapter = Adapter()

        init {
            adapter.setOnItemClickListener(onItemClickListener)
            recyclerView.setRefreshListener(true, false, object : QRecyclerView.OnRefreshListener {
                override fun onRefresh() {
                    if (!BluetoothAdapter.getDefaultAdapter().isDiscovering) BluetoothAdapter.getDefaultAdapter().startDiscovery()
                    else showToast("已开始搜索")
                }

                override fun onLoading() {

                }

            })
            recyclerView.setLayoutManager(LinearLayoutManager(context))
            recyclerView.setAdapter(adapter)
            confirmBtn.setOnClickListener { dismiss() }
        }
    }

    class Adapter : org.quick.library.b.BaseAdapter<BluetoothDevice>() {
        override fun onBindData(holder: BaseViewHolder, position: Int, itemData: BluetoothDevice, viewType: Int) {
            holder.setText(org.quick.library.R.id.bluetoothNameTv, if (!TextUtils.isEmpty(itemData.name)) itemData.name else itemData.address)
            holder.setText(org.quick.library.R.id.macTv, itemData.address)
        }

        override fun onResultLayoutResId(viewType: Int): Int = R.layout.item_bluetooth_device_dialog
    }
}
