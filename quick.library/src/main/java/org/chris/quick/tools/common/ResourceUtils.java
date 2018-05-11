package org.chris.quick.tools.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;


/**
 * Created by chris on 2016/5/9.
 */
public class ResourceUtils {


    /**
     * 设置本地
     *
     * @param context
     * @param imageView
     * @param prefix    前缀
     * @param postfix   后缀
     */
    public static void setImgResource(Context context, ImageView imageView, String prefix, String postfix) {
        String resName = prefix + postfix;
        try {
            int resId = context.getResources().getIdentifier(resName, "mipmap", context.getPackageName());
            imageView.setImageResource(resId);
        } catch (Exception ex) {
            Log.e("setImgResource", "set image resource aborted");
        }
    }

    /**
     * @param context
     * @param postfix 后缀
     * @return
     * @throws Resources.NotFoundException
     */
    public static int getMipmapResId(Context context, String prefix, String postfix) throws Resources.NotFoundException {

        return context.getResources().getIdentifier(prefix + postfix, "mipmap", context.getPackageName());
    }

    /**
     * @param context
     * @param postfix 后缀
     * @return
     * @throws Resources.NotFoundException
     */
    public static int getId(Context context, String prefix, String postfix) throws Resources.NotFoundException {

        return context.getResources().getIdentifier(prefix + postfix, "id", context.getPackageName());
    }

    /**
     * @param context
     * @param imageView
     * @param postfix   资源名称后缀
     */
    public static void setImageView(Context context, final ImageView imageView, String prefix, String postfix) {

        imageView.setImageResource(getMipmapResId(context, prefix, postfix));
    }

    /**
     * 设置imageview的资源
     *
     * @param context
     * @param imageView
     * @param postfix   资源名称的后缀
     */
    public static void setImageViewForMipmap(Context context, ImageView imageView, String prefix, String postfix) {

        setImageView(context, imageView, prefix, postfix);
    }


}
