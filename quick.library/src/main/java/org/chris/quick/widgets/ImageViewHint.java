package org.chris.quick.widgets;

/**
 * 图片右上角小圆点
 *
 * @author Administrator
 * @Date 2016/10/19 0019
 * @modifyInfo1 Administrator-2016/10/19 0019
 * @modifyContent
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.AttributeSet;

import org.chris.quick.tools.common.CommonUtils;
import org.chris.quick.tools.common.FormatUtils;


/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/17
 * @modifyInfo1 chriszou-16/10/17
 * @modifyContent
 */
public class ImageViewHint extends android.support.v7.widget.AppCompatImageView {

    private Paint mHintPaint;
    private Paint mHintBgPaint;
    private int mHintBgColor;
    private int mHintColor;
    private int mHintSize;
    private String mHint;

    private int mHintSpace;

    public ImageViewHint(Context context) {

        this(context, null);
    }

    public ImageViewHint(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    private void init() {
        mHintSpace = FormatUtils.dip2px(getContext(), 4);

        mHintBgColor = Color.RED;
        mHintColor = Color.WHITE;
        mHintSize = FormatUtils.dip2px(getContext(), 12);

        mHintPaint = new Paint();
        mHintPaint.setAntiAlias(true);
        mHintPaint.setColor(mHintColor);

        mHintBgPaint = new Paint();
        mHintBgPaint.setColor(mHintBgColor);
        mHintBgPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (TextUtils.isEmpty(mHint)) {
            return;
        }
        mHintPaint.setTextSize(mHintSize);

        float hintWidth = mHintPaint.measureText(mHint);
        float hintHeight = CommonUtils.getFontHeight(mHintPaint, mHint);

        PointF circlePoint = new PointF();
        circlePoint.x = getWidth() - mHintSpace - hintWidth;
        circlePoint.y = mHintSpace + hintHeight;
        drawCircle(canvas, circlePoint, hintHeight * 0.8f, mHintBgPaint);

        PointF txtPoint = new PointF();
        txtPoint.x = circlePoint.x - hintWidth / 2;
        txtPoint.y = circlePoint.y + hintHeight / 2;
        drawText(canvas, mHint, txtPoint, mHintPaint);
    }

    private void drawText(Canvas canvas, String hint, PointF point, Paint paint) {

        canvas.drawText(hint, point.x, point.y, paint);
    }

    private void drawCircle(Canvas canvas, PointF point, float radius, Paint paint) {

        canvas.drawCircle(point.x, point.y, radius, paint);
    }

    public void setHint(String hint) {

        setConfig(hint, mHintSize, mHintColor, mHintBgColor);
    }

    public void setHintSize(int hintSize) {

        setConfig(mHint, hintSize, mHintColor, mHintBgColor);
    }

    public void setConfig(String hint, int hintSize, int hintColor, int hintBgColor) {

        this.mHint = hint;
        this.mHintSize = hintSize;
        this.mHintColor = hintColor;
        this.mHintBgColor = hintBgColor;
        postInvalidate();
    }
}
