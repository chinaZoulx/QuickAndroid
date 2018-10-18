/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package org.quick.library.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import org.quick.library.R;
import org.quick.component.utils.CommonUtils;
import org.quick.component.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 滑动显示字母
 */
public class SideBarView extends View {

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    /**
     * 右边滑动字母
     */
    private List<String> b = new ArrayList<>();
    private int choose = -1;
    private Paint sideTextPaint;
    private int sideTextSize = 20;
    private int sideTextColorNormal;
    private int sideTextColorFocus;
    private int sideTextBackGroundRes;
    private int sideTextSpac;

    private TextView mTextDialog;
    private int widthSpec;

    public SideBarView(Context context) {
        super(context);
        init();
    }

    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        sideTextPaint = new Paint();
        sideTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        sideTextPaint.setAntiAlias(true);
        sideTextPaint.setTextSize(sideTextSize);
        sideTextSpac = (int) FormatUtils.INSTANCE.formatDip2Px(10);

        sideTextSize = (int) FormatUtils.INSTANCE.formatDip2Px(10);
        sideTextColorNormal = Color.GRAY;
        sideTextColorFocus = Color.WHITE;
        sideTextBackGroundRes = R.drawable.shape_sidebar_background;
        for (char i = 'A'; i <= 'Z'; i++) {
            b.add(i + "");
        }
        b.add("#");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!(b.size() > 0)) {
            setMeasuredDimension(widthMeasureSpec, 0);
            return;
        }
        widthSpec = widthMeasureSpec;
        int height = b.size() * (CommonUtils.INSTANCE.getFontHeight(sideTextPaint) + sideTextSpac) + sideTextSpac * 2;
        setMeasuredDimension(widthMeasureSpec, height);
    }

    public void setLayout(int widthMeasureSpec) {
        if (!(b.size() > 0)) {
            setMeasuredDimension(widthMeasureSpec, 0);
            return;
        }
        widthSpec = widthMeasureSpec;
        int height = b.size() * (CommonUtils.INSTANCE.getFontHeight(sideTextPaint) + sideTextSpac) + sideTextSpac * 2;
        setMeasuredDimension(widthMeasureSpec, height);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(b.size() > 0)) {
            return;
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = (height - sideTextSpac) / b.size();
        for (int i = 0; i < b.size(); i++) {
            if (TextUtils.isEmpty(b.get(i))) {
                continue;
            }
            sideTextPaint.setColor(sideTextColorNormal);
            sideTextPaint.setTextSize(sideTextSize);
            if (i == choose) {
                sideTextPaint.setColor(sideTextColorFocus);
                sideTextPaint.setFakeBoldText(true);
            }
            float xPos = width / 2 - sideTextPaint.measureText(b.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight / 2 + sideTextSpac;
            canvas.drawText(b.get(i), xPos, yPos, sideTextPaint);
            sideTextPaint.reset();//
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.size());
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                sideTextColorNormal = Color.GRAY;
                break;

            default:
                setBackgroundResource(sideTextBackGroundRes);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        sideTextColorNormal = Color.BLACK;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 设置右边滑动字母集
     *
     * @param sideLetters 滑动字母集
     */
    public void setSideLetter(List<String> sideLetters) {
        this.b = sideLetters;
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        postInvalidate();
    }

    public void setSideTextSize(int size) {
        this.sideTextSize = size;
        postInvalidate();
    }

    public void setSideTextBg(int sideTextBackGroundRes) {
        this.sideTextBackGroundRes = sideTextBackGroundRes;
        postInvalidate();
    }

    public void setSideTextColor(int sideTextColorNormal, int sideTextColorFocus) {
        this.sideTextColorNormal = sideTextColorNormal;
        this.sideTextColorFocus = sideTextColorFocus;
        postInvalidate();
    }


    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {

        void onTouchingLetterChanged(String s);
    }

}