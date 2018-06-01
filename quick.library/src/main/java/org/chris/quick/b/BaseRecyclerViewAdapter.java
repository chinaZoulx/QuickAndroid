package org.chris.quick.b;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import org.chris.quick.R;
import org.chris.quick.listener.OnClickListener2;
import org.chris.quick.m.ImageManager;
import org.chris.quick.m.Log;
import org.chris.quick.tools.common.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris Zou on 2016/6/12.
 * 基础适配器,继承便能快速生成adapter
 * 分割线需使用 {@link org.chris.quick.widgets.XRecyclerViewLine#setDivider(Drawable, int, int, int, int)}
 *
 * @author chris Zou
 * @Date 2016/6/12
 */
public abstract class BaseRecyclerViewAdapter<M> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {//<M extends BaseRecyclerViewAdapter.BaseModel>
    private List<M> dataList = new ArrayList<>();
    public Context mContext;

    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;
    public OnClickListener mOnClickListener;
    public OnCheckedChangedListener mOnCheckedChangedListener;
    public int[] clickResId;
    public int[] checkedChangedResId;

    public Context getContext() {
        return mContext;
    }

    /**
     * 布局文件
     *
     * @return
     */
    public abstract int onResultLayoutResId();

    public abstract void onBindData(@NonNull BaseViewHolder holder, int position, M itemData);

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemMargin() {
        return 0;
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemMarginTop(int position) {
        if (onResultItemMargin() > 0)
            return onResultItemMargin() / 2;
        return onResultItemMargin();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemMarginBottom(int position) {
        if (onResultItemMargin() > 0)
            return onResultItemMargin() / 2;
        return onResultItemMargin();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemMarginLeft(int position) {
        return onResultItemMargin();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemMarginRight(int position) {
        return onResultItemMargin();
    }

    public int onResultItemPadding() {
        return 0;
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemPaddingTop(int position) {
        if (onResultItemPadding() > 0)
            return onResultItemPadding() / 2;
        return onResultItemPadding();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemPaddingBottom(int position) {
        if (onResultItemPadding() > 0)
            return onResultItemPadding() / 2;
        return onResultItemPadding();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemPaddingLeft(int position) {
        return onResultItemPadding();
    }

    /**
     * 上下左右的padding
     *
     * @return
     */
    public int onResultItemPaddingRight(int position) {
        return onResultItemPadding();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(onResultLayoutResId(), parent, false);
        return setupLayout(view);
    }

    protected BaseViewHolder setupLayout(View itemView) {
        if (itemView instanceof CardView) {
            CardView cardView = (CardView) itemView;
            if (cardView.getForeground() == null)
                cardView.setForeground(ContextCompat.getDrawable(mContext, CommonUtils.getSystemAttrTypeValue(mContext, R.attr.selectableItemBackgroundBorderless).resourceId));
        } else {
            if (itemView.getBackground() == null)
                itemView.setBackgroundResource(CommonUtils.getSystemAttrTypeValue(mContext, R.attr.selectableItemBackground).resourceId);
        }
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        setupListener(holder, position);
        setupLayout(holder, position);
        onBindData(holder, position, dataList.get(position));
    }

    /**
     * 设置各种监听
     *
     * @param holder
     * @param position
     */
    private void setupListener(final BaseViewHolder holder, final int position) {
        /*单击事件*/
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnClickListener2() {

                @Override
                public void onClick2(View v) {
                    mOnItemClickListener.onClick(v, position);
                }

            });
        }
        /*长按事件*/
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListener.onLongClick(v, position);
                }
            });
        }
        /*选择事件*/
        if (mOnCheckedChangedListener != null && checkedChangedResId != null && checkedChangedResId.length > 0) {
            for (int resId : checkedChangedResId) {
                View compoundButton = holder.getView(resId);
                if (compoundButton instanceof CompoundButton)
                    ((CompoundButton) compoundButton).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            mOnCheckedChangedListener.onCheckedChanged(buttonView, position, isChecked);
                        }
                    });
                else
                    Log.e("列表选择事件错误：", String.format("from%s id:%d类型不正确，无法设置OnCheckedChangedListener", getContext().getClass().getSimpleName(), resId));
//                    throw new ClassCastException(String.format("id:%d类型不正确，无法设置OnCheckedChangedListener", resId));
            }
        }
        /*item项内View的独立点击事件，与OnItemClickListner不冲突*/
        if (mOnClickListener != null && clickResId != null && clickResId.length > 0) {
            for (int resId : clickResId) {
                try {
                    holder.setOnClickListener(new OnClickListener2() {
                        @Override
                        public void onClick2(View v) {
                            mOnClickListener.onClicked(v, holder, position);
                        }
                    }, resId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置布局
     *
     * @param holder
     * @param position
     */
    private void setupLayout(final BaseViewHolder holder, final int position) {
        if (onResultItemMargin() > 0) {
            int margin = AutoUtils.getPercentHeightSize(onResultItemMargin());
            int left = AutoUtils.getPercentWidthSize(onResultItemMarginLeft(position));
            int top = AutoUtils.getPercentHeightSize(onResultItemMarginTop(position));
            int right = AutoUtils.getPercentHeightSize(onResultItemMarginRight(position));
            int bottom = AutoUtils.getPercentWidthSize(onResultItemMarginBottom(position));

            if (position == getItemCount() - 1) {
                bottom = margin;
            }
            RecyclerView.LayoutParams itemLayoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            itemLayoutParams.setMargins(left, top, right, bottom);
        }
        if (onResultItemPadding() > 0) {
            int padding = AutoUtils.getPercentHeightSize(onResultItemPadding());
            int left = AutoUtils.getPercentWidthSize(onResultItemPaddingLeft(position));
            int top = AutoUtils.getPercentHeightSize(onResultItemPaddingTop(position));
            int right = AutoUtils.getPercentHeightSize(onResultItemPaddingRight(position));
            int bottom = AutoUtils.getPercentWidthSize(onResultItemPaddingBottom(position));

            if (position == getItemCount() - 1) {
                bottom = padding;
            }
            holder.itemView.setPadding(left, top, right, bottom);
        }
    }

    public void setDataList(@NonNull List<M> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void addDataAll(@NonNull List<M> dataList) {
        if (dataList.size() > 0) {
            getDataList().addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        remove(getItem(position));
    }

    public void remove(M m) {
        getDataList().remove(m);
        notifyDataSetChanged();
    }

    public void removeAll() {
        getDataList().clear();
        notifyDataSetChanged();
    }

    public void add(M m) {
        getDataList().add(m);
        notifyDataSetChanged();
    }

    public List<M> getDataList() {
        return dataList;
    }

    public M getItem(int position) {
        return getDataList().get(position);
    }

    public void setOnClickListener(OnClickListener onClickListener, int... params) {
        this.clickResId = params;
        this.mOnClickListener = onClickListener;
    }

    public void setOnCheckedChangedListener(OnCheckedChangedListener onCheckedChangedListener, int... checkedChangedResId) {
        this.checkedChangedResId = checkedChangedResId;
        this.mOnCheckedChangedListener = onCheckedChangedListener;
    }

    public void setOnItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener) {

        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;

        public BaseViewHolder(View itemView) {

            super(itemView);
            this.mViews = new SparseArray<>();
        }

        public <T extends View> T getView(@IdRes int id) {

            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public BaseViewHolder setText(@IdRes int id, CharSequence content) {

            return setText(id, content, null);
        }

        public BaseViewHolder setText(@IdRes int id, CharSequence content, View.OnClickListener onClickListener) {
            TextView textView = getView(id);
            textView.setText(content);
            if (onClickListener != null) {
                textView.setOnClickListener(onClickListener);
            }

            return this;
        }

        /**
         * 原样本地图片
         *
         * @param id
         * @param iconId
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, @DrawableRes int iconId) {

            return setImg(id, false, 0, "", iconId, null);
        }

        /**
         * 圆角本地图片
         *
         * @param id
         * @param radius
         * @param iconId
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, int radius, @DrawableRes int iconId) {

            return setImg(id, false, radius, "", iconId, null);
        }

        /**
         * 圆角本地图片
         *
         * @param id
         * @param isCir
         * @param iconId
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, boolean isCir, @DrawableRes int iconId) {

            return setImg(id, isCir, 0, "", iconId, null);
        }

        /**
         * 圆角本地图片
         *
         * @param id
         * @param isCir
         * @param iconId
         * @param onClickListener
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, boolean isCir, @DrawableRes int iconId, View.OnClickListener onClickListener) {

            return setImg(id, isCir, 0, "", iconId, onClickListener);
        }

        /**
         * 原样网络图片
         *
         * @param id
         * @param url
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, CharSequence url) {

            return setImg(id, false, 0, url, 0, null);
        }

        /**
         * 圆角网络图片
         *
         * @param id
         * @param radius
         * @param url
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, int radius, CharSequence url) {

            return setImg(id, false, radius, url, 0, null);
        }

        /**
         * 圆角网络图片
         *
         * @param id
         * @param isCir
         * @param url
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, boolean isCir, CharSequence url) {

            return setImg(id, isCir, 0, url, 0, null);
        }

        /**
         * 圆角网络图片
         *
         * @param id
         * @param isCir
         * @param url
         * @param onClickListener
         * @return
         */
        public BaseViewHolder setImg(@IdRes int id, boolean isCir, CharSequence url, View.OnClickListener onClickListener) {

            return setImg(id, isCir, 0, url, 0, onClickListener);
        }

        /**
         * @param id              图片ID
         * @param isCir           是否正圆
         * @param radius          圆角
         * @param url             网络链接
         * @param iconId          图片资源ID
         * @param onClickListener 监听
         * @return
         */
        private synchronized BaseViewHolder setImg(int id, boolean isCir, int radius, CharSequence url, int iconId, View.OnClickListener onClickListener) {

            ImageView img = getView(id);
            if (!TextUtils.isEmpty(url) && isCir) {
                ImageManager.loadCircleImage(itemView.getContext(), url + "", img);
            } else if (!TextUtils.isEmpty(url)) {
                if (radius == 0)
                    ImageManager.loadImage(itemView.getContext(), url + "", img);
                else
                    ImageManager.loadRoundImage(itemView.getContext(), url + "", radius, img);
            }
            if (iconId != 0) {
                ImageManager.loadImage(itemView.getContext(), iconId, img);
                img.setImageResource(iconId);
            }
            if (onClickListener != null) img.setOnClickListener(onClickListener);
            return this;
        }

        public BaseViewHolder setOnClickListener(View.OnClickListener onClickListener, @IdRes int... ids) {
            for (int id : ids)
                getView(id).setOnClickListener(onClickListener);
            return this;
        }

        public BaseViewHolder setOnClickListener(View.OnClickListener onClickListener, @IdRes int id) {
            getView(id).setOnClickListener(onClickListener);
            return this;
        }

        public BaseViewHolder setBackgroundResource(@IdRes int id, int bgResId) {

            getView(id).setBackgroundResource(bgResId);
            return this;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public BaseViewHolder setBackground(@IdRes int id, Drawable background) {

            getView(id).setBackground(background);
            return this;
        }

        public BaseViewHolder setBackgroundColor(@IdRes int id, int background) {

            getView(id).setBackgroundColor(background);
            return this;
        }

        public TextView getTextView(@IdRes int id) {
            return getView(id);
        }

        public Button getButton(@IdRes int id) {
            return getView(id);
        }

        public ImageView getImageView(@IdRes int id) {
            return getView(id);
        }

        public LinearLayout getLinearLayout(@IdRes int id) {
            return getView(id);
        }

        public RelativeLayout getRelativeLayout(@IdRes int id) {
            return getView(id);
        }

        public FrameLayout getFramLayout(@IdRes int id) {
            return getView(id);
        }

        public CheckBox getCheckBox(@IdRes int id) {
            return getView(id);
        }

        public EditText getEditText(@IdRes int id) {
            return getView(id);
        }

        public BaseViewHolder setVisibility(int visibility, @IdRes int... resIds) {
            for (int resId : resIds)
                getView(resId).setVisibility(visibility);
            return this;
        }
    }


    /**
     * 每个Item项单个单击
     */
    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    /**
     * 每个Item项长按事件
     */
    public interface OnItemLongClickListener {
        boolean onLongClick(View v, int position);
    }

    /**
     * 每个Item里面的控件-每个的单击事件，与OnItemLongClickListener、OnItemClickListener不冲突
     */
    public interface OnClickListener {
        void onClicked(View view, BaseViewHolder holder, int position);
    }

    /**
     * 每个Item里面的控件-每个的选择事件
     */
    public interface OnCheckedChangedListener {
        void onCheckedChanged(View view, int position, boolean isChecked);
    }
}
