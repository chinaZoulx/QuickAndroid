package org.chris.quick.b;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.callback.StringCallback;

import org.chris.quick.R;
import org.chris.quick.b.fragments.ThemeFragment;
import org.chris.quick.function.IsOkDialog;
import org.chris.quick.function.LoadingDialog;
import org.chris.quick.m.HttpManager;
import org.chris.quick.tools.GsonUtils;
import org.chris.quick.tools.common.HttpUtils;
import org.chris.quick.widgets.CustomCompatSwipeRefreshLayout;
import org.chris.quick.widgets.XStickyListHeadersListView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by work on 2017/8/2.
 * 带Head的列表
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public abstract class BaseListFragment2 extends ThemeFragment implements SwipeRefreshLayout.OnRefreshListener, XStickyListHeadersListView.OnRefreshListener {

    private BaseListFragment.OnRequestListener onRequestListener;

    View noMsgContainer;
    ImageView hintErrorIv;
    TextView hintErrorTv;
    Button refreshBtn;

    private CustomCompatSwipeRefreshLayout swipeRefreshLayout;
    private FrameLayout headerContainer;
    private FrameLayout footerContainer;
    private FrameLayout listContainer;

    private XStickyListHeadersListView stickyListHeadersListView;

    boolean isDefaultNoMsgLayout = true;
    public int pageNumber = 1;
    public IsOkDialog isOkDialog;
    public LoadingDialog loadingDialog;

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
        return R.layout.app_base_list2;
    }

    @Override
    public boolean isUsingBaseLayout() {
        return true;
    }

    private void setupLayout() {
        swipeRefreshLayout = getView(R.id.swipeRefreshLayout);
        listContainer = getView(R.id.listContainer);
        stickyListHeadersListView = getView(R.id.stickyListHeadersListView);
        headerContainer = getView(R.id.headerContainer);
        footerContainer = getView(R.id.footerContainer);

        noMsgContainer = onResultNoMsgLayout();
        listContainer.addView(noMsgContainer);
        noMsgContainer.setVisibility(View.GONE);
        if (isDefaultNoMsgLayout) {
            refreshBtn = getView(R.id.refreshBtn, noMsgContainer);
            hintErrorIv = getView(R.id.hintErrorIv, noMsgContainer);
            hintErrorTv = getView(R.id.hintErrorTv, noMsgContainer);
            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }

        swipeRefreshLayout.setEnabled(isPullRefreshEnable());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setResolveListView(getStickyListHeadersListView().getWrappedList());
        swipeRefreshLayout.setOnRefreshListener(this);
        stickyListHeadersListView.setLoadMore(isLoadMoreEnable());
        stickyListHeadersListView.setOnRefreshListener(this);
        stickyListHeadersListView.setAdapter(onResultAdapter());
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
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        requestData(pageNumber = BaseListFragment.PAGER_FIRST_NUMBER);
    }

    public void onLoadMore() {
        requestData(++pageNumber);
    }

    public void requestData(int pageNumber) {
        if (!HttpUtils.isNetWorkAvailable(getActivity())) {
            setHintErrorStyle(BaseListFragment.ErrorType.errorNetWork);
            return;
        }
        params.clear();
        onResultParams(params);
        checkNotNull();
        if (!params.containsKey(BaseListFragment.PAGER_NUMBER_KEY))
            params.put(BaseListFragment.PAGER_NUMBER_KEY, pageNumber + "");
        HttpManager.Post(getActivity(), onResultUrl(), params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (getActivity() == null) {
                    return;
                }
                setHasData(false);
                setHintErrorStyle(BaseListFragment.ErrorType.errorService);
                onRequestError();
            }

            @Override
            public void onResponse(String response, int id) {
                if (getActivity() == null) {
                    return;
                }
                BaseModel model = GsonUtils.INSTANCE.parseFromJson(response, BaseModel.class);
                if (model != null) {
                    if (model.getCode()==BaseApplication.APP_SUCCESS_TAG) {//成功了返回
                        setHasData(true);
                        onRequestDataSuccess(response, isPullRefresh());
                    } else if (isPullRefresh()) {//下拉刷新
                        setHasData(false);
                        if (model != null) {
                            showToast(model.getMsg());
                        }
                    } else {//上拉加载
                        setHasData(true);
                    }
                } else {
                    onRequestError();
                    setHasData(false, BaseListFragment.ErrorType.errorService);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (getActivity() == null) {
                    return;
                }
                if (isPullRefresh() && isPullRefreshEnable() && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                } else if (isLoadMoreEnable()) {
                    stickyListHeadersListView.loadMoreConfirm();
                }
                if (onRequestListener != null) {
                    onRequestListener.onRequestEnd();
                }
                onRequestEnd();
            }
        });
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
            setNoMore(false);
            if (noMsgContainer.getVisibility() == View.VISIBLE)
                noMsgContainer.setVisibility(View.GONE);
            if (stickyListHeadersListView.getVisibility() == View.GONE)
                stickyListHeadersListView.setVisibility(View.VISIBLE);
        } else {//无
            setNoMore(true);
            if (noMsgContainer.getVisibility() == View.GONE)
                noMsgContainer.setVisibility(View.VISIBLE);
            if (stickyListHeadersListView.getVisibility() == View.VISIBLE)
                stickyListHeadersListView.setVisibility(View.GONE);
            setHintErrorStyle(type);
        }
    }

    private void setHintErrorStyle(BaseListFragment.ErrorType type) {
        if (isDefaultNoMsgLayout) {
            switch (type) {
                case errorNoMsg:
                    hintErrorIv.setImageResource(onResultErrorNoMsgIcon());
                    refreshBtn.setVisibility(View.GONE);
                    hintErrorTv.setText(getString(R.string.errorNoMsgHint));
                    break;
                case errorNetWork:
                    hintErrorIv.setImageResource(onResultErrorNetWorkIcon());
                    refreshBtn.setVisibility(View.VISIBLE);
                    hintErrorTv.setText(getString(R.string.errorNetWorkHint));
                    break;
                case errorService:
                    hintErrorIv.setImageResource(onResultErrorServiceIcon());
                    refreshBtn.setVisibility(View.VISIBLE);
                    hintErrorTv.setText(getString(R.string.errorServiceHint));
                    break;
            }
        }
    }

    public void setNoMore(boolean isNoMore) {
        getStickyListHeadersListView().setNoMore(isNoMore);
    }

    @DrawableRes
    public int onResultErrorNetWorkIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    @DrawableRes
    public int onResultErrorNoMsgIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    @DrawableRes
    public int onResultErrorServiceIcon() {
        return R.drawable.ic_broken_image_gray_24dp;
    }

    @LayoutRes
    public int onResultNoMsgLayoutRes() {
        return R.layout.app_include_no_msg;
    }

    public View onResultNoMsgLayout() {
        isDefaultNoMsgLayout = true;
        return LayoutInflater.from(getActivity()).inflate(onResultNoMsgLayoutRes(), listContainer, false);
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
                    throw new NullPointerException(String.format("The key ： %s value cannot be empty,please check", key));
                }
            }
        } else {
            throw new NullPointerException(String.format("url or params cannot be empty,please check"));
        }
    }

    public XStickyListHeadersListView getStickyListHeadersListView() {
        return stickyListHeadersListView;
    }

    @NonNull
    public <T> T getAdapter() {
        return (T) getStickyListHeadersListView().getAdapter();
    }

    public void onRequestEnd() {
    }

    public void onRequestError() {
    }

    public void setOnRequestListener(BaseListFragment.OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
    }

    public abstract void onInit();

    public abstract void start();

    public abstract boolean isPullRefreshEnable();

    public abstract boolean isLoadMoreEnable();

    @NonNull
    public abstract StickyListHeadersAdapter onResultAdapter();

    public abstract String onResultUrl();

    public abstract void onResultParams(Map<String, String> params);

    /**
     * 数据请求成功
     *
     * @param jsonData
     * @return
     */
    public abstract void onRequestDataSuccess(String jsonData, boolean isPullRefresh);
}
