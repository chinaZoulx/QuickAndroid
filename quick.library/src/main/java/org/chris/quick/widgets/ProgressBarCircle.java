package org.chris.quick.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.chris.quick.R;

/**
 * Created by work on 2017/8/8.
 * 图形进度条不带百分比
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class ProgressBarCircle extends View {

    /*轮廓：最外圈*/
    int outlineColor;
    int outlineWidth;
    Paint outlinePaint;
    /*end*/

    /*内外圈*/
    int outlineAndInteriorSpaceWidth;//内外圈之间间距
    int outlineAndInteriorSpaceColor;//颜色
    Paint outlineAndInteriorSpacePaint;
    /*end*/

    /*内圈*/
    int interiorColorCover;
    int interiorColorBg;
    Paint interiorCoverPaint;
    Paint interiorBgPaint;
    RectF interiorBounds;
    /*end*/
    int w, h;
    int compareMinSize;//高与宽较小那个
    float radius;

    boolean isLoop;//超出360的，是否循环显示

    PointF middleLocation = new PointF();

    float progress;

    public ProgressBarCircle(Context context) {
        this(context, null);
    }

    public ProgressBarCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context.obtainStyledAttributes(attrs, R.styleable.ProgressBarCircle));
    }

    public void init(TypedArray typedArray) {
        setupParams(typedArray);
        setupPaint();
    }

    /**
     * 设置参数
     */
    public void setupParams(TypedArray typedArray) {
        interiorColorCover = typedArray.getColor(R.styleable.ProgressBarCircle_pbcInteriorColorCover, Color.BLACK);
        interiorColorBg = typedArray.getColor(R.styleable.ProgressBarCircle_pbcInteriorColorBg, Color.TRANSPARENT);
        outlineColor = typedArray.getColor(R.styleable.ProgressBarCircle_pbcOutlineColor, Color.BLACK);
        outlineWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressBarCircle_pbcOutlineWidth, 0);
        outlineAndInteriorSpaceWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressBarCircle_pbcOAndISpaceWidth, 0);
        outlineAndInteriorSpaceColor = typedArray.getColor(R.styleable.ProgressBarCircle_pbcOAndISpaceColor, Color.TRANSPARENT);
        isLoop = typedArray.getBoolean(R.styleable.ProgressBarCircle_pbcIsLoop, false);
        progress = typedArray.getFloat(R.styleable.ProgressBarCircle_pbcProgress, 0);
    }

    public void setupPaint() {
        interiorCoverPaint = new Paint();
        interiorCoverPaint.setAntiAlias(true);
        interiorCoverPaint.setColor(interiorColorCover);
        interiorCoverPaint.setStyle(Paint.Style.FILL);

        interiorBgPaint = new Paint();
        interiorBgPaint.setAntiAlias(true);
        interiorBgPaint.setColor(interiorColorBg);
        interiorBgPaint.setStyle(Paint.Style.FILL);

        outlinePaint = new Paint();
        outlinePaint.setAntiAlias(true);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(outlineColor);
        outlinePaint.setStrokeWidth(outlineWidth);

        outlineAndInteriorSpacePaint = new Paint();
        outlineAndInteriorSpacePaint.setAntiAlias(true);
        outlineAndInteriorSpacePaint.setStyle(Paint.Style.STROKE);
        outlineAndInteriorSpacePaint.setColor(outlineAndInteriorSpaceColor);
        outlineAndInteriorSpacePaint.setStrokeWidth(outlineAndInteriorSpaceWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        middleLocation.x = w / 2.0f;
        middleLocation.y = h / 2.0f;

        compareMinSize = w > h ? h : w;
        radius = compareMinSize / 2.0f;
//        int left = (int) (middleLocation.x - radius + getComparePaddingSize() + outlineAndInteriorSpaceWidth + outlineWidth);
//        int top = h - compareMinSize + getComparePaddingSize() + outlineAndInteriorSpaceWidth + outlineWidth;
//        int right = (int) (middleLocation.x + radius - getComparePaddingSize() - outlineAndInteriorSpaceWidth - outlineWidth);
//        int bottom = compareMinSize - getComparePaddingSize() - outlineAndInteriorSpaceWidth - outlineWidth;

        float left = middleLocation.x - radius + getComparePaddingSize() + outlineAndInteriorSpaceWidth + outlineWidth;
        float top = middleLocation.y - radius + getComparePaddingSize() + outlineAndInteriorSpaceWidth + outlineWidth;
        float right = middleLocation.x + radius - getComparePaddingSize() - outlineAndInteriorSpaceWidth - outlineWidth;
        float bottom = middleLocation.y + radius - getComparePaddingSize() - outlineAndInteriorSpaceWidth - outlineWidth;

        interiorBounds = new RectF(left, top, right, bottom);
    }

    public int getComparePaddingSize() {
        int a;
        int b;

        if (getPaddingLeft() > getPaddingRight())
            a = getPaddingLeft();
        else
            a = getPaddingRight();


        if (getPaddingTop() > getPaddingBottom())
            b = getPaddingTop();
        else
            b = getPaddingBottom();
        return a > b ? a : b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (outlineWidth > 0) {
            /*画外圈*/
            canvas.drawCircle(middleLocation.x, middleLocation.y, radius - getComparePaddingSize() - outlineWidth / 2.0f, outlinePaint);
        }

        if (outlineAndInteriorSpaceWidth > 0 && outlineAndInteriorSpaceColor != Color.TRANSPARENT) {
            /*画间隔圈*/
            canvas.drawCircle(middleLocation.x, middleLocation.y, radius - getComparePaddingSize() - outlineWidth - outlineAndInteriorSpaceWidth / 2.0f, outlineAndInteriorSpacePaint);
        }

        if (interiorColorBg != Color.TRANSPARENT) {
            canvas.drawOval(interiorBounds, interiorBgPaint);
        }

        /*画内圈-弧形进度*/
        int temp = (int) Math.abs((progress / 360));
        if (isLoop && temp > 0 && progress % 360 != 0) {
            if (progress > 0) progress -= temp * 360;
            else progress += temp * 360;
        }
        canvas.drawArc(interiorBounds, -90, progress, true, interiorCoverPaint);
    }

    /**
     * @param progress
     */
    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public float getProgress() {
        return progress;
    }
}
