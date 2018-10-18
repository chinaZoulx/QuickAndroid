package org.quick.library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * 监听ScrollView滚动
 */
public class CustomScrollView extends ScrollView {

    private ScrollListener scrollListener;
    private boolean isScrolling = false;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollListener != null) {
            isScrolling = true;
            scrollListener.onScrollChanged(this, x, y, oldx, oldy);
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

    //--------------
    public interface ScrollListener {
        void onScrollChanged(ScrollView scrollView, int x, int y, int oldX, int oldY);

        void onScrollBottom(ScrollView scrollView);

        void onScrollTop(ScrollView scrollView);

        void onScrollStop(ScrollView scrollView);
    }
}
