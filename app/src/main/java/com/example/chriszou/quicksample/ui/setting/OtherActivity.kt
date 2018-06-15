package com.example.chriszou.quicksample.ui.setting

import android.app.Activity
import com.example.chriszou.quicksample.R
import org.chris.quick.b.BaseActivity

/**
 * @describe
 * @author ChrisZou
 * @date 2018/6/15-11:38
 * @email chrisSpringSmell@gmail.com
 */
class OtherActivity : BaseActivity() {
    override fun onInit() {

    }

    override fun onInitLayout() {

    }

    override fun onBindListener() {

    }

    override fun start() {
        setResult(Activity.RESULT_OK)
    }

    override fun onResultLayoutResId(): Int = R.layout.activity_other
}