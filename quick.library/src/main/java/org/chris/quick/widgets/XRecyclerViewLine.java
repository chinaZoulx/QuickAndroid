package org.chris.quick.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.DimenUtils;

import org.chris.quick.R;
import org.chris.quick.b.BaseApplication;

/**
 * Created by work on 2017/9/29.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class XRecyclerViewLine extends XRecyclerView {

    Context mContext;

    DividerItemDecoration mDividerItemDecoration;
    Drawable mDivider;
    int mDividerSize;
    int mDividerPadding;
    int mOrientation;
    int mDividerShowState;

    boolean isAddDivider;
    boolean isLoadMore;
    boolean isPullRefresh;

    public XRecyclerViewLine(Context context) {
        this(context, null);
    }

    public XRecyclerViewLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerViewLine(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        setupParams(context.obtainStyledAttributes(attrs, R.styleable.XRecyclerViewLine));
        setupLayout();
        setupListener();
    }

    private void setupParams(TypedArray typedArray) {
        mDivider = typedArray.getDrawable(R.styleable.XRecyclerViewLine_xDivider);
        mOrientation = typedArray.getInt(R.styleable.XRecyclerViewLine_xDividerOrientation, VERTICAL);
        mDividerShowState = typedArray.getInt(R.styleable.XRecyclerViewLine_xShowDividers, DividerItemDecoration.NONE);
        int tempSize = typedArray.getDimensionPixelSize(R.styleable.XRecyclerViewLine_xDividerSize, 1);
        if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.XRecyclerViewLine_xDividerSize)))
            mDividerSize = AutoUtils.getPercentHeightSize(tempSize);
        else
            mDividerSize = tempSize;

        int tempPadding = typedArray.getDimensionPixelOffset(R.styleable.XRecyclerViewLine_xDividerPadding, AutoUtils.getPercentWidthSize(getContext().getResources().getDimensionPixelSize(R.dimen.borderWidth)));
        if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.XRecyclerViewLine_xDividerPadding)))
            mDividerPadding = AutoUtils.getPercentHeightSize(tempPadding);
        else
            mDividerPadding = tempPadding;
    }

    private void setupLayout() {
        mDividerItemDecoration = new DividerItemDecoration(mContext, mDivider, mOrientation, mDividerSize, mDividerPadding, mDividerShowState);
        if (mDivider != null && mDividerShowState != DividerItemDecoration.NONE) {
            isAddDivider = true;
            addItemDecoration(mDividerItemDecoration);
        }
    }

    private void setupListener() {
        addOnScrollListener(new OnRecyclerViewScrollListener() {
            @Override
            public void onBottom() {
                mDividerItemDecoration.setXRevScrollState(true, isLoadMore);
            }

            @Override
            public void onOther() {
                mDividerItemDecoration.setXRevScrollState(false, isLoadMore);
            }
        });
    }

    @Override
    public void setPullRefreshEnabled(boolean isPullRefresh) {
        this.isPullRefresh = isPullRefresh;
        super.setPullRefreshEnabled(isPullRefresh);
    }

    @Override
    public void setLoadingMoreEnabled(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        mDividerItemDecoration.setXRevScrollState(true,isLoadMore);
        super.setLoadingMoreEnabled(isLoadMore);
    }

    /**
     * 设置分割线
     *
     * @param orientation Divider mOrientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     * @param drawable
     * @param height      分割线高度
     * @param padding     分割线两边线
     * @param state       Should be {@link DividerItemDecoration#TOP} or {@link DividerItemDecoration#BOTTOM} or {@link DividerItemDecoration#MIDDLE} or {@link DividerItemDecoration#NONE}
     */
    public void setDivider(Drawable drawable, int orientation, int height, int padding, int state) {
        this.mOrientation = orientation;
        this.mDivider = drawable;
        this.mDividerSize = height;
        this.mDividerPadding = padding;
        this.mDividerShowState = state;
        if (!isAddDivider) {
            addItemDecoration(mDividerItemDecoration);
        }
        mDividerItemDecoration.setDividerItemDecoration(drawable, orientation, height, padding, state);
    }

    public static class DividerItemDecoration extends ItemDecoration {

        public static final int TOP = 0x01;
        public static final int BOTTOM = 0x02;
        public static final int MIDDLE = 0x04;
        public static final int NONE = 0x08;

        private final Rect mBounds = new Rect();

        Context mContext;
        Drawable mDivider;
        int mDividerSize;
        int mDividerPadding;
        int mOrientation;
        int mDividerShowState;
        boolean isXRevScrollBottom = true;
        boolean isLoadMore;

        /**
         * Creates a mDivider {@link ItemDecoration} that can be used with a
         * {@link LinearLayoutManager}.
         *
         * @param context     Current mContext, it will be used to access resources.
         * @param orientation Divider mOrientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
         */
        public DividerItemDecoration(Context context, Drawable drawable, int orientation, int height, int padding, int showState) {
            this.mContext = context;
            this.mOrientation = orientation;
            this.mDivider = drawable;
            this.mDividerSize = height;
            this.mDividerPadding = padding;
            this.mDividerShowState = showState;
            if (this.mDividerSize <= 1) {
                mDividerSize = 2;
            }
        }

        public DividerItemDecoration setDividerItemDecoration(@NonNull Drawable drawable, int orientation, int height, int padding, int showState) {
            this.mOrientation = orientation;
            this.mDivider = drawable;
            this.mDividerSize = height;
            this.mDividerPadding = padding;
            this.mDividerShowState = showState;
            if (this.mDividerSize <= 1) {
                mDividerSize = 2;
            }
            return this;
        }

        public void setXRevScrollState(boolean isXRevScrollBottom, boolean isLoadMore) {
            this.isXRevScrollBottom = isXRevScrollBottom;
            this.isLoadMore = isLoadMore;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            if (parent.getLayoutManager() == null) {
                return;
            }
            if (mOrientation == VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        /**
         * 兼容XRecyclerView
         *
         * @param canvas
         * @param parent
         */
        @SuppressLint("NewApi")
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            canvas.save();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                int realIndex = parent.getChildAdapterPosition(parent.getChildAt(i));/*真实位置*/
                if (realIndex == 0 || i == childCount - 1) {
                    if (parent instanceof XRecyclerView) {
                        if (realIndex == 0 || isLoadMore) {
                            continue;
                        } else if (!hasState(mDividerShowState, BOTTOM))
                            continue;
                    }
                    if (realIndex == 0) {
                        if (hasState(mDividerShowState, TOP))
                            drawVerticalLine(canvas, parent, i, true, false);
                        else
                            drawVerticalLine(canvas, parent, i, false, false);
                    } else if (hasState(mDividerShowState, BOTTOM))
                        drawVerticalLine(canvas, parent, i, false, true);
                } else if (hasState(mDividerShowState, MIDDLE)) {
                    if (parent instanceof XRecyclerView) {
                        if (isLoadMore) {
                            if (i < childCount - 2 || !isXRevScrollBottom)
                                drawVerticalLine(canvas, parent, i, false, true);
                            else if (hasState(mDividerShowState, BOTTOM)) {
                                drawVerticalLine(canvas, parent, i, false, true);
                            }
                        } else {
                            if (i < childCount - 1 || hasState(mDividerShowState, BOTTOM))
                                drawVerticalLine(canvas, parent, i, false, true);
                        }
                    } else drawVerticalLine(canvas, parent, i, false, false);
                }
            }
            canvas.restore();
        }

        private void drawVerticalLine(Canvas canvas, RecyclerView parent, int index, boolean isTop, boolean isBottom) {
            View child = parent.getChildAt(index);
            int left = child.getLeft() + mDividerPadding;
            int right = (child.getRight() - mDividerPadding);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            int bottom = mBounds.bottom + Math.round(child.getTranslationY()) + mDividerSize / 2;
            int top = bottom - mDividerSize / 2;
            if (isTop) {
                mDivider.setBounds(left, mBounds.top, right, mBounds.top + mDividerSize / 2);
                mDivider.draw(canvas);
            }
            if (isBottom) {
                top -= mDividerSize / 2;
                bottom -= mDividerSize / 2;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

        /**
         * 水平的未兼容XRecyclerView(待完善)
         *
         * @param canvas
         * @param parent
         */
        @SuppressLint("NewApi")
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            canvas.save();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                int realIndex = parent.getChildAdapterPosition(parent.getChildAt(i));/*真实位置*/
                if (realIndex == 0 || i == childCount - 1) {
                    if (hasState(mDividerShowState, TOP) && realIndex == 0)
                        drawHorizontalLine(canvas, parent, i, true, false);
                    else drawHorizontalLine(canvas, parent, i, false, false);
                    if (hasState(mDividerShowState, BOTTOM) && i == childCount - 1)
                        drawHorizontalLine(canvas, parent, i, false, true);
                } else if (hasState(mDividerShowState, MIDDLE))
                    drawHorizontalLine(canvas, parent, i, false, false);
            }
            canvas.restore();
        }

        private void drawHorizontalLine(Canvas canvas, RecyclerView parent, int index, boolean isTop, boolean isBottom) {
            final View child = parent.getChildAt(index);
            int top = mDividerPadding;
            int bottom = child.getHeight() - mDividerPadding;
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            int right = mBounds.right + Math.round(child.getTranslationX()) + mDividerSize / 2;
            int left = right - mDividerSize / 2;
            if (isTop) {//画首条
                mDivider.setBounds(mBounds.left, top, mBounds.left + mDividerSize, bottom);
                mDivider.draw(canvas);
            }
            if (isBottom) {//往左移，避免出界
                right -= mDividerSize / 2;
                left -= mDividerSize / 2;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            if (mOrientation == VERTICAL) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }

        public boolean hasState(int currentState, int targetState) {
            return (targetState & currentState) != 0;
        }
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * @author Jack Tony
     * @brief recyle view 滚动监听器
     * @date 2015/4/6
     */
    public class OnRecyclerViewScrollListener extends OnScrollListener implements OnScrollBottomListener {

        private String TAG = getClass().getSimpleName();

        /**
         * layoutManager的类型（枚举）
         */
        protected LAYOUT_MANAGER_TYPE layoutManagerType;

        /**
         * 最后一个的位置
         */
        private int[] lastPositions;

        /**
         * 最后一个可见的item的位置
         */
        private int lastVisibleItemPosition;
/*    *//**
         * 是否正在加载
         *//*
    private boolean isLoadingMore = false;*/

        /**
         * 当前滑动的状态
         */
        private int currentScrollState = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LayoutManager layoutManager = recyclerView.getLayoutManager();
            //  int lastVisibleItemPosition = -1;
            if (layoutManagerType == null) {
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                } else if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                } else {
                    throw new RuntimeException(
                            "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case LINEAR:
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                    break;
            }

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            currentScrollState = newState;
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                    (lastVisibleItemPosition) >= totalItemCount - 1)) {
                //Log.d(TAG, "is loading more");
                onBottom();
            } else onOther();
        }


        @Override
        public void onBottom() {
            //Log.d(TAG, "is onBottom");
        }

        @Override
        public void onOther() {

        }

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }

    public interface OnScrollBottomListener {
        void onBottom();

        void onOther();
    }
}
