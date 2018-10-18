package org.quick.library.widgets;

import java.lang.System;

/**
 * * Created by work on 2017/4/20.
 * * 底部圆形
 * * @author chris zou
 * * @mail chrisSpringSmell@gmail.com
 */
@kotlin.Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0015\n\u0000\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u00100\u001a\u000201H\u0002J\u0012\u00102\u001a\u0002012\b\u00103\u001a\u0004\u0018\u000104H\u0016J\u0012\u00105\u001a\u0002012\b\u00103\u001a\u0004\u0018\u000104H\u0002J\b\u00106\u001a\u000201H\u0002J(\u00107\u001a\u0002012\u0006\u00108\u001a\u00020\b2\u0006\u00109\u001a\u00020\b2\u0006\u0010:\u001a\u00020\b2\u0006\u0010;\u001a\u00020\bH\u0014J\u0012\u0010<\u001a\u0002012\n\u0010=\u001a\u00020>\"\u00020\bR\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001e\"\u0004\b#\u0010 R\u001a\u0010$\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u001e\"\u0004\b&\u0010 R\u001a\u0010\'\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u001e\"\u0004\b)\u0010 R\u001a\u0010*\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\n\"\u0004\b,\u0010\fR\u001a\u0010-\u001a\u00020\bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\n\"\u0004\b/\u0010\f\u00a8\u0006?"}, d2 = {"Lorg/quick/library/widgets/SemicircleBottomView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "endColor", "", "getEndColor", "()I", "setEndColor", "(I)V", "height", "getHeight$quick_library_debug", "setHeight$quick_library_debug", "isShadow", "", "()Z", "setShadow", "(Z)V", "mPaint", "Landroid/graphics/Paint;", "getMPaint", "()Landroid/graphics/Paint;", "setMPaint", "(Landroid/graphics/Paint;)V", "paddingHeight", "", "getPaddingHeight", "()F", "setPaddingHeight", "(F)V", "radius", "getRadius", "setRadius", "shadowDistance", "getShadowDistance", "setShadowDistance", "shadowRadious", "getShadowRadious", "setShadowRadious", "startColor", "getStartColor", "setStartColor", "width", "getWidth$quick_library_debug", "setWidth$quick_library_debug", "configPaint", "", "draw", "canvas", "Landroid/graphics/Canvas;", "drawLine", "init", "onSizeChanged", "w", "h", "oldw", "oldh", "setColors", "colors", "", "quick-library_debug"})
public final class SemicircleBottomView extends android.view.View {
    private int width;
    private int height;
    @org.jetbrains.annotations.NotNull()
    public android.graphics.Paint mPaint;
    private float radius;
    private float shadowDistance;
    private float shadowRadious;
    private int startColor;
    private int endColor;
    private float paddingHeight;
    private boolean isShadow;
    private java.util.HashMap _$_findViewCache;
    
    public final int getWidth$quick_library_debug() {
        return 0;
    }
    
    public final void setWidth$quick_library_debug(int p0) {
    }
    
    public final int getHeight$quick_library_debug() {
        return 0;
    }
    
    public final void setHeight$quick_library_debug(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Paint getMPaint() {
        return null;
    }
    
    public final void setMPaint(@org.jetbrains.annotations.NotNull()
    android.graphics.Paint p0) {
    }
    
    public final float getRadius() {
        return 0.0F;
    }
    
    public final void setRadius(float p0) {
    }
    
    public final float getShadowDistance() {
        return 0.0F;
    }
    
    public final void setShadowDistance(float p0) {
    }
    
    public final float getShadowRadious() {
        return 0.0F;
    }
    
    public final void setShadowRadious(float p0) {
    }
    
    public final int getStartColor() {
        return 0;
    }
    
    public final void setStartColor(int p0) {
    }
    
    public final int getEndColor() {
        return 0;
    }
    
    public final void setEndColor(int p0) {
    }
    
    public final float getPaddingHeight() {
        return 0.0F;
    }
    
    public final void setPaddingHeight(float p0) {
    }
    
    public final boolean isShadow() {
        return false;
    }
    
    public final void setShadow(boolean p0) {
    }
    
    @java.lang.Override()
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }
    
    private final void init() {
    }
    
    public final void setColors(@org.jetbrains.annotations.NotNull()
    int... colors) {
    }
    
    @java.lang.Override()
    public void draw(@org.jetbrains.annotations.Nullable()
    android.graphics.Canvas canvas) {
    }
    
    private final void configPaint() {
    }
    
    private final void drawLine(android.graphics.Canvas canvas) {
    }
    
    public SemicircleBottomView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public SemicircleBottomView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}