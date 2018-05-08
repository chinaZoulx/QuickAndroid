package org.chris.quick.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.chris.quick.tools.ViewUtils;

/**
 * Created by work on 2017/8/4.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class TextViewQuick extends android.support.v7.widget.AppCompatTextView {

    private static final int[] LL = new int[]
            { //
                    android.R.attr.textSize,
                    android.R.attr.padding,//
                    android.R.attr.paddingLeft,//
                    android.R.attr.paddingTop,//
                    android.R.attr.paddingRight,//
                    android.R.attr.paddingBottom,//
                    android.R.attr.layout_width,//
                    android.R.attr.layout_height,//
                    android.R.attr.layout_margin,//
                    android.R.attr.layout_marginLeft,//
                    android.R.attr.layout_marginTop,//
                    android.R.attr.layout_marginRight,//
                    android.R.attr.layout_marginBottom,//
                    android.R.attr.maxWidth,//
                    android.R.attr.maxHeight,//
                    android.R.attr.minWidth,//
                    android.R.attr.minHeight,//16843072
                    android.R.attr.background,

            };

    public TextViewQuick(Context context) {
        this(context, null);
    }

    public TextViewQuick(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewQuick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customParams(attrs);
    }

    public void customParams(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, LL);
        if (!ViewUtils.viewAttrHasValue(typedArray,android.R.attr.background)) {
            TypedValue typedValue = new TypedValue();
            if(getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)){
                setBackgroundResource(typedValue.resourceId);
            }
        }
    }
}
