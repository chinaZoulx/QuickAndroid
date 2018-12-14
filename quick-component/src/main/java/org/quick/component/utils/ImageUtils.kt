package org.quick.component.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.*
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.ColorInt
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.util.LruCache
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.view.PixelCopy
import android.view.View
import org.quick.component.QuickAsync
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


object ImageUtils {


    fun decodeSampledBitmapFromResource(res: Resources, resId: Int): Bitmap {
        return decodeSampledBitmapFromResource(res, resId, 0, 0)
    }

    /**
     * 获取指定大小的位图
     *
     * @param res
     * @param resId
     * @param reqWidth  希望取得的宽度
     * @param reqHeight 希望取得的高度
     * @return 按指定大小压缩之后的图片
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html#read-bitmap
     */
    fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {

        if (reqWidth == 0 || reqHeight == 0) {
            return BitmapFactory.decodeResource(res, resId)
                    ?: drawableToBitmap(
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                res.getDrawable(resId, null)
                            else res.getDrawable(resId)
                    )
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    /**
     * 获取指定大小的位图
     *
     * @param filePath  文件路径
     * @param reqWidth  希望取得的宽度
     * @param reqHeight 希望取得的高度
     * @return 按指定大小压缩之后的图片
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html#read-bitmap
     */
    fun decodeSampledBitmapFromResource(filePath: String, reqWidth: Int, reqHeight: Int): Bitmap {

        if (reqWidth == 0 || reqHeight == 0) return BitmapFactory.decodeFile(filePath)

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * 计算与指定位图的大小比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return 缩放的比例
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap.html #read-bitmap
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            // 取高宽中更大一边，进行同比例缩放，这样才不会变形。
            inSampleSize = if (width > height) Math.round(height.toFloat() / reqHeight.toFloat()) else Math.round(width.toFloat() / reqWidth.toFloat())
        }
        return inSampleSize
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        // 取 drawable 的长宽
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight

        // 取 drawable 的颜色格式
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        // 建立对应 bitmap
        val bitmap = Bitmap.createBitmap(w, h, config)
        // 建立对应 bitmap 的画布
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        // 把 drawable 内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 图片链接是否正确
     * The image link correct or not
     *
     * @param url
     * @return
     */
    fun isPicUrlFormatRight(url: String): Boolean {
        var url = url
        url = url.toLowerCase()
        val postfixs = arrayOf("png", "jpg", "jpg", "gif")
        if (url.startsWith("http://") || url.startsWith("https://"))
            for (postfix in postfixs)
                if (url.endsWith(postfix))
                    return true
        return false
    }

    /**
     * 屏幕截图
     */
    fun screenshot(activity: Activity?): Bitmap? {
        if (activity == null) return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val bitmap = Bitmap.createBitmap(activity.window.decorView.measuredWidth, activity.window.decorView.measuredHeight, Bitmap.Config.ARGB_8888)
            PixelCopy.request(activity.window, bitmap, {

            }, QuickAsync.mainHandler)
            bitmap
        } else {
            activity.window.decorView.isDrawingCacheEnabled = true
            activity.window.decorView.buildDrawingCache()
            val newBitmap = Bitmap.createBitmap(activity.window.decorView.drawingCache)
            activity.window.decorView.drawingCache.recycle()
            activity.window.decorView.isDrawingCacheEnabled = false
            newBitmap
        }
    }

    /**
     * 屏幕截图
     */
    fun screenshot(view: View?): Bitmap? {
        if (view == null) return null
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            PixelCopy.request(view, bitmap, {
//
//            }, QuickAsync.mainHandler)
        } else view.draw(Canvas(bitmap))
        return bitmap
    }

    /**
     * @param radius 圆角
     */
    fun cropRoundRect(bitmap: Bitmap, radius: Float): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val bitmapCorp: Bitmap

        val paint = Paint()
        paint.isAntiAlias = true

        val left = 0
        val top = 0
        val right = bitmap.width
        val bottom = bitmap.height

        val src = Rect(left, top, right, bottom)/*控制显示哪部分*/
        val dst = Rect(left, top, right, bottom)/*从哪展示的位置*/
        return try {
            bitmapCorp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmapCorp)
            canvas.drawARGB(0, 0, 0, 0)
            canvas.drawRoundRect(RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat()), radius, radius, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, src, dst, paint)
            bitmapCorp
        } catch (O_O: OutOfMemoryError) {
            O_O.printStackTrace()
            bitmap
        }
    }

    /**
     * 裁剪为正圆形
     */
    fun cropCircle(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height

        val bitmapCorp: Bitmap

        val paint = Paint()
        paint.isAntiAlias = true

        val left: Int
        val top: Int
        val right: Int
        val bottom: Int

        val leftDst: Int
        val topDst: Int
        val rightDst: Int
        val bottomDst: Int

        val radii: Float/*半径*/

        if (width <= height) {
            radii = width / 2.0F
            left = 0
            top = (height - width) / 2
            right = width
            bottom = width

            leftDst = 0
            topDst = 0
            rightDst = width
            bottomDst = width
            height = width
        } else {
            radii = height / 2.0F
            left = Math.abs(Math.round((height - width) / 2.0F))
            top = 0
            right = width - Math.abs(Math.round((height - width) / 2.0F))
            bottom = height

            leftDst = Math.abs(Math.round((height - width) / 2.0F))
            topDst = 0
            rightDst = width - Math.abs(Math.round((height - width) / 2.0F))
            bottomDst = height
            width = height
        }

        val src = Rect(left, top, right, bottom)/*控制显示哪部分*/
        val dst = Rect(leftDst, topDst, rightDst, bottomDst)/*从哪展示的位置*/
        return try {
            bitmapCorp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmapCorp)
            canvas.drawARGB(0, 0, 0, 0)
            canvas.drawCircle(radii, radii, radii, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, src, dst, paint)
            bitmapCorp
        } catch (O_O: OutOfMemoryError) {
            O_O.printStackTrace()
            bitmap
        }

    }

    /**
     * @param scrollView
     * @return
     * @from https://www.cnblogs.com/BoBoMEe/p/4556917.html
     */
    fun shotScrollView(scrollView: NestedScrollView): Bitmap {
        var h = 0
        var bitmap: Bitmap? = null
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"))
        }
        bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap!!)
        scrollView.draw(canvas)
        return bitmap
    }

    /**
     * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     */
    fun shotRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 8
            val bitmaCache = LruCache<String, Bitmap>(cacheSize)
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                holder.itemView.layout(0, 0, holder.itemView.measuredWidth,
                        holder.itemView.measuredHeight)
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {

                    bitmaCache.put(i.toString(), drawingCache)
                }
                height += holder.itemView.measuredHeight
            }

            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap!!)
            val lBackground = view.background
            if (lBackground is ColorDrawable) {
                val lColor = lBackground.color
                bigCanvas.drawColor(lColor)
            }

            for (i in 0 until size) {
                val bitmap = bitmaCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap?.height?:0
                bitmap?.recycle()
            }
        }
        return bigBitmap
    }

    fun saveBitmapAsPng(bmp: Bitmap, f: File) {
        try {
            val out = FileOutputStream(f)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun drawBg4Bitmap(color: Int, orginBitmap: Bitmap): Bitmap {
        val paint = Paint()
        paint.color = color
        val bitmap = Bitmap.createBitmap(orginBitmap.width, orginBitmap.height, orginBitmap.config)
        val canvas = Canvas(bitmap)
        canvas.drawRect(0f, 0f, orginBitmap.width.toFloat(), orginBitmap.height.toFloat(), paint)
        canvas.drawBitmap(orginBitmap, 0f, 0f, paint)
        return bitmap
    }

    /**
     * 给drawable 着色
     *
     * @param drawable
     * @param color
     */
    fun setTint(drawable: Drawable, @ColorInt color: Int) {
        DrawableCompat.setTint(DrawableCompat.wrap(drawable), color)
    }


    fun bitmap2GrayScale(bitmap: Bitmap): Bitmap {
        return bitmap2GrayScale(bitmap, false, 0f)
    }

    /**
     * 改变图片灰度，暗色图
     *
     * @param bitmap
     * @param saturation 饱和度 0：灰色 1：原色
     * @return
     */
    fun bitmap2GrayScale(bitmap: Bitmap, isCir: Boolean, saturation: Float?): Bitmap {
        var width: Int
        var height: Int

        height = bitmap.height

        width = bitmap.width

        val bmpGray: Bitmap

        val paint = Paint()

        val cm = ColorMatrix()

        cm.setSaturation(saturation!!)

        val f = ColorMatrixColorFilter(cm)

        paint.colorFilter = f


        if (isCir) {
            val roundPx: Float
            val left: Float
            val top: Float
            val right: Float
            val bottom: Float
            val dst_left: Float
            val dst_top: Float
            val dst_right: Float
            val dst_bottom: Float
            if (width <= height) {
                roundPx = (width / 2).toFloat()
                left = 0f
                top = 0f
                right = width.toFloat()
                bottom = width.toFloat()
                height = width
                dst_left = 0f
                dst_top = 0f
                dst_right = width.toFloat()
                dst_bottom = width.toFloat()
            } else {
                roundPx = (height / 2).toFloat()
                val clip = ((width - height) / 2).toFloat()
                left = clip
                right = width - clip
                top = 0f
                bottom = height.toFloat()
                width = height
                dst_left = 0f
                dst_top = 0f
                dst_right = height.toFloat()
                dst_bottom = height.toFloat()
            }
            val src = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            val dst = Rect(dst_left.toInt(), dst_top.toInt(), dst_right.toInt(), dst_bottom.toInt())

            paint.isAntiAlias = true// 设置画笔无锯齿

            bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            val c = Canvas(bmpGray)
            c.drawARGB(0, 0, 0, 0) // 填充整个Canvas
            c.drawCircle(roundPx, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
            c.drawBitmap(bitmap, src, dst, paint)
        } else {
            bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            val c = Canvas(bmpGray)
            c.drawBitmap(bitmap, 0f, 0f, paint)
        }


        return bmpGray
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            if (cursor == null) {
                return ""
            }
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }
}