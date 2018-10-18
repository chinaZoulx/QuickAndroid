package org.quick.library.widgets;

import java.lang.System;

/**
 * * @function
 * * @Author ChrisZou
 * * @Date 2018/6/8-9:31
 * * @Email chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001:\u0001FB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0013J\u000e\u0010(\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0013J\u000e\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020+J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\n\u00100\u001a\u0006\u0012\u0002\b\u000301J\u0006\u00102\u001a\u00020$J\b\u00103\u001a\u00020&H\u0002J\b\u00104\u001a\u00020&H\u0002J\u0006\u00105\u001a\u00020&J\u0006\u00106\u001a\u00020&J\u0012\u00107\u001a\u00020&2\n\u00108\u001a\u0006\u0012\u0002\b\u000301J\u000e\u00109\u001a\u00020&2\u0006\u0010:\u001a\u00020;J\u000e\u0010<\u001a\u00020&2\u0006\u0010=\u001a\u00020\u000bJ\u000e\u0010>\u001a\u00020&2\u0006\u0010\r\u001a\u00020\u000bJ\u000e\u0010?\u001a\u00020&2\u0006\u0010@\u001a\u00020\"J\u000e\u0010A\u001a\u00020&2\u0006\u0010=\u001a\u00020\u000bJ\u000e\u0010B\u001a\u00020&2\u0006\u0010=\u001a\u00020\u000bJ\b\u0010C\u001a\u00020&H\u0002J\b\u0010D\u001a\u00020&H\u0002J\b\u0010E\u001a\u00020&H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0013X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006G"}, d2 = {"Lorg/quick/library/widgets/CustomPullRefreshRecyclerView;", "Lorg/quick/library/widgets/CustomCompatSwipeRefreshLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attributeSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "contentContainer", "Landroid/widget/LinearLayout;", "isLoadMoreEnabled", "", "isLoadingData", "isNoMore", "isScrollEnd", "loadMoreHintTxt", "", "loadMoreNoMoreHintTxt", "mFootView", "Landroid/view/View;", "getMFootView", "()Landroid/view/View;", "setMFootView", "(Landroid/view/View;)V", "mFooterAnimView", "getMFooterAnimView", "setMFooterAnimView", "mFooterLoadingTv", "Landroid/widget/TextView;", "getMFooterLoadingTv", "()Landroid/widget/TextView;", "setMFooterLoadingTv", "(Landroid/widget/TextView;)V", "mOnRefreshListener", "Lorg/quick/library/widgets/CustomPullRefreshRecyclerView$OnRefreshListener;", "mRecyclerView", "Landroid/support/v7/widget/RecyclerView;", "addFooterView", "", "view", "addHeaderView", "addItemDecoration", "itemDecoration", "Landroid/support/v7/widget/DividerItemDecoration;", "findMax", "", "lastPositions", "", "getAdapter", "Landroid/support/v7/widget/RecyclerView$Adapter;", "getRecyclerView", "init", "loadMore", "loadMoreComplete", "refreshComplete", "setAdapter", "adapter", "setLayoutManager", "layoutManager", "Landroid/support/v7/widget/RecyclerView$LayoutManager;", "setLoadMoreEnabled", "isEnabled", "setNoMore", "setOnRefreshListener", "onRefreshListener", "setPullRefreshEnabled", "setRefreshPullEnabled", "setupFooter", "setupRecyclerView", "startLoadingAnim", "OnRefreshListener", "quick-library_debug"})
public final class CustomPullRefreshRecyclerView extends org.quick.library.widgets.CustomCompatSwipeRefreshLayout {
    private android.support.v7.widget.RecyclerView mRecyclerView;
    private org.quick.library.widgets.CustomPullRefreshRecyclerView.OnRefreshListener mOnRefreshListener;
    @org.jetbrains.annotations.NotNull()
    public android.view.View mFootView;
    @org.jetbrains.annotations.NotNull()
    public android.view.View mFooterAnimView;
    @org.jetbrains.annotations.NotNull()
    public android.widget.TextView mFooterLoadingTv;
    private boolean isScrollEnd;
    private boolean isLoadingData;
    private boolean isNoMore;
    private boolean isLoadMoreEnabled;
    private java.lang.String loadMoreNoMoreHintTxt;
    private final java.lang.String loadMoreHintTxt = "\u4f7f\u52b2\u52a0\u8f7d\u4e2d";
    private android.widget.LinearLayout contentContainer;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.View getMFootView() {
        return null;
    }
    
    public final void setMFootView(@org.jetbrains.annotations.NotNull()
    android.view.View p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.View getMFooterAnimView() {
        return null;
    }
    
    public final void setMFooterAnimView(@org.jetbrains.annotations.NotNull()
    android.view.View p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.TextView getMFooterLoadingTv() {
        return null;
    }
    
    public final void setMFooterLoadingTv(@org.jetbrains.annotations.NotNull()
    android.widget.TextView p0) {
    }
    
    private final void init() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupFooter() {
    }
    
    private final int findMax(int[] lastPositions) {
        return 0;
    }
    
    private final void loadMore() {
    }
    
    private final void startLoadingAnim() {
    }
    
    public final void loadMoreComplete() {
    }
    
    public final void refreshComplete() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.support.v7.widget.RecyclerView getRecyclerView() {
        return null;
    }
    
    public final void addHeaderView(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void addFooterView(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void addItemDecoration(@org.jetbrains.annotations.NotNull()
    android.support.v7.widget.DividerItemDecoration itemDecoration) {
    }
    
    public final void setRefreshPullEnabled(boolean isEnabled) {
    }
    
    public final void setLoadMoreEnabled(boolean isEnabled) {
    }
    
    public final void setNoMore(boolean isNoMore) {
    }
    
    public final void setAdapter(@org.jetbrains.annotations.NotNull()
    android.support.v7.widget.RecyclerView.Adapter<?> adapter) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.support.v7.widget.RecyclerView.Adapter<?> getAdapter() {
        return null;
    }
    
    public final void setLayoutManager(@org.jetbrains.annotations.NotNull()
    android.support.v7.widget.RecyclerView.LayoutManager layoutManager) {
    }
    
    public final void setOnRefreshListener(@org.jetbrains.annotations.NotNull()
    org.quick.library.widgets.CustomPullRefreshRecyclerView.OnRefreshListener onRefreshListener) {
    }
    
    public final void setPullRefreshEnabled(boolean isEnabled) {
    }
    
    public CustomPullRefreshRecyclerView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    public CustomPullRefreshRecyclerView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.util.AttributeSet attributeSet) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&\u00a8\u0006\u0005"}, d2 = {"Lorg/quick/library/widgets/CustomPullRefreshRecyclerView$OnRefreshListener;", "", "onLoadMore", "", "onRefresh", "quick-library_debug"})
    public static abstract interface OnRefreshListener {
        
        public abstract void onRefresh();
        
        public abstract void onLoadMore();
    }
}