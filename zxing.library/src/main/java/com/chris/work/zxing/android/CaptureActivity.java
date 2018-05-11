package com.chris.work.zxing.android;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.work.zxing.R;
import com.chris.work.zxing.camera.CameraManager;
import com.chris.work.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    public static final String CONTENT = "codeContent";
    public static final String BITMAP = "codeBitmap";

    public static final int RESULT_CODE = 0xcc;

    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private TextView titleTv;
    private TextView hintTv;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;

    private ImageView imageButton_back;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public static void startAction(Activity context, int requestCode, String title, String hint) {
        Intent intent = new Intent(context, CaptureActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("hint", hint);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        titleTv = ((TextView) findViewById(R.id.titleTv));
        titleTv.setText(getIntent().getStringExtra("title"));
        hintTv = (TextView) findViewById(R.id.hintTv);
        hintTv.setText(getIntent().getStringExtra("hint"));

        imageButton_back = (ImageView) findViewById(R.id.capture_imageview_back);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.openFlashLightIv).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                boolean flag = false;
                if (view.getTag() != null) {
                    flag = (boolean) view.getTag();
                }
                if (flag) {
                    cameraManager.closeFlashLight();
                    ((ImageView) view).setImageResource(R.mipmap.ic_flash_light);
                } else {
                    ((ImageView) view).setImageResource(R.mipmap.ic_flash_light_blue);
                    cameraManager.openFlashLight();
                }
                view.setTag(!flag);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            requestPermission();
        } else {
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            requestPermission();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
            Intent intent = getIntent();
            intent.putExtra(CONTENT, rawResult.getText());
            intent.putExtra(BITMAP, barcode);
            setResult(RESULT_CODE, intent);
            finish();
        }
    }

    public void requestPermission() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        String[] permissions = {Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            initCamera(surfaceHolder);
        } else if (shouldShowRequestPermissionRationale(permissions)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goAppDetailSettingIntent(CaptureActivity.this);
                }
            }).setPositiveButton("取消", null).setMessage("此功能需要获取您的相机权限").setTitle("温馨提示").setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//通过权限
                requestPermission();
            } else if (shouldShowRequestPermissionRationale(permissions)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setNegativeButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goAppDetailSettingIntent(CaptureActivity.this);
                    }
                }).setPositiveButton("取消", null).setMessage("此功能需要获取您的相机权限").setTitle("温馨提示").setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).create().show();
            }
        }
    }

    /**
     * 上一次是否拒绝
     *
     * @param permissions
     * @return
     */
    public boolean shouldShowRequestPermissionRationale(@NonNull String[] permissions) {
        for (String per : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, per)) {
                return true;
            }
        }
        return false;
    }

    public static void goAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }
}
