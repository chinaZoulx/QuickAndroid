package org.chris.quick.function;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.chris.quick.R;


/**
 * Created by work on 2017/3/21.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class LoadingDialog {

    public Activity context;
    private TextView loadingHintTv;
    private AlertDialog mLoadingDialog;
    private boolean isHandle;

    public LoadingDialog(Activity context) {
        this.context = context;
    }

    private void initDialog(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.pw_loading, null);
        builder.setView(contentView);
        mLoadingDialog = builder.create();
        Window window = mLoadingDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        loadingHintTv = contentView.findViewById(R.id.loadingHint);
        mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mLoadingDialog != null && mLoadingDialog.isShowing() && isHandle) {
                        mLoadingDialog.dismiss();
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public AlertDialog getDialog() {
        return mLoadingDialog;
    }

    /**
     * 弹出框，不屏蔽返回键，默认：返回键关闭、点击diglog之外不关闭
     */
    public void alertLoadingDialog() {
        alertLoadingDialog("加载中");
    }

    /**
     * 弹出框，自定义文字内容，不屏蔽返回键
     *
     * @param hint
     */
    public void alertLoadingDialog(CharSequence hint) {
        alertLoadingDialog(true, hint);
    }

    /**
     * 弹出框，屏蔽返回键
     *
     * @param isHandle
     */
    public void alertLoadingDialog(boolean isHandle) {
        alertLoadingDialog(isHandle, "加载中");
    }

    /**
     * 弹出框
     *
     * @param isHandle 是否屏蔽返回键
     * @param hint     文字信息
     */
    public void alertLoadingDialog(boolean isHandle, CharSequence hint) {
        this.isHandle = isHandle;
        if (getDialog() == null) {
            initDialog(context);
        }
        loadingHintTv.setText(hint);
        if (mLoadingDialog != null && !mLoadingDialog.isShowing() && mLoadingDialog.getContext() != null && !context.isFinishing()) {
            mLoadingDialog.show();
        }
    }

    public void dismiss() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && mLoadingDialog.getContext() != null) {
            mLoadingDialog.dismiss();
        }
    }

    public void setLoadingHint(String hint) {
        if (loadingHintTv != null) {
            loadingHintTv.setText(hint);
        }
    }
}
