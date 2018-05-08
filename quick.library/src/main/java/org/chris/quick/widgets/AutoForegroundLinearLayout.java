package org.chris.quick.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.internal.ForegroundLinearLayout;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by work on 2017/9/28.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */
@SuppressLint("RestrictedApi")
public class AutoForegroundLinearLayout extends ForegroundLinearLayout {

    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoForegroundLinearLayout(Context context) {
        super(context);
    }

    public AutoForegroundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoForegroundLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public AutoLinearCompatLayout.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new AutoLinearCompatLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
