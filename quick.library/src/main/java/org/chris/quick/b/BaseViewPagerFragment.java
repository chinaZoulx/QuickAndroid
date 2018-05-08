package org.chris.quick.b;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhy.autolayout.utils.AutoUtils;

import org.chris.quick.R;
import org.chris.quick.b.fragments.ThemeFragment;
import org.chris.quick.tools.common.CommonUtils;
import org.chris.quick.widgets.CustomCompatViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by work on 2017/9/6.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public abstract class BaseViewPagerFragment<M> extends ThemeFragment {

    public static final int AUTO_SCROLL_WHAT = 0x1;

    private OnTouchListener onTouchListener;
    protected ViewPager.OnPageChangeListener onPageChangeListener;
    protected TabLayout.OnTabSelectedListener onTabSelectedListener;
    protected LinearLayout dotsContainer;
    protected TabLayout tabLayout;
    protected DataSetObserver dataSetObserver;

    private CustomCompatViewPager customViewPager;
    private PagerAdapter mAdapter;

    private String[] tabs;
    List<M> imgUrlList = new ArrayList<>();

    View lastView;
    private int viewPagerScrollTime = -1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isPullRefresh;

    @Override
    protected int onResultLayoutResId() {
        return R.layout.app_base_view_pager;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_SCROLL_WHAT:
                    if (getActivity() != null) {
                        getViewPager().setCurrentItem(getLastSelectItemPosition());
                        startAutoScroll();
                    }
                    break;
            }
        }
    };

    @Override
    public void init() {
        initView();
        setupLayout();
        setupListener();
        onInit();
        start();
    }

    @Override
    public void onStart() {
        super.onStart();
        startAutoScroll();
    }

    private void initView() {
        customViewPager = getView(R.id.customCompatViewPager);
        tabLayout = getView(R.id.tabLayout);
        dotsContainer = getView(R.id.dotsContainer);
    }

    private void setupLayout() {
        mAdapter = onResultAdapter();
        getViewPager().setAdapter(mAdapter);
        getViewPager().setPageTransformer(true, onResultPageTransformer());
        setScrollTimeType(isAutoScroll() ? onResultScrollChangeTimeFocus() : onResultScrollChangeTimeNormal());
    }

    private void setupListener() {
        getViewPager().addOnPageChangeListener(this.onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int tempPosition = position % imgUrlList.size();
                setScrollTimeType(isAutoScroll() ? onResultScrollChangeTimeFocus() : onResultScrollChangeTimeNormal());
                if (isShowHintDots() && getAdapter().getCount() > 1) {
                    lastView.setBackgroundResource(onResultIndicatorDotBgNormal());
                    if (dotsContainer.getVisibility() != View.VISIBLE)
                        dotsContainer.setVisibility(View.VISIBLE);
                    dotsContainer.getChildAt(tempPosition).setBackgroundResource(onResultIndicatorDotBgFocus());
                    lastView = dotsContainer.getChildAt(tempPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                switch (state) {
//                    case ViewPager.SCROLL_STATE_IDLE://停止了
//
//                        break;
//                    case ViewPager.SCROLL_STATE_DRAGGING://拖动中
//
//                        break;
//                    case ViewPager.SCROLL_STATE_SETTLING://滑动停止了
//                        setScrollTimeType(onResultScrollChangeTimeFocus());
//                        break;
//                }
            }
        });
        getAdapter().registerDataSetObserver(dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setupDots();
                startAutoScroll();
            }
        });
        getViewPager().setOnTouchListener(new View.OnTouchListener() {
            PointF downPoint = new PointF();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onTouchListener != null) {
                    onTouchListener.onTouch(v, event);
                }
                if (isAutoScroll())
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downPoint.x = event.getX();
                            downPoint.y = event.getY();
                            stopAutoScroll();
                            if (isResolveSwipeRefreshLayout())
                                swipeRefreshLayout.setEnabled(false);
                        case MotionEvent.ACTION_MOVE:
                            setScrollTimeType(onResultScrollChangeTimeNormal());
                            stopAutoScroll();
                            if (isResolveSwipeRefreshLayout() && Math.abs(event.getX() - downPoint.x) < Math.abs(event.getY() - downPoint.y))//Y轴距离大于X轴
                                if (isPullRefresh)
                                    swipeRefreshLayout.setEnabled(true);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            startAutoScroll();
                            setScrollTimeType(onResultScrollChangeTimeNormal());
                            if (isResolveSwipeRefreshLayout() && isPullRefresh)
                                swipeRefreshLayout.setEnabled(true);
                            break;
                    }
                return false;
            }
        });

        tabLayout.addOnTabSelectedListener(onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setScrollTimeType(onResultScrollChangeTimeNormal());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupDots() {
        if (getActivity() == null) {
            return;
        }
        dotsContainer.removeAllViews();
        if (isShowHintDots() && getAdapter().getCount() > 1) {
            if (dotsContainer.getVisibility() != View.VISIBLE)
                dotsContainer.setVisibility(View.VISIBLE);
            for (int i = 0; i < imgUrlList.size(); i++) {
                dotsContainer.addView(onResultIndicatorDot());
            }
            lastView = dotsContainer.getChildAt(0);
            lastView.setBackgroundResource(onResultIndicatorDotBgFocus());
        }
    }

    public void setupTab(String[] tabs) {
        setupTab(tabs, null);
    }

    public void setupTab(String[] tabs, TabLayout.OnTabSelectedListener onTabSelectedListener) {
        setupTab(tabs, 0, onTabSelectedListener);
    }

    /**
     * 安装顶部TabLayout
     *
     * @param tabs
     */
    public void setupTab(String[] tabs, int selectorPosition, TabLayout.OnTabSelectedListener onTabSelectedListener) {
        this.tabs = tabs;
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(getViewPager());
        if (onTabSelectedListener != null && this.onTabSelectedListener != null) {
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
    }

    /**
     * 返回适配器
     *
     * @return
     */
    public PagerAdapter onResultAdapter() {
        return new BaseBannerVpAdapter();
    }

    /**
     * 返回提示点
     *
     * @return
     */
    public View onResultIndicatorDot() {
        View view = new View(getActivity());
        int size = AutoUtils.getPercentHeightSize(onResultIndicatorDotSize());
        int margin = AutoUtils.getPercentHeightSize(onResultIndicatorDotMargin());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.setMargins(margin, 0, margin, 0);
        view.setBackgroundResource(onResultIndicatorDotBgNormal());
        view.setLayoutParams(layoutParams);
        return view;
    }

    /**
     * 返回提示点背景（选中）
     *
     * @return
     */
    @DrawableRes
    public int onResultIndicatorDotBgFocus() {
        return R.drawable.shape_oval_black;
    }

    /**
     * 返回提示点背景（未选中）
     *
     * @return
     */
    @DrawableRes
    public int onResultIndicatorDotBgNormal() {
        return R.drawable.shape_oval_black80;
    }

    /**
     * 返回提示大小
     *
     * @return
     */
    public int onResultIndicatorDotSize() {
        return 15;
    }

    /**
     * 返回提示点之间距离
     *
     * @return
     */
    public int onResultIndicatorDotMargin() {
        return 6;
    }

    /**
     * 切换Item之间切换时间（正常）
     *
     * @return
     */
    public int onResultScrollChangeTimeNormal() {
        return 200;
    }

    /**
     * 切换Item之间切换时间（自动滚动）
     *
     * @return
     */
    public int onResultScrollChangeTimeFocus() {
        return 1000;
    }

    /**
     * 返回自动滚动延迟时间
     *
     * @return
     */
    public long onResultAutoScrollDelayTime() {
        return 5000L + onResultScrollChangeTimeFocus();
    }

    public ViewPager.PageTransformer onResultPageTransformer() {
        return CustomCompatViewPager.TransformerFactory.Companion.getInstance().getTransformer(getViewPager(), CustomCompatViewPager.TransformerFactory.TransformerType.Flyme);
    }

    /**
     * 是否显示提示点
     *
     * @return
     */
    public boolean isShowHintDots() {
        return false;
    }

    public CustomCompatViewPager getViewPager() {
        return customViewPager;
    }

    public String[] getTabs() {
        return tabs;
    }

    /**
     * 开始自动滚动
     */
    public void startAutoScroll() {
        if (isAutoScroll() && getAdapter().getCount() > 1) {
            handler.sendEmptyMessageDelayed(AUTO_SCROLL_WHAT, onResultAutoScrollDelayTime());
        }
    }

    /**
     * 停止自动滚动
     */
    public void stopAutoScroll() {
        if (handler.hasMessages(AUTO_SCROLL_WHAT)) {
            handler.removeMessages(AUTO_SCROLL_WHAT);
        }
    }

    @CallSuper
    public PagerAdapter getAdapter() {
        return getViewPager().getAdapter();
    }

    private int getLastSelectItemPosition() {
        int tempPosition = getViewPager().getCurrentItem();
        if (isAutoScroll()) {
            if (tempPosition >= getAdapter().getCount() - 1) {
                tempPosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % imgUrlList.size();
            } else if (tempPosition == 0) {
                tempPosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % imgUrlList.size() + 1;
            } else tempPosition++;
        } else {
            if (tempPosition >= getAdapter().getCount() - 1) {
                tempPosition = 0;
            } else tempPosition++;
        }
        return tempPosition;
    }

    /**
     * 设置滚动时的时间类型
     *
     * @param type {@link #onResultScrollChangeTimeFocus()}{@link #onResultScrollChangeTimeNormal()}
     */
    public void setScrollTimeType(int type) {
        if (viewPagerScrollTime != type)
            CommonUtils.controlViewPagerSpeed(getActivity(), getViewPager(), viewPagerScrollTime = type);
    }

    /**
     * 添加触摸事件监听
     * 若需额外设置：{@link #getViewPager().setOnTouchListener}
     *
     * @param onTouchListener
     */
    public void addOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void setOnItemClickListener(CustomCompatViewPager.OnItemClickListener onItemClickListener) {
        getViewPager().setOnItemClickListener(onItemClickListener);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        getViewPager().removeOnPageChangeListener(this.onPageChangeListener);
        this.onPageChangeListener = onPageChangeListener;
    }

    public void setDataList(@NonNull List<M> imgUrlList) {
        this.imgUrlList = imgUrlList;
        getAdapter().notifyDataSetChanged();
        int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % imgUrlList.size();
        getViewPager().setCurrentItem(zeroItem);
        getViewPager().setRealSize(imgUrlList.size());
    }

    public List<M> getDataList() {
        return imgUrlList;
    }

    public abstract void onInit();

    public abstract void start();

    public abstract boolean isAutoScroll();

    public abstract View onResultItemView(ViewGroup container, int position, M itemData);

    public void setResolveSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout, boolean isPullRefresh) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.isPullRefresh = isPullRefresh;
    }

    public boolean isResolveSwipeRefreshLayout() {
        return swipeRefreshLayout != null;
    }

    @Override
    public void onStop() {
        super.onStop();
        stopAutoScroll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getAdapter().unregisterDataSetObserver(dataSetObserver);
    }

    public class BaseBannerVpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgUrlList != null && imgUrlList.size() > 0 ? isAutoScroll() ? Integer.MAX_VALUE : imgUrlList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int tempPosition = position % imgUrlList.size();
            View view = onResultItemView(container, tempPosition, imgUrlList.get(tempPosition));
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            container.addView(view);
            return view;
        }
    }

    public interface OnTouchListener {
        void onTouch(View v, MotionEvent event);
    }
}