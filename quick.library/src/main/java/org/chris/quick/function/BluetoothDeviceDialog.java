package org.chris.quick.function;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.chris.quick.R;
import org.chris.quick.b.BaseRecyclerViewAdapter;

import java.util.Set;

/**
 * Created by work on 2017/6/28.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class BluetoothDeviceDialog {

    OnItemClickedListener onItemClickedListener;

    public Context context;
    Dialog dialog;
    ViewHolder holder;
    BluetoothAdapter bluetoothAdapter;

    public BluetoothDeviceDialog(Context context, BluetoothAdapter bluetoothAdapter, OnItemClickedListener onItemClickedListener) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.onItemClickedListener = onItemClickedListener;
        initDialog(context);
    }

    public void initDialog(Context context) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_SexDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bluetooth_device, null);
        builder.setView(view);
        builder.setCancelable(true);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.SelectSexAnimation);
        holder = new ViewHolder(view);
//        initBluetooth();
    }

    public void initBluetooth() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(receiver, intentFilter);
    }

    public void show() {
        checkNotNull();
        if (!dialog.isShowing())
            dialog.show();
        holder.adapter.getDataList().clear();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device != null)
                holder.adapter.getDataList().add(device);
        }
        holder.adapter.notifyDataSetChanged();
//        holder.recyclerView.refresh();
    }

    public void dismiss() {
        checkNotNull();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void checkNotNull() {
        if (dialog == null) {
            throw new NullPointerException("The dialog uninitialized");
        }
    }

    public void showToast(String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    switch (device.getBondState()) {
                        case BluetoothDevice.BOND_BONDED:
                            break;
                        default:
                            holder.adapter.getDataList().add(device);
                            holder.adapter.notifyDataSetChanged();
                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    holder.recyclerView.refreshComplete();
                    if (holder.adapter.getItemCount() <= 0) {
                        showToast("未找到设备");
                    }
                    break;
            }
        }
    };

    public class ViewHolder {

        public XRecyclerView recyclerView;
        public Button confirmBtn;
        public Adapter adapter;

        public ViewHolder(View view) {
            confirmBtn = (Button) view.findViewById(R.id.confirmBtn);
            recyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);

            recyclerView.setPullRefreshEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallScale);
            recyclerView.setArrowImageView(R.drawable.ic_refresh_downward_gray);
            adapter = new Adapter();
            recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    if (!bluetoothAdapter.isDiscovering()) {
                        bluetoothAdapter.startDiscovery();
                    }
                }

                @Override
                public void onLoadMore() {

                }
            });
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    if (onItemClickedListener != null)
                        onItemClickedListener.onItemClicked(position, adapter.getItem(position));
                }
            });
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public class Adapter extends BaseRecyclerViewAdapter<BluetoothDevice> {

        @Override
        public void onBindData(BaseViewHolder holder, int position, BluetoothDevice itemData) {
            holder.setText(R.id.bluetoothNameTv, itemData.getName());
            holder.setText(R.id.macTv, itemData.getAddress());
        }

        @Override
        public int onResultLayoutResId() {
            return R.layout.item_bluetooth_device_dialog;
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(int position, BluetoothDevice bluetoothDevice);
    }
}
