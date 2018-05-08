/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package org.chris.quick.b;

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.chris.quick.R;
import org.chris.quick.b.activities.ThemeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @comment FragmentTabHost基类
 * @Date 2016/9/27 0027
 * @modifyInfo1 Administrator-2016/9/27 0027
 * @modifyContent
 */
public abstract class BaseFragmentTabHost extends ThemeActivity {

    public FragmentTabHost mTabHost;

    public List<TabHostMenu> mIndexMenus;// 设置底部按钮文字图片

    @Override
    public int onResultLayoutResId() {

        return R.layout.fragment_tabhost;
    }

    @Override
    public boolean isUsingBaseLayout() {
        return false;
    }



    public void init() {
        mIndexMenus = new ArrayList<>();
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
        mIndexMenus = initData(mIndexMenus);
        onBindData();
    }

    protected abstract List<TabHostMenu> initData(List<TabHostMenu> mIndexMenus);

    public void onBindData() {
        //去掉分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mIndexMenus.size(); i++) {
            //对Tab按钮添加标记和图片
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mIndexMenus.get(i).getName())
                    .setIndicator(getIndicatorView(
                            mIndexMenus.get(i).getName(),
                            mIndexMenus.get(i).getIcon(),
                            mIndexMenus.get(i).getVisible()));
            //添加Fragment
            mTabHost.addTab(tabSpec, mIndexMenus.get(i).getFragmentIndex(), null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

    }

    /**
     * 显示底部按钮
     *
     * @param nameStr
     * @return
     */
    private View getIndicatorView(String nameStr, int id, int visible) {

        View view = getLayoutInflater().inflate(R.layout.tabhost_item, null);
        TextView tvIndexTitle = (TextView) view.findViewById(R.id.tv_index_title);
        ImageView imgIndex = (ImageView) view.findViewById(R.id.img_index);
        tvIndexTitle.setVisibility(visible);
        tvIndexTitle.setText(nameStr);
        imgIndex.setImageResource(id);
        return view;
    }

    public class TabHostMenu {

        private Class fragmentIndex;// 设置fragment
        private int icon;
        private int tablayout;
        private int type;

        public int getVisible() {

            return visible;
        }

        public void setVisible(int visible) {

            this.visible = visible;
        }

        private int visible;


        private String name;

        public int getTablayout() {

            return tablayout;
        }

        public void setTablayout(int tablayout) {

            this.tablayout = tablayout;
        }


        public void setIcon(int icon) {

            this.icon = icon;
        }

        public void setType(int type) {

            this.type = type;
        }

        public void setName(String name) {

            this.name = name;
        }

        public void setFragmentIndex(Class fragmentIndex) {

            this.fragmentIndex = fragmentIndex;
        }

        public Class getFragmentIndex() {

            return fragmentIndex;
        }

        public int getIcon() {

            return icon;
        }

        public int getType() {

            return type;
        }

        public String getName() {

            return name;
        }

    }
}
