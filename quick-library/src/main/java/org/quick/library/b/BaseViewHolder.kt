package org.quick.library.b

import android.content.Context
import android.view.View
import android.widget.ImageView
import org.quick.component.QuickViewHolder
import org.quick.component.img.ImageManager

class BaseViewHolder(itemView: View) : QuickViewHolder(itemView) {

    override fun bindImg(context: Context, url: String, imageView: ImageView?): QuickViewHolder {
        //在这里绑定普通图片
        if (imageView != null)
            ImageManager.loadImage(context, url, imageView)
        return this
    }

    override fun bindImgRoundRect(context: Context, url: String, radius: Float, imageView: ImageView?): QuickViewHolder {
        //在这里绑定圆角图片
        if (imageView != null)
            ImageManager.loadRoundImage(context, url, radius.toInt(), imageView)
        return this
    }

    override fun bindImgCircle(context: Context, url: String, imageView: ImageView?): BaseViewHolder {
        //在这里绑定圆形图片
        if (imageView != null)
            ImageManager.loadCircleImage(context, url, imageView)
        return this
    }
}