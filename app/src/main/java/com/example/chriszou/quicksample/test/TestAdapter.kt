package com.example.chriszou.quicksample.test

import android.view.View
import org.quick.component.QuickAdapter
import org.quick.library.b.BaseViewHolder

abstract class TestAdapter<M> : QuickAdapter<M>(){
    override fun onResultViewHolder(itemView: View): BaseViewHolder = BaseViewHolder(itemView)

}