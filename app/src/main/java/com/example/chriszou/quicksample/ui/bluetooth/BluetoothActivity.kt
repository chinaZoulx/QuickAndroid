package com.example.chriszou.quicksample.ui.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.bluetooth.BluetoothChatService.STATE_CONNECTED
import kotlinx.android.synthetic.main.activity_bluetooth.*
import org.quick.component.Log2
import org.quick.component.utils.CommonUtils
import org.quick.component.utils.DateUtils


/**
 * @Author ChrisZou
 * @Date 2018/6/1-11:24
 * @Email chrisSpringSmell@gmail.com
 */
class BluetoothActivity : org.quick.library.b.BaseActivity() {
    companion object {
        const val REQUEST_ENABLE_BT = 3
    }

    lateinit var bluetoothDeviceDialog: BluetoothDeviceDialog
    lateinit var bluetoothChatService: BluetoothChatService
    lateinit var bluetoothCommandListFragment: BluetoothCommandListFragment

    override fun onResultLayoutResId(): Int = R.layout.activity_bluetooth

    override fun onInit() {
        bluetoothDeviceDialog = BluetoothDeviceDialog(this) { view, viewHolder, position, itemData ->
            bluetoothDeviceDialog.dismiss()
            bluetoothChatService.connect(itemData)//开始连接配对
        }
    }

    @SuppressLint("HardwareIds")
    override fun onInitLayout() {
        bluetoothCommandListFragment = supportFragmentManager.findFragmentById(R.id.bluetoothCommandListFragment) as BluetoothCommandListFragment
        bluetoothNameTv.text = String.format("蓝牙名称：%s", BluetoothAdapter.getDefaultAdapter().name+"")
        bluetoothMacTv.text = String.format("蓝牙Mac：%s", BluetoothAdapter.getDefaultAdapter().address+"")
        bluetoothPairStatusTv.text = String.format("连接状态：%s", "未连接")
    }

    override fun onBindListener() {
        dialogSampleTv.setOnClickListener {
            bluetoothDeviceDialog.show()
        }
        sendMsgBtn.setOnClickListener {
            if (TextUtils.isEmpty(msgContentEtc.text)) {
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
                                Log2.e("蓝牙状态", "MESSAGE_STATE_CHANGE: " + msg.arg1)
                                when (msg.arg1) {
                                    BluetoothChatService.STATE_CONNECTED -> bluetoothPairStatusTv.text = String.format("连接状态：%s", "连接成功")
                                    BluetoothChatService.STATE_CONNECTING -> bluetoothPairStatusTv.text = String.format("连接状态：%s", "连接中...")
                                    BluetoothChatService.STATE_LISTEN -> bluetoothPairStatusTv.text = String.format("连接状态：%s", "连接开始")
                                    BluetoothChatService.STATE_NONE -> bluetoothPairStatusTv.text = String.format("连接状态：%s", "连接失败")
                                }
                            }
                            BluetoothChatService.MESSAGE_WRITE//我发出的
                            -> {
                                val writeBuf = msg.obj as ByteArray
                                val temp = CommonUtils.bytesToHexString(writeBuf)
                                bluetoothCommandListFragment.getAdapter().addData(String.format("发 time:%s content:%s", DateUtils.toStr(DateUtils.getCurrentTimeInMillis()), temp))
                            }
                            BluetoothChatService.MESSAGE_READ//收到消息
                            -> {
                                val readBuf = msg.obj as ByteArray
                                val temp = CommonUtils.bytesToHexString(readBuf)
                                bluetoothCommandListFragment.getAdapter().addData(String.format("收 time:%s content:%s", DateUtils.toStr(DateUtils.getCurrentTimeInMillis()), temp))
                            }
                            BluetoothChatService.MESSAGE_DEVICE_NAME//连接成功后的名字
                            -> {
                                val mConnectedDeviceName = msg.data.getString(BluetoothChatService.DEVICE_NAME)
                                bluetoothPairStatusTv.text = String.format("配对状态：%s", "配对成功（$mConnectedDeviceName）")
                            }
                            BluetoothChatService.MESSAGE_TOAST -> Log2.e("消息", msg.data.getString(BluetoothChatService.TOAST))
                            BluetoothChatService.MESSAGE_LOST -> bluetoothPairStatusTv.text = String.format("配对状态：%s", "连接断开")
                        }
                    }
                })
            }
        } else {//设备不支持蓝牙
            showToast("设备不支持蓝牙")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ENABLE_BT//打开蓝牙返回
            -> {
                if (resultCode == Activity.RESULT_OK) {
                    openBluetooth()
                    showToast("蓝牙已成功打开")
                } else isOkDialog.setContent("打开蓝牙App才能正常工作").setBtnLeft("退出").setBtnRight("马上开启").show { _, isRight ->
                    if (isRight) {
                        openBluetooth()
                    } else finish()
                }
            }
        }
    }

    override fun onDestroy() {
        bluetoothDeviceDialog.unregisterReceiver()
        super.onDestroy()
    }
}