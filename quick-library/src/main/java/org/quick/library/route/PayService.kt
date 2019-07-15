package org.quick.library.route

import org.quick.library.b.BaseActivity

interface PayService {

    fun showPayDialog(context: BaseActivity, id: String, count: Int, time: String, onPayListener: OnPayListener)

    fun showPayDialog(context: BaseActivity, tradeNo: String, onPayListener: OnPayListener)

    interface OnPayListener {
        /**
         * 支付成功
         */
        fun onResultSuccess(totalAmount: String?)

        /**
         * 支付失败
         *
         * @param msg
         */
        fun onResultFail(msg: String?)
    }
}