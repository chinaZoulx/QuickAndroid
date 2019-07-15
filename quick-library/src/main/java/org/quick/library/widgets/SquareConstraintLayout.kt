package org.quick.library.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 正方形
 */
class SquareConstraintLayout : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var tempWidth = widthMeasureSpec
        var tempHeight = heightMeasureSpec
        // For simple implementation, or internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view. We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        setMeasuredDimension(View.getDefaultSize(0, tempWidth), View.getDefaultSize(0, tempHeight))

        // Children are just made to fill our space.
        val childWidthSize = measuredWidth
        //高度和宽度一样
        tempWidth = View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY)
        tempHeight = tempWidth
        super.onMeasure(tempWidth, tempHeight)
    }

}