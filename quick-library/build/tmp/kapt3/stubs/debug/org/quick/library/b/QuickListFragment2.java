package org.quick.library.b;

import java.lang.System;

/**
 * * Created by work on 2017/8/2.
 * * 带Head的列表
 * *
 * * @author chris zou
 * * @mail chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u00ae\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0018\n\u0002\u0010$\n\u0002\b\u0011\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002\u0085\u0001B\u0005\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010M\u001a\u00020NJ\u001a\u0010O\u001a\u00020N2\u0006\u0010P\u001a\u00020\u001e2\b\b\u0002\u0010Q\u001a\u00020\tH\u0007J\u0010\u0010R\u001a\f0\u0007R\b\u0012\u0004\u0012\u00028\u00000\u0000J\b\u0010S\u001a\u00020NH\u0016J%\u0010T\u001a\u00020N2\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00028\u00002\u0006\u0010X\u001a\u000201H&\u00a2\u0006\u0002\u0010YJ%\u0010Z\u001a\u00020N2\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00028\u00002\u0006\u0010X\u001a\u000201H&\u00a2\u0006\u0002\u0010YJ\u001d\u0010[\u001a\u00020\\2\u0006\u0010W\u001a\u00028\u00002\u0006\u0010X\u001a\u000201H&\u00a2\u0006\u0002\u0010]J\b\u0010^\u001a\u00020NH\u0016J\b\u0010_\u001a\u00020NH\u0016J\u000e\u0010`\u001a\u00020N2\u0006\u0010\b\u001a\u00020\tJ\u001d\u0010a\u001a\u00020N2\u0006\u0010b\u001a\u00028\u00002\u0006\u0010#\u001a\u00020\u001eH&\u00a2\u0006\u0002\u0010cJ\u0006\u0010d\u001a\u00020NJ\u0006\u0010e\u001a\u00020NJ\u0006\u0010f\u001a\u000208J\b\u0010g\u001a\u000201H\u0007J\u0006\u0010h\u001a\u000208J\b\u0010i\u001a\u000201H\u0007J\u0006\u0010j\u001a\u000208J\b\u0010k\u001a\u000201H\u0007J\u0006\u0010l\u001a\u000208J\b\u0010m\u001a\u000201H\u0007J\u0006\u0010n\u001a\u000208J\b\u0010o\u001a\u000201H\'J\b\u0010p\u001a\u000201H\'J\b\u0010q\u001a\u000201H\u0016J\u0006\u0010r\u001a\u00020\'J\b\u0010s\u001a\u000201H\u0007J\u001c\u0010t\u001a\u00020N2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u0002080uH&J\b\u0010v\u001a\u000208H&J\u000e\u0010w\u001a\u00020N2\u0006\u00100\u001a\u000201J\u000e\u0010x\u001a\u00020N2\u0006\u0010y\u001a\u00020\'J\u000e\u0010z\u001a\u00020N2\u0006\u0010y\u001a\u00020\'J\u0010\u0010{\u001a\u00020N2\u0006\u0010Q\u001a\u00020\tH\u0002J\u000e\u0010|\u001a\u00020N2\u0006\u0010}\u001a\u00020\u001eJ\u000e\u0010~\u001a\u00020N2\u0006\u0010,\u001a\u00020-J\b\u0010\u007f\u001a\u00020NH\u0002J+\u0010\u0080\u0001\u001a\u00020N2\b\u0010.\u001a\u0004\u0018\u00010/2\u0012\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002080K\"\u000208\u00a2\u0006\u0003\u0010\u0081\u0001J4\u0010\u0080\u0001\u001a\u00020I2\b\u0010.\u001a\u0004\u0018\u00010/2\u0007\u0010\u0082\u0001\u001a\u0002012\u0012\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002080K\"\u000208\u00a2\u0006\u0003\u0010\u0083\u0001J!\u0010\u0080\u0001\u001a\u00020N2\u0012\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002080K\"\u000208\u00a2\u0006\u0003\u0010\u0084\u0001R\u001a\u0010\u0006\u001a\u000e\u0018\u00010\u0007R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001f\"\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020\u001eX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\u001fR\u0014\u0010#\u001a\u00020\u001e8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u001fR\u0012\u0010$\u001a\u00020\u001eX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010\u001fR\u000e\u0010%\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u00020\'X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00100\u001a\u000201X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001a\u00106\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020807X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u00109\u001a\u0004\u0018\u00010:X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R(\u0010A\u001a\u0004\u0018\u00010@2\b\u0010?\u001a\u0004\u0018\u00010@@BX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u000e\u0010F\u001a\u00020GX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020IX\u0082.\u00a2\u0006\u0002\n\u0000R\u0018\u0010J\u001a\n\u0012\u0004\u0012\u000208\u0018\u00010KX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010L\u00a8\u0006\u0086\u0001"}, d2 = {"Lorg/quick/library/b/QuickListFragment2;", "M", "Lorg/quick/library/b/BaseFragment;", "Landroid/support/v4/widget/SwipeRefreshLayout$OnRefreshListener;", "Lorg/quick/library/widgets/XStickyListHeadersListView$OnRefreshListener;", "()V", "adapter", "Lorg/quick/library/b/QuickListFragment2$Adapter;", "errorType", "Lorg/quick/library/b/QuickListActivity$ErrorType;", "getErrorType", "()Lorg/quick/library/b/QuickListActivity$ErrorType;", "setErrorType", "(Lorg/quick/library/b/QuickListActivity$ErrorType;)V", "footerContainer", "Landroid/widget/FrameLayout;", "headerContainer", "hintErrorIv", "Landroid/widget/ImageView;", "getHintErrorIv", "()Landroid/widget/ImageView;", "setHintErrorIv", "(Landroid/widget/ImageView;)V", "hintErrorTv", "Landroid/widget/TextView;", "getHintErrorTv", "()Landroid/widget/TextView;", "setHintErrorTv", "(Landroid/widget/TextView;)V", "isDefaultNoMsgLayout", "", "()Z", "setDefaultNoMsgLayout", "(Z)V", "isLoadMoreEnable", "isPullRefresh", "isPullRefreshEnable", "listContainer", "noMsgContainer", "Landroid/view/View;", "getNoMsgContainer", "()Landroid/view/View;", "setNoMsgContainer", "(Landroid/view/View;)V", "onRequestListener", "Lorg/quick/library/b/QuickListActivity$OnRequestListener;", "onTabSelectedListener", "Landroid/support/design/widget/TabLayout$OnTabSelectedListener;", "pageNumber", "", "getPageNumber", "()I", "setPageNumber", "(I)V", "params", "Ljava/util/HashMap;", "", "refreshBtn", "Landroid/widget/Button;", "getRefreshBtn", "()Landroid/widget/Button;", "setRefreshBtn", "(Landroid/widget/Button;)V", "<set-?>", "Lorg/quick/library/widgets/XStickyListHeadersListView;", "stickyListHeadersListView", "getStickyListHeadersListView", "()Lorg/quick/library/widgets/XStickyListHeadersListView;", "setStickyListHeadersListView", "(Lorg/quick/library/widgets/XStickyListHeadersListView;)V", "swipeRefreshLayout", "Lorg/quick/library/widgets/CustomCompatSwipeRefreshLayout;", "tabLayout", "Landroid/support/design/widget/TabLayout;", "tabs", "", "[Ljava/lang/String;", "checkNotNull", "", "dataHas", "isHasData", "type", "getAdapter", "init", "onBindDataHeaderView", "holder", "Lorg/quick/library/b/BaseViewHolder;", "itemData", "position", "(Lorg/quick/library/b/BaseViewHolder;Ljava/lang/Object;I)V", "onBindDataItemView", "onBindHeaderId", "", "(Ljava/lang/Object;I)J", "onLoadMore", "onRefresh", "onRefreshClick", "onRequestDataSuccess", "model", "(Ljava/lang/Object;Z)V", "onRequestEnd", "onRequestError", "onResultErrorBtnTxt", "onResultErrorNetWorkIcon", "onResultErrorNetWorkTxt", "onResultErrorNoMsgIcon", "onResultErrorNoMsgTxt", "onResultErrorOtherIcon", "onResultErrorOtherTxt", "onResultErrorServiceIcon", "onResultErrorServiceTxt", "onResultHeaderLayout", "onResultItemLayout", "onResultLayoutResId", "onResultNoMsgLayout", "onResultNoMsgLayoutRes", "onResultParams", "", "onResultUrl", "requestData", "setFooterContainer", "view", "setHeaderContainer", "setHintErrorStyle", "setNoMore", "isNoMore", "setOnRequestListener", "setupLayout", "setupTab", "(Landroid/support/design/widget/TabLayout$OnTabSelectedListener;[Ljava/lang/String;)V", "selectorPosition", "(Landroid/support/design/widget/TabLayout$OnTabSelectedListener;I[Ljava/lang/String;)Landroid/support/design/widget/TabLayout;", "([Ljava/lang/String;)V", "Adapter", "quick-library_debug"})
public abstract class QuickListFragment2<M extends java.lang.Object> extends org.quick.library.b.BaseFragment implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, org.quick.library.widgets.XStickyListHeadersListView.OnRefreshListener {
    private org.quick.library.b.QuickListActivity.OnRequestListener onRequestListener;
    private android.support.design.widget.TabLayout.OnTabSelectedListener onTabSelectedListener;
    @org.jetbrains.annotations.NotNull()
    public android.view.View noMsgContainer;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView hintErrorIv;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView hintErrorTv;
    @org.jetbrains.annotations.Nullable()
    private android.widget.Button refreshBtn;
    private org.quick.library.widgets.CustomCompatSwipeRefreshLayout swipeRefreshLayout;
    private android.widget.FrameLayout headerContainer;
    private android.widget.FrameLayout footerContainer;
    private android.widget.FrameLayout listContainer;
    private android.support.design.widget.TabLayout tabLayout;
    private java.lang.String[] tabs;
    @org.jetbrains.annotations.Nullable()
    private org.quick.library.widgets.XStickyListHeadersListView stickyListHeadersListView;
    private org.quick.library.b.QuickListFragment2<M>.Adapter adapter;
    private boolean isDefaultNoMsgLayout;
    private int pageNumber;
    @org.jetbrains.annotations.NotNull()
    private org.quick.library.b.QuickListActivity.ErrorType errorType;
    private final java.util.HashMap<java.lang.String, java.lang.String> params = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.View getNoMsgContainer() {
        return null;
    }
    
    public final void setNoMsgContainer(@org.jetbrains.annotations.NotNull()
    android.view.View p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getHintErrorIv() {
        return null;
    }
    
    public final void setHintErrorIv(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getHintErrorTv() {
        return null;
    }
    
    public final void setHintErrorTv(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.Button getRefreshBtn() {
        return null;
    }
    
    public final void setRefreshBtn(@org.jetbrains.annotations.Nullable()
    android.widget.Button p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final org.quick.library.widgets.XStickyListHeadersListView getStickyListHeadersListView() {
        return null;
    }
    
    private final void setStickyListHeadersListView(org.quick.library.widgets.XStickyListHeadersListView p0) {
    }
    
    public final boolean isDefaultNoMsgLayout() {
        return false;
    }
    
    public final void setDefaultNoMsgLayout(boolean p0) {
    }
    
    public final int getPageNumber() {
        return 0;
    }
    
    public final void setPageNumber(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.quick.library.b.QuickListActivity.ErrorType getErrorType() {
        return null;
    }
    
    public final void setErrorType(@org.jetbrains.annotations.NotNull()
    org.quick.library.b.QuickListActivity.ErrorType p0) {
    }
    
    private final boolean isPullRefresh() {
        return false;
    }
    
    public abstract boolean isPullRefreshEnable();
    
    public abstract boolean isLoadMoreEnable();
    
    @java.lang.Override()
    public void init() {
    }
    
    @java.lang.Override()
    public int onResultLayoutResId() {
        return 0;
    }
    
    private final void setupLayout() {
    }
    
    public final void setupTab(@org.jetbrains.annotations.NotNull()
    java.lang.String... tabs) {
    }
    
    public final void setupTab(@org.jetbrains.annotations.Nullable()
    android.support.design.widget.TabLayout.OnTabSelectedListener onTabSelectedListener, @org.jetbrains.annotations.NotNull()
    java.lang.String... tabs) {
    }
    
    /**
     * * 安装顶部TabLayout
     *     *
     *     * @param tabs
     */
    @org.jetbrains.annotations.NotNull()
    public final android.support.design.widget.TabLayout setupTab(@org.jetbrains.annotations.Nullable()
    android.support.design.widget.TabLayout.OnTabSelectedListener onTabSelectedListener, int selectorPosition, @org.jetbrains.annotations.NotNull()
    java.lang.String... tabs) {
        return null;
    }
    
    public final void setHeaderContainer(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void setFooterContainer(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void onRefreshClick(@org.jetbrains.annotations.NotNull()
    org.quick.library.b.QuickListActivity.ErrorType errorType) {
    }
    
    @java.lang.Override()
    public void onRefresh() {
    }
    
    @java.lang.Override()
    public void onLoadMore() {
    }
    
    public final void requestData(int pageNumber) {
    }
    
    /**
     * * 设置是否有数据
     *     *
     *     * @param isHasData
     */
    public final void dataHas(boolean isHasData, @org.jetbrains.annotations.NotNull()
    org.quick.library.b.QuickListActivity.ErrorType type) {
    }
    
    /**
     * * 设置是否有数据
     *     *
     *     * @param isHasData
     */
    public final void dataHas(boolean isHasData) {
    }
    
    private final void setHintErrorStyle(org.quick.library.b.QuickListActivity.ErrorType type) {
    }
    
    public final void setNoMore(boolean isNoMore) {
    }
    
    /**
     * * 返回网络错误图片
     *     *
     *     * @return
     */
    @android.support.annotation.DrawableRes()
    public final int onResultErrorNetWorkIcon() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onResultErrorNetWorkTxt() {
        return null;
    }
    
    /**
     * * 返回没有数据的图片
     *     *
     *     * @return
     */
    @android.support.annotation.DrawableRes()
    public final int onResultErrorNoMsgIcon() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onResultErrorNoMsgTxt() {
        return null;
    }
    
    /**
     * * 返回服务器错误的图片
     *     *
     *     * @return
     */
    @android.support.annotation.DrawableRes()
    public final int onResultErrorServiceIcon() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onResultErrorServiceTxt() {
        return null;
    }
    
    /**
     * * 返回服务器错误的图片
     *     *
     *     * @return
     */
    @android.support.annotation.DrawableRes()
    public final int onResultErrorOtherIcon() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onResultErrorOtherTxt() {
        return null;
    }
    
    /**
     * * 网络出错时的文字
     *     *
     *     * @return
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onResultErrorBtnTxt() {
        return null;
    }
    
    @android.support.annotation.LayoutRes()
    public final int onResultNoMsgLayoutRes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.View onResultNoMsgLayout() {
        return null;
    }
    
    public final void checkNotNull() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.quick.library.b.QuickListFragment2<M>.Adapter getAdapter() {
        return null;
    }
    
    public final void onRequestEnd() {
    }
    
    public final void onRequestError() {
    }
    
    public final void setOnRequestListener(@org.jetbrains.annotations.NotNull()
    org.quick.library.b.QuickListActivity.OnRequestListener onRequestListener) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String onResultUrl();
    
    public abstract void onResultParams(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> params);
    
    public abstract void onRequestDataSuccess(M model, boolean isPullRefresh);
    
    @android.support.annotation.LayoutRes()
    public abstract int onResultItemLayout();
    
    @android.support.annotation.LayoutRes()
    public abstract int onResultHeaderLayout();
    
    public abstract long onBindHeaderId(M itemData, int position);
    
    public abstract void onBindDataItemView(@org.jetbrains.annotations.NotNull()
    org.quick.library.b.BaseViewHolder holder, M itemData, int position);
    
    public abstract void onBindDataHeaderView(@org.jetbrains.annotations.NotNull()
    org.quick.library.b.BaseViewHolder holder, M itemData, int position);
    
    public QuickListFragment2() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0014\u0010\u0006\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\"\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0015\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\"\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0014\u0010\u0018\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lorg/quick/library/b/QuickListFragment2$Adapter;", "Landroid/widget/BaseAdapter;", "Lse/emilsjolander/stickylistheaders/StickyListHeadersAdapter;", "(Lorg/quick/library/b/QuickListFragment2;)V", "dataList", "", "addDataList", "", "", "getCount", "", "getDataList", "getHeaderId", "", "position", "getHeaderView", "Landroid/view/View;", "convertView", "parent", "Landroid/view/ViewGroup;", "getItem", "(I)Ljava/lang/Object;", "getItemId", "getView", "setDataList", "quick-library_debug"})
    public final class Adapter extends android.widget.BaseAdapter implements se.emilsjolander.stickylistheaders.StickyListHeadersAdapter {
        private java.util.List<M> dataList;
        
        @java.lang.Override()
        public int getCount() {
            return 0;
        }
        
        @java.lang.Override()
        public M getItem(int position) {
            return null;
        }
        
        @java.lang.Override()
        public long getItemId(int position) {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public android.view.View getView(int position, @org.jetbrains.annotations.Nullable()
        android.view.View convertView, @org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public android.view.View getHeaderView(int position, @org.jetbrains.annotations.Nullable()
        android.view.View convertView, @org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent) {
            return null;
        }
        
        @java.lang.Override()
        public long getHeaderId(int position) {
            return 0L;
        }
        
        public final void setDataList(@org.jetbrains.annotations.NotNull()
        java.util.List<M> dataList) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.util.List<M> getDataList() {
            return null;
        }
        
        public final void addDataList(@org.jetbrains.annotations.NotNull()
        java.util.List<? extends M> dataList) {
        }
        
        public Adapter() {
            super();
        }
    }
}