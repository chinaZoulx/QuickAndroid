package org.quick.component.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

/**
 * @describe
 * @author ChrisZou
 * @date 2018/6/8-15:44
 * @email chrisSpringSmell@gmail.com
 */
object AnimUtils {
    /**
     *  渐入
     */
    fun fadeIn(onAnimListener: (() -> Unit)?, duration: Long, vararg views: View) {
        var objectAnimator: ObjectAnimator? = null
        for (view in views) {
            if (view.visibility != View.VISIBLE)
                view.visibility = View.VISIBLE
            objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(duration)
            objectAnimator!!.start()
        }
        if (onAnimListener != null && objectAnimator != null)
            objectAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    onAnimListener.invoke()
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
    }

    /**
     *  渐出
     */
    fun fadeOut(onAnimListener: (() -> Unit)?, duration: Long, vararg views: View) {
        var objectAnimator: ObjectAnimator? = null
        for (view in views) {
            objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(duration)
            objectAnimator!!.start()
        }
        if (onAnimListener != null && objectAnimator != null)
            objectAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    onAnimListener.invoke()
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
    }

    /**
     *  Y轴平移
     */
    fun translationY(onAnimListener: (() -> Unit)?, duration: Long, startOffset: Float, endOffset: Float, view: View) {
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", startOffset, endOffset).setDuration(duration)
        if (onAnimListener != null)
            objectAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    onAnimListener.invoke()
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
        objectAnimator.start()
    }

    /**
     *  Y轴平移
     */
    fun translationX(onAnimListener: (() -> Unit)?, duration: Long, startOffset: Float, endOffset: Float, view: View) {
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", startOffset, endOffset).setDuration(duration)

        if (onAnimListener != null)
            objectAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    onAnimListener.invoke()
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
        objectAnimator.start()
    }
}