package com.example.chriszou.quicksample.ui.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Handler
import android.os.Message
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.bluetooth.BluetoothChatService.STATE_CONNECTED
import kotlinx.android.synthetic.main.activity_bluetooth.*
import org.chris.quick.b.BaseActivity
import org.chris.quick.b.BaseRecyclerViewAdapter
import org.chris.quick.m.Log
import org.chris.quick.tools.DateUtils
import org.chris.quick.tools.common.CommonUtils

/**
 * @Author ChrisZou
 * @Date 2018/6/1-11:24
 * @Email chrisSpringSmell@gmail.com
 */
class BluetoothActivity : BaseActivity() {
    companion object {
        const val REQUEST_ENABLE_BT = 3
    }

    lateinit var bluetoothDeviceDialog: BluetoothDeviceDialog
    lateinit var bluetoothChatService: BluetoothChatService
    lateinit var bluetoothCommandListFragment: BluetoothCommandListFragment

    override fun onResultLayoutResId(): Int = R.layout.activity_bluetooth

    override fun onInit() {
        bluetoothDeviceDialog = BluetoothDeviceDialog(this, BaseRecyclerViewAdapter.OnItemClickListener { v, position ->
            bluetoothDeviceDialog.dismiss()
            bluetoothChatService.connect(bluetoothDeviceDialog.holder.adapter.getItem(position))//开始连接配对
        })
    }

    @SuppressLint("HardwareIds")
    override fun onInitLayout() {
        bluetoothCommandListFragment = supportFragmentManager.findFragmentById(R.id.bluetoothCommandListFragment) as BluetoothCommandListFragment
        bluetoothNameTv.text = String.format("蓝牙名称：%s", BluetoothAdapter.getDefaultAdapter().name)
        bluetoothMacTv.text = String.format("蓝牙Mac：%s", BluetoothAdapter.getDefaultAdapter().address)
        bluetoothPairStatusTv.text = String.format("连接状态：%s", "未连接")
    }

    override fun onBindListener() {
        dialogSampleTv.setOnClickListener {
            bluetoothDeviceDialog.show()
        }
        sendMsgBtn.setOnClickListener {
            if (!msgContentEtc.text.isEmpty()) {
                if (bluetoothChatService.state == STATE_CONNECTED) bluetoothChatService.write(CommonUtils.parseCommand(msgContentEtc.textStr))
                else showToast("设备未连接")
            } else showToast("请输入命令")
        }
    }

    override fun start() {
        openBluetooth()
    }

    private fun openBluetooth() {
        if (BluetoothAdapter.getDefaultAdapter() != null) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled) {//蓝牙未开启
                val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                activity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
            } else {
                bluetoothChatService = BluetoothChatService(this, @SuppressLint("HandlerLeak")
                object : Handler() {
                    override fun handleMessage(msg: Message?) {
                        when (msg?.what) {
                            BluetoothChatService.MESSAGE_STATE_CHANGE//状态改变
                            -> {
                                Log.e("蓝牙状态", "MESSAGE_STATE_CHANGE: " + msg.arg1)
                                when (msg.arg1) {
                                    BluetoothChatService.STATE_CONNECTED -> bluetoothPairStatusTv.text = String.format("配对状态：%s", "连接成功")
                                    BluetoothChatService.STATE_CONNECTING -> bluetoothPairStatusTv.text = String.format("配对状态：%s", "连接中...")
                                    BluetoothChatService.STATE_LISTEN, BluetoothChatService.STATE_NONE -> bluetoothPairStatusTv.text = String.format("配对状态：%s", "连接失败")
                                }
                            }
                            BluetoothChatService.MESSAGE_WRITE//我发出的
                            -> {
                                val writeBuf = msg.obj as ByteArray
                                val temp = CommonUtils.bytesToHexString(writeBuf)
                                bluetoothCommandListFragment.getAdapter<BluetoothCommandListFragment.Adapter>().add(String.format("发 time:%s content:%s", DateUtils.formatDate(DateUtils.getCurrentTimeInMillis()), temp))
                            }
                            BluetoothChatService.MESSAGE_READ//收到消息
                            -> {
                                val readBuf = msg.obj as ByteArray
                                val temp = CommonUtils.bytesToHexString(readBuf)
                                bluetoothCommandListFragment.getAdapter<BluetoothCommandListFragment.Adapter>().add(String.format("收 time:%s content:%s", DateUtils.formatDate(DateUtils.getCurrentTimeInMillis()), temp))
                            }
                            BluetoothChatService.MESSAGE_DEVICE_NAME//连接成功后的名字
                            -> {
                                val mConnectedDeviceName = msg.data.getString(BluetoothChatService.DEVICE_NAME)
                                bluetoothPairStatusTv.text = String.format("配对状态：%s", "配对成功（$mConnectedDeviceName）")
                            }
                            BluetoothChatService.MESSAGE_TOAST -> Log.e("消息", msg.data.getString(BluetoothChatService.TOAST))
                            BluetoothChatService.MESSAGE_LOST -> bluetoothPairStatusTv.text = String.format("配对状态：%s", "连接断开")
                        }
                    }
                })
            }
        } else {//设备不支持蓝牙
            showToast("设备不支持蓝牙")
        }
    }

    private fun onceAgainRequestOpenBluetooth() {
        isOkDialog.alertIsOkDialog("打开蓝牙App才能正常工作", "退出", "马上开启") { view, isRight ->
            if (isRight) {
                openBluetooth()
            } else finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_ENABLE_BT//打开蓝牙返回
            -> {
                if (resultCode == Activity.RESULT_OK) {
                    showToast("蓝牙已成功打开")
                } else {
                    onceAgainRequestOpenBluetooth()
                }
            }
        }
    }

    override fun onDestroy() {
        bluetoothDeviceDialog.unregisterReceiver()
        super.onDestroy()
    }
}