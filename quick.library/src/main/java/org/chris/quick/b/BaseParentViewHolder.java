package org.chris.quick.b;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.chris.quick.tools.common.ImageUtils;


/**
 * Created by chris Zou on 2016/7/4.
 */
public class BaseParentViewHolder {

    private SparseArray<View> mViews;
    public View rootView;

    public BaseParentViewHolder(View rootView) {

        if (rootView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.rootView = rootView;
        this.mViews = new SparseArray<>();
    }

    public <V extends View> V getView(int id) {

        View view = mViews.get(id);
        if (view == null) {
            view = rootView.findViewById(id);
            mViews.put(id, view);
        }
        return (V) view;
    }

    public BaseParentViewHolder setText(int id, CharSequence content) {

        return setText(id, content, null);
    }

    public BaseParentViewHolder setText(int id, CharSequence content, View.OnClickListener onClickListener) {

        TextView textView = getView(id);
        textView.setText(content);
        textView.setOnClickListener(onClickListener);
        return this;
    }

    public BaseParentViewHolder setImgRes(Context context, int id, int iconId) {

        return setImg(context, id, "", iconId, null);
    }

    public BaseParentViewHolder setImgRes(Context context, int id, int iconId, View.OnClickListener onClickListener) {

        return setImg(context, id, "", iconId, onClickListener);
    }

    public BaseParentViewHolder setImgUrl(Context context, int id, CharSequence url) {

        return setImg(context, id, url, 0, null);
    }

    public BaseParentViewHolder setImgUrl(Context context, int id, CharSequence url, View.OnClickListener onClickListener) {

        return setImg(context, id, url, 0, onClickListener);
    }

    public BaseParentViewHolder setOnClickListener(int id, View.OnClickListener onClickListener) {

        getView(id).setOnClickListener(onClickListener);
        return this;
    }

    public BaseParentViewHolder setBackgroundResource(int id, int bgResId) {

        getView(id).setBackgroundResource(bgResId);
        return this;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public BaseParentViewHolder setBackground(int id, Drawable background) {

        getView(id).setBackground(background);
        return this;
    }

    public BaseParentViewHolder setBackGroundColor(int id, int color) {

        getView(id).setBackgroundColor(color);
        return this;
    }

    private BaseParentViewHolder setImg(Context context, int id, CharSequence url, int iconId, View.OnClickListener onClickListener) {

        ImageView img = getView(id);
        if (!TextUtils.isEmpty(url)) {
            ImageUtils.displayImg(context, img, url + "");
        }
        if (iconId != 0) {
            img.setImageResource(iconId);
        }
        img.setOnClickListener(onClickListener);
        return this;
    }

    public void onDestroy() {

        mViews.clear();
        mViews = null;
        rootView = null;
    }
}
