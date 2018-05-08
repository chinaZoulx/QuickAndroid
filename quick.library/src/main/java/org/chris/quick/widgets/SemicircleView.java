package org.chris.quick.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.chris.quick.R;
import org.chris.quick.b.BaseApplication;

/**
 * Created by work on 2017/4/20.
 * 半圆-红包上半截
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class SemicircleView extends View {
    public static final double BASE_WIDTH = 720;
    float scale;
    Paint mPaint;
    float radius;

    public SemicircleView(Context context) {
        this(context, null);
    }

    public SemicircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SemicircleView);
        radius = ta.getDimension(R.styleable.SemicircleView_SemicircleViewRadius, 0);
        ta.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (MeasureSpec.getSize(heightMeasureSpec) * scale);
        setMeasuredDimension(width, height);
    }

    private void init() {
        scale = (float) BaseApplication.width / (float) BASE_WIDTH;//换算为指定比例
        radius *= scale;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawCirle(canvas);
    }

    private void drawCirle(Canvas canvas) {
        int[] colors = new int[]{Color.parseColor("#f55a67"), Color.parseColor("#f44b55")};
        RadialGradient linearGradient = new RadialGradient(getWidth() / 2, 0, getHeight() / 2, colors, null, Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient);
        mPaint.setShadowLayer(10 * scale, 0, 2 * scale, Color.BLACK);
        float startX = radius, startY = 0;
        float endX = getWidth(), endY = radius;

        Path path = new Path();
        path.moveTo(startX, startY);
        if (radius > 0) {
            path.quadTo(0, 0, 0, radius);
        }
        //
        path.quadTo((endX - startX) / 2, getHeight(), endX, endY);
        path.moveTo(endX, endY);
        if (radius > 0) {
            path.quadTo(endX, endY - radius, endX - radius, 0);
        }
        path.lineTo(startX, startY);
        canvas.drawPath(path, mPaint);
    }
}
