package org.chris.quick.b;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.chris.quick.R;
import org.chris.quick.b.activities.ThemeActivity;
import org.chris.quick.function.IsOkDialog;
import org.chris.quick.function.LoadingDialog;
import org.chris.quick.m.HttpManager;
import org.chris.quick.m.Log;
import org.chris.quick.tools.GsonUtils;
import org.chris.quick.tools.common.HttpUtils;
import org.chris.quick.widgets.CustomCompatSwipeRefreshLayout;
import org.chris.quick.widgets.XStickyListHeadersListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by work on 2017/8/10.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public abstract class BaseListActivity2<M> extends ThemeActivity implements SwipeRefreshLayout.OnRefreshListener, XStickyListHeadersListView.OnRefreshListener {

    private BaseListFragment.OnRequestListener onRequestListener;
    private TabLayout.OnTabSelectedListener onTabSelectedListener;

    View noMsgContainer;
    ImageView hintErrorIv;
    TextView hintErrorTv;
    Button refreshBtn;

    private CustomCompatSwipeRefreshLayout swipeRefreshLayout;
    private FrameLayout headerContainer;
    private FrameLayout footerContainer;
    private FrameLayout listContainer;
    private TabLayout tabLayout;
    private String[] tabs;

    private XStickyListHeadersListView stickyListHeadersListView;
    private Adapter adapter;

    boolean isDefaultNoMsgLayout = true;
    public int pageNumber = 1;
    public IsOkDialog isOkDialog;
    public LoadingDialog loadingDialog;
    BaseListFragment.ErrorType errorType = BaseListFragment.ErrorType.normal;

    private Map<String, String> params = new HashMap<>();

    @Override
    public void init() {
        isOkDialog = new IsOkDialog(this);
        loadingDialog = new LoadingDialog(this);

        setBackValid();
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
        tabLayout = getView(R.id.tabLayout);

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
                    onRefreshClick(errorType);
                }
            });
        }

        swipeRefreshLayout.setEnabled(isPullRefreshEnable());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setResolveListView(getStickyListHeadersListView().getWrappedList());
        swipeRefreshLayout.setOnRefreshListener(this);
        stickyListHeadersListView.setLoadMore(isLoadMoreEnable());
        stickyListHeadersListView.setOnRefreshListener(this);
        stickyListHeadersListView.setAdapter(getAdapter());
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
            for (int i = 0; i < tabs.length; i++) {
                tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            }
        }
        if (selectorPosition != 0)
            tabLayout.getTabAt(selectorPosition).select();
        return tabLayout;
    }

    public void setHeaderContainer(View view) {
        headerContainer.removeAllViews();
        headerContainer.addView(view);
    }

    public void setFooterContainer(View view) {
        footerContainer.removeAllViews();
        footerContainer.addView(view);
    }

    public void onRefreshClick(BaseListFragment.ErrorType errorType) {
        onRefresh();
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
                onRequestError();
            }

            @Override
            public void onResponse(String response, int id) {
                BaseModel model = GsonUtils.INSTANCE.parseFromJson(response, BaseModel.class);
                if (model != null) {
                    if (model.getCode()==BaseApplication.APP_SUCCESS_TAG) {//成功了返回
                        setHasData(true);
                        onRequestDataSuccess(response, isPullRefresh());
                    } else if (isPullRefresh()) {//下拉刷新
                        setHasData(false);
                        showToast(model.getMsg());
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

    public void setNoMore(boolean isNoMore) {
        getStickyListHeadersListView().setNoMore(isNoMore);
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
    public Adapter getAdapter() {
        if (adapter == null)
            adapter = new Adapter();
        return adapter;
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

    public abstract String onResultUrl();

    public abstract void onResultParams(Map<String, String> params);

    public abstract void onRequestDataSuccess(String jsonData, boolean isPullRefresh);

    public abstract @LayoutRes
    int onResultItemLayout();

    public abstract @LayoutRes
    int onResultHeaderLayout();

    public abstract long onBindHeaderId(M itemData, int position);

    public abstract void onBindDataItemView(BaseRecyclerViewAdapter.BaseViewHolder holder, M itemData, int position);

    public abstract void onBindDataHeaderView(BaseRecyclerViewAdapter.BaseViewHolder holder, M itemData, int position);

    public class Adapter extends BaseAdapter implements StickyListHeadersAdapter {
        private List<M> dataList = new ArrayList<>();

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public M getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BaseRecyclerViewAdapter.BaseViewHolder holder;
            if (convertView == null) {
                holder = new BaseRecyclerViewAdapter.BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(onResultItemLayout(), parent, false));
                holder.itemView.setTag(holder);
            } else holder = (BaseRecyclerViewAdapter.BaseViewHolder) convertView.getTag();
            onBindDataItemView(holder, getItem(position), position);
            return holder.itemView;
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            BaseRecyclerViewAdapter.BaseViewHolder holder;
            if (convertView == null) {
                holder = new BaseRecyclerViewAdapter.BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(onResultHeaderLayout(), parent, false));
                holder.itemView.setTag(holder);
            } else holder = (BaseRecyclerViewAdapter.BaseViewHolder) convertView.getTag();
            onBindDataHeaderView(holder, getItem(position), position);
            return holder.itemView;
        }

        @Override
        public long getHeaderId(int position) {
            return onBindHeaderId(getItem(position), position);
        }

        public void setData(List<M> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        public List<M> getDataList() {
            return dataList;
        }

        public void addAll(List<M> dataList) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }
}
