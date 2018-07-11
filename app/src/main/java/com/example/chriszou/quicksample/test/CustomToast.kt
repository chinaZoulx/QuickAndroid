package com.example.chriszou.quicksample.test

import android.content.Context
import org.chris.quick.function.QuickToast

/**
 * @describe
 * @author ChrisZou
 * @date 2018/7/11-11:42
 * @email chrisSpringSmell@gmail.com
 */
class CustomToast : QuickToast() {

    override val context: Context
        get() = super.context/*这里替换为自定义context*/
}