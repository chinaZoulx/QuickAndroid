package org.chris.quick.tools.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FunctionUtils {


    public static final void openWebBrowser(Context context,String url){
        Intent browserAction=new Intent(Intent.ACTION_VIEW);
       browserAction.setData(Uri.parse(url));
        context.startActivity(browserAction);
    }
}
