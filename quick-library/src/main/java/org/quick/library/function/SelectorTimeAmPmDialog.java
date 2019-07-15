package org.quick.library.function;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import org.quick.library.R;
import org.quick.library.widgets.RollView;
import org.quick.component.utils.DevicesUtils;

import java.util.ArrayList;

/**
 * 时间选择
 * The class is activity select year and month and day and hour and minute
 */
public class SelectorTimeAmPmDialog {

    public interface OnResultHandler {

        void handle(String AM, String PM);
    }

    private OnResultHandler handler;
    private Context context;
    private Dialog selectorDialog;
    private RollView AMHourRv;
    private RollView AMMinuteRv;
    private RollView PMHourRv;
    private RollView PMMinuteRv;

    private ArrayList<String> AMHours, AMMinutes, PMHours, PMMinutes;

    private TextView tv_cancle;
    private TextView tv_select, tv_title;

    private String AMHourStr, AMMinuteStr;
    private String PMHourStr, PMMinuteStr;

    public SelectorTimeAmPmDialog(Context context, OnResultHandler resultHandler) {

        this.context = context;
        this.handler = resultHandler;
        initDialog();
        initView();
    }

    public void show() {
        initTimer();
        addListener();
        selectorDialog.show();
    }

    private void initDialog() {

        if (selectorDialog == null) {
            selectorDialog = new Dialog(context, R.style.AppTheme_TimeDialog);
            selectorDialog.setCancelable(false);
            selectorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            selectorDialog.setContentView(R.layout.dialog_selector_time_am_pm);
            Window window = selectorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = DevicesUtils.INSTANCE.getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    private void initView() {

        AMHourRv = (RollView) selectorDialog.findViewById(R.id.amHourRv);
        AMMinuteRv = (RollView) selectorDialog.findViewById(R.id.amMinuteRv);
        PMHourRv = (RollView) selectorDialog.findViewById(R.id.pmHourRv);
        PMMinuteRv = (RollView) selectorDialog.findViewById(R.id.pmMinuteRv);
        tv_cancle = (TextView) selectorDialog.findViewById(R.id.tv_cancle);
        tv_select = (TextView) selectorDialog.findViewById(R.id.tv_select);
        tv_title = (TextView) selectorDialog.findViewById(R.id.tv_title);

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorDialog.dismiss();
            }
        });

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AM = AMHourStr + " : " + AMMinuteStr;
                String PM = PMHourStr + " : " + PMMinuteStr;
                handler.handle(AM, PM);
                selectorDialog.dismiss();
            }
        });
    }

    private void initTimer() {
        initArrayList();
        initData();
        loadComponent();
    }

    private void initArrayList() {
        if (AMHours == null) AMHours = new ArrayList<>();
        if (AMMinutes == null) AMMinutes = new ArrayList<>();
        if (PMHours == null) PMHours = new ArrayList<>();
        if (PMMinutes == null) PMMinutes = new ArrayList<>();
        AMHours.clear();
        AMMinutes.clear();
        PMHours.clear();
        PMMinutes.clear();
    }

    private void initData() {
        /**早上小时**/
        for (int i = 0; i < 24; i++) {
            String AMHourStr;
            if (i < 10) {
                AMHourStr = "0" + i;
            } else {
                AMHourStr = "" + i;
            }
            AMHours.add(AMHourStr);
        }
        if (TextUtils.isEmpty(AMHourStr))
            AMHourStr = AMHours.get(0);

        /**早上分钟**/
        for (int i = 0; i < 6; i++) {
            String AMMinuteStr;
            if (i == 0) {
                AMMinuteStr = "00";
            } else {
                AMMinuteStr = "" + i * 10;
            }
            AMMinutes.add(AMMinuteStr);
        }
        if (TextUtils.isEmpty(AMMinuteStr))
            AMMinuteStr = AMMinutes.get(0);

        /**下午小时**/
        for (int i = 0; i < 24; i++) {
            String PMHourStr;
            if (i < 10) {
                PMHourStr = "0" + i;
            } else {
                PMHourStr = "" + i;
            }
            PMHours.add(PMHourStr);
        }
        if (TextUtils.isEmpty(PMHourStr))
            PMHourStr = PMHours.get(0);

        /**下午分钟**/
        for (int i = 0; i < 6; i++) {
            String PMMinuteStr;
            if (i == 0) {
                PMMinuteStr = "00";
            } else {
                PMMinuteStr = "" + i * 10;
            }
            PMMinutes.add(PMMinuteStr);
        }
//        PMMinutes.addData("00");
//        PMMinutes.addData("30");
        if (TextUtils.isEmpty(PMMinuteStr))
            PMMinuteStr = PMMinutes.get(0);
    }

    private void addListener() {
        AMHourRv.setOnSelectListener(new RollView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                AMHourStr = text;
            }
        });
        AMMinuteRv.setOnSelectListener(new RollView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                AMMinuteStr = text;
            }
        });
        PMHourRv.setOnSelectListener(new RollView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                PMHourStr = text;
            }
        });
        PMMinuteRv.setOnSelectListener(new RollView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                PMMinuteStr = text;
            }
        });
    }

    private void loadComponent() {
        AMHourRv.setData(AMHours);
        AMHourRv.setSelected(0);
        AMMinuteRv.setData(AMMinutes);
        AMMinuteRv.setSelected(0);
        PMHourRv.setData(PMHours);
        PMHourRv.setSelected(0);
        PMMinuteRv.setData(PMMinutes);
        PMMinuteRv.setSelected(0);

        AMHourRv.setSelected(AMHourStr);
        AMMinuteRv.setSelected(AMMinuteStr);
        PMHourRv.setSelected(PMHourStr);
        PMMinuteRv.setSelected(PMMinuteStr);
        excuteScroll();
    }

    private void excuteScroll() {
        AMHourRv.setCanScroll(AMHours.size() > 1);
        AMMinuteRv.setCanScroll(AMMinutes.size() > 1);
        PMHourRv.setCanScroll(PMHours.size() > 1);
        PMMinuteRv.setCanScroll(PMMinutes.size() > 1);
    }

    private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ)
                .setDuration(ANIMATORDELAY).start();
    }

    public void setTitle(String str) {
        tv_title.setText(str);
    }

    public void setIsLoop(boolean isLoop) {
        this.AMHourRv.setIsLoop(isLoop);
        this.AMMinuteRv.setIsLoop(isLoop);
        this.PMHourRv.setIsLoop(isLoop);
        this.PMMinuteRv.setIsLoop(isLoop);
    }
}
