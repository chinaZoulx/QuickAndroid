package org.chris.quick.b;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import org.chris.quick.R;
import org.chris.quick.tools.common.DevicesUtils;
import org.chris.quick.tools.common.FormatUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 侧滑菜单框架
 */
public abstract class BaseSideSlipMenuActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    protected DrawerLayout mDrawerLayout;
    protected Toolbar mToolbar;
    protected CoordinatorLayout appContent;
    protected LinearLayout dotsBorder;

    private LinkedList< MainMenu > mainMenuContentList;
    private ExpandableListView elvMenuList;
    protected MenuAdapter menuAdapter;
    protected FrameLayout headView;
    protected FrameLayout footerView;
    protected LinearLayout appContentMenu;

    @Override protected void onCreate ( @Nullable Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_sideslip_menu );
        onInit();
        onInitView();
        onInitLayout ();
        onBindData ();
    }

    public void onInit () {

        mainMenuContentList = new LinkedList<> ();
        menuAdapter = new MenuAdapter ( mainMenuContentList );
    }

    public void onInitView () {

        mDrawerLayout = getView ( R.id.main_drawer_layout );
        mToolbar = getView ( R.id.main_toolbar );
        appContent = getView ( R.id.appContentBorder );
        dotsBorder = getView ( R.id.dotsBorder );
        appContentMenu = getView ( R.id.appContentMenu );
        elvMenuList = getView ( R.id.main_menu_item_list );

        elvMenuList.setAdapter ( menuAdapter );
        elvMenuList.setOnChildClickListener ( this );
        elvMenuList.setOnGroupClickListener ( this );
        mDrawerLayout.addDrawerListener ( new DrawerLayout.DrawerListener () {

            @Override
            public void onDrawerSlide ( View drawerView, float slideOffset ) {

                View  mContent    = mDrawerLayout.getChildAt ( 0 );//内容
                View  mMenu       = drawerView;//菜单
                float scale       = 1 - slideOffset;
                float scaleHeight = 0.9f;//缩放之后所占高度：90%，1：整个高度（100%）
                float rightScale  = scaleHeight + scale * 0.1f;//
                float leftScale   = 1 - 0.3f * scale;
                ViewHelper.setScaleX ( mMenu, leftScale );
                ViewHelper.setScaleY ( mMenu, leftScale );
                ViewHelper.setAlpha ( mMenu, 0.6f + 0.4f * ( 1 - scale ) );
                ViewHelper.setTranslationX ( mContent, mMenu.getMeasuredWidth () * ( 1 - scale ) );
                ViewHelper.setPivotX ( mContent, 0 );
                ViewHelper.setPivotY ( mContent, mContent.getMeasuredHeight () );
                mContent.invalidate ();
                ViewHelper.setScaleX ( mContent, rightScale );
                ViewHelper.setScaleY ( mContent, rightScale );
            }

            @Override
            public void onDrawerOpened ( View drawerView ) {

            }

            @Override
            public void onDrawerClosed ( View drawerView ) {

            }

            @Override
            public void onDrawerStateChanged ( int newState ) {

            }
        } );
    }

    public <T> T getView(int id){
        return ( T ) findViewById ( id );
    }

    public void onInitLayout () {

        setMaterialLeftView ( R.string.openMenu, R.string.closeMenu );
    }

    public void onBindData () {

        mainMenuContentList = initData ( mainMenuContentList );
        menuAdapter.setData ( mainMenuContentList, elvMenuList );
    }

    protected View addMenuHeadView ( int layoutResId ) {

        return addMenuHeadView ( LayoutInflater.from ( this ).inflate ( layoutResId, null ) );
    }

    protected View addMenuHeadView ( View head ) {

        headView = ( FrameLayout ) findViewById ( R.id.menuHeadFrame );
        headView.addView ( head );
        return head;
    }

    protected View addMenuFooterView ( int layoutResId ) {

        return addMenuFooterView ( LayoutInflater.from ( this ).inflate ( layoutResId, null ) );
    }

    protected View addMenuFooterView ( View footer ) {

        footerView = ( FrameLayout ) findViewById ( R.id.menuBottomFrame );
        footerView.addView ( footer );
        return footer;
    }

    protected abstract LinkedList< MainMenu > initData ( LinkedList< MainMenu > mainMenuContentList );

    @Override
    public void onBackPressed () {

        if ( mDrawerLayout.isDrawerOpen ( GravityCompat.START ) ) {
            mDrawerLayout.closeDrawer ( GravityCompat.START );
        } else {
            super.onBackPressed ();
        }
    }

    /**
     * 更新指定Fragment
     *
     * @param fragment
     */
    protected void replaceFragment ( Fragment fragment ) {

        FragmentManager fragmentManager = getSupportFragmentManager ();
        fragmentManager.beginTransaction ().replace ( R.id.main_content_frame, fragment )
                       .commitAllowingStateLoss ();
        mangerDrawerLayout ();
    }

    protected void mangerDrawerLayout () {

        if ( mDrawerLayout.isDrawerOpen ( GravityCompat.START ) ) {
            mDrawerLayout.closeDrawer ( GravityCompat.START );
        }
    }

    protected void setTitle ( CharSequence name, View.OnClickListener onClickListener ) {

        this.setTitle ( 0, name, onClickListener );
    }

    /**
     * @param icon 为0时，不设置图标
     * @param name
     */
    protected void setTitle ( int icon, CharSequence name, View.OnClickListener onClickListener ) {

        TextView title = ( TextView ) findViewById ( R.id.pageTitle );
        title.setVisibility ( View.VISIBLE );
        if ( onClickListener != null ) {
            title.setOnClickListener ( onClickListener );
        }
        title.setVisibility ( View.VISIBLE );
        title.setText ( name );
        if ( icon != 0 ) {
            title.setCompoundDrawablesWithIntrinsicBounds ( getResources ().getDrawable ( icon ),
                                                            null, null, null );
        }
    }

    protected void setTitleBackGroundRes ( @ColorRes int colorResId ) {

        this.setTitleBackGround ( getResources ().getColor ( colorResId ) );
    }

    protected void setTitleBackGround ( @ColorInt int colorId ) {

        mToolbar.setBackgroundColor ( colorId );
    }


    protected TextView getPageTitle () {

        return ( TextView ) findViewById ( R.id.pageTitle );
    }

    /**
     * 使用Material Design 默认图标
     *
     * @param openStr
     * @param closeStr
     */
    @TargetApi ( Build.VERSION_CODES.LOLLIPOP )
    protected void setMaterialLeftView ( int openStr, int closeStr ) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, mDrawerLayout, mToolbar,
                                                                   openStr, closeStr );
        toggle.setDrawerIndicatorEnabled ( true );
        mDrawerLayout.addDrawerListener ( toggle );
        toggle.syncState ();
    }

    /**
     * @return int[] 0:width,1:height
     */
    protected int[] getMaterialTitleViewSize () {

        Rect rect = new Rect ();
        mToolbar.getDrawingRect ( rect );

        return new int[] {rect.width () , rect.height ()};
    }

    /**
     * @param icon            为0时，不设置图标
     * @param onClickListener
     */
    protected void setPageRightView ( int icon, View.OnClickListener onClickListener ) {

        this.setPageRightView ( icon, "", onClickListener );
    }

    /**
     * @param onClickListener
     */
    protected void setPageRightView ( CharSequence name, View.OnClickListener onClickListener ) {

        this.setPageRightView ( 0, name, onClickListener );
    }

    /**
     * @param icon            为0时，不设置图标
     * @param name
     * @param onClickListener
     */
    protected void setPageRightView ( int icon, CharSequence name, View.OnClickListener onClickListener ) {

        TextView rightView = ( TextView ) findViewById ( R.id.pageRight );
        rightView.setVisibility ( View.VISIBLE );
        if ( onClickListener != null ) {
            rightView.setOnClickListener ( onClickListener );
        }
        rightView.setText ( name );
        if ( icon != 0 ) {
            rightView
                    .setCompoundDrawablesWithIntrinsicBounds ( getResources ().getDrawable ( icon ),
                                                               null, null, null );
        }
    }

    /**
     * @param icon            为0时，不设置图标
     * @param name
     * @param onClickListener
     */
    protected void setPageLeftView ( int icon, CharSequence name, View.OnClickListener onClickListener ) {

        TextView pageLeftView = ( TextView ) findViewById ( R.id.pageLeft );
        pageLeftView.setVisibility ( View.VISIBLE );
        if ( onClickListener != null ) {
            pageLeftView.setOnClickListener ( onClickListener );
        }
        pageLeftView.setText ( name );
        if ( icon != 0 ) {
            pageLeftView
                    .setCompoundDrawablesWithIntrinsicBounds ( getResources ().getDrawable ( icon ),
                                                               null, null, null );
        }
    }

    protected void setBackGroundMenu ( int id ) {

        appContentMenu.setBackgroundResource ( id );
    }

    protected void setBackGroundMenuColor ( int color ) {

        appContentMenu.setBackgroundColor ( color );
    }

    public void setBackGroundContent ( int id ) {

        mDrawerLayout.getChildAt ( 0 ).setBackgroundResource ( id );
    }

    public void setBackGroundContentColor ( int color ) {

        mDrawerLayout.getChildAt ( 0 ).setBackgroundColor ( color );
    }

    protected void showToast ( CharSequence content ) {

        Toast.makeText ( this, content, Toast.LENGTH_SHORT ).show ();
    }

    protected void showSnackbar ( CharSequence content) {

        showSnackbar(content,"",null);
    }


    protected void showSnackbar ( CharSequence content, CharSequence actionName, View.OnClickListener onClickListener ) {

       this.showSnackbar(appContent,content,actionName,onClickListener);
    }

    protected void showSnackbar ( View parent, CharSequence content, CharSequence actionName, View.OnClickListener onClickListener ) {

        Snackbar.make ( parent, content, Snackbar.LENGTH_SHORT )
                .setAction ( actionName, onClickListener ).show ();
    }

    public abstract void onBind ();

    @CallSuper
    public void onInitTitle () {
        appContentMenu.setPadding ( 0, DevicesUtils.getStatusHeight ( this ),0,0 );
    }

    @Override
    protected void onResume () {

        super.onResume ();
        onInitTitle ();
        onBind ();
    }

    public class MenuAdapter extends BaseExpandableListAdapter {

        private LinkedList< MainMenu > menuContentList;
        private int groupFocusItem, childFocusItem = 0x123456;

        public MenuAdapter ( LinkedList< MainMenu > menuContentList ) {

            this.menuContentList = menuContentList;
        }

        public void setData ( LinkedList< MainMenu > menuContentList, ExpandableListView content ) {

            if ( menuContentList != null ) {
                this.menuContentList = menuContentList;
                notifyDataSetChanged ();
                for ( int i = 0 ; i < menuContentList.size () ; i++ ) {
                    content.expandGroup ( i );
                }
            }
        }

        public void setFocusItem ( int groupFocusItem, int childFocusItem ) {

            this.groupFocusItem = groupFocusItem;
            this.childFocusItem = childFocusItem;
            notifyDataSetChanged ();
        }

        @Override
        public int getGroupCount () {

            return menuContentList.size ();
        }

        @Override
        public int getChildrenCount ( int i ) {

            return menuContentList.get ( i ).getChildMenu ().size ();
        }

        @Override
        public MainMenu getGroup ( int i ) {

            return menuContentList.get ( i );
        }

        @Override
        public MainMenu getChild ( int i, int i1 ) {

            return menuContentList.get ( i ).getChildMenu ().get ( i1 );
        }

        @Override
        public long getGroupId ( int i ) {

            return i;
        }

        @Override
        public long getChildId ( int i, int i1 ) {

            return i1;
        }

        @Override
        public boolean hasStableIds () {

            return true;
        }

        @Override
        public View getGroupView ( final int i, boolean b, View convertView, ViewGroup parent ) {

            final MainMenu menuBean = getGroup ( i );
            ViewHolder     holder;
            if ( convertView == null ) {
                convertView = LayoutInflater.from ( parent.getContext () )
                                            .inflate ( R.layout.item_side_menu, parent, false );
                holder = new ViewHolder ();
                holder.initView ( convertView );
                convertView.setTag ( holder );
            } else {
                holder = ( ViewHolder ) convertView.getTag ();
            }

            int type = menuBean.getType ();
            switch ( type ) {
                case MainMenu.TYPE_NO_ICON:
                    holder.name.setText ( menuBean.getName () );
                    break;

                case MainMenu.TYPE_HINT:
                    holder.name.setText ( menuBean.getName () );
                    holder.name.setCompoundDrawablesWithIntrinsicBounds (
                            parent.getContext ().getResources ()
                                  .getDrawable ( menuBean.getIcon () ), null,
                            parent.getContext ().getResources ()
                                  .getDrawable ( menuBean.getHint () ), null );
                    break;

                case MainMenu.TYPE_NORMAL:
                    holder.name.setText ( menuBean.getName () );
                    holder.name.setCompoundDrawablesWithIntrinsicBounds (
                            parent.getContext ().getResources ()
                                  .getDrawable ( menuBean.getIcon () ), null, null, null );
                    break;
                case MainMenu.TYPE_SEPARATOR:
                    holder.name.setVisibility ( View.GONE );
                    holder.separator.setVisibility ( View.VISIBLE );
                    break;
            }
            convertView.setClickable ( false );
            return convertView;
        }

        @Override
        public View getChildView ( final int i, int i1, boolean b, View convertView, ViewGroup parent ) {

            final MainMenu menuBean = getChild ( i, i1 );
            ViewHolder     holder;
            if ( convertView == null ) {
                convertView = LayoutInflater.from ( parent.getContext () )
                                            .inflate ( R.layout.item_side_menu, parent, false );
                holder = new ViewHolder ();
                holder.initView ( convertView );
                convertView.setTag ( holder );
            } else {
                holder = ( ViewHolder ) convertView.getTag ();
            }

            if ( groupFocusItem == i && childFocusItem == i1 ) {
                holder.name.setBackgroundColor (
                        parent.getContext ().getResources ().getColor ( R.color.colorBlack ) );
                holder.name.setTextColor ( Color.WHITE );
            } else {
                holder.name.setBackgroundColor ( Color.TRANSPARENT );
                holder.name.setTextColor (
                        parent.getContext ().getResources ().getColor ( R.color.colorWhite ) );
            }
            int type = menuBean.getType ();
            switch ( type ) {
                case MainMenu.TYPE_NO_ICON:
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                holder.name.setLayoutParams(params);
                    holder.name.setText ( menuBean.getName () );
                    break;

                case MainMenu.TYPE_HINT:
                    holder.name.setText ( menuBean.getName () );
                    holder.name.setCompoundDrawablesWithIntrinsicBounds (
                            parent.getContext ().getResources ()
                                  .getDrawable ( menuBean.getIcon () ), null,
                            parent.getContext ().getResources ()
                                  .getDrawable ( menuBean.getHint () ), null );
                    break;

                case MainMenu.TYPE_NORMAL:
                    holder.name.setText ( menuBean.getName () );
                    holder.name.setCompoundDrawablesWithIntrinsicBounds (
                            parent.getContext ().getResources ()
                                  .getDrawable ( menuBean.getIcon () ), null, null, null );
                    break;
                case MainMenu.TYPE_SEPARATOR:
                    holder.name.setVisibility ( View.GONE );
                    holder.separator.setVisibility ( View.VISIBLE );
                    break;
            }
            holder.name.setPadding ( FormatUtils.dip2px ( parent.getContext (), 10 ),
                                     FormatUtils.dip2px ( parent.getContext (), 5 ), 0,
                                     FormatUtils.dip2px ( parent.getContext (), 5 ) );
            return convertView;
        }

        @Override
        public boolean isChildSelectable ( int i, int i1 ) {

            return true;
        }

        private class ViewHolder {

            private TextView name;
            private View separator;

            private void initView ( View rootView ) {

                name = ( TextView ) rootView.findViewById ( R.id.main_menu_name );
                separator = rootView.findViewById ( R.id.main_menu_separator );
            }
        }
    }

    public class MainMenu {

        private int icon;
        private int type;
        private int hint;
        private CharSequence name;
        private List< MainMenu > childMenu;
        /**
         * 只有文字
         */
        private static final int NO_ICON = 0x0;
        private static final int N0_HINT = 0x1;

        /**
         * 有图标、有文字，没有提示
         */
        public static final int TYPE_NORMAL = 0;
        /**
         * 只有文字
         */
        public static final int TYPE_NO_ICON = 1;
        /**
         * 只有分割线
         */
        public static final int TYPE_SEPARATOR = 2;
        /**
         * 有图标、有文字、有提示
         */
        public static final int TYPE_HINT = 3;

        public MainMenu () {

            this ( null, null );
        }

        public MainMenu ( CharSequence name ) {

            this ( name, null );
        }

        public MainMenu ( CharSequence name, List< MainMenu > childMenu ) {

            this ( name, NO_ICON, childMenu );
        }

        public MainMenu ( CharSequence name, int icon ) {

            this ( name, icon, N0_HINT, null );
        }

        public MainMenu ( CharSequence name, int icon, List< MainMenu > childMenu ) {

            this ( name, icon, N0_HINT, childMenu );
        }

        public MainMenu ( CharSequence name, int icon, int hint, List< MainMenu > childMenu ) {

            if ( childMenu == null ) {
                childMenu = new ArrayList<> ();
            }
            this.childMenu = childMenu;
            this.name = name;
            this.icon = icon;
            this.hint = hint;
            if ( icon == NO_ICON && TextUtils.isEmpty ( name ) ) {
                type = TYPE_SEPARATOR;
            } else if ( icon == NO_ICON ) {
                type = TYPE_NO_ICON;
            } else if ( hint == N0_HINT ) {
                type = TYPE_NORMAL;
            } else {
                type = TYPE_HINT;
            }

            if ( type != TYPE_SEPARATOR && TextUtils.isEmpty ( name ) ) {
                throw new IllegalArgumentException (
                        "You need set a name for a non-separator item" );
            }

        }

        public int getIcon () {

            return icon;
        }

        public int getType () {

            return type;
        }

        public CharSequence getName () {

            return name;
        }

        public int getHint () {

            return hint;
        }

        public List< MainMenu > getChildMenu () {

            return childMenu;
        }
    }

}
