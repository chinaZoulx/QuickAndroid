package org.chris.quick.widgets;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.jcodecraeer.xrecyclerview.AppBarStateChangeListener;

/**
 * The class resolve clash for absListView
 *
 * @url http://www.eoeandroid.com/thread-914273-1-1.html?_dsign=bf09d67b
 * Created by chris zou on 2016/8/2.
 */
public class CustomCompatSwipeRefreshLayout extends SwipeRefreshLayout {

    AbsListView mAbsListView;
    ScrollView mScrollView;
    int type = 0;
    public AppBarStateChangeListener mAppBarStateChangeListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            switch (state) {
                case EXPANDED: {//展开
                    setEnabled(true);
                    break;
                }
//                case COLLAPSED:{//拆起了
//
//                }
                default: {//
                    if (isEnabled())
                        setEnabled(false);
                }

            }
        }
    };

    public CustomCompatSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public CustomCompatSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * RecyclerView未有冲突，建议使用
     *
     * @param listView
     */
    public void setResolveListView(AbsListView listView) {
        this.mAbsListView = listView;
        type = 1;
    }

    public void setResolveScrollView(ScrollView scrollView) {
        this.mScrollView = scrollView;
        type = 2;
    }

    public void setupAppBarLayout(AppBarLayout appBarLayout) {
        appBarLayout.addOnOffsetChangedListener(mAppBarStateChangeListener);
    }

    public void removeAppBarLayout(AppBarLayout appBarLayout) {
        appBarLayout.removeOnOffsetChangedListener(mAppBarStateChangeListener);
    }

    @Override
    public boolean canChildScrollUp() {
        switch (type) {
            case 1://ListView
                if (mAbsListView != null && mAbsListView instanceof AbsListView && mAbsListView.getVisibility() == VISIBLE) {
                    return mAbsListView.getChildCount() > 0 && (mAbsListView.getFirstVisiblePosition() > 0 || mAbsListView.getChildAt(0).getTop() < mAbsListView.getPaddingTop());
                }
            case 2://ScrollView
                if (mScrollView != null && mScrollView.getVisibility() == VISIBLE) {
                    return mScrollView.getScrollY() > 0;
                }
                break;
        }
        return super.canChildScrollUp();
    }
}
