package org.quick.component.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View

import org.quick.component.R


/**
 * Created by work on 2017/8/8.
 * 横向进度条
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

class ProgressBarHorizontal @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    var maxProgress = 100f
    /*背景*/
    var bgBitmap: Bitmap? = null
    var bgColorStart: Int = 0
    var bgColorEnd: Int = 0
    private var bgColors: IntArray = intArrayOf()
    var bgRadius: Int = 0
    var bgLtRadius: Int = 0
    var bgLbRadius: Int = 0
    var bgRbRadius: Int = 0
    var bgRtRadius: Int = 0
    var bgPaint: Paint
    /*end*/

    /*图片*/
    var coverBitmap: Bitmap? = null
    var coverColorStart: Int = 0
    var coverColorEnd: Int = 0
    private var coverColors: IntArray = intArrayOf()
    var coverRadius: Int = 0
    var coverLtRadius: Int = 0
    var coverLbRadius: Int = 0
    var coverRtRadius: Int = 0
    var coverRbRadius: Int = 0
    var coverPaint: Paint
    /*end*/

    var compareMinSize: Int = 0
        //高与宽较小那个
        get() = if (width > height) width else height

    var scaleType: Int = 0
    private var progress: Float = 0f

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarHorizontal)
            bgColorStart = ta.getColor(R.styleable.ProgressBarHorizontal_bgColorStart, Color.DKGRAY)
            bgColorEnd = ta.getColor(R.styleable.ProgressBarHorizontal_bgColorEnd, bgColorStart)
            bgColors = intArrayOf(bgColorStart, bgColorEnd)
            bgRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_bgRadius, 0)
            bgLtRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_bgLtRadius, 0)
            bgLbRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_bgLbRadius, 0)
            bgRbRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_bgRbRadius, 0)
            bgRtRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_bgRtRadius, 0)
            val coverDrawable = ta.getDrawable(R.styleable.ProgressBarHorizontal_bgSrc)
            bgBitmap = if (coverDrawable != null) (coverDrawable as BitmapDrawable).bitmap else null

            coverColorStart = ta.getColor(R.styleable.ProgressBarHorizontal_coverColorStart, Color.GRAY)
            coverColorEnd = ta.getColor(R.styleable.ProgressBarHorizontal_coverColorEnd, coverColorStart)
            coverColors = intArrayOf(coverColorStart, coverColorEnd)
            coverRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_coverRadius, 0)
            coverLtRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_coverLtRadius, 0)
            coverLbRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_coverLbRadius, 0)
            coverRbRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_coverRbRadius, 0)
            coverRtRadius = ta.getDimensionPixelSize(R.styleable.ProgressBarHorizontal_coverRtRadius, 0)
            val bottomDrawable = ta.getDrawable(R.styleable.ProgressBarHorizontal_coverSrc)
            coverBitmap = if (bottomDrawable != null) (bottomDrawable as BitmapDrawable).bitmap else null

            progress = ta.getFloat(R.styleable.ProgressBarHorizontal_progress, 0f)
            scaleType = ta.getInteger(R.styleable.ProgressBarHorizontal_scaleType, 0)
            ta.recycle()
        }

        bgPaint = Paint()
        bgPaint.color = bgColorStart
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true

        coverPaint = Paint()
        coverPaint.color = coverColorStart
        coverPaint.style = Paint.Style.FILL
        coverPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (scaleType == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            if (coverBitmap == null) {
                throw NullPointerException("wrap_content must set image res")
            }
            setMeasuredDimension(coverBitmap!!.width, coverBitmap!!.height)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //bg
        bgPaint.shader = LinearGradient(0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), bgColors, null, Shader.TileMode.MIRROR)
        when {
            bgBitmap != null -> canvas.drawBitmap(bgBitmap!!, null, RectF(0f, 0f, width.toFloat(), height.toFloat()), null)
            bgRadius != 0 -> {
                val bgRec = RectF(0f, 0f, width.toFloat(), height.toFloat())
                canvas.drawRoundRect(bgRec, bgRadius.toFloat(), bgRadius.toFloat(), bgPaint)
            }
            else -> drawPath(canvas, bgLtRadius, bgLbRadius, bgRbRadius, bgRtRadius, width.toFloat(), bgPaint)
        }
        //cover
        coverPaint.shader = LinearGradient(0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), coverColors, null, Shader.TileMode.MIRROR)
        val progressWidth = width - width * ((maxProgress - progress) / maxProgress)
        when {
            coverBitmap != null -> {
                val dst = RectF(0f, 0f, width.toFloat(), height.toFloat())
                val src = Rect(0, 0, width, height)
                canvas.drawBitmap(coverBitmap!!, src, dst, null)
            }
            coverRadius != 0 -> {
                val coverRec = RectF(0f, 0f, progressWidth, height.toFloat())
                canvas.drawRoundRect(coverRec, coverRadius.toFloat(), coverRadius.toFloat(), coverPaint)
            }
            else -> drawPath(canvas, coverLtRadius, coverLbRadius, coverRbRadius, coverRtRadius, progressWidth, coverPaint)
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
    private fun drawPath(canvas: Canvas, lt: Int, lb: Int, rb: Int, rt: Int, w: Float, paint: Paint) {
        //左上角，逆时针开始
        val path = Path()
        //左上
        path.moveTo(lt.toFloat(), 0f)
        path.quadTo(0f, 0f, 0f, lt.toFloat())
        //左下
        path.lineTo(0f, (height - lb).toFloat())
        path.quadTo(0f, height.toFloat(), lb.toFloat(), height.toFloat())
        //右下
        path.lineTo(w - rb, height.toFloat())
        path.quadTo(w, height.toFloat(), w, (height - rb).toFloat())
        //右上
        path.lineTo(w, rt.toFloat())
        path.quadTo(w, 0f, w - rt, 0f)
        //回到左上角
        path.lineTo(lt.toFloat(), 0f)
        canvas.drawPath(path, paint)
    }

    /**
     * @param progress 0-100
     */
    fun setProgress(progress: Float) {
        var progress = progress
        if (progress > maxProgress) {
            progress = maxProgress
        }
        this.progress = progress
        postInvalidate()
    }

    fun setBgColors(colors: IntArray) {
        this.bgColors = colors
    }

    fun setCoverColors(colors: IntArray) {
        this.coverColors = colors
    }
}
