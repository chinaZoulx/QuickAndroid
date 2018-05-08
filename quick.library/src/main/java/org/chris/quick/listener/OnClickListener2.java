package org.chris.quick.listener;

import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by work on 2017/6/16.
 * 防止用户快速点击
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public abstract class OnClickListener2 implements View.OnClickListener {

    long lastTime;
    public static final long intervalTime = 500;//间隔时间（毫秒）

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long temp = currentTime - lastTime;
        if (temp > intervalTime) {//间隔时间，成功触发
            onClick2(v);
            lastTime = currentTime;
            Log.e("点击", "触发，毫秒" + temp);
        }
        Log.e("点击差值", " 差值(毫秒) ：" + temp);
        Log.e("dfdf", "-----------------------------");
    }

    public abstract void onClick2(View view);
}
