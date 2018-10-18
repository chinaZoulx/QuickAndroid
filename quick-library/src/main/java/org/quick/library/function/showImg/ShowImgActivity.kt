package org.quick.library.function.showImg

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import com.kevin.crop.UCrop
import org.quick.library.R
import org.quick.library.b.BaseActivity
import org.quick.library.function.SelectorImgActivity
import org.quick.library.function.selectorimg.UCropActivity
import org.quick.library.function.selectorimg.photoandselectorshow.PhotoShowAndSelectorActivity
import java.io.File

class ShowImgActivity : BaseActivity() {

    private val totalList = arrayListOf<String>()
    private val selectorList = arrayListOf<String>()
    private var isCrop = false
    override fun onResultLayoutResId(): Int = R.layout.activity_show_img

    override fun onInit() {

    }

    override fun onInitLayout() {

    }

    override fun onBindListener() {

    }

    override fun start() {

    }

    fun confirm(view: View) {
        if (selectorList.size > 0) {
            if (isCrop) {
                val uri = Uri.fromFile(File(selectorList[0]))
                startCropActivity(uri)
            } else {
                setResult()
                finish()
            }
        }
    }

    fun cancel(view: View) {
        finish()
    }

    private fun setResult() {
        val intent = Intent()
        intent.putExtra(SelectorImgActivity.ALREADY_PATHS, selectorList)
        intent.putExtra("from", PhotoShowAndSelectorActivity::class.java.simpleName)
        super.setResult(Activity.RESULT_OK, intent)
    }

    private fun startCropActivity(uri: Uri) {
        val uCrop = UCrop.of(uri, SelectorImgActivity.mDestinationUri)
        val options = UCrop.Options()
        options.setCompressionFormat(Bitmap.CompressFormat.PNG)
        uCrop.withOptions(options)
        uCrop.withTargetActivity(UCropActivity::class.java)
        uCrop.start(this)
    }
}