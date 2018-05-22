package org.chris.quick.tools.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageView;

import org.chris.quick.m.ImageManager;


/**
 * Created by Administrator on 2016/6/6.
 */
public class ImageUtils {

    /**
     * 获取指定大小的位图
     *
     * @param res
     * @param resId
     * @param reqWidth  希望取得的宽度
     * @param reqHeight 希望取得的高度
     * @return 按指定大小压缩之后的图片
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html#read-bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 获取指定大小的位图
     *
     * @param filePath  文件路径
     * @param reqWidth  希望取得的宽度
     * @param reqHeight 希望取得的高度
     * @return 按指定大小压缩之后的图片
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html#read-bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算与指定位图的大小比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return 缩放的比例
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html #read-bitmap
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // 取高宽中更大一边，进行同比例缩放，这样才不会变形。
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * 显示图片
     *
     * @param img
     * @param url
     */
    public static void displayImg(Context context, ImageView img, String url) {
        ImageManager.loadCircleImage(context, url, img);
    }

    /**
     * 判断该文件是否是一个图片。
     */
    public static boolean isImage(String fileName) {
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    public static StateListDrawable getSelector(Context context, int idSelected, int idUnselect) {
        return getSelector(context, idUnselect, idSelected, idSelected, idUnselect);
    }

    private static StateListDrawable getSelector(Context context, int idNormal, int idPressed, int idFocused, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }
}
