package org.quick.library.function;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.quick.library.R;

/**
 * Created by work on 2017/3/21.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class IsOkDialog implements View.OnClickListener {

    private Context context;
    private AlertDialog mIsOkDialog;
    private DialogViewHolder holder;
    boolean isHandle;
    private OnClickListener mOnClickListener;

    public IsOkDialog(Activity activity) {
        initDialog(activity);
    }

    public void initDialog(Activity context) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_is_ok, null);
        builder.setView(view);
        mIsOkDialog = builder.create();
        holder = new DialogViewHolder(view);
        holder.msgOkBtn.setTag(0x1);
        holder.msgOkBtn.setOnClickListener(this);
        holder.msgCancelBtn.setTag(0x2);
        holder.msgCancelBtn.setOnClickListener(this);
        mIsOkDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mIsOkDialog != null && mIsOkDialog.isShowing() && isHandle) {
                        mIsOkDialog.dismiss();
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 没有标题与按钮（隐藏），只有内容，点击框外不消失
     *
     * @param msgContent 内容
     */
    public void alertIsOkDialog(CharSequence msgContent) {

        this.alertIsOkDialog(msgContent, "", "确定", null);
    }

    /**
     * 有标题内容与按钮，按钮文字默认左边取消、右边确定，点击框外不消失
     *
     * @param msgTitle
     * @param msgContent
     */
    public void alertIsOkDialog(CharSequence msgTitle, CharSequence msgContent) {

        this.alertIsOkDialog(msgTitle, msgContent, null, "确定", null);
    }

    /**
     * 有标题内容与按钮，按钮文字默认左边取消、右边确定，点击框外不消失
     *
     * @param msgTitle
     * @param msgContent
     */
    public void alertIsOkDialog(CharSequence msgTitle, CharSequence msgContent, CharSequence btnHint) {

        this.alertIsOkDialog(msgTitle, msgContent, null, btnHint, null);
    }

    /**
     * 没有标题，有按钮与内容，点击框外不消失
     *
     * @param msgContent
     * @param onClickListener
     */
    public void alertIsOkDialog(CharSequence msgContent, OnClickListener onClickListener) {

        this.alertIsOkDialog(msgContent, "取消", "确定", onClickListener);
    }


    /**
     * 有标题内容与按钮，按钮文字默认左边取消、右边确定，点击框外不消失
     *
     * @param msgTitle
     * @param msgContent
     * @param onClickListener
     */
    public void alertIsOkDialog(CharSequence msgTitle, CharSequence msgContent, OnClickListener onClickListener) {

        this.alertIsOkDialog(msgTitle, msgContent, "取消", "确定", onClickListener);
    }

    /**
     * 除了标题，其他全都有，按钮自定义，点击框外不消失
     *
     * @param msgContent
     * @param leftBtnTxt
     * @param rightBtnTxt
     * @param onClickListener
     */
    public void alertIsOkDialog(CharSequence msgContent, CharSequence leftBtnTxt, CharSequence rightBtnTxt, OnClickListener onClickListener) {

        this.alertIsOkDialog("", msgContent, leftBtnTxt, rightBtnTxt, onClickListener);
    }

    /**
     * 啥都有，点击屏幕之外不消息
     *
     * @param msgTitle
     * @param msgContent
     * @param leftBtnTxt
     * @param rightBtnTxt
     * @param onClickListener
     */
    public void alertIsOkDialog(CharSequence msgTitle, CharSequence msgContent, CharSequence leftBtnTxt, CharSequence rightBtnTxt, OnClickListener onClickListener) {

        this.alertIsOkDialog(true, msgTitle, null, msgContent, leftBtnTxt, rightBtnTxt, onClickListener);
    }

    public void alertIsOkDialog(CharSequence msgTitle, View customMsgContentView, CharSequence leftBtnTxt, CharSequence rightBtnTxt, OnClickListener onClickListener) {
        alertIsOkDialog(true, msgTitle, customMsgContentView, "", leftBtnTxt, rightBtnTxt, onClickListener);
    }

    public void alertIsOkDialog(boolean isHandle, String msgContent, CharSequence leftBtnTxt, CharSequence rightBtnTxt, OnClickListener onClickListener) {
        alertIsOkDialog(isHandle, "", null, msgContent, leftBtnTxt, rightBtnTxt, onClickListener);
    }

    public void alertIsOkDialog(boolean isHandle,String msgTitle, String msgContent, CharSequence leftBtnTxt, CharSequence rightBtnTxt, OnClickListener onClickListener) {
        alertIsOkDialog(isHandle, msgTitle, null, msgContent, leftBtnTxt, rightBtnTxt, onClickListener);
    }

    /**
     * 啥都有，点击框外自定义
     *
     * @param isHandle
     * @param msgTitle
     * @param msgContent
     * @param leftBtnTxt
     * @param rightBtnTxt
     * @param onClickListener
     */
    public void alertIsOkDialog(boolean isHandle, CharSequence msgTitle, View customMsgContentView, CharSequence msgContent, CharSequence leftBtnTxt, CharSequence rightBtnTxt, OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        this.isHandle = isHandle;
        if (!TextUtils.isEmpty(msgTitle)) {//没有标题隐藏
            holder.msgTitleTv.setVisibility(View.VISIBLE);
            holder.msgTitleTv.setText(msgTitle);
        } else {
            holder.msgTitleTv.setVisibility(View.GONE);
        }

        if (holder.msgContentContainer.getChildCount() > 1)
            holder.msgContentContainer.removeViews(1, holder.msgContentContainer.getChildCount()-1);
        if (customMsgContentView != null) {
            holder.msgContentTv.setVisibility(View.GONE);
            holder.msgContentContainer.addView(customMsgContentView);
        } else {
            holder.msgContentTv.setVisibility(View.VISIBLE);
            holder.msgContentTv.setText(msgContent);
        }

        if (TextUtils.isEmpty(rightBtnTxt)) {//没有右边按钮隐藏
            holder.msgOkBtn.setVisibility(View.GONE);
        } else {
            holder.msgOkBtn.setVisibility(View.VISIBLE);
            holder.msgOkBtn.setText(rightBtnTxt);
        }

        if (TextUtils.isEmpty(leftBtnTxt)) {//没有左边按钮隐藏
            holder.msgCancelBtn.setVisibility(View.GONE);
        } else {
            holder.msgCancelBtn.setVisibility(View.VISIBLE);
            holder.msgCancelBtn.setText(leftBtnTxt);
        }

        mIsOkDialog.setCanceledOnTouchOutside(false);
        show();
    }

    @Override
    public void onClick(View v) {
        mIsOkDialog.dismiss();
        if (mOnClickListener == null) {
            return;
        }
        int tag = (int) v.getTag();
        switch (tag) {
            case 0x1://ok
                mOnClickListener.onClicked(v, true);
                break;
            case 0x2://false
                mOnClickListener.onClicked(v, false);
                break;
        }
    }

    private void show() {
        if (mIsOkDialog != null && !mIsOkDialog.isShowing()) {
            mIsOkDialog.show();
        }
    }

    public void dismiss() {
        if (mIsOkDialog != null && mIsOkDialog.isShowing()) {
            mIsOkDialog.dismiss();
        }
    }

    public AlertDialog getDialog() {
        return mIsOkDialog;
    }

    public class DialogViewHolder {

        public TextView msgTitleTv;
        public TextView msgContentTv;
        public Button msgOkBtn;
        public Button msgCancelBtn;
        public FrameLayout msgContentContainer;

        public DialogViewHolder(View view) {

            msgTitleTv = view.findViewById(R.id.msgTitleTv);
            msgContentTv = view.findViewById(R.id.msgContentTv);
            msgCancelBtn = view.findViewById(R.id.msgCancelBtn);
            msgOkBtn = view.findViewById(R.id.msgOkBtn);
            msgContentContainer = view.findViewById(R.id.msgContentContainer);
        }
    }

    public interface OnClickListener {

        void onClicked(View view, boolean isRight);
    }
}
