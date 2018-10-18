package org.quick.component.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import org.quick.component.R

/**
 * 选中动画
 * @author chris Zou
 * @date 2018-09-07
 * @from
 */
class CheckAnimView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var pathCir: Path = Path()
    private var pathTick: Path = Path()
    private var paintCir: Paint = Paint()
    private var paintTick: Paint = Paint()
    private var paintStar: Paint = Paint()
    private var paintBg: Paint = Paint()
    private var lengthCir: Float = 0.toFloat()
    private var lengthTick: Float = 0.toFloat()
    private var backgroundScale = 0f/*背景实心圆进度*/
    private var isDrawCirTemp = true
    private var isDrawTickTemp = true
    private var isDrawStarTemp = true
    private var isDrawBgTemp = true
    private var isDefaultSizeStar = false
    private var onCheckedChangeListener: ((isCheck: Boolean) -> Unit)? = null
    private val animatorStar: ValueAnimator
    private val starList = mutableListOf<Star>()
    private lateinit var sizeF: RectF/*绘制范围*/

    val animatorTick: ValueAnimator
    val animatorCir: ValueAnimator
    val animatorBg: ValueAnimator

    var focusDrawType = 0
    var normalDrawType = 0
    var sizeCir = 10f
    var sizeTick = sizeCir
    var sizeStar = -1f

    var durationBg: Long = 300
    var durationCir: Long = 300
    var durationTick: Long = 300
    var durationStar: Long = 1000

    var focusColorCir = Color.GRAY
    var focusColorTick = focusColorCir
    var focusColorBg = Color.TRANSPARENT
    var focusColorStar = Color.TRANSPARENT

    var normalColorCir = Color.GRAY
    var normalColorTick = normalColorCir
    var normalColorBg = Color.TRANSPARENT

    private var isCheck: Boolean = false


    enum class TYPE(var value: Int) {
        FOCUS_BG(0x01), FOCUS_CIR(0x02), FOCUS_TICK(0x04), FOCUS_STAR(0x08),
        NORMAL_BG(0x01), NORMAL_CIR(0x02), NORMAL_TICK(0x04)
    }

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.CheckAnimView)
            isCheck = ta.getBoolean(R.styleable.CheckAnimView_checked, false)
            sizeCir = ta.getDimension(R.styleable.CheckAnimView_sizeCir, 10f)
            sizeTick = ta.getDimension(R.styleable.CheckAnimView_sizeTick, 10f)
            sizeStar = ta.getDimension(R.styleable.CheckAnimView_sizeStar, -1f)

            durationBg = ta.getInt(R.styleable.CheckAnimView_durationBg, 300).toLong()
            durationCir = ta.getInt(R.styleable.CheckAnimView_durationCir, 300).toLong()
            durationTick = ta.getInt(R.styleable.CheckAnimView_durationTick, 400).toLong()
            durationStar = ta.getInt(R.styleable.CheckAnimView_durationStar, 1000).toLong()

            focusColorCir = ta.getColor(R.styleable.CheckAnimView_focusColorCir, Color.GRAY)
            focusColorTick = ta.getColor(R.styleable.CheckAnimView_focusColorTick, focusColorCir)
            focusColorBg = ta.getColor(R.styleable.CheckAnimView_focusColorBg, Color.TRANSPARENT)
            focusColorStar = ta.getColor(R.styleable.CheckAnimView_focusColorStar, focusColorTick)
            focusDrawType = ta.getInt(R.styleable.CheckAnimView_focusDrawType, TYPE.FOCUS_BG.value + TYPE.FOCUS_CIR.value + TYPE.FOCUS_TICK.value + TYPE.FOCUS_STAR.value)

            normalColorCir = ta.getColor(R.styleable.CheckAnimView_normalColorCir, Color.GRAY)
            normalColorTick = ta.getColor(R.styleable.CheckAnimView_normalColorTick, normalColorCir)
            normalColorBg = ta.getColor(R.styleable.CheckAnimView_normalColorBg, Color.TRANSPARENT)
            normalDrawType = ta.getInt(R.styleable.CheckAnimView_normalDrawType, TYPE.NORMAL_BG.value + TYPE.NORMAL_CIR.value + TYPE.NORMAL_TICK.value)
            ta.recycle()
        }
        isDefaultSizeStar = sizeStar == -1f
        paintCir.color = focusColorCir
        paintCir.strokeWidth = sizeCir
        paintCir.isAntiAlias = true
        paintCir.style = Paint.Style.STROKE

        paintTick.color = focusColorTick
        paintTick.strokeWidth = sizeTick
        paintTick.isAntiAlias = true
        paintTick.style = Paint.Style.STROKE

        paintBg.color = focusColorBg
        paintBg.isAntiAlias = true
        paintBg.style = Paint.Style.FILL_AND_STROKE

        paintStar.color = focusColorStar
        paintStar.isAntiAlias = true
        paintStar.style = Paint.Style.FILL_AND_STROKE

        /*星星*/
        animatorStar = ValueAnimator.ofFloat(0f, 1f, 0f)
        animatorStar.repeatCount = Animation.INFINITE
        animatorStar.duration = durationStar
        animatorStar.interpolator = LinearInterpolator()
        animatorStar.addUpdateListener {
            var flag = false
            starList.forEach { star ->
                if (flag) {
                    star.size = it.animatedValue.toString().toFloat() * sizeStar
                    star.alpha = (it.animatedValue.toString().toFloat() * 255).toInt()
                } else {
                    star.size = sizeStar - it.animatedValue.toString().toFloat() * sizeStar
                    star.alpha = (255 - it.animatedValue.toString().toFloat() * 255).toInt()
                }
                flag = !flag
                postInvalidate()
            }
        }
        /*打勾*/
        animatorTick = ObjectAnimator.ofFloat(this, "phaseTick", 0.0f, 1.0f)
        animatorTick.duration = durationTick
        animatorTick.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit

            override fun onAnimationEnd(animation: Animator?) {
                if (isCheck && focusDrawType and TYPE.FOCUS_STAR.value == TYPE.FOCUS_STAR.value) {
                    isDrawStarTemp = true
                    animatorStar.start()
                }
            }

            override fun onAnimationCancel(animation: Animator?) = Unit

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        /*画圈*/
        animatorCir = ObjectAnimator.ofFloat(this, "phaseCir", 0.0f, 1.0f)
        animatorCir.duration = durationCir
        animatorCir.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit

            override fun onAnimationEnd(animation: Animator?) {
                when {
                    focusDrawType and TYPE.FOCUS_TICK.value == TYPE.FOCUS_TICK.value -> {
                        isDrawTickTemp = true
                        animatorTick.start()
                    }
                    isCheck && focusDrawType and TYPE.FOCUS_STAR.value == TYPE.FOCUS_STAR.value -> {
                        isDrawStarTemp = true
                        animatorStar.start()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) = Unit

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        /*背景*/
        animatorBg = ObjectAnimator.ofFloat(this, "backGroundScale", 0.0f, 1.0f)
        animatorBg.duration = durationBg
        animatorBg.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit

            override fun onAnimationEnd(animation: Animator?) {
                when {
                    focusDrawType and TYPE.FOCUS_CIR.value == TYPE.FOCUS_CIR.value -> {
                        isDrawCirTemp = true
                        animatorCir.start()
                    }

                    focusDrawType and TYPE.FOCUS_TICK.value == TYPE.FOCUS_TICK.value -> {
                        isDrawTickTemp = true
                        animatorTick.start()
                    }
                    isCheck && focusDrawType and TYPE.FOCUS_STAR.value == TYPE.FOCUS_STAR.value -> {
                        isDrawStarTemp = true
                        animatorStar.start()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) = Unit

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        measureLocation()
    }

    private fun measureLocation() {
        if (isAnimIng()) animCancel()

        configStyle()

        val padding = sizeCir / 2
        val temp = Math.abs((height - width) / 2.0f)
        sizeF = RectF(if (height > width) 0f + padding else temp + padding, if (height > width) temp + padding else 0f + padding, if (height > width) width.toFloat() - padding else width - temp - padding, if (height > width) height - temp - padding else height.toFloat() - padding)

        val widthDistance = sizeF.right - sizeF.left
        val heightDistance = (sizeF.bottom - sizeF.top)

        pathCir = Path()
        pathCir.addOval(sizeF, Path.Direction.CCW)
        /*
        画圆
        pathCir.moveTo(rectF.centerX(), rectF.top)
        pathCir.quadTo(rectF.left, rectF.top, rectF.left, rectF.centerY())
        pathCir.quadTo(rectF.left, rectF.bottom, rectF.centerX(), rectF.bottom)
        pathCir.quadTo(rectF.right, rectF.bottom, rectF.right, rectF.centerY())
        pathCir.quadTo(rectF.right, rectF.top, rectF.centerX(), rectF.top)
        */
        lengthCir = PathMeasure(pathCir, false).length

        pathTick = Path()
        pathTick.moveTo(sizeF.left + widthDistance / 4f, sizeF.centerY())
        pathTick.lineTo(sizeF.centerX(), sizeF.bottom - heightDistance / 3f)
        pathTick.lineTo(sizeF.centerX() + widthDistance / 4f, sizeF.top + heightDistance / 4f)
        lengthTick = PathMeasure(pathTick, false).length

        if (isDefaultSizeStar) sizeStar = if (sizeF.centerX() * 0.125f > 30) 30f else sizeF.centerX() * 0.1f

        starList.clear()
        starList.add(Star(sizeF.centerX() + widthDistance / 4f + sizeStar * 0f, sizeF.top + heightDistance / 4f + sizeStar * 4))
        starList.add(Star(sizeF.centerX() + widthDistance / 4f - sizeStar * 2f, sizeF.top + heightDistance / 4f - sizeStar))
        starList.add(Star(sizeF.left + widthDistance / 4f + sizeStar * 1.125f, sizeF.centerY() - sizeStar * 2))
        starList.add(Star(sizeF.centerX() - sizeStar * 1f, sizeF.bottom - heightDistance / 3f + sizeStar * 1.5f))

        if (isCheck) {
            isDrawCirTemp = false
            isDrawTickTemp = false
            isDrawStarTemp = false
            isDrawBgTemp = false
            when {
                focusDrawType and TYPE.FOCUS_BG.value == TYPE.FOCUS_BG.value -> {
                    isDrawBgTemp = true
                    animatorBg.start()
                }
                focusDrawType and TYPE.FOCUS_CIR.value == TYPE.FOCUS_CIR.value -> {
                    isDrawCirTemp = true
                    animatorCir.start()
                }
                focusDrawType and TYPE.FOCUS_TICK.value == TYPE.FOCUS_TICK.value -> {
                    isDrawTickTemp = true
                    animatorTick.start()
                }
                isCheck && focusDrawType and TYPE.FOCUS_STAR.value == TYPE.FOCUS_STAR.value -> {
                    isDrawStarTemp = true
                    animatorStar.start()
                }
            }
        } else postInvalidate()
    }

    private fun configStyle() {
        if (isCheck) {
            paintTick.color = focusColorTick
            paintCir.color = focusColorCir
            paintBg.color = focusColorBg
        } else {
            paintTick.color = normalColorTick
            paintCir.color = normalColorCir
            paintBg.color = normalColorBg
            animatorStar.cancel()
        }
    }

    private fun setPhaseCir(phase: Float) {
        paintCir.pathEffect = DashPathEffect(floatArrayOf(lengthCir, lengthCir), lengthCir - phase * lengthCir)
        postInvalidate()
    }

    private fun setPhaseTick(phase: Float) {
        paintTick.pathEffect = DashPathEffect(floatArrayOf(lengthTick, lengthTick), lengthTick - phase * lengthTick)
        postInvalidate()
    }

    private fun setBackGroundScale(progress: Float) {
        backgroundScale = progress
        postInvalidate()
    }

    fun setOnCheckedChangeListener(onCheckedChangeListener: ((isCheck: Boolean) -> Unit)) {
        this.onCheckedChangeListener = onCheckedChangeListener
        setOnClickListener {
            isCheck = !isCheck
            animCancel()
            onCheckedChangeListener.invoke(isCheck)
            measureLocation()
        }
    }

    fun animCancel() {
        animatorStar.cancel()
        animatorBg.cancel()
        animatorCir.cancel()
        animatorTick.cancel()

        paintTick.pathEffect = DashPathEffect(floatArrayOf(lengthTick, lengthTick), 0f)
        paintCir.pathEffect = DashPathEffect(floatArrayOf(lengthCir, lengthCir), 0f)
    }

    fun isCheck() = this.isCheck

    fun setCheck(isCheck: Boolean) {
        animCancel()
        this.isCheck = isCheck
        measureLocation()
    }

    @SuppressLint("DrawAllocation")
    public override fun onDraw(c: Canvas) {
        super.onDraw(c)
        if (isCheck) {
            if (focusDrawType and TYPE.FOCUS_BG.value == TYPE.FOCUS_BG.value && isDrawBgTemp && focusColorBg != Color.TRANSPARENT) c.drawCircle(sizeF.centerX(), sizeF.centerY(), (sizeF.right - sizeF.left) / 2f * backgroundScale, paintBg)
            if (focusDrawType and TYPE.FOCUS_CIR.value == TYPE.FOCUS_CIR.value && isDrawCirTemp && focusColorCir != focusColorBg) c.drawPath(pathCir, paintCir)
            if (focusDrawType and TYPE.FOCUS_TICK.value == TYPE.FOCUS_TICK.value && isDrawTickTemp && focusColorTick != focusColorBg) c.drawPath(pathTick, paintTick)
        } else {
            if (normalDrawType and TYPE.NORMAL_BG.value == TYPE.NORMAL_BG.value && normalColorBg != Color.TRANSPARENT) c.drawCircle(sizeF.centerX(), sizeF.centerY(), (sizeF.right - sizeF.left) / 2f * backgroundScale, paintBg)
            if (normalDrawType and TYPE.NORMAL_CIR.value == TYPE.NORMAL_CIR.value && normalColorCir != Color.TRANSPARENT) c.drawPath(pathCir, paintCir)
            if (normalDrawType and TYPE.NORMAL_TICK.value == TYPE.NORMAL_TICK.value && normalColorTick != Color.TRANSPARENT) c.drawPath(pathTick, paintTick)
        }
        pathCir.close()
        if (focusDrawType and TYPE.FOCUS_STAR.value == TYPE.FOCUS_STAR.value && focusColorStar != Color.TRANSPARENT && focusColorStar != focusColorBg && isDrawStarTemp) starList.forEach { drawStar(c, it) }
    }

    private fun drawStar(c: Canvas, star: Star) {
        val path = Path()
        val starX = star.centerX
        val starY = star.centerY - star.size
        path.moveTo(starX, starY)
        path.quadTo(starX - star.size * 0.25f, starY + star.size * 0.75f, starX - star.size, starY + star.size)
        path.quadTo(starX - star.size * 0.25f, starY + star.size * 1.25f, starX, starY + star.size * 2f)
        path.quadTo(starX + star.size * 0.25f, starY + star.size * 1.25f, starX + star.size, starY + star.size)
        path.quadTo(starX + star.size * 0.25f, starY + star.size * 0.75f, starX, starY)
        paintStar.alpha = star.alpha
        c.drawPath(path, paintStar)
        path.reset()
        path.close()
    }

    fun isAnimIng() = animatorBg.isStarted || animatorCir.isStarted || animatorTick.isStarted

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isCheck) measureLocation()
    }

    override fun onDetachedFromWindow() {
        animatorStar.cancel()
        super.onDetachedFromWindow()
    }

    class Star(var centerX: Float, var centerY: Float, var size: Float = 0f, var alpha: Int = 0)
}