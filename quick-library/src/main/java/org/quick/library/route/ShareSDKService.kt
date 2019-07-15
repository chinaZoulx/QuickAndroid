package org.quick.library.route

import android.content.Context
import android.graphics.Bitmap

interface ShareSDKService {

    fun shareAllImg(context: Context,title: String,txt: String,bitmap: Bitmap,listener:(isSuc:Boolean)->Unit)
    fun shareAllUrl(context: Context, title: String, txt: String, url:String,listener:(isSuc:Boolean)->Unit)
    /**
     * type:
     * 微信：Wechat
     * QQ：QQ
     * 微博：SinaWeibo
     */
    fun login(type:String,listener:(isSuc:Boolean,data:HashMap<String,Any>?)->Unit)
}