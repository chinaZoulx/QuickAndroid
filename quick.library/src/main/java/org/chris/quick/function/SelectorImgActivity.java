package org.chris.quick.function;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.kevin.crop.UCrop;

import org.chris.quick.R;
import org.chris.quick.b.BasePermissionActivity;
import org.chris.quick.config.GlideCatchConfig;
import org.chris.quick.function.selectorimg.PhotoAlbumActivity;
import org.chris.quick.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorActivity;
import org.chris.quick.function.selectorimg.PhotoWallAdapter;
import org.chris.quick.function.selectorimg.UCropActivity;
import org.chris.quick.m.SystemActionManager;
import org.chris.quick.tools.common.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 选择照片页面
 * Created by Chris Zou on 14-10-15.
 */
public class SelectorImgActivity extends BasePermissionActivity implements PhotoWallAdapter.OnItemClickListener {
    public static final String IS_CROP = "isCrop";
    public static final String ALREADY_PATHS = "alreadyPaths";
    public static final String MAX_COUNT = "maxCount";
    public static final String TITLE = "title";

    public static final int RESULT_CODE = 0x1238;
    public static final int requestPermissionCode = 0x456;

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.png";

    public static Activity albumActivity = null;

    private ArrayList<String> imgPathList;
    private GridView mPhotoWall;
    private PhotoWallAdapter adapter;
    private TextView mSelectTv;

    /**
     * 当前文件夹路径
     */
    private String currentFolder = null;
    /**
     * 当前展示的是否为最近照片
     */
    private boolean isLatest = true;
    /**
     * 拍照保存路径
     */
    private String savePath = "";
    private ArrayList<String> alreadySelectorPaths;
    private int maxCount = 0;
    private boolean isCrop;
    private boolean isSingle = false;
    public static Uri mDestinationUri;

    private int code;
    private String folderPath;

    /**
     * @param context
     * @param requestCode
     * @param title
     */
    public static void startAction(Activity context, int requestCode, String title) {
        startAction(context, requestCode, title, null, 1, false);
    }

    /**
     * @param context
     * @param requestCode
     * @param title
     */
    public static void startAction(Activity context, int requestCode, String title, boolean isCrop) {
        startAction(context, requestCode, title, null, 1, isCrop);
    }

    /**
     * @param context
     * @param requestCode
     * @param title
     * @param alreadyList 已选中值
     * @param maxCount    最大选中值
     */
    public static void startAction(Activity context, int requestCode, String title, ArrayList<String> alreadyList, int maxCount) {
        startAction(context, requestCode, title, alreadyList, maxCount, false);
    }

    /**
     * 需要裁剪只能选中一张图片
     *
     * @param context
     * @param requestCode
     * @param title
     * @param alreadyList 已选中的图片
     * @param isCrop      是否需要裁剪
     */
    public static void startAction(Activity context, int requestCode, String title, ArrayList<String> alreadyList, boolean isCrop) {
        startAction(context, requestCode, title, alreadyList, 1, isCrop);
    }

    /**
     * @param context
     * @param requestCode
     * @param title
     * @param alreadyList 已选中的图片
     * @param maxCount    可选择的最大值
     * @param isCrop      是否需要裁剪
     */
    private static void startAction(Activity context, int requestCode, String title, ArrayList<String> alreadyList, int maxCount, boolean isCrop) {
        Intent intent = new Intent(context, SelectorImgActivity.class);
        intent.putExtra(TITLE, title);
        if (alreadyList != null) {
            intent.putExtra(ALREADY_PATHS, alreadyList);
        }
        intent.putExtra(IS_CROP, isCrop);
        intent.putExtra(MAX_COUNT, maxCount);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int onResultLayoutResId() {
        return R.layout.activity_photo_wall;
    }

    @Override
    public void init() {
        isCrop = getIntent().getBooleanExtra(IS_CROP, false);
        imgPathList = new ArrayList<>();
        mDestinationUri = Uri.fromFile(new File(getCacheDir() + File.separator + GlideCatchConfig.GLIDE_CARCH_DIR, SAMPLE_CROPPED_IMAGE_NAME));
        maxCount = getIntent().getIntExtra(MAX_COUNT, 0);

        View function = LayoutInflater.from(this).inflate(R.layout.select_photo_title_right, null);
        setRightView(function);

        mSelectTv = (TextView) function.findViewById(R.id.selectImgAlbum);
        mSelectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadySelectorPaths.size() > 0)
                    if (isCrop) {
                        Uri uri = Uri.fromFile(new File(alreadySelectorPaths.get(0)));
                        startCropActivity(uri);
                    } else {
                        setResult(alreadySelectorPaths);
                    }
                else showToast("请至少选择一张图片");
            }
        });

        TextView album = (TextView) function.findViewById(R.id.selectImgConfirm);
        album.setText(getString(R.string.photo_album));
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAlbum();
            }
        });
        setBackValid(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent(PhotoAlbumActivity.EXIT));
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);
        adapter = new PhotoWallAdapter(this, imgPathList);
        mPhotoWall.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnCheckChangeListener(new PhotoWallAdapter.OnCheckChangeListener() {
            @Override
            public boolean onCheckChange(int position, boolean isChecked, String path) {
                boolean flag = true;
                if (maxCount <= 1 || isCrop) {//需要裁剪
                    if (isChecked) {
                        alreadySelectorPaths.clear();
                        alreadySelectorPaths.add(path);
                        adapter.setLastIsSelect(false);
                    } else {
                        alreadySelectorPaths.remove(path);
                    }
                } else if (isChecked) {
                    if (!alreadySelectorPaths.contains(path)) {
                        alreadySelectorPaths.add(path);
                    }
                    if (alreadySelectorPaths.size() > maxCount) {
                        showSnackbar("最多可选择" + maxCount + "张");
                        alreadySelectorPaths.remove(alreadySelectorPaths.size() - 1);
                        flag = false;
                    }
                } else {
                    alreadySelectorPaths.remove(path);
                }
                setComplete(alreadySelectorPaths.size());
                return flag;
            }
        });
        onBindData();
    }

    private void setComplete(int selectCount) {
        String text;
        if (maxCount == 0) {
            text = getString(R.string.sure);
        } else {
            text = String.format(getString(R.string.sure) + "(%d/%d)", selectCount, maxCount);
        }
        mSelectTv.setText(text);
    }

    public void onBindData() {
        imgPathList.add(null);
        Intent intent = getIntent();
        intent.putExtra("code", 200);
        startIntent(intent);
    }

    /**
     * 获取指定路径下的所有图片文件。
     */
    private ArrayList<String> getAllImagePathsByFolder(String folderPath) {
        File folder = new File(folderPath);
        String[] allFileNames = folder.list();
        if (allFileNames == null || allFileNames.length == 0) {
            return null;
        }
        ArrayList<String> imageFilePaths = new ArrayList<>();
        for (int i = allFileNames.length - 1; i >= 0; i--) {
            if (ImageUtils.isImage(allFileNames[i])) {
                imageFilePaths.add(folderPath + File.separator + allFileNames[i]);
            }
        }
        return imageFilePaths;
    }

    /**
     * 使用ContentProvider读取SD卡最近图片。
     */
    private ArrayList<String> getLatestImagePaths(int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();
        // 只查询jpg、png、gif的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png", "image/gif"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<String> latestImagePaths = new ArrayList<String>();
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                latestImagePaths = new ArrayList<String>();
                while (true) {
                    // 获取图片的路径
                    String path = cursor.getString(0);
                    latestImagePaths.add(path);
                    if (latestImagePaths.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }

        return latestImagePaths;
    }

    //从相册页面跳转至此页
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startIntent(intent);
    }

    private void startIntent(Intent intent) {
        //动画
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        if (intent.hasExtra(ALREADY_PATHS))
            alreadySelectorPaths = intent.getStringArrayListExtra(ALREADY_PATHS);
        if (alreadySelectorPaths == null) {
            alreadySelectorPaths = new ArrayList<>();
        }
        setComplete(alreadySelectorPaths.size());
        adapter.setAlreadyList(alreadySelectorPaths);
        code = intent.getIntExtra("code", -1);
        //某个相册
        folderPath = intent.getStringExtra("folderPath");
        requestPermission();
    }

    /**
     * 请求权限
     */
    @AfterPermissionGranted(requestPermissionCode)
    public void requestPermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            updateView(code, folderPath);
        } else {
            EasyPermissions.requestPermissions(SelectorImgActivity.this, "我们需要读写文件与拍照权限才能继续", requestPermissionCode, permissions);
        }
    }

    /**
     * 根据图片所属文件夹路径，刷新页面
     */
    private void updateView(int code, String folderPath) {
        imgPathList.clear();
        adapter.clearSelectionMap();
        imgPathList.add(null);
        if (code == 100) {   //某个相册
            if (isLatest || (folderPath != null && !folderPath.equals(currentFolder))) {
                currentFolder = folderPath;
                isLatest = false;
            }
            int lastSeparator = folderPath.lastIndexOf(File.separator);
            String folderName = folderPath.substring(lastSeparator + 1);
            setTitle(folderName);
            imgPathList.addAll(getAllImagePathsByFolder(folderPath));
        } else if (code == 200) {  //最近照片
            //“最近照片”
            if (!isLatest) {
                isLatest = true;
            }
            setTitle(R.string.latest_image);
            imgPathList.addAll(getLatestImagePaths(100));
        }
        if (imgPathList.size() > 0) {
            //滚动至顶部
            mPhotoWall.smoothScrollToPosition(0);
        }
    }

    /**
     * 点击返回时，跳转至相册页面
     */
    private void goAlbum() {
        Intent intent = new Intent(this, PhotoAlbumActivity.class);
        intent.putExtra("title", "选择相册");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if (imgPathList != null && imgPathList.size() > 1) {
            intent.putExtra("latest_count", imgPathList.size());
            intent.putExtra("latest_first_img", imgPathList.get(1));
        }
        startActivity(intent);
        //动画
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    private void getCameraImg() {
        String fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";
        savePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        SystemActionManager.startActionCamera(this, savePath);
    }


    private void startCropActivity(@NonNull Uri uri) {
        UCrop uCrop = UCrop.of(uri, mDestinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        uCrop.withOptions(options);
        uCrop.withTargetActivity(UCropActivity.class);
        uCrop.start(SelectorImgActivity.this);
    }

    private void setResult(ArrayList<String> paths) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("code", paths != null ? 100 : 101);
        intent.putStringArrayListExtra(ALREADY_PATHS, paths);
        setResult(RESULT_CODE, intent);
        sendBroadcast(new Intent(PhotoAlbumActivity.EXIT));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        requestPermission();
    }

    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == 0) {//第1条数据拍照
            getCameraImg();
        } else {
            List<String> tempList = (List<String>) imgPathList.clone();
            tempList.remove(0);//去除空的
            PhotoShowAndSelectorActivity.startAction(this, getIntent().getStringExtra(TITLE), view, tempList, alreadySelectorPaths, maxCount, position - 1, isCrop);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SystemActionManager.REQUEST_CODE_CAMERA:
                    if (new File(savePath).exists()) {
                        if (isCrop) {
                            Uri uri = Uri.fromFile(new File(savePath));
                            startCropActivity(uri);
                        } else {
                            if (alreadySelectorPaths.size() >= maxCount) {
                                alreadySelectorPaths.remove(alreadySelectorPaths.size() - 1);
                            }
                            alreadySelectorPaths.add(0, savePath);
                            setResult(alreadySelectorPaths);
                        }
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    crop(data);
                    break;
                case PhotoShowAndSelectorActivity.REQUEST_CODE:
                    if (UCropActivity.class.getSimpleName().equalsIgnoreCase(data.getStringExtra("from"))) {
                        crop(data);
                    } else {
                        ArrayList<String> tempList = data.getStringArrayListExtra(ALREADY_PATHS);
                        if (tempList != null && tempList.size() > 0) {
                            setResult(tempList);
                        }
                    }
                    break;
            }
        }
    }

    public void crop(Intent data) {
        Uri imageUri = UCrop.getOutput(data);
        if (imageUri != null && imageUri.getScheme().equals("file")) {
            try {
                String path = imageUri.getPath();
                if (alreadySelectorPaths.size() >= maxCount) {
                    alreadySelectorPaths.remove(alreadySelectorPaths.size() - 1);
                }
                alreadySelectorPaths.add(path);
                setResult(alreadySelectorPaths);
            } catch (Exception e) {
                showToast("裁剪失败，请重试");
            }
        } else {
            showToast("裁剪失败");
        }
    }
}
