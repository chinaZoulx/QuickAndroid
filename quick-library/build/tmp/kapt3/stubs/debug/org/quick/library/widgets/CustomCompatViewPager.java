package org.quick.library.widgets;

import java.lang.System;

/**
 * * 1、嵌套滚动兼容
 * * 2、单击事件
 * * 3、子View缩放兼容
 * *
 * * @author chrisZou
 * * @blog http://blog.csdn.net/mcy478643968/article/details/19609407
 * * @blog http://blog.csdn.net/leewenjin/article/details/21011841
 * * @from http://blog.csdn.net/fy993912_chris/article/details/75006138
 * * @email chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \'2\u00020\u0001:\u0003\'()B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001aH\u0002J\u0010\u0010$\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u000e\u0010%\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010&\u001a\u00020 2\u0006\u0010\u0019\u001a\u00020\u001aR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u001a\u0010\u0011\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0014\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\rR\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager;", "Landroid/support/v4/view/ViewPager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "curX", "", "getCurX", "()F", "setCurX", "(F)V", "curY", "getCurY", "setCurY", "downX", "getDownX", "setDownX", "downY", "getDownY", "setDownY", "onItemClickListener", "Lorg/quick/library/widgets/CustomCompatViewPager$OnItemClickListener;", "realSize", "", "onInterceptTouchEvent", "", "ev", "Landroid/view/MotionEvent;", "onItemClick", "", "adapter", "Landroid/support/v4/view/PagerAdapter;", "currentItem", "onTouchEvent", "setOnItemClickListener", "setRealSize", "Companion", "OnItemClickListener", "TransformerFactory", "quick-library_debug"})
public final class CustomCompatViewPager extends android.support.v4.view.ViewPager {
    private org.quick.library.widgets.CustomCompatViewPager.OnItemClickListener onItemClickListener;
    private float curX;
    private float curY;
    private float downX;
    private float downY;
    private int realSize;
    private static final int CLICKED_DISTANCE = 10;
    public static final org.quick.library.widgets.CustomCompatViewPager.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    public final float getCurX() {
        return 0.0F;
    }
    
    public final void setCurX(float p0) {
    }
    
    public final float getCurY() {
        return 0.0F;
    }
    
    public final void setCurY(float p0) {
    }
    
    public final float getDownX() {
        return 0.0F;
    }
    
    public final void setDownX(float p0) {
    }
    
    public final float getDownY() {
        return 0.0F;
    }
    
    public final void setDownY(float p0) {
    }
    
    public final void setRealSize(int realSize) {
    }
    
    @java.lang.Override()
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent ev) {
        return false;
    }
    
    /**
     * * 解决viewpager嵌套ImageScaleView的问题
     *     *
     *     * @param ev
     *     * @return
     */
    @java.lang.Override()
    public boolean onInterceptTouchEvent(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent ev) {
        return false;
    }
    
    /**
     * * 单击
     */
    private final void onItemClick(android.support.v4.view.PagerAdapter adapter, int currentItem) {
    }
    
    public final void setOnItemClickListener(@org.jetbrains.annotations.NotNull()
    org.quick.library.widgets.CustomCompatViewPager.OnItemClickListener onItemClickListener) {
    }
    
    public CustomCompatViewPager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    public CustomCompatViewPager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&\u00a8\u0006\n"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$OnItemClickListener;", "", "onItemClick", "", "adapter", "Landroid/support/v4/view/PagerAdapter;", "currentItem", "", "itemView", "Landroid/view/View;", "quick-library_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClick(@org.jetbrains.annotations.NotNull()
        android.support.v4.view.PagerAdapter adapter, int currentItem, @org.jetbrains.annotations.Nullable()
        android.view.View itemView);
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \n2\u00020\u0001:\u0007\t\n\u000b\f\r\u000e\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\u0010"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;", "", "()V", "getTransformer", "Landroid/support/v4/view/ViewPager$PageTransformer;", "viewPager", "Landroid/support/v4/view/ViewPager;", "type", "Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$TransformerType;", "ClassHolder", "Companion", "DepthPageTransformer", "FlagPageTransformer", "FlymePageTransformer", "TransformerType", "ZoomOutPageTransformer", "quick-library_debug"})
    public static final class TransformerFactory {
        public static final org.quick.library.widgets.CustomCompatViewPager.TransformerFactory.Companion Companion = null;
        
        @org.jetbrains.annotations.NotNull()
        public final android.support.v4.view.ViewPager.PageTransformer getTransformer(@org.jetbrains.annotations.NotNull()
        android.support.v4.view.ViewPager viewPager, @org.jetbrains.annotations.NotNull()
        org.quick.library.widgets.CustomCompatViewPager.TransformerFactory.TransformerType type) {
            return null;
        }
        
        public TransformerFactory() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$ClassHolder;", "", "()V", "INSTANCE", "Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;", "getINSTANCE", "()Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;", "quick-library_debug"})
        static final class ClassHolder {
            @org.jetbrains.annotations.NotNull()
            private static final org.quick.library.widgets.CustomCompatViewPager.TransformerFactory INSTANCE = null;
            public static final org.quick.library.widgets.CustomCompatViewPager.TransformerFactory.ClassHolder INSTANCE = null;
            
            @org.jetbrains.annotations.NotNull()
            public final org.quick.library.widgets.CustomCompatViewPager.TransformerFactory getINSTANCE() {
                return null;
            }
            
            private ClassHolder() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$TransformerType;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "setValue", "(I)V", "Depth", "ZoomOut", "Flag", "Flyme", "quick-library_debug"})
        public static enum TransformerType {
            /*public static final*/ Depth /* = new Depth(0) */,
            /*public static final*/ ZoomOut /* = new ZoomOut(0) */,
            /*public static final*/ Flag /* = new Flag(0) */,
            /*public static final*/ Flyme /* = new Flyme(0) */;
            private int value;
            
            public final int getValue() {
                return 0;
            }
            
            public final void setValue(int p0) {
            }
            
            TransformerType(int value) {
            }
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\f"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$DepthPageTransformer;", "Landroid/support/v4/view/ViewPager$PageTransformer;", "(Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;)V", "MIN_SCALE", "", "getMIN_SCALE", "()F", "transformPage", "", "view", "Landroid/view/View;", "position", "quick-library_debug"})
        public final class DepthPageTransformer implements android.support.v4.view.ViewPager.PageTransformer {
            private final float MIN_SCALE = 0.75F;
            
            public final float getMIN_SCALE() {
                return 0.0F;
            }
            
            @java.lang.Override()
            public void transformPage(@org.jetbrains.annotations.NotNull()
            android.view.View view, float position) {
            }
            
            public DepthPageTransformer() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$ZoomOutPageTransformer;", "Landroid/support/v4/view/ViewPager$PageTransformer;", "(Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;)V", "MIN_ALPHA", "", "MIN_SCALE", "transformPage", "", "view", "Landroid/view/View;", "position", "quick-library_debug"})
        public final class ZoomOutPageTransformer implements android.support.v4.view.ViewPager.PageTransformer {
            private final float MIN_SCALE = 0.85F;
            private final float MIN_ALPHA = 0.5F;
            
            @android.annotation.SuppressLint(value = {"NewApi"})
            @java.lang.Override()
            public void transformPage(@org.jetbrains.annotations.NotNull()
            android.view.View view, float position) {
            }
            
            public ZoomOutPageTransformer() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$FlagPageTransformer;", "Landroid/support/v4/view/ViewPager$PageTransformer;", "(Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;)V", "MIN_ALPHA", "", "MIN_SCALE", "transformPage", "", "view", "Landroid/view/View;", "position", "quick-library_debug"})
        public final class FlagPageTransformer implements android.support.v4.view.ViewPager.PageTransformer {
            private final float MIN_SCALE = 0.85F;
            private final float MIN_ALPHA = 0.5F;
            
            @android.annotation.SuppressLint(value = {"NewApi"})
            @java.lang.Override()
            public void transformPage(@org.jetbrains.annotations.NotNull()
            android.view.View view, float position) {
            }
            
            public FlagPageTransformer() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$FlymePageTransformer;", "Landroid/support/v4/view/ViewPager$PageTransformer;", "mViewPager", "Landroid/support/v4/view/ViewPager;", "(Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;Landroid/support/v4/view/ViewPager;)V", "itemWidth", "", "mCoverWidth", "", "mScaleMax", "mScaleMin", "offsetPosition", "reduceX", "transformPage", "", "view", "Landroid/view/View;", "position", "quick-library_debug"})
        public final class FlymePageTransformer implements android.support.v4.view.ViewPager.PageTransformer {
            private float reduceX;
            private float itemWidth;
            private float offsetPosition;
            private final int mCoverWidth = 0;
            private final float mScaleMax = 0.9F;
            private final float mScaleMin = 0.7F;
            private final android.support.v4.view.ViewPager mViewPager = null;
            
            @java.lang.Override()
            public void transformPage(@org.jetbrains.annotations.NotNull()
            android.view.View view, float position) {
            }
            
            public FlymePageTransformer(@org.jetbrains.annotations.NotNull()
            android.support.v4.view.ViewPager mViewPager) {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory$Companion;", "", "()V", "instance", "Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;", "getInstance", "()Lorg/quick/library/widgets/CustomCompatViewPager$TransformerFactory;", "quick-library_debug"})
        public static final class Companion {
            
            @org.jetbrains.annotations.NotNull()
            public final org.quick.library.widgets.CustomCompatViewPager.TransformerFactory getInstance() {
                return null;
            }
            
            private Companion() {
                super();
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lorg/quick/library/widgets/CustomCompatViewPager$Companion;", "", "()V", "CLICKED_DISTANCE", "", "getCLICKED_DISTANCE", "()I", "quick-library_debug"})
    public static final class Companion {
        
        public final int getCLICKED_DISTANCE() {
            return 0;
        }
        
        private Companion() {
            super();
        }
    }
}