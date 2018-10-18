package com.example.chriszou.quicksample.test

import android.view.View
import org.quick.library.b.BaseViewHolder
import org.quick.component.QuickAdapter

abstract class TestAdapter<M> : QuickAdapter<M, BaseViewHolder>(){
    override fun onResultViewHolder(itemView: View): BaseViewHolder = BaseViewHolder(itemView)

}