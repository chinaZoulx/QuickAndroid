package org.quick.component

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.*
import org.quick.component.callback.OnClickListener2
import org.quick.component.utils.ImageUtils

open class QuickViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mViews: SparseArray<View> by lazy { return@lazy SparseArray<View>() }

    fun <T : View> getView(@IdRes id: Int): T? {

        var view: View? = mViews.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            mViews.put(id, view)
        }
        return view as T?
    }

    fun setText(@IdRes id: Int, content: CharSequence?, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        val textView = getView<TextView>(id)
        textView?.text = content
        if (onClickListener != null) {
            textView?.setOnClickListener (object : OnClickListener2() {
                override fun onClick2(view: View) {
                    onClickListener.invoke(view, this@QuickViewHolder)
                }
            })
        }
        return this
    }

    /**
     * 原样本地图片
     *
     * @param id
     * @param iconId
     * @return
     */
    fun setImg(@IdRes id: Int, @DrawableRes iconId: Int, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        return setImg(id, false, 0f, "", iconId, onClickListener)
    }

    /**
     * 原样网络图片
     *
     * @param id
     * @param url
     * @return
     */
    fun setImg(@IdRes id: Int, url: CharSequence, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        return setImg(id, false, 0f, url, 0, onClickListener)
    }


    /**
     * 圆角-本地图片
     *
     * @param id
     * @param radius
     * @param iconId
     * @return
     */
    fun setImgRoundRect(@IdRes id: Int, radius: Float, @DrawableRes iconId: Int, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        return setImg(id, false, radius, "", iconId, onClickListener)
    }

    /**
     * 圆角-网络图片
     *
     * @param id
     * @param radius
     * @param url
     * @return
     */
    fun setImgRoundRect(@IdRes id: Int, radius: Float, url: CharSequence, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        return setImg(id, false, radius, url, 0, onClickListener)
    }


    /**
     * 圆形-网络图片
     *
     * @param id
     * @param url
     * @param onClickListener
     * @return
     */
    fun setImgCircle(@IdRes id: Int, url: CharSequence, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        return setImg(id, true, 0f, url, 0, onClickListener)
    }

    /**
     * 圆形-本地图片
     *
     */
    fun setImgCircle(@IdRes id: Int, @DrawableRes imgRes: Int, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)? = null): QuickViewHolder {
        return setImg(id, true, 0f, "", imgRes, onClickListener)
    }

    /**
     * @param id              图片ID
     * @param isCir           是否正圆
     * @param radius          圆角
     * @param url             网络链接
     * @param imgRes          图片资源ID
     * @param onClickListener 监听
     * @return
     */
    @Synchronized
    private fun setImg(id: Int, isCir: Boolean, radius: Float, url: CharSequence, @DrawableRes imgRes: Int, onClickListener: ((view: View, viewHolder: QuickViewHolder) -> Unit)?): QuickViewHolder {

        val img = getView<ImageView>(id)
        if (TextUtils.isEmpty(url)) {
            when {
                isCir -> img?.setImageBitmap(ImageUtils.cropCircle(ImageUtils.decodeSampledBitmapFromResource(itemView.context.resources, imgRes, img.measuredWidth, img.measuredHeight)))
                radius > 0 -> img?.setImageBitmap(ImageUtils.cropRoundRect(ImageUtils.decodeSampledBitmapFromResource(itemView.context.resources, imgRes, img.measuredWidth, img.measuredHeight), radius))
                else -> img?.setImageResource(imgRes)
            }
        } else {
            when {
                isCir -> bindImgCircle(itemView.context, url.toString(), img)
                radius > 0 -> bindImgRoundRect(itemView.context, url.toString(), radius, img)
                else -> bindImg(itemView.context, url.toString(), img)
            }
        }
        if (onClickListener != null) img?.setOnClickListener (object : OnClickListener2() {
            override fun onClick2(view: View) {
                onClickListener.invoke(view, this@QuickViewHolder)
            }
        })
        return this
    }

    open fun bindImgCircle(context: Context, url: String, imageView: ImageView?): QuickViewHolder {
        return this
    }

    open fun bindImg(context: Context, url: String, imageView: ImageView?): QuickViewHolder {
        return this
    }

    open fun bindImgRoundRect(context: Context, url: String, radius: Float, imageView: ImageView?): QuickViewHolder {
        return this
    }

    fun setOnClickListener(onClickListener: (view: View, viewHolder: QuickViewHolder) -> Unit, @IdRes vararg ids: Int): QuickViewHolder {
        for (id in ids) setOnClickListener(onClickListener, id)
        return this
    }

    fun setOnClickListener(onClickListener: (view: View, viewHolder: QuickViewHolder) -> Unit, @IdRes id: Int): QuickViewHolder {
        getView<View>(id)?.setOnClickListener(object : OnClickListener2() {
            override fun onClick2(view: View) {
                onClickListener.invoke(view, this@QuickViewHolder)
            }
        })
        return this
    }

    fun setProgress(@IdRes id: Int, value: Int): QuickViewHolder {
        getView<ProgressBar>(id)?.progress = value
        return this
    }

    fun setCheck(@IdRes id: Int, isChecked: Boolean): QuickViewHolder {
        getView<CompoundButton>(id)?.isChecked = isChecked
        return this
    }

    fun setBackgroundResource(@IdRes id: Int, bgResId: Int): QuickViewHolder {
        getView<View>(id)?.setBackgroundResource(bgResId)
        return this
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setBackground(@IdRes id: Int, background: Drawable): QuickViewHolder {
        getView<View>(id)?.background = background
        return this
    }

    fun setBackgroundColor(@IdRes id: Int, background: Int): QuickViewHolder {
        getView<View>(id)?.setBackgroundColor(background)
        return this
    }

    fun setVisibility(visibility: Int, @NonNull @IdRes vararg resIds: Int): QuickViewHolder {
        for (resId in resIds) getView<View>(resId)?.visibility = visibility
        return this
    }

    fun getTextView(@IdRes id: Int): TextView? {
        return getView(id)
    }

    fun getButton(@IdRes id: Int): Button? {
        return getView(id)
    }

    fun getImageView(@IdRes id: Int): ImageView? {
        return getView(id)
    }

    fun getLinearLayout(@IdRes id: Int): LinearLayout? {
        return getView(id)
    }

    fun getRelativeLayout(@IdRes id: Int): RelativeLayout? {
        return getView(id)
    }

    fun getFramLayout(@IdRes id: Int): FrameLayout? {
        return getView(id)
    }

    fun getCheckBox(@IdRes id: Int): CheckBox? {
        return getView(id)
    }

    fun getEditText(@IdRes id: Int): EditText? {
        return getView(id)
    }
}
