package org.quick.library.m;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.quick.library.R;
import org.quick.library.mvp.BaseModel;
import org.quick.library.config.QuickConfigConstant;
import org.quick.component.Log2;
import org.quick.component.QuickSPHelper;
import org.quick.component.utils.DateUtils;
import org.quick.component.utils.GsonUtils;
import org.quick.component.utils.check.CheckUtils;
import org.quick.component.utils.encipherment.Md5Utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 网络请求的相关方法
 *
 * @author hzm
 * @Date 28/9/23
 * @modifyInfo1 28/9/23
 * @modifyContent
 */

public class HttpManager {

    /* 签名秘钥 */
    private static final String SIGN_P = "cq196.cn";

    /**
     * 对参数进行md5加密
     *
     * @param pars 网络请求的参数
     */
    private static void addSignPar(Map<String, String> pars) {

        if (pars != null) {

            String parString = pars.toString().substring(1, pars.toString().length());
            parString = parString.substring(0, parString.length() - 1);
            String[] array = parString.split(", ");
            Arrays.sort(array);
            StringBuilder sb = new StringBuilder();
            for (String str : array) {
                sb.append(str).append("&");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append(SIGN_P);
            String signStr = Md5Utils.getMD5String(sb.toString());
            pars.put("s", signStr);

        }
    }


    /**
     * Get请求
     *
     * @param url      请求的url
     * @param callback 网络请求的回调接口
     */
    public static void Get(String url, StringCallback callback) {

        OkHttpUtils.get().url(url).tag("default").build().execute(callback);
    }

    /**
     * Get请求
     *
     * @param url      请求的url
     * @param tag      请求的tag，可以用来取消网络请求
     * @param callback 网络请求的回调接口
     */
    public static void Get(String url, String tag, StringCallback callback) {

        OkHttpUtils.get().url(url).tag(tag).build().execute(callback);
    }

    public static void Post(boolean isCheck, final String url, Map<String, String> params, StringCallback callback) {
        Post(null, isCheck, url, "", params, callback);
    }

    public static void Post(Activity activity, final String url, StringCallback callback) {
        Post(activity, url, null, callback);
    }

    public static void Post(Activity activity, final String url, Map<String, String> params, StringCallback callback) {

        Post(activity, url, "noTag", params, callback);
    }

    public static void Post(Activity activity, final String url, final String tag, Map<String, String> params, final StringCallback callback) {
        Post(activity, true, url, tag, params, callback);
    }

    /**
     * Post请求
     *
     * @param url      请求的url
     * @param tag      请求的tag，可以用来取消网络请求
     * @param params   请求的参数
     * @param callback 网络请求的回调接口
     */
    public static void Post(final Activity activity, final boolean isCheck, final String url, final String tag, Map<String, String> params, final StringCallback callback) {
        if (CheckUtils.INSTANCE.checkActivityIsRunning(activity) && !CheckUtils.INSTANCE.isNetWorkAvailable()) {
            Toast.makeText(activity, activity.getString(R.string.errorNetWorkHint), Toast.LENGTH_SHORT).show();
            return;
        }
        if (params == null) {
            params = new HashMap<>();
        }
        if (!params.containsKey(QuickConfigConstant.APP_TOKEN)) {
            params.put(QuickConfigConstant.APP_TOKEN, (String) QuickSPHelper.INSTANCE.getValue(QuickConfigConstant.APP_TOKEN, "chrisZou"));
        }
        Log2.INSTANCE.e("okHttpUtils",
                String.format("Start：%s URL:%s?%s",
                        DateUtils.INSTANCE.formatToStr(DateUtils.INSTANCE.getCurrentTimeInMillis(), DateUtils.INSTANCE.getFormatDefault() + ":SSS"),
                        url,
                        params.toString().replace("}", "").replace("{", "")));
        OkHttpUtils.post().url(url).tag(tag).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                if (isCheck && !checkActivityIsRunning(activity)) {
                    Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                    return;
                }
                callback.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                Log2.INSTANCE.e("请求成功", "json : " + response);
                if (isCheck && !checkActivityIsRunning(activity)) {
                    Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                    return;
                }
                BaseModel bean = GsonUtils.INSTANCE.parseFromJson(response, BaseModel.class);
//                if (bean != null && bean.getCode().equalsIgnoreCase("5")) {
//                    if (isNormal && activity != null) {
//                        Toast.makeText(activity, "身份失效，请重新登陆", Toast.LENGTH_LONG).show();
//                    }
//                }
                callback.onResponse(response, id);
            }

            @Override
            public void onAfter(int id) {
                Log2.INSTANCE.e("okHttpUtils", String.format("\tEND：%s URL：%s", DateUtils.INSTANCE.formatToStr(DateUtils.INSTANCE.getCurrentTimeInMillis(), DateUtils.INSTANCE.getFormatDefault() + ":SSS"), url));
                if (isCheck && !checkActivityIsRunning(activity)) {
                    Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                    return;
                }
                callback.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {
                if (isCheck && !checkActivityIsRunning(activity)) {
                    Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                    return;
                }
                callback.onBefore(request, id);
            }
        });
    }

    /**
     * 根据tag取消网络请求
     *
     * @param tag 发起请求的tag
     */
    public static void Cancel(final String tag) {

        OkHttpUtils.getInstance().cancelTag(tag);
    }

    public static <T> void UploadImage(Activity activity, final String url, final File file, Map<String, String> params, Callback<T> callback) {

        UploadImage(activity, url, file, "noTag", params, callback);
    }

    public static <T> void UploadImage(Activity activity, final String url, final File file, final String tag, Map<String, String> params, Callback<T> callback) {

        UploadImage(activity, url, tag, file, params, callback);

    }

    /**
     * 上传图片
     *
     * @param url      请求的url
     * @param tag      请求的tag，可以用来取消网络请求
     * @param file     图片文件，一般可以根据图片名称获取得到如 new File(Environment.getExternalStorageDirectory() + "/" + "HaiPa/", name);
     * @param callback 网络请求的回调接口
     */
    public static <T> void UploadImage(final Activity activity, final String url, final String tag, final File file, Map<String, String> params, final Callback<T> callback) {
        if (CheckUtils.INSTANCE.checkActivityIsRunning(activity) && !CheckUtils.INSTANCE.isNetWorkAvailable()) {
            Toast.makeText(activity, activity.getString(R.string.errorNetWorkHint), Toast.LENGTH_SHORT).show();
            return;
        }
        if (params == null) {
            params = new HashMap<>();
        }
        if (!params.containsKey(QuickConfigConstant.INSTANCE.APP_TOKEN)) {
            params.put(QuickConfigConstant.INSTANCE.APP_TOKEN, (String) QuickSPHelper.INSTANCE.getValue(QuickConfigConstant.INSTANCE.APP_TOKEN, "chrisZou"));
        }
        Log2.INSTANCE.e("okHttpUtils",
                String.format("Start：%s URL:%s?%s",
                        DateUtils.INSTANCE.formatToStr(DateUtils.INSTANCE.getCurrentTimeInMillis(), DateUtils.INSTANCE.getFormatDefault() + ":SSS"),
                        url,
                        params.toString().replace("}", "").replace("{", "")));
        if (file == null) {
            OkHttpUtils.post().url(url).tag(tag).params(params).build().execute(callback);
        } else {
            OkHttpUtils.post().addFile("file", file.getName(), file).url(url).tag(tag).params(params).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    e.printStackTrace();
                    if (!checkActivityIsRunning(activity)) {
                        Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                        return;
                    }
                    callback.onError(call, e, id);
                }

                @Override
                public void onResponse(String response, int id) {
                    Log2.INSTANCE.e("请求成功", "json : " + response);
                    if (!checkActivityIsRunning(activity)) {
                        Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                        return;
                    }
                    BaseModel bean = GsonUtils.INSTANCE.parseFromJson(response, BaseModel.class);
//                if (bean != null && bean.getCode().equalsIgnoreCase("5")) {
//                    if (isNormal && activity != null) {
//                        Toast.makeText(activity, "身份失效，请重新登陆", Toast.LENGTH_LONG).show();
//                    }
//                }
                    callback.onResponse((T) response, id);
                }

                @Override
                public void onAfter(int id) {
                    Log2.INSTANCE.e("okHttpUtils", String.format("\tEND：%s URL：%s", DateUtils.INSTANCE.formatToStr(DateUtils.INSTANCE.getCurrentTimeInMillis(), DateUtils.INSTANCE.getFormatDefault() + ":SSS"), url));
                    if (!checkActivityIsRunning(activity)) {
                        Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                        return;
                    }
                    callback.onAfter(id);
                }

                @Override
                public void onBefore(Request request, int id) {
                    if (!checkActivityIsRunning(activity)) {
                        Log2.INSTANCE.e("网络请求", "依赖的Activity已销毁,取消任务");
                        return;
                    }
                    callback.onBefore(request, id);
                }
            });
        }
        Log2.INSTANCE.e("请求网络链接", "url : " + url);
    }


    /**
     * @param url      下载的地址
     * @param callback 下载文件的回调内部包含有进度信息
     */
    public static void DownLoadFile(String url, String tag, FileCallBack callback) {

        OkHttpUtils.get().tag(tag).url(url).build().execute(callback);
    }

    /**
     * @param url      文件上传地址
     * @param tag      请求的tag，可以用来取消网络请求
     * @param file     待上传的文件2
     * @param fileName 上传的文件名
     * @param params   上传的参数
     * @param callback
     */
    public static void UpLoadFile(String url, String tag, File file, String fileName, Map<String, String> params, StringCallback callback) {

        OkHttpUtils.post().addFile("mFile", fileName, file).url(url).tag(tag)
                .params(params).build().execute(callback);
    }


    /**
     * @param url      图片的地址
     * @param tag      请求的tag，可以用来取消网络请求
     * @param callback 图片下载的回调
     */
    public void DownImage(String url, String tag, BitmapCallback callback) {

        OkHttpUtils.get().url(url).tag(tag).build().execute(callback);
    }

    public static Map<String, String> getParams(String... params) {
        Map<String, String> tempParams = new HashMap<>();
        for (String keyValue : params) {
//            tempParams.put(keyValue.getClass().c)
        }
        return tempParams;
    }

    public static boolean checkActivityIsRunning(Activity activity) {
        return !(activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()));
    }
}

