/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package org.quick.library.function.selectorimg;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.quick.library.R;
import org.quick.library.b.activities.ThemeActivity;
import org.quick.library.function.SelectorImgActivity;
import org.quick.component.utils.check.CheckUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * 分相册查看SD卡所有图片。
 * Created by hanj on 14-10-14.
 */
public class PhotoAlbumActivity extends ThemeActivity {

    public static final String EXIT="Exit";

    @Override
    public void init() {
        onBindData();
    }

    @Override
    public int onResultLayoutResId() {
        SelectorImgActivity.albumActivity = this;
        return R.layout.activity_photo_album;
    }

    BroadcastReceiver exitReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    public void onBindData() {
        registerReceiver();
        if (!CheckUtils.INSTANCE.isSDcardOK()) {
            Toast.makeText(this, "SD卡不可用。", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent t = getIntent();
        if (!t.hasExtra("latest_count")) {
            return;
        }
        ListView listView = (ListView) findViewById(R.id.select_img_listView);

        final ArrayList<PhotoAlbumLVItem> list = new ArrayList<>();
        //“最近照片”
        list.add(new PhotoAlbumLVItem(getResources().getString(R.string.latest_image), t.getIntExtra("latest_count", -1), t.getStringExtra("latest_first_img")));
        //相册
        list.addAll(getImagePathsByContentProvider());
        PhotoAlbumLVAdapter adapter = new PhotoAlbumLVAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhotoAlbumActivity.this, SelectorImgActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //第一行为“最近照片”
                if (position == 0) {
                    intent.putExtra("code", 200);
                } else {
                    intent.putExtra("code", 100);
                    intent.putExtra("folderPath", list.get(position).getPathName());
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 获取目录中图片的个数。
     */
    private int getImageCount(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            if (CheckUtils.INSTANCE.isImgUrl(file.getName())) {
                count++;
            }
        }

        return count;
    }

    /**
     * 获取目录中最新的一张图片的绝对路径。
     */
    private String getFirstImagePath(File folder) {
        File[] files = folder.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            File file = files[i];
            if (CheckUtils.INSTANCE.isImgUrl(file.getName())) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 使用ContentProvider读取SD卡所有图片。
     */
    private ArrayList<PhotoAlbumLVItem> getImagePathsByContentProvider() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png", "image/gif"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<PhotoAlbumLVItem> list = null;
        if (cursor != null) {
            if (cursor.moveToLast()) {
                //路径缓存，防止多次扫描同一目录
                HashSet<String> cachePath = new HashSet<>();
                list = new ArrayList<>();

                do {
                    // 获取图片的路径
                    String imagePath = cursor.getString(0);

                    File parentFile = new File(imagePath).getParentFile();
                    String parentPath = parentFile.getAbsolutePath();

                    //不扫描重复路径
                    if (!cachePath.contains(parentPath)) {
                        list.add(new PhotoAlbumLVItem(parentPath, getImageCount(parentFile), getFirstImagePath(parentFile)));
                        cachePath.add(parentPath);
                    }

                } while (cursor.moveToPrevious());
            }

            cursor.close();
        }

        return list;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //动画
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    @Override
    public void finish() {
        //动画
        overridePendingTransition(R.anim.out_from_right, R.anim.in_from_left);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(exitReceiver);
    }

    public void registerReceiver(){
        super.registerReceiver(exitReceiver,new IntentFilter(EXIT));
    }
}
