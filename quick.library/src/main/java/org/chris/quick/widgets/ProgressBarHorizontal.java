package org.chris.quick.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.chris.quick.R;


/**
 * Created by work on 2017/8/8.
 * 横向进度条
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class ProgressBarHorizontal extends View {
    public float maxProgress = 100;
    /*背景*/
    Bitmap bgBitmap;
    int bgColorStart;
    int bgColorEnd;
    int[] bgColors;
    int bgRadius;
    int bgLtRadius;
    int bgLbRadius;
    int bgRbRadius;
    int bgRtRadius;
    Paint bgPaint;
    /*end*/

    /*图片*/
    Bitmap coverBitmap;
    int coverColorStart;
    int coverColorEnd;
    int[] coverColors;
    int coverRadius;
    int coverLtRadius;
    int coverLbRadius;
    int coverRtRadius;
    int coverRbRadius;
    Paint coverPaint;
    /*end*/

    int w, h;
    int compareMinSize;//高与宽较小那个

    int scaleType;
    float progress;

    public ProgressBarHorizontal(Context context) {
        this(context, null);
    }

    public ProgressBarHorizontal(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarHorizontal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context.obtainStyledAttributes(attrs, R.styleable.ProgressBarHorizontal));
    }

    public void init(TypedArray typedArray) {
        setupParams(typedArray);
        setupPaint();
    }

    /**
     * 设置参数
     */
    public void setupParams(TypedArray typedArray) {
        bgColorStart = typedArray.getColor(R.styleable.ProgressBarHorizontal_pbhBgColorStart, Color.DKGRAY);
        bgColorEnd = typedArray.getColor(R.styleable.ProgressBarHorizontal_pbhBgColorEnd, bgColorStart);
        bgColors = new int[]{bgColorStart, bgColorEnd};
        bgRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhBgRadius, 0);
        bgLtRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhBgLtRadius, 0);
        bgLbRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhBgLbRadius, 0);
        bgRbRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhBgRbRadius, 0);
        bgRtRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhBgRtRadius, 0);
        Drawable coverDrawable = typedArray.getDrawable(R.styleable.ProgressBarHorizontal_pbhBgSrc);
        bgBitmap = coverDrawable != null ? ((BitmapDrawable) coverDrawable).getBitmap() : null;

        coverColorStart = typedArray.getColor(R.styleable.ProgressBarHorizontal_pbhCoverColorStart, Color.GRAY);
        coverColorEnd = typedArray.getColor(R.styleable.ProgressBarHorizontal_pbhCoverColorEnd, coverColorStart);
        coverColors = new int[]{coverColorStart, coverColorEnd};
        coverRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhCoverRadius, 0);
        coverLtRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhCoverLtRadius, 0);
        coverLbRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhCoverLbRadius, 0);
        coverRbRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhCoverRbRadius, 0);
        coverRtRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_pbhCoverRtRadius, 0);
        Drawable bottomDrawable = typedArray.getDrawable(R.styleable.ProgressBarHorizontal_pbhCoverSrc);
        coverBitmap = bottomDrawable != null ? ((BitmapDrawable) bottomDrawable).getBitmap() : null;

        progress = typedArray.getInteger(R.styleable.ProgressBarHorizontal_pbhProgress, 0);
        scaleType = typedArray.getInteger(R.styleable.ProgressBarHorizontal_pbhScaleType, 0);
    }

    public void setupPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(bgColorStart);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);

        coverPaint = new Paint();
        coverPaint.setColor(coverColorStart);
        coverPaint.setStyle(Paint.Style.FILL);
        coverPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (scaleType == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            if (coverBitmap == null) {
                throw new NullPointerException("wrap_content must set image res");
            }
            setMeasuredDimension(coverBitmap.getWidth(), coverBitmap.getHeight());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;

        compareMinSize = w > h ? h : w;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //bg
        bgPaint.setShader(new LinearGradient(0, h / 2, w, h / 2, bgColors, null, Shader.TileMode.MIRROR));
        if (bgBitmap != null) {
            canvas.drawBitmap(bgBitmap, null, new RectF(0, 0, w, h), null);
        } else if (bgRadius != 0) {
            RectF bgRec = new RectF(0, 0, w, h);
            canvas.drawRoundRect(bgRec, bgRadius, bgRadius, bgPaint);
        } else {
            drawPath(canvas, bgLtRadius, bgLbRadius, bgRbRadius, bgRtRadius, w, bgPaint);
        }
        //cover
        coverPaint.setShader(new LinearGradient(0, h / 2, w, h / 2, coverColors, null, Shader.TileMode.MIRROR));
        float progressWidth = w - w * ((maxProgress - progress) / maxProgress);
        if (coverBitmap != null) {
            RectF imgRect = new RectF(0, 0, progressWidth, h);
            Rect srcRect = new Rect(0, 0, (int) progressWidth, h);
            canvas.drawBitmap(coverBitmap, srcRect, imgRect, null);
        } else if (coverRadius != 0) {
            RectF coverRec = new RectF(0, 0, progressWidth, h);
            canvas.drawRoundRect(coverRec, coverRadius, coverRadius, coverPaint);
        } else {
            drawPath(canvas, coverLtRadius, coverLbRadius, coverRbRadius, coverRtRadius, progressWidth, coverPaint);
        }
    }

    /**
     * 做一阶贝塞尔曲线
     *
     * @param canvas
     * @param lt
     * @param lb
     * @param rb
     * @param rt
     * @param w
     * @param paint
     */
    private void drawPath(Canvas canvas, int lt, int lb, int rb, int rt, float w, Paint paint) {
        //左上角，逆时针开始
        Path path = new Path();
        //左上
        path.moveTo(lt, 0);
        path.quadTo(0, 0, 0, lt);
        //左下
        path.lineTo(0, h - lb);
        path.quadTo(0, h, lb, h);
        //右下
        path.lineTo(w - rb, h);
        path.quadTo(w, h, w, h - rb);
        //右上
        path.lineTo(w, rt);
        path.quadTo(w, 0, w - rt, 0);
        //回到左上角
        path.lineTo(lt, 0);
        canvas.drawPath(path, paint);
    }

    /**
     * @param progress 0-100
     */
    public void setProgress(float progress) {
        if (progress > maxProgress) {
            progress = maxProgress;
        }
        this.progress = progress;
        postInvalidate();
    }

    public void setBgColors(int[] colors) {
        this.bgColors = colors;
    }

    public void setCoverColors(int[] colors) {
        this.coverColors = colors;
    }
}
