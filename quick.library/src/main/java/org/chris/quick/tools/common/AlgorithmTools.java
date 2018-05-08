/*
 *
 *  * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 *
 */

package org.chris.quick.tools.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmTools {

    /**
     * 判断是否是电话号码 <br/>
     * 130 131 132 133 134x（0-8）135 136 137 138 139 (1349卫星电话)<br/>
     * 150 151 152 153 155 156 157 158 159 (154 暂时未启用)<br/>
     * 176 177 178 (最新4G段位号)<br/>
     * 180 181 182 183 184 185 186 187 188 189
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String[] dwh = new String[]{"13[0-9]", "15[^4,\\D]", "17[6-8]", "18[0-9]"};

        StringBuffer zz = new StringBuffer();
        zz.append("^(");
        for (String str : dwh) {
            zz.append("(");
            zz.append(str);
            zz.append(")|");
        }
        zz.delete(zz.length() - 1, zz.length());
        zz.append(")\\d{8}$");
        System.out.println("匹配语法：" + zz.toString());
        Pattern p = Pattern.compile(zz.toString());
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
