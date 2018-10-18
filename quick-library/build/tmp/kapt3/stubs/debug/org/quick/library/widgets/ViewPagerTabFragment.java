package org.quick.library.widgets;

import java.lang.System;

/**
 * * 快速构建ViewPager
 * * Created by zoulx on 2017/11/14.
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\"B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\'\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00180\u0017\"\u00020\u0018\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cJ/\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020 0\u0017\"\u00020 \u00a2\u0006\u0002\u0010!R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006#"}, d2 = {"Lorg/quick/library/widgets/ViewPagerTabFragment;", "Landroid/support/v4/view/ViewPager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "aAdapter", "Lorg/quick/library/widgets/ViewPagerTabFragment$Adapter;", "getAAdapter", "()Lorg/quick/library/widgets/ViewPagerTabFragment$Adapter;", "setAAdapter", "(Lorg/quick/library/widgets/ViewPagerTabFragment$Adapter;)V", "destroy", "", "fm", "Landroid/support/v4/app/FragmentManager;", "setupBottomNavigationView", "bottomNavigationView", "Landroid/support/design/widget/BottomNavigationView;", "setupData", "fragments", "", "Landroid/support/v4/app/Fragment;", "(Landroid/support/v4/app/FragmentManager;[Landroid/support/v4/app/Fragment;)V", "setupTabLayout", "tabLayout", "Landroid/support/design/widget/TabLayout;", "selectorPosition", "", "strings", "", "(Landroid/support/design/widget/TabLayout;I[Ljava/lang/String;)V", "Adapter", "quick-library_debug"})
public final class ViewPagerTabFragment extends android.support.v4.view.ViewPager {
    @org.jetbrains.annotations.Nullable()
    private org.quick.library.widgets.ViewPagerTabFragment.Adapter aAdapter;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final org.quick.library.widgets.ViewPagerTabFragment.Adapter getAAdapter() {
        return null;
    }
    
    public final void setAAdapter(@org.jetbrains.annotations.Nullable()
    org.quick.library.widgets.ViewPagerTabFragment.Adapter p0) {
    }
    
    public final void setupData(@org.jetbrains.annotations.NotNull()
    android.support.v4.app.FragmentManager fm, @org.jetbrains.annotations.NotNull()
    android.support.v4.app.Fragment... fragments) {
    }
    
    public final void setupBottomNavigationView(@org.jetbrains.annotations.NotNull()
    android.support.design.widget.BottomNavigationView bottomNavigationView) {
    }
    
    public final void setupTabLayout(@org.jetbrains.annotations.NotNull()
    android.support.design.widget.TabLayout tabLayout) {
    }
    
    public final void setupTabLayout(@org.jetbrains.annotations.NotNull()
    android.support.design.widget.TabLayout tabLayout, int selectorPosition, @org.jetbrains.annotations.NotNull()
    java.lang.String... strings) {
    }
    
    public final void destroy(@org.jetbrains.annotations.NotNull()
    android.support.v4.app.FragmentManager fm) {
    }
    
    public ViewPagerTabFragment(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    public ViewPagerTabFragment(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\tJ\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0014\u0010\u000f\u001a\u00020\u00102\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tR\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lorg/quick/library/widgets/ViewPagerTabFragment$Adapter;", "Landroid/support/v4/app/FragmentPagerAdapter;", "fm", "Landroid/support/v4/app/FragmentManager;", "fragments", "", "Landroid/support/v4/app/Fragment;", "(Landroid/support/v4/app/FragmentManager;[Landroid/support/v4/app/Fragment;)V", "dataList", "", "getCount", "", "getDataList", "getItem", "position", "setDataList", "", "quick-library_debug"})
    public static final class Adapter extends android.support.v4.app.FragmentPagerAdapter {
        private java.util.List<android.support.v4.app.Fragment> dataList;
        
        public final void setDataList(@org.jetbrains.annotations.NotNull()
        java.util.List<android.support.v4.app.Fragment> dataList) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.util.List<android.support.v4.app.Fragment> getDataList() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public android.support.v4.app.Fragment getItem(int position) {
            return null;
        }
        
        @java.lang.Override()
        public int getCount() {
            return 0;
        }
        
        public Adapter(@org.jetbrains.annotations.NotNull()
        android.support.v4.app.FragmentManager fm, @org.jetbrains.annotations.NotNull()
        android.support.v4.app.Fragment... fragments) {
            super(null);
        }
    }
}