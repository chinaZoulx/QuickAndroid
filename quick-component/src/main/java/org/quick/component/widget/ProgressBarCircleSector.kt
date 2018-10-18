package org.quick.component.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

import org.quick.component.R

/**
 * Created by work on 2017/8/8.
 * 扇形进度条不带百分比
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

class ProgressBarCircleSector @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    /*轮廓：最外圈*/
    var outlineColor: Int = 0
    var outlineWidth: Int = 0
    var outlinePaint: Paint
    /*end*/

    /*内外圈*/
    var outlineAndInteriorSpaceWidth: Int = 0//内外圈之间间距
    var outlineAndInteriorSpaceColor: Int = 0//颜色
    var outlineAndInteriorSpacePaint: Paint
    /*end*/

    /*内圈*/
    var interiorColorCover: Int = 0
    var interiorColorBg: Int = 0
    var interiorCoverPaint: Paint
    var interiorBgPaint: Paint
    var interiorBounds: RectF = RectF()
    /*end*/

    var compareMinSize: Int = 0//高与宽较小那个
        get() = if (width > height) height else width

    var radius: Float = 0.toFloat()

    var isLoop: Boolean = false//超出360的，是否循环显示

    var middleLocation = PointF()

    private var progress: Float = 0.toFloat()

    val comparePaddingSize: Int
        get() {
            val a: Int = if (paddingLeft > paddingRight)
                paddingLeft
            else
                paddingRight
            val b: Int = if (paddingTop > paddingBottom)
                paddingTop
            else
                paddingBottom

            return if (a > b) a else b
        }

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarCircleSector)
            interiorColorCover = ta.getColor(R.styleable.ProgressBarCircleSector_cir3ColorCover, Color.BLACK)
            interiorColorBg = ta.getColor(R.styleable.ProgressBarCircleSector_cir3ColorBg, Color.TRANSPARENT)

            outlineColor = ta.getColor(R.styleable.ProgressBarCircleSector_cir1Color, Color.BLACK)
            outlineWidth = ta.getDimensionPixelSize(R.styleable.ProgressBarCircleSector_cir1Width, 0)
            outlineAndInteriorSpaceWidth = ta.getDimensionPixelSize(R.styleable.ProgressBarCircleSector_cir2Width, 0)
            outlineAndInteriorSpaceColor = ta.getColor(R.styleable.ProgressBarCircleSector_cir2Color, Color.TRANSPARENT)
            isLoop = ta.getBoolean(R.styleable.ProgressBarCircleSector_progressIsLoop, false)
            progress = ta.getFloat(R.styleable.ProgressBarCircleSector_progress, 0f)
            ta.recycle()
        }
        interiorCoverPaint = Paint()
        interiorCoverPaint.isAntiAlias = true
        interiorCoverPaint.color = interiorColorCover
        interiorCoverPaint.style = Paint.Style.FILL

        interiorBgPaint = Paint()
        interiorBgPaint.isAntiAlias = true
        interiorBgPaint.color = interiorColorBg
        interiorBgPaint.style = Paint.Style.FILL

        outlinePaint = Paint()
        outlinePaint.isAntiAlias = true
        outlinePaint.style = Paint.Style.STROKE
        outlinePaint.color = outlineColor
        outlinePaint.strokeWidth = outlineWidth.toFloat()

        outlineAndInteriorSpacePaint = Paint()
        outlineAndInteriorSpacePaint.isAntiAlias = true
        outlineAndInteriorSpacePaint.style = Paint.Style.STROKE
        outlineAndInteriorSpacePaint.color = outlineAndInteriorSpaceColor
        outlineAndInteriorSpacePaint.strokeWidth = outlineAndInteriorSpaceWidth.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        middleLocation.x = w / 2.0f
        middleLocation.y = h / 2.0f

        compareMinSize = if (w > h) h else w
        radius = compareMinSize / 2.0f
        //        int left = (int) (middleLocation.x - radius + getComparePaddingSize() + outlineAndInteriorSpaceWidth + outlineWidth);
        //        int top = height - compareMinSize + getComparePaddingSize() + outlineAndInteriorSpaceWidth + outlineWidth;
        //        int right = (int) (middleLocation.x + radius - getComparePaddingSize() - outlineAndInteriorSpaceWidth - outlineWidth);
        //        int bottom = compareMinSize - getComparePaddingSize() - outlineAndInteriorSpaceWidth - outlineWidth;

        val left = middleLocation.x - radius + comparePaddingSize.toFloat() + outlineAndInteriorSpaceWidth.toFloat() + outlineWidth.toFloat()
        val top = middleLocation.y - radius + comparePaddingSize.toFloat() + outlineAndInteriorSpaceWidth.toFloat() + outlineWidth.toFloat()
        val right = middleLocation.x + radius - comparePaddingSize.toFloat() - outlineAndInteriorSpaceWidth.toFloat() - outlineWidth.toFloat()
        val bottom = middleLocation.y + radius - comparePaddingSize.toFloat() - outlineAndInteriorSpaceWidth.toFloat() - outlineWidth.toFloat()

        interiorBounds = RectF(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (outlineWidth > 0) {
            /*画外圈*/
            canvas.drawCircle(middleLocation.x, middleLocation.y, radius - comparePaddingSize.toFloat() - outlineWidth / 2.0f, outlinePaint)
        }

        if (outlineAndInteriorSpaceWidth > 0 && outlineAndInteriorSpaceColor != Color.TRANSPARENT) {
            /*画间隔圈*/
            canvas.drawCircle(middleLocation.x, middleLocation.y, radius - comparePaddingSize.toFloat() - outlineWidth.toFloat() - outlineAndInteriorSpaceWidth / 2.0f, outlineAndInteriorSpacePaint)
        }

        if (interiorColorBg != Color.TRANSPARENT) {
            canvas.drawOval(interiorBounds, interiorBgPaint)
        }

        /*画内圈-弧形进度*/
        val temp = Math.abs(progress / 360).toInt()
        if (isLoop && temp > 0 && progress % 360 != 0f) {
            if (progress > 0)
                progress -= (temp * 360).toFloat()
            else
                progress += (temp * 360).toFloat()
        }
        canvas.drawArc(interiorBounds, -90f, progress, true, interiorCoverPaint)
    }

    /**
     * @param progress
     */
    fun setProgress(progress: Float) {
        this.progress = progress
        postInvalidate()
    }

    fun getProgress(): Float {
        return progress
    }
}
