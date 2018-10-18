package org.quick.library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import org.quick.library.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * Created by work on 2017/8/25.
 * 带上拉加载的StickyListHeadersListView
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class XStickyListHeadersListView extends StickyListHeadersListView {
    /**
     * 分布每页数量
     */
    public static final int PAGE_ITEM_COUNT = 10;
    private OnRefreshListener onRefreshListener;

    View footerView;
    View footerAnimView;
    TextView footerLoadingTv;
    ObjectAnimator objectAnimatorX;
    ObjectAnimator objectAnimatorY;

    private boolean isScrollEnd;//滚动到最后
    private boolean isLoadMore;//加载更多
    private boolean isNoMore = false;//没有更多
    private boolean isLoadingMore;//正在加载
    private String loadMoreEndHintTxt = "别扯啦，我是有底线的";
    private String loadMoreHintTxt = "使劲加载中";

    public XStickyListHeadersListView(Context context) {
        this(context, null);
    }

    public XStickyListHeadersListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XStickyListHeadersListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.app_loading_more_view, null);
        footerAnimView = footerView.findViewById(R.id.animView);
        footerLoadingTv = footerView.findViewById(R.id.loadTv);

        setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (getAdapter().getCount() >= PAGE_ITEM_COUNT && getLastVisiblePosition() == totalItemCount - 1 && isListViewReachBottomEdge(view)) {
                    isScrollEnd = true;
                } else {
                    isScrollEnd = false;
                }
            }
        });
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (isScrollEnd && isLoadMore && !isNoMore && !isLoadingMore) {
                            loadMore();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void loadMore() {
        if (!isLoadingMore) {
            actionLoadingAnim(true);
            if (onRefreshListener != null) {
                onRefreshListener.onLoadMore();
            }
        }
    }

    public void actionLoadingAnim(boolean isLoadingMore) {
        if (getFooterViewsCount() <= 0 || footerView.getTag() != null || !(Boolean) footerView.getTag()) {
            addFooterView(footerView);
            footerView.setTag(true);
            getWrappedList().setSelection(getWrappedList().getBottom());
        }
        if (isLoadingMore) {
            footerLoadingTv.setText(loadMoreHintTxt);
            startLoadingAnim();
        } else {
            footerLoadingTv.setText(loadMoreEndHintTxt);
        }
    }

    public void startLoadingAnim() {
        footerAnimView.setVisibility(View.VISIBLE);
        int duration = 500;
        isLoadingMore = true;
        ObjectAnimator.ofFloat(footerAnimView, "alpha", 1, 0).setDuration(duration).start();
        objectAnimatorX = ObjectAnimator.ofFloat(footerAnimView, "scaleX", 0, 1).setDuration(duration);
        objectAnimatorY = ObjectAnimator.ofFloat(footerAnimView, "scaleY", 0, 1).setDuration(duration);

        objectAnimatorX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startLoadingAnim();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimatorX.start();
        objectAnimatorY.start();
    }

    public void loadMoreConfirm() {
        loadMoreConfirm(true);
    }

    /**
     * @param isNoMore false 没有更多
     */
    public void loadMoreConfirm(boolean isNoMore) {
//        if (!isLoadingMore) {
//            return;
//        }
        if (isNoMore) {//没有更多-移除动画
            if (getFooterViewsCount() > 0 && footerView.getTag() != null && (Boolean) footerView.getTag()) {
                removeFooterView(footerView);
                footerView.setTag(false);
            }
        } else {//还有，加载动画
            if (footerView.getTag() != null && !(Boolean) footerView.getTag())
                actionLoadingAnim(false);
        }
        if (objectAnimatorX != null) {
            objectAnimatorX.removeAllListeners();
        }
        isLoadingMore = false;
    }

    public void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public void setNoMore(boolean isNoMore) {
        this.isNoMore = isNoMore;
        if (isNoMore) {//没有数据了
            loadMoreConfirm(isNoMore);
        }
    }

    public void setLoadingMoreHintTxt(String hintTxt) {
        this.loadMoreEndHintTxt = hintTxt;
    }

    public boolean isListViewReachTopEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            final View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }

    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void onLoadMore();
    }
}
