package com.example.chriszou.quicksample.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.chriszou.quicksample.R;
import com.example.chriszou.quicksample.ui.setting.SettingActivity;

import org.quick.library.b.activities.ThemeActivity;

/**
 * Created by work on 2017/9/25.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class FloatService extends Service {

    View baseView;
    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;

    int statusBarHeight;
    boolean isViewAdded = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isViewAdded) {
            setupView();
            setupListener();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addAndUpdateView();
        return super.onStartCommand(intent, flags, startId);
    }

    private void setupView() {
        baseView = LayoutInflater.from(this).inflate(R.layout.float_main, null);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
        } else {
            layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
        }
//        WindowManager.LayoutParams.TYPE_TOAST ：无需申请权限
        //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ：窗口无焦点，可以操作桌面其他应用
        //PixelFormat.TRANSPARENT：透明
        layoutParams.gravity = Gravity.END | Gravity.TOP;//右上出现
    }

    private void setupListener() {
        baseView.findViewById(R.id.jumpTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView();
                Intent intent = new Intent(FloatService.this, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(ThemeActivity.TITLE, "设置");
                startActivity(intent);
            }
        });
        baseView.setOnTouchListener(new View.OnTouchListener() {
            float downX, downY, curX, curY;

            public boolean onTouch(View v, MotionEvent event) {
                layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                curX = event.getRawX();
                curY = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 按下事件，记录按下时手指在悬浮窗的XY坐标值
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        addAndUpdateView((int) (event.getRawX() - downX), (int) (event.getRawY() - downY));
                        break;
                    case MotionEvent.ACTION_UP:
                        int locationX = 0;
                        Rect rect = new Rect();
                        windowManager.getDefaultDisplay().getRectSize(rect);
                        if (curX > rect.right / 2) {
                            locationX = rect.right;
                        }
                        addAndUpdateView(locationX, (int) (event.getRawY() - downY));
                        break;
                }
                return true;
            }
        });
    }

    private void addAndUpdateView() {
        if (isViewAdded) {
            windowManager.updateViewLayout(baseView, layoutParams);
        } else {
            isViewAdded = true;
            windowManager.addView(baseView, layoutParams);
        }
    }

    /**
     * 刷新悬浮窗
     *
     * @param x 拖动后的X轴坐标
     * @param y 拖动后的Y轴坐标
     */
    private void addAndUpdateView(int x, int y) {
        // 状态栏高度不能立即取，不然得到的值是0
        if (statusBarHeight == 0) {
            View rootView = baseView.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }

        layoutParams.x = x;
        // y轴减去状态栏的高度，因为状态栏不是用户可以绘制的区域，不然拖动的时候会有跳动
        layoutParams.y = y - statusBarHeight;// STATUS_HEIGHT;
        addAndUpdateView();
        Log.e("移动到", "x:" + x + " y:" + y);
    }

    /**
     * 移除一个View
     */
    private void removeView() {
        if (isViewAdded) {
            windowManager.removeView(baseView);
            isViewAdded = false;
        }
    }
}


