package org.quick.library.function.selectorimg.photoandselectorshow

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.quick.library.R
import org.quick.library.m.ImageManager
import org.quick.library.widgets.ImageViewScale
import org.quick.library.widgets.ProgressWheel
import java.io.File

/**
 * Created by work on 2017/6/30.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

class PhotoShowAndSelectorAdapter : PagerAdapter() {

    private var imgList: List<String>? = null
    private var onItemClickListener: OnItemClickListener? = null

    override fun getCount(): Int {
        return if (imgList != null) imgList!!.size else 0
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val filePath = imgList!![position]
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_photo_show, container, false)
        val loadingAnimView = view.findViewById<ProgressWheel>(R.id.loadingAnimView)
        val imgScaleView = view.findViewById<ImageViewScale>(R.id.imageScaleView)
        imgScaleView.setOnViewTapListener { view, x, y ->
            onItemClickListener?.onItemClick(view, position)
        }
        if (filePath.contains("http")) {
            loadingAnimView.visibility = View.VISIBLE
            ImageManager.loadImage(container.context, filePath, imgScaleView)
        } else {
            loadingAnimView.visibility = View.GONE
            imgScaleView.visibility = View.VISIBLE
            ImageManager.loadImage(container.context, File(filePath), imgScaleView)
        }

        container.addView(view)
        return view
    }

    fun setImgList(imgList: List<String>) {
        this.imgList = imgList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
