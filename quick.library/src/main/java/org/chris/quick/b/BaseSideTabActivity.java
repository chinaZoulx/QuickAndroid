package org.chris.quick.b;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.chris.quick.R;
import org.chris.quick.b.activities.ThemeActivity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

/**
 * 水平滑动Tab
 * Created by chris Zou on 2016/4/15.
 */
public abstract class BaseSideTabActivity extends ThemeActivity implements TabLayout.OnTabSelectedListener {

    public static final String TAG = BaseSideTabActivity.class.getSimpleName();
    protected TabLayout mTabs;
    protected ViewPager mViewPager;
    protected SideTabBaseParentAdapter mSideTabAdapter;
    private LinkedList<SideTabContent> mSideTabContentList;
    public static int DEFAULT_LAYOUT = R.layout.activity_sidetab;

    public void init() {
        onBindData();
    }

    @Override
    public int onResultLayoutResId() {
        return DEFAULT_LAYOUT;
    }

    public void onBindData() {
        mViewPager = (ViewPager) findViewById(R.id.sideMainViewPager);
        mTabs = (TabLayout) findViewById(R.id.sideMainTabLayout);

        mSideTabContentList = new LinkedList<>();
        mSideTabContentList = initTabData(mSideTabContentList);
        mSideTabAdapter = new SideTabBaseParentAdapter(getSupportFragmentManager(), mSideTabContentList);

        mViewPager.setAdapter(mSideTabAdapter);
        mTabs.setupWithViewPager(mViewPager);
        if (mSideTabContentList.size() > 5) {
            mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);//支持超出水平的内容滚动
            mTabs.setTabGravity(TabLayout.GRAVITY_CENTER);
        } else {
            mTabs.setTabMode(TabLayout.MODE_FIXED);//支持超出水平的内容滚动
            mTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        mTabs.removeAllTabs();
        for (SideTabContent sideTabContent : mSideTabContentList) {
            switch (sideTabContent.getType()) {
                case SideTabContent.TYPE_CUSTOM:
                    mTabs.addTab(mTabs.newTab().setCustomView(sideTabContent.getCustomView()));
                    break;
                case SideTabContent.TYPE_CUSTOM_NO:
//                    TabLayout.Tab tab = mTabs.newTab();
//                    tab.setText(sideTabContent.getTitle());
//                    if (sideTabContent.getIcon() != 0) {
//                        tab.setIcon(sideTabContent.getIcon());
//                    }
                    mTabs.addTab(mTabs.newTab().setText("").setIcon(sideTabContent.getIcon()));
                    break;
            }
        }
        mSideTabAdapter.setData(mSideTabContentList);

    }

    /**
     * @param position 下标
     * @return
     * @hint 该只有在界面加载完成之后才能取到值, 且只有展示过的页面才有值
     */
    public <T> T getFragment(int position) {
        return (T) this.mSideTabContentList.get(position).getFragment();
    }

    /**
     * 显示指定页面
     *
     * @param position
     */
    public void setCurrentView(int position) {
        if (position < mSideTabAdapter.getCount() && position >= 0) {
            mViewPager.setCurrentItem(position);
        } else {
            Log.e(TAG, "\"需要显示的位置未包含在数组内\"");
        }
    }

    /**
     * 初始化Tab项的数据
     *
     * @param mSideTabContentList
     * @return
     */
    public abstract LinkedList<SideTabContent> initTabData(LinkedList<SideTabContent> mSideTabContentList);

    /**
     * 绑定数据
     *
     * @param holder 控件器，内包含根view及实用方法
     */
    public abstract void bindData(BaseParentViewHolder holder);

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setCurrentView(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * Model
     */
    public class SideTabContent implements Serializable {

        private long id;
        private Class fragment;
        private CharSequence title;
        private int icon;

        /**
         * 自定义Tab
         */
        private View customView;

        private int type = TYPE_CUSTOM_NO;

        /**
         * 自定义Tab标签
         */
        public static final int TYPE_CUSTOM = 0;
        /**
         * 默认的Tab标签
         */
        public static final int TYPE_CUSTOM_NO = 1;

        public SideTabContent(Class fragment, CharSequence title, View customView) {
            this(fragment, title, 0, customView);
        }

        public SideTabContent(Class fragment, CharSequence title, int icon) {
            this(fragment, title, icon, null);
        }

        public SideTabContent(Class fragment, CharSequence title, int icon, View customView) {
            this.id = makeId();
            this.fragment = fragment;
            this.title = title;
            this.icon = icon;
            this.customView = customView;
            if (customView != null) {
                type = TYPE_CUSTOM;
            }
            if (fragment == null) {
                throw new IllegalArgumentException("You need set a class");
            }
        }

        public CharSequence getTitle() {
            return title;
        }

        public Class getFragment() {
            return fragment;
        }

        public int getIcon() {
            return icon;
        }

        public int getType() {
            return type;
        }

        public View getCustomView() {
            return customView;
        }

        public long getId() {
            return id;
        }

        private long makeId() {
            Random random = new Random();
            return random.nextLong();
        }
    }

    /**
     * Adapter
     */
    public class SideTabBaseParentAdapter extends BaseFragmentPagerAdapter {

        private LinkedList<SideTabContent> mSideTabContentList;

        public SideTabBaseParentAdapter(FragmentManager fm, LinkedList<SideTabContent> sideTabContentList) {
            super(fm);
            this.mSideTabContentList = sideTabContentList;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            try {
                fragment = (Fragment) mSideTabContentList.get(position).getFragment().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return mSideTabContentList != null ? mSideTabContentList.size() : 0;
        }

        @Override
        public long getItemId(int position) {
            return mSideTabContentList.get(position).getId();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mSideTabContentList.get(position).getTitle();
        }

        public void setData(LinkedList<SideTabContent> sideTabContentList) {
            if (sideTabContentList != null) {
                this.mSideTabContentList = sideTabContentList;
                notifyDataSetChanged();
            } else {
                throw new NullPointerException("You cannot set null data");
            }
        }
    }
}
