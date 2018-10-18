package org.quick.library.function.selectorimg.photoandselectorshow;

import java.lang.System;

/**
 * * Created by work on 2017/6/30.
 * *
 * * @author chris zou
 * * @mail chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0019B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u0014\u0010\u0017\u001a\u00020\t2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u000e\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lorg/quick/library/function/selectorimg/photoandselectorshow/PhotoShowAndSelectorAdapter;", "Landroid/support/v4/view/PagerAdapter;", "()V", "imgList", "", "", "onItemClickListener", "Lorg/quick/library/function/selectorimg/photoandselectorshow/PhotoShowAndSelectorAdapter$OnItemClickListener;", "destroyItem", "", "container", "Landroid/view/ViewGroup;", "position", "", "object", "", "getCount", "instantiateItem", "isViewFromObject", "", "arg0", "Landroid/view/View;", "arg1", "setImgList", "setOnItemClickListener", "OnItemClickListener", "quick-library_debug"})
public final class PhotoShowAndSelectorAdapter extends android.support.v4.view.PagerAdapter {
    private java.util.List<java.lang.String> imgList;
    private org.quick.library.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorAdapter.OnItemClickListener onItemClickListener;
    
    @java.lang.Override()
    public int getCount() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean isViewFromObject(@org.jetbrains.annotations.NotNull()
    android.view.View arg0, @org.jetbrains.annotations.NotNull()
    java.lang.Object arg1) {
        return false;
    }
    
    @java.lang.Override()
    public void destroyItem(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup container, int position, @org.jetbrains.annotations.NotNull()
    java.lang.Object object) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.Object instantiateItem(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup container, int position) {
        return null;
    }
    
    public final void setImgList(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imgList) {
    }
    
    public final void setOnItemClickListener(@org.jetbrains.annotations.NotNull()
    org.quick.library.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorAdapter.OnItemClickListener onItemClickListener) {
    }
    
    public PhotoShowAndSelectorAdapter() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lorg/quick/library/function/selectorimg/photoandselectorshow/PhotoShowAndSelectorAdapter$OnItemClickListener;", "", "onItemClick", "", "view", "Landroid/view/View;", "position", "", "quick-library_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClick(@org.jetbrains.annotations.NotNull()
        android.view.View view, int position);
    }
}