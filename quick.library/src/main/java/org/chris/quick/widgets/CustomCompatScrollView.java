package org.chris.quick.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * 1、监听ScrollView滚动
 * 2、嵌套滚动兼容
 *
 * @author chrisZou
 * @from http://blog.csdn.net/fy993912_chris/article/details/72765260
 * @email chrisSpringSmell@gmail.com
 */
public class CustomCompatScrollView extends ScrollView {

    private GestureDetector mGestureDetector;

    private ScrollListener scrollListener;

    private boolean isScrolling = false;

    public CustomCompatScrollView(Context context) {
        this(context, null);
    }

    public CustomCompatScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCompatScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (scrollListener != null) {
            isScrolling = true;
            scrollListener.onScrollChanged(this, x, y, oldX, oldY);
            if (getChildAt(0) != null && getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight()) {
                scrollListener.onScrollBottom(this);
            } else if (getScrollY() == 0) {
                scrollListener.onScrollTop(this);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (MotionEvent.ACTION_UP == ev.getAction()) {
            if (isScrolling) {
                scrollListener.onScrollStop(this);
            }
            isScrolling = false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }

    public interface ScrollListener {
        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);

        void onScrollBottom(ScrollView scrollView);

        void onScrollTop(ScrollView scrollView);

        void onScrollStop(ScrollView scrollView);
    }
}
