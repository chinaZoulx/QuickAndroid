package com.example.chriszou.quicksample.ui.cuttoanim

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.transition.TransitionSet
import android.view.View
import com.example.chriszou.quicksample.R
import com.example.chriszou.quicksample.ui.cuttoanim.transition.*
import kotlinx.android.synthetic.main.activity_cut_to_anim.*
import org.chris.quick.b.BaseActivity

/**
 * @Author ChrisZou
 * @Date 2018/6/6-17:07
 * @Email chrisSpringSmell@gmail.com
 */
class CutToAnimActivity : BaseActivity() {

    companion object {
        fun startAction(activity: Activity, title: String, view: View) {
            val intent = Intent(activity, CutToAnimActivity::class.java)
            intent.putExtra(TITLE, title)
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair(view, activity.getString(R.string.cut_to_anim_name))).toBundle())
        }
    }

    override fun onResultLayoutResId(): Int = R.layout.activity_cut_to_anim

    override fun onInit() {
//        window.enterTransition = Explode().setDuration(2000)
//        window.exitTransition = Explode().setDuration(2000)

//        window.enterTransition = Slide().setDuration(2000)
//        window.exitTransition = Slide().setDuration(2000)

//        window.enterTransition = CommentEnterTransition(this, null, fab).setDuration(200)
//        window.exitTransition = Fade().setDuration(200)
//
//        //ParallaxTransition
//        window.enterTransition = ().setDuration(2000)
//        window.exitTransition = Slide().setDuration(2000)
        window.sharedElementEnterTransition = buildShareElemEnterSet().setDuration(300)
        window.sharedElementReturnTransition = buildShareElemReturnSet().setDuration(300)
    }

    override fun onInitLayout() {

    }

    override fun onBindListener() {

    }

    override fun start() {

    }

    /**
     * 分享 元素 进入动画
     * @return
     */
    private fun buildShareElemEnterSet(): TransitionSet {
        val transitionSet = TransitionSet()

        val changePos = ChangePosition()
        changePos.duration = 300
        changePos.addTarget(R.id.container)
        transitionSet.addTransition(changePos)

        val revealTransition = ShareElemEnterRevealTransition(container)
        transitionSet.addTransition(revealTransition)
        revealTransition.addTarget(R.id.container)
        revealTransition.interpolator = FastOutSlowInInterpolator()
        revealTransition.duration = 300

        val changeColor = ChangeColor(Color.parseColor("#5000A2E9"), Color.WHITE)
        changeColor.addTarget(R.id.container)
        changeColor.duration = 350

        transitionSet.addTransition(changeColor)

        transitionSet.duration = 900

        return transitionSet
    }

    /**
     * 分享元素返回动画
     * @return
     */
    private fun buildShareElemReturnSet(): TransitionSet {
        val transitionSet = TransitionSet()

        val changePos = ShareElemReturnChangePosition()
        changePos.addTarget(R.id.container)
        transitionSet.addTransition(changePos)

        val changeColor = ChangeColor(Color.parseColor("#5000A2E9"), Color.WHITE)
        changeColor.addTarget(R.id.container)
        transitionSet.addTransition(changeColor)


        val revealTransition = ShareElemReturnRevealTransition(container)
        revealTransition.addTarget(R.id.container)
        transitionSet.addTransition(revealTransition)

        transitionSet.duration = 900

        return transitionSet
    }

}