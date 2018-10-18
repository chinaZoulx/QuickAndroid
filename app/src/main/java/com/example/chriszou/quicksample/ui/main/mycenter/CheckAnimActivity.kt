package com.example.chriszou.quicksample.ui.main.mycenter

import android.graphics.Color
import android.view.ViewGroup
import com.example.chriszou.quicksample.R
import kotlinx.android.synthetic.main.activity_diy_view.*
import org.quick.library.b.BaseActivity

class CheckAnimActivity : BaseActivity() {
    override fun onResultLayoutResId(): Int = R.layout.activity_diy_view

    override fun onInit() {

    }

    override fun onInitLayout() {


    }

    override fun onBindListener() {
        selectedTv0.setOnClickListener {
            checkAniView0.setCheck(true)
        }
        selectedTv1.setOnClickListener {
            checkAniView1.setCheck(true)
        }
        selectedTv2.setOnClickListener {
            checkAniView2.setCheck(true)
        }
        selectedTv3.setOnClickListener {
            checkAniView3.setCheck(true)
        }
        selectedTv4.setOnClickListener {
        }

        checkAniView0.setOnCheckedChangeListener {

        }
        checkAniView1.setOnCheckedChangeListener {

        }
        checkAniView2.setOnCheckedChangeListener {

        }
        checkAniView3.setOnCheckedChangeListener {

        }
    }

    override fun start() {

    }
}