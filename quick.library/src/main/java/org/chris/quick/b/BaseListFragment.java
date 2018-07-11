package org.chris.quick.b;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.chris.quick.R;
import org.chris.quick.b.fragments.ThemeFragment;
import org.chris.quick.config.QuickConfigConstant;
import org.chris.quick.function.IsOkDialog;
import org.chris.quick.function.LoadingDialog;
import org.chris.quick.listener.OnClickListener2;
import org.chris.quick.m.HttpManager;
import org.chris.quick.tools.GsonUtils;
import org.chris.quick.tools.common.HttpUtils;
import org.chris.quick.widgets.XRecyclerViewUsingLine;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;

/**
 * Created by work on 2017/8/2.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public abstract class BaseListFragment extends ThemeFragment implements XRecyclerView.LoadingListener {

    public enum ErrorType {
        /**
         * 没有数据
         */
        errorNoMsg,
        /**
         * 网络问题
         */
        errorNetWork,
        /**
         * 服务器问题
         */
        errorService,
        /**
         * 其他问题-自定义
         */
        errorOther,
        normal;

        public int value;
    }

    /**
     * 分页关键字
     */
    public static final String PAGER_NUMBER_KEY = "pageNumber";
    public static final int PAGER_FIRST_NUMBER=1;

    private BaseListFragment.OnRequestListener onRequestListener;

    public View noMsgContainer;
    private ImageView hintErrorIv;
    private TextView hintErrorTv;
    public Button refreshBtn;

    private FrameLayout headerContainer;
    private FrameLayout footerContainer;
    private FrameLayout listContainer;
    private XRecyclerViewUsingLine xRecyclerView;
    private ArrowRefreshHeader mRefreshHeader;

    boolean isDefaultNoMsgLayout = true;
    public int pageNumber = 1;
    public IsOkDialog isOkDialog;
    public LoadingDialog loadingDialog;
    ErrorType errorType = ErrorType.normal;
    private Map<String, String> params = new HashMap<>();

    @Override
    public void init() {
        isOkDialog = new IsOkDialog(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
        setupLayout();
        onInit();
        start();
    }

    @Override
    protected int onResultLayoutResId() {
        return R.layout.app_base_list;
    }

    @Override
    public boolean isUsingBaseLayout() {
        return true;
    }

    private void setupLayout() {
        listContainer = getView(R.id.listContainer);
        xRecyclerView = getView(R.id.recyclerView);
        headerContainer = getView(R.id.headerContainer);
        footerContainer = getView(R.id.footerContainer);

        noMsgContainer = onResultNoMsgLayout();
        listContainer.addView(noMsgContainer);
        noMsgContainer.setVisibility(View.GONE);
        if (isDefaultNoMsgLayout) {
            refreshBtn = getView(R.id.refreshBtn, noMsgContainer);
            hintErrorIv = getView(R.id.hintErrorIv, noMsgContainer);
            hintErrorTv = getView(R.id.hintErrorTv, noMsgContainer);
            refreshBtn.setOnClickListener(new OnClickListener2() {
                @Override
                public void onClick2(View view) {
                    onRefreshClick(errorType);
                }
            });
        }

        getRecyclerView().setPullRefreshEnabled(isPullRefreshEnable());
        getRecyclerView().setLoadingMoreEnabled(isLoadMoreEnable());
        getRecyclerView().setRefreshProgressStyle(onResultProgressStylePullRefresh());
        getRecyclerView().setLoadingMoreProgressStyle(onResultProgressStyleLoadMore());
        getRecyclerView().setArrowImageView(onResultPullRefreshIcon());
        RecyclerView.LayoutManager layoutManager = onResultLayoutManager();
        getRecyclerView().setLayoutManager(layoutManager);
        getRecyclerView().setHasFixedSize(true);
        getRecyclerView().setNestedScrollingEnabled(true);
        getRecyclerView().setLoadingListener(this);
        getRecyclerView().setAdapter(onResultAdapter());
        if (isShowCuttingLine())
            getRecyclerView().addItemDecoration(onResultCuttingLine());
        try {
            Class<?> class1 = Class.forName("com.jcodecraeer.xrecyclerview.XRecyclerView");
            Field field = class1.getDeclaredField("mRefreshHeader");
            field.setAccessible(true);
            mRefreshHeader = (ArrowRefreshHeader) field.get(getRecyclerView());
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setHeaderContainer(View view) {
        headerContainer.removeAllViews();
        headerContainer.addView(view);
    }

    public void setFooterContainer(View view) {
        footerContainer.removeAllViews();
        footerContainer.addView(view);
    }

    @Override
    public void onRefresh() {
        if (getActivity() == null) {
            return;
        }
        if (!HttpUtils.isNetWorkAvailable(getActivity())) {
            setHasData(false, BaseListFragment.ErrorType.errorNetWork);
            onRequestError(errorType);
            return;
        }
        onShowLoading();
        requestData(pageNumber = 1);
        if (isPullRefreshEnable() && mRefreshHeader.getState() != ArrowRefreshHeader.STATE_REFRESHING)
            mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
    }

    @Override
    public void onLoadMore() {
        requestData(++pageNumber);
    }

    public void requestData(int pageNumber) {
        params.clear();
        onResultParams(params);
        checkNotNull();
        if (!params.containsKey(BaseListFragment.PAGER_NUMBER_KEY))
            params.put(BaseListFragment.PAGER_NUMBER_KEY, pageNumber + "");
        HttpManager.Post(getActivity(), onResultUrl(), params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                setHasData(false, BaseListFragment.ErrorType.errorService);
                onRequestError(errorType);
            }

            @Override
            public void onResponse(String response, int id) {
                if (getActivity() == null) {
                    return;
                }
                BaseModel model = GsonUtils.INSTANCE.parseFromJson(response, BaseModel.class);
                if (model != null) {
                    if (model.getCode() == QuickConfigConstant.APP_SUCCESS_TAG) {//成功了返回
                        getRecyclerView().setNoMore(false);
                        setHasData(true);
                        onRequestDataSuccess(response, isPullRefresh());
                    } else if (isPullRefresh()) {//下拉刷新
                        setHasData(false);
                        showToast(model.getMsg());
                    } else {//上拉加载
                        getRecyclerView().setNoMore(true);
                        setHasData(true);
                    }
                } else {
                    onRequestError(errorType);
                    setHasData(false, BaseListFragment.ErrorType.errorService);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (getActivity() == null) {
                    return;
                }
                onCloseLoading();
                if (isPullRefresh() && isPullRefreshEnable() || mRefreshHeader.getState() == ArrowRefreshHeader.STATE_REFRESHING) {
                    getRecyclerView().refreshComplete();
                } else if (isLoadMoreEnable()) {
                    getRecyclerView().loadMoreComplete();
                }
                if (onRequestListener != null) {
                    onRequestListener.onRequestEnd();
                }
                onRequestEnd();
            }
        });
    }

    public void setNoMore(boolean isNoMore) {
        getRecyclerView().setNoMore(isNoMore);
    }

    /**
     * 设置是否有数据
     *
     * @param isHasData
     */
    public void setHasData(boolean isHasData) {
        setHasData(isHasData, BaseListFragment.ErrorType.errorNoMsg);
    }

    /**
     * 设置是否有数据
     *
     * @param isHasData
     */
    public void setHasData(boolean isHasData, BaseListFragment.ErrorType type) {
        if (isHasData) {//有
            errorType = ErrorType.normal;
            if (noMsgContainer.getVisibility() == View.VISIBLE)
                noMsgContainer.setVisibility(View.GONE);
            if (getRecyclerView().getVisibility() == View.GONE)
                getRecyclerView().setVisibility(View.VISIBLE);
        } else {//无
            if (noMsgContainer.getVisibility() == View.GONE)
                noMsgContainer.setVisibility(View.VISIBLE);
            if (getRecyclerView().getVisibility() == View.VISIBLE)
                getRecyclerView().setVisibility(View.GONE);
            setHintErrorStyle(type);
        }
    }

    private void setHintErrorStyle(BaseListFragment.ErrorType type) {
        this.errorType = type;
        if (isDefaultNoMsgLayout) {
            switch (type) {
                case errorNoMsg:
                    hintErrorIv.setImageResource(onResultErrorNoMsgIcon());
                    hintErrorTv.setText(onResultErrorNoMsgTxt());
                    refreshBtn.setVisibility(View.GONE);
                    refreshBtn.setText(onResultErrorBtnTxt());
                    break;
                case errorNetWork:
                    hintErrorIv.setImageResource(onResultErrorNetWorkIcon());
                    hintErrorTv.setText(onResultErrorNetWorkTxt());
                    refreshBtn.setVisibility(View.VISIBLE);
                    refreshBtn.setText(onResultErrorBtnTxt());
                    break;
                case errorService:
                    hintErrorIv.setImageResource(onResultErrorServiceIcon());
                    hintErrorTv.setText(onResultErrorServiceTxt());
                    refreshBtn.setVisibility(View.VISIBLE);
                    refreshBtn.setText(onResultErrorBtnTxt());
                    break;
                case errorOther:
                    hintErrorIv.setImageResource(onResultErrorOtherIcon());
                    hintErrorTv.setText(onResultErrorOtherTxt());
                    refreshBtn.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * 返回网络错误图片
     *
     * @return
     */
    @DrawableRes
    public int onResultErrorNetWorkIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    public String onResultErrorNetWorkTxt() {
        return getString(R.string.errorNetWorkHint);
    }

    /**
     * 返回没有数据的图片
     *
     * @return
     */
    @DrawableRes
    public int onResultErrorNoMsgIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    public String onResultErrorNoMsgTxt() {
        return getString(R.string.errorNoMsgHint);
    }

    /**
     * 返回服务器错误的图片
     *
     * @return
     */
    @DrawableRes
    public int onResultErrorServiceIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    public String onResultErrorServiceTxt() {
        return getString(R.string.errorServiceHint);
    }

    /**
     * 返回服务器错误的图片
     *
     * @return
     */
    @DrawableRes
    public int onResultErrorOtherIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    public String onResultErrorOtherTxt() {
        return getString(R.string.errorOtherHint);
    }

    /**
     * 网络出错时的文字
     *
     * @return
     */
    public String onResultErrorBtnTxt() {
        return getString(R.string.refresh);
    }

    /**
     * 返回数据请求失败的布局
     *
     * @return
     */
    @LayoutRes
    public int onResultNoMsgLayoutRes() {
        return R.layout.app_include_no_msg;
    }

    public View onResultNoMsgLayout() {
        isDefaultNoMsgLayout = true;
        return LayoutInflater.from(getActivity()).inflate(onResultNoMsgLayoutRes(), listContainer, false);
    }

    /**
     * 刷新按钮的点击事件
     */
    public void onRefreshClick(ErrorType errorType) {
        onRefresh();
    }

    private boolean isPullRefresh() {
        if (pageNumber <= 1) {
            return true;
        }
        return false;
    }

    public void checkNotNull() {
        if (!TextUtils.isEmpty(onResultUrl())) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                if (TextUtils.isEmpty(key)) {
                    throw new NullPointerException("key cannot be empty,please check");
                } else if (params.get(key) == null) {
                    throw new NullPointerException(String.format("The key - %s value cannot be empty,please check", key));
                }
            }
        } else {
            throw new NullPointerException(String.format("url or params cannot be empty,please check"));
        }
    }

    public XRecyclerView getRecyclerView() throws NullPointerException {
        if (xRecyclerView == null)
            xRecyclerView = getView(R.id.recyclerView);
        return xRecyclerView;
    }

    public <T> T getAdapter() throws NullPointerException {
        try {
            return (T) getRecyclerView().getAdapter();
        } catch (Exception O_o) {
            O_o.printStackTrace();
            return null;
        }
    }


    public RecyclerView.LayoutManager onResultLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    public RecyclerView.ItemDecoration onResultCuttingLine() {
        return getDefaultCuttingLine(getActivity());
    }

    public static XRecyclerViewUsingLine.DividerItemDecoration getDefaultCuttingLine(Activity activity) {
        return new XRecyclerViewUsingLine.DividerItemDecoration(activity, ContextCompat.getDrawable(activity, R.drawable.shape_app_divider_colorline_border), DividerItemDecoration.VERTICAL, (int) AutoUtils.getPercentHeight1px(), AutoUtils.getPercentWidthSize(activity.getResources().getDimensionPixelSize(R.dimen.borderWidth)), XRecyclerViewUsingLine.DividerItemDecoration.MIDDLE);
    }

    @DrawableRes
    public int onResultPullRefreshIcon() {
        return R.drawable.ic_refresh_downward_gray;
    }

    public int onResultProgressStylePullRefresh() {
        return ProgressStyle.BallSpinFadeLoader;
    }

    public int onResultProgressStyleLoadMore() {
        return ProgressStyle.BallScale;
    }

    public boolean isShowCuttingLine() {
        return false;
    }

    public void onShowLoading() {
    }

    public void onCloseLoading() {
    }

    public void onRequestEnd() {
    }

    public void onRequestError(ErrorType errorType) {
    }

    public abstract void onInit();

    public abstract void start();

    public abstract boolean isPullRefreshEnable();

    public abstract boolean isLoadMoreEnable();

    public abstract RecyclerView.Adapter onResultAdapter();

    public abstract String onResultUrl();

    public abstract void onResultParams(@NonNull Map<String, String> params);

    /**
     * 数据请求成功
     *
     * @param jsonData
     * @return
     */
    public abstract void onRequestDataSuccess(@NonNull String jsonData, boolean isPullRefresh);

    public void setOnRequestListener(OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
    }

    public interface OnRequestListener {
        void onRequestEnd();
    }
}
