package org.chris.quick.b;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
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
import org.chris.quick.b.activities.ThemeActivity;
import org.chris.quick.function.IsOkDialog;
import org.chris.quick.function.LoadingDialog;
import org.chris.quick.m.HttpManager;
import org.chris.quick.tools.GsonUtils;
import org.chris.quick.tools.common.HttpUtils;
import org.chris.quick.widgets.XRecyclerViewUsingLine;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import okhttp3.Call;

/**
 * Created by work on 2017/8/10.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public abstract class BaseListActivity extends ThemeActivity implements XRecyclerView.LoadingListener {
    private BaseListFragment.OnRequestListener onRequestListener;
    private TabLayout.OnTabSelectedListener onTabSelectedListener;

    View noMsgContainer;
    ImageView hintErrorIv;
    TextView hintErrorTv;
    Button refreshBtn;
    private FrameLayout headerContainer;
    private FrameLayout footerContainer;
    private FrameLayout listContainer;
    private XRecyclerView xRecyclerView;
    private TabLayout tabLayout;
    ArrowRefreshHeader mRefreshHeader;

    boolean isDefaultNoMsgLayout = true;
    public int pageNumber = 1;
    public IsOkDialog isOkDialog;
    public LoadingDialog loadingDialog;

    private String[] tabs;
    BaseListFragment.ErrorType errorType = BaseListFragment.ErrorType.normal;
    private Map<String, String> params = new HashMap<>();

    @Override
    public void init() {
        setBackValid();
        isOkDialog = new IsOkDialog(this);
        loadingDialog = new LoadingDialog(this);
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
        tabLayout = getView(R.id.tabLayout);
        noMsgContainer = getNoMsgLayout();
        listContainer.addView(noMsgContainer);
        noMsgContainer.setVisibility(View.GONE);
        if (isDefaultNoMsgLayout) {
            refreshBtn = getView(R.id.refreshBtn, noMsgContainer);
            hintErrorIv = getView(R.id.hintErrorIv, noMsgContainer);
            hintErrorTv = getView(R.id.hintErrorTv, noMsgContainer);
            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefreshClick(BaseListFragment.ErrorType.normal);
                }
            });
        }

        xRecyclerView.setPullRefreshEnabled(isPullRefreshEnable());
        xRecyclerView.setLoadingMoreEnabled(isLoadMoreEnable());
        xRecyclerView.setRefreshProgressStyle(onResultProgressStylePullRefresh());
        xRecyclerView.setLoadingMoreProgressStyle(onResultProgressStyleLoadMore());
        xRecyclerView.setArrowImageView(onResultPullRefreshIcon());
        xRecyclerView.setLayoutManager(onResultLayoutManager());
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setAdapter(onResultAdapter());
        if (isShowCuttingLine())
            getRecyclerView().addItemDecoration(onResultCuttingLine());

        try {
            Class<?> class1 = Class.forName("com.jcodecraeer.xrecyclerview.XRecyclerView");
            Field field = class1.getDeclaredField("mRefreshHeader");
            field.setAccessible(true);
            mRefreshHeader = (ArrowRefreshHeader) field.get(xRecyclerView);
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setupTab(String... tabs) {
        setupTab(null, tabs);
    }

    public void setupTab(TabLayout.OnTabSelectedListener onTabSelectedListener, String... tabs) {
        setupTab(onTabSelectedListener, 0, tabs);
    }

    /**
     * 安装顶部TabLayout
     *
     * @param tabs
     */
    public TabLayout setupTab(TabLayout.OnTabSelectedListener onTabSelectedListener, int selectorPosition, String... tabs) {
        this.tabs = tabs;
        tabLayout.setVisibility(View.VISIBLE);
        if (this.onTabSelectedListener != null) {
            tabLayout.removeOnTabSelectedListener(this.onTabSelectedListener);
        }
        if (onTabSelectedListener != null)
            tabLayout.addOnTabSelectedListener(this.onTabSelectedListener = onTabSelectedListener);

        if (tabLayout.getTabCount() > 0) {
            for (int i = 0; i < tabLayout.getTabCount() && i < tabs.length; i++) {
                tabLayout.getTabAt(i).setText(tabs[i]);
            }
        } else {
            for (String tab : tabs) {
                tabLayout.addTab(tabLayout.newTab().setText(tab));
            }
        }
        if (selectorPosition != 0)
            Objects.requireNonNull(tabLayout.getTabAt(selectorPosition)).select();
        return tabLayout;
    }

    public void setHeaderContainer(@LayoutRes int resId) {
        setHeaderContainer(LayoutInflater.from(getActivity()).inflate(resId, null));
    }
    public void setHeaderContainer(View view) {
        headerContainer.removeAllViews();
        headerContainer.addView(view);
    }

    public void setFooterContainer(@LayoutRes int resId) {
        setFooterContainer(LayoutInflater.from(getActivity()).inflate(resId, null));
    }

    public void setFooterContainer(View view) {
        footerContainer.removeAllViews();
        footerContainer.addView(view);
    }

    @Override
    public void onRefresh() {
        requestData(pageNumber = BaseListFragment.PAGER_FIRST_NUMBER);
        if (isPullRefreshEnable() && mRefreshHeader.getState() != ArrowRefreshHeader.STATE_REFRESHING)
            mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
    }

    @Override
    public void onLoadMore() {
        requestData(++pageNumber);
    }

    public void requestData(int pageNumber) {
        if (!HttpUtils.isNetWorkAvailable(getActivity())) {
            setHasData(false, BaseListFragment.ErrorType.errorNetWork);
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
                setHasData(false, BaseListFragment.ErrorType.errorService);
                onRequestError("", isPullRefresh(), errorType);
            }

            @Override
            public void onResponse(String response, int id) {
                BaseModel model = GsonUtils.INSTANCE.parseFromJson(response, BaseModel.class);
                if (model != null) {
                    if (model.getCode() == BaseApplication.APP_SUCCESS_TAG) {//成功了返回
                        xRecyclerView.setNoMore(false);
                        setHasData(true);
                        onRequestDataSuccess(response, isPullRefresh());
                    } else {
                        if (isPullRefresh()) {//下拉刷新
                            setHasData(false);
                            showToast(model.getMsg());
                        } else {//上拉加载
                            xRecyclerView.setNoMore(true);
                            setHasData(true);
                        }
                        onRequestError(response, isPullRefresh(), errorType);
                    }
                } else {
                    onRequestError(response, isPullRefresh(), errorType);
                    setHasData(false, BaseListFragment.ErrorType.errorService);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (isPullRefresh() && isPullRefreshEnable()) {
                    xRecyclerView.refreshComplete();
                } else if (isLoadMoreEnable()) {
                    xRecyclerView.loadMoreComplete();
                }
                if (onRequestListener != null) {
                    onRequestListener.onRequestEnd();
                }
                onRequestEnd();
            }
        });
    }

    public void setNoMoreData(boolean isNoMore) {
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
    public synchronized void setHasData(boolean isHasData, BaseListFragment.ErrorType type) {
        if (isHasData) {//有
            errorType = BaseListFragment.ErrorType.normal;
            if (noMsgContainer.getVisibility() == View.VISIBLE)
                noMsgContainer.setVisibility(View.GONE);
            if (xRecyclerView.getVisibility() == View.GONE)
                xRecyclerView.setVisibility(View.VISIBLE);
        } else {//无
            if (noMsgContainer.getVisibility() == View.GONE)
                noMsgContainer.setVisibility(View.VISIBLE);
            if (xRecyclerView.getVisibility() == View.VISIBLE)
                xRecyclerView.setVisibility(View.GONE);
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

    @LayoutRes
    public int getNoMsgLayoutRes() {
        return R.layout.app_include_no_msg;
    }

    public View getNoMsgLayout() {
        isDefaultNoMsgLayout = true;
        return LayoutInflater.from(getActivity()).inflate(getNoMsgLayoutRes(), null);
    }

    /**
     * 刷新按钮的点击事件
     */
    public void onRefreshClick(BaseListFragment.ErrorType errorType) {
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

    public XRecyclerView getRecyclerView() {
        return xRecyclerView;
    }

    public <T extends BaseRecyclerViewAdapter> T getAdapter() {
        return (T) getRecyclerView().getAdapter();
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

    public RecyclerView.LayoutManager onResultLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    public RecyclerView.ItemDecoration onResultCuttingLine() {
        return getDefaultCuttingLine(getActivity());
    }

    public static XRecyclerViewUsingLine.DividerItemDecoration getDefaultCuttingLine(Activity activity) {
        return new XRecyclerViewUsingLine.DividerItemDecoration(activity, ContextCompat.getDrawable(activity, R.drawable.shape_app_divider_colorline), DividerItemDecoration.VERTICAL, (int) AutoUtils.getPercentHeight1px(), AutoUtils.getPercentWidthSize(BaseApplication.APP_BORDER_MARGIN), XRecyclerViewUsingLine.DividerItemDecoration.MIDDLE);
    }

    public boolean isShowCuttingLine() {
        return false;
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

    public void onRequestEnd() {
    }

    public void onRequestError(String jsonData, boolean isPullRefresh, BaseListFragment.ErrorType errorType) {
    }

    public abstract void onInit();

    public abstract void start();

    public abstract boolean isPullRefreshEnable();

    public abstract boolean isLoadMoreEnable();

    public abstract RecyclerView.Adapter onResultAdapter();

    public abstract String onResultUrl();

    public abstract void onResultParams(Map<String, String> params);

    /**
     * 数据请求成功
     *
     * @param jsonData
     * @return
     */
    public abstract void onRequestDataSuccess(String jsonData, boolean isPullRefresh);

    public void setOnRequestListener(BaseListFragment.OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
    }

}
