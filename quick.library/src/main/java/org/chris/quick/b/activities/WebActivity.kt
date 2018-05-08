package org.chris.quick.b.activities

import android.content.Context
import android.content.Intent
import android.view.View

import org.chris.quick.R
import org.chris.quick.b.BaseActivity
import org.chris.quick.b.fragments.WebFragment

/**
 * @author Chris zou
 * @Date 2016/11/3
 * @modifyInfo1 Zuo-2016/11/3
 * @modifyContent
 */

class WebActivity : BaseActivity() {

    lateinit var webViewFragment: WebFragment

    override fun onResultLayoutResId(): Int {
        return R.layout.app_activity_web
    }

    override fun onInit() {
        webViewFragment = supportFragmentManager.findFragmentById(R.id.WebFragment) as WebFragment
    }

    override fun onInitLayout() {
        setBackValid(R.drawable.ic_close_white_24dp, View.OnClickListener { finish() })
    }

    override fun onBindListener() {

    }

    override fun start() {
        webViewFragment.start(intent.getStringExtra("url"))
    }


    override fun onBackPressed() {
        if (!webViewFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        fun startAction(context: Context, title: String, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(ThemeActivity.TITLE, title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}
