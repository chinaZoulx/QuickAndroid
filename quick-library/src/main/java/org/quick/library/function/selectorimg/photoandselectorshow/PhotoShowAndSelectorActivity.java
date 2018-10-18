package org.quick.library.function.selectorimg.photoandselectorshow;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.kevin.crop.UCrop;

import org.quick.library.R;
import org.quick.library.b.activities.ThemeActivity;
import org.quick.library.function.SelectorImgActivity;
import org.quick.library.function.selectorimg.UCropActivity;
import org.quick.library.m.SystemBarTintManager;
import org.quick.component.Log2;
import org.quick.component.utils.DevicesUtils;
import org.quick.component.utils.HttpUtils;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by work on 2017/6/30.
 * 查看与选择——本地图片或者网络图片
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class PhotoShowAndSelectorActivity extends ThemeActivity implements PhotoShowAndSelectorAdapter.OnItemClickListener {

    public static final String TOTAL_LIST = "totalList";
    public static final int REQUEST_CODE = 0x12;
    public static final long AnimationTime = 300l;

    ViewPager viewPager;
    PhotoShowAndSelectorAdapter adapter;
    TextView countHintTv;
    View countHintContainer;
    TextView confirmTv;
    View actionContainer;

    private ArrayList<String> totalList;/*总数据*/
    private ArrayList<String> alreadySelectorPaths;/*已选择的图片路径*/
    private int selectPosition;
    private int maxSelectCount;/*最大选中*/
    private boolean isSingleSelect;/*是否单选*/
    private boolean isCrop = false;
    private boolean isActionHide = false;/*标题和Action是否隐藏*/
    private boolean isLoadNetworkImg = false;/*是否加载网络图片*/
    private boolean isAnimatingShow = false;/*是否正在显示*/
    private boolean isAnimatingHide = false;/*是否正在隐藏*/
    private MenuItem menuItem;

    /**
     * 网络图片
     *
     * @param context
     * @param view    转场动画需要，可为空
     * @param imgList
     */
    public static void startAction(@NonNull Activity context, View view, @NonNull String... imgList) {
        startAction(context, "", view, Arrays.asList(imgList), null, 1, 0, false);
    }

    /**
     * 网络图片
     *
     * @param context
     * @param view
     * @param imgList
     * @param selectPosition 选中的位置
     */
    public static void startAction(@NonNull Activity context, View view, int selectPosition, @NonNull String... imgList) {
        startAction(context, "", view, Arrays.asList(imgList), null, 1, selectPosition, false);
    }

    /**
     * 网络图片
     *
     * @param context
     * @param view
     * @param totalList
     */
    public static void startAction(@NonNull Activity context, View view, @NonNull List<String> totalList) {
        startAction(context, "", view, totalList, null, 1, 0, false);
    }

    /**
     * 网络图片
     *
     * @param context
     * @param view
     * @param totalList
     * @param selectPosition 选中的位置
     */
    public static void startAction(@NonNull Activity context, View view, @NonNull List<String> totalList, int selectPosition) {
        startAction(context, "", view, totalList, null, 1, selectPosition, false);
    }

    /**
     * 本地图片
     *
     * @param context
     * @param title
     * @param view
     * @param totalList           总图片
     * @param alreadySelectorList 已经选中的图片
     * @param maxCount            选中的最大值
     */
    public static void startAction(@NonNull Activity context, String title, View view, @NonNull List<String> totalList, List<String> alreadySelectorList, int maxCount) {
        startAction(context, title, view, totalList, alreadySelectorList, maxCount, 0, false);
    }

    /**
     * 本地图片
     *
     * @param context
     * @param title
     * @param view
     * @param totalList           总图片
     * @param alreadySelectorList 已经选中的图片
     * @param maxCount            选中的最大值
     * @param selectPosition      选中的位置
     */
    public static void startAction(@NonNull Activity context, String title, View view, @NonNull List<String> totalList, List<String> alreadySelectorList, int maxCount, int selectPosition) {
        startAction(context, title, view, totalList, alreadySelectorList, maxCount, selectPosition, false);
    }

    /**
     * 需要裁剪，只能选择一张
     *
     * @param context
     * @param title
     * @param view
     * @param totalList
     * @param isCorp
     */
    public static void startAction(@NonNull Activity context, String title, View view, @NonNull List<String> totalList, List<String> alreadySelectorList, boolean isCorp) {
        startAction(context, title, view, totalList, alreadySelectorList, 1, 0, isCorp);
    }

    /**
     * @param activity
     * @param title
     * @param view           转场动画不需要，可为空
     * @param maxCount       最多选择
     * @param totalList      总数据
     * @param selectPosition 选择的图片位置
     * @param isCorp         是否裁剪
     */
    public static void startAction(@NonNull Activity activity, String title, View view, @NonNull List<String> totalList, List<String> alreadySelectorList, int maxCount, int selectPosition, boolean isCorp) {
        //让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = null;
        if (view != null) {
            Pair<View, String> imagePair = Pair.create(view, activity.getString(R.string.cutToAlbum));
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imagePair);
//            options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        }

        Intent intent = new Intent(activity, PhotoShowAndSelectorActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra("selectPosition", selectPosition);
        intent.putExtra("isCrop", isCorp);
        intent.putExtra("maxCount", maxCount);
        intent.putExtra(SelectorImgActivity.ALREADY_PATHS, (Serializable) alreadySelectorList);
        intent.putExtra(TOTAL_LIST, (Serializable) totalList);

        if (options != null)
            ActivityCompat.startActivityForResult(activity, intent, REQUEST_CODE, options.toBundle());
        else
            activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public int onResultLayoutResId() {
        return R.layout.activity_photo_show_and_selector;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isLoadNetworkImg) {
            getMenuInflater().inflate(R.menu.menu_photo_show, menu);
            menuItem = menu.findItem(R.id.menu_select);
        }
        onBindData();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean isUsingBaseLayout() {
        return false;
    }

    public Toolbar onResultToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void init() {
        new SystemBarTintManager(this).setStatusBarTintColor(Color.parseColor("#f2f2f2"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getAppBaseToolbar().setPadding(0, DevicesUtils.INSTANCE.getStatusHeight(this), 0, 0);
        }
        setBackValid(R.drawable.ic_arrow_back_black_24dp);
        alreadySelectorPaths = getIntent().getStringArrayListExtra(SelectorImgActivity.ALREADY_PATHS);
        if (alreadySelectorPaths == null) {
            alreadySelectorPaths = new ArrayList<>();
        }
        totalList = getIntent().getStringArrayListExtra(TOTAL_LIST);
        selectPosition = getIntent().getIntExtra("selectPosition", 0);
        isCrop = getIntent().getBooleanExtra("isCrop", false);
        maxSelectCount = getIntent().getIntExtra("maxCount", 0);
        if (maxSelectCount <= 1) {
            isSingleSelect = true;
        } else {
            isSingleSelect = false;
        }
        if (totalList != null && totalList.size() > 0 && HttpUtils.INSTANCE.isHttpUrlFormRight(totalList.get(0))) {
            isLoadNetworkImg = true;
        } else {
            isLoadNetworkImg = false;
        }
        initView();
    }

    public void initView() {
        countHintContainer = findViewById(R.id.countHintContainer);
        countHintTv = (TextView) findViewById(R.id.countHintTv);
        actionContainer = findViewById(R.id.actionContainer);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        confirmTv = (TextView) findViewById(R.id.confirmTv);
        getAppBaseToolbar().setTitleTextColor(Color.BLACK);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isLoadNetworkImg) {
                    setCountHint(position + 1);
                }
                if (menuItem != null && alreadySelectorPaths.size() > 0) {
                    if (alreadySelectorPaths.contains(totalList.get(position))) {
                        menuItem.setIcon(R.drawable.ic_check_circle_focus_blue_24dp);
                        menuItem.setChecked(true);
                    } else {
                        menuItem.setIcon(R.drawable.ic_check_circle_normal_border_white_bg_black50_24dp);
                        menuItem.setChecked(false);
                    }
                    setSelectComplete();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (isLoadNetworkImg) {
            actionContainer.setVisibility(View.GONE);
        }
    }

    public void onBindData() {
        if (isLoadNetworkImg) {
            setCountHint(1);
            setTitle("");
            countHintContainer.setVisibility(View.VISIBLE);
            int paddingRight = getAppBaseToolbar().getContentInsetStartWithNavigation();
            countHintTv.setPadding(countHintTv.getPaddingLeft(), countHintTv.getPaddingTop(), paddingRight, countHintTv.getPaddingBottom());
        } else {
            countHintContainer.setVisibility(View.GONE);
            getAppBaseToolbar().setTitle(getIntent().getStringExtra(TITLE));
        }

        adapter = new PhotoShowAndSelectorAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(50);
        viewPager.setOffscreenPageLimit(5);
        adapter.setImgList(totalList);
        viewPager.setCurrentItem(selectPosition);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuItem == null) {
            menuItem = item;
        }
        if (!menuItem.isChecked()) {//当前选中
            checked(item);
        } else {//当前未选中
            checkNo(item);
        }
        menuItem.setChecked(!item.isChecked());
        setSelectComplete();
        return super.onOptionsItemSelected(item);
    }

    private void setCountHint(int currentItemPosition) {
        countHintTv.setText(currentItemPosition + "/" + totalList.size());
    }

    /**
     * 选中
     *
     * @param item
     */
    public void checked(MenuItem item) {
        if (isSingleSelect) {
            alreadySelectorPaths.clear();
            alreadySelectorPaths.add(totalList.get(viewPager.getCurrentItem()));
            item.setIcon(R.drawable.ic_check_circle_focus_blue_24dp);
        } else if (alreadySelectorPaths.size() < maxSelectCount) {
            alreadySelectorPaths.add(totalList.get(viewPager.getCurrentItem()));
            item.setIcon(R.drawable.ic_check_circle_focus_blue_24dp);
        } else {
            showToast(String.format("最多选择%d张图片", maxSelectCount), Gravity.CENTER);
        }
    }

    /**
     * 取消
     *
     * @param item
     */
    public void checkNo(MenuItem item) {
        if (isSingleSelect) {//单选
            alreadySelectorPaths.clear();
        } else {
            alreadySelectorPaths.remove(totalList.get(viewPager.getCurrentItem()));
        }
        item.setIcon(R.drawable.ic_check_circle_normal_border_white_bg_black50_24dp);
    }

    private void setSelectComplete() {
        String text;
        int selectCount = alreadySelectorPaths.size();
        if (maxSelectCount == 0 || selectCount <= 0) {
            text = getIntent().getStringExtra(TITLE);
            confirmTv.setEnabled(false);
            confirmTv.setTextColor(getResources().getColor(R.color.colorGray));
        } else {
            if (isSingleSelect) {
                text = String.format("已选择 %d 项", selectCount);
            } else {
                text = String.format("已选择( %d/%d )", selectCount, maxSelectCount);
            }
            confirmTv.setEnabled(true);
            confirmTv.setTextColor(getResources().getColor(R.color.colorBlueShallow));
        }
        setTitle(text);
    }

    public void hide() {
        isAnimatingHide = true;
        isAnimatingShow = false;
        final ObjectAnimator translation2Y = ObjectAnimator.ofFloat(getAppBaseToolbar(), View.TRANSLATION_Y, -100);
        final ObjectAnimator alpha2 = ObjectAnimator.ofFloat(getAppBaseToolbar(), "alpha", 1F, 0F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translation2Y, alpha2);
        animatorSet.setDuration(AnimationTime);
        animatorSet.setInterpolator(new OvershootInterpolator());
        if (!isLoadNetworkImg) {
            ObjectAnimator translationY = ObjectAnimator.ofFloat(actionContainer, View.TRANSLATION_Y, +50);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(actionContainer, "alpha", 1F, 0F);
            animatorSet.playTogether(translationY, alpha);
        }
        animatorSet.start();
        Observable.interval(1, TimeUnit.MILLISECONDS).take(AnimationTime).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Long>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long value) {
                if (isAnimatingShow) {
                    disposable.dispose();
                }
                if (value >= AnimationTime - 1) {
                    viewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getAppBaseToolbar().setVisibility(View.GONE);
                    actionContainer.setVisibility(View.GONE);
                }
                int rgbValue = (int) ((255f / AnimationTime) * value) + 1;//0~255
                int rgb = 255 - rgbValue;
                Log2.INSTANCE.e("hide", "rgb:" + rgb + " rgbValue:" + rgbValue + " value:" + value);
                viewPager.setBackgroundColor(Color.argb(rgbValue, rgb, rgb, rgb));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void show() {
        viewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        isAnimatingShow = true;
        isAnimatingHide = false;
        final long tempAnimationTime = AnimationTime;
        Observable.interval(1, TimeUnit.MILLISECONDS).take(tempAnimationTime).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Long>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long value) {
                if (isAnimatingHide) {
                    disposable.dispose();
                }
                if (value >= tempAnimationTime - 1) {
                    showAnim();
                }
                int rgbValue = (int) ((255f / tempAnimationTime) * value) + 1;
                int rgb = rgbValue;
                Log2.INSTANCE.e("show", "rgb:" + rgb + " rgbValue:" + rgbValue + " value:" + value);
                viewPager.setBackgroundColor(Color.argb(rgb, rgb, rgb, rgb));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void showAnim() {
        getAppBaseToolbar().setVisibility(View.VISIBLE);
        ObjectAnimator translation2Y = ObjectAnimator.ofFloat(getAppBaseToolbar(), View.TRANSLATION_Y, -0);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(getAppBaseToolbar(), "alpha", 0, 1F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translation2Y, alpha2);
        animatorSet.setDuration(AnimationTime);
        animatorSet.setInterpolator(new OvershootInterpolator());
        if (!isLoadNetworkImg) {
            actionContainer.setVisibility(View.VISIBLE);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(actionContainer, View.TRANSLATION_Y, -0);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(actionContainer, "alpha", 0F, 1F);
            animatorSet.playTogether(translationY, alpha);
        }
        animatorSet.start();
    }

    public void confirm(View view) {
        if (alreadySelectorPaths == null || alreadySelectorPaths.size() <= 0)
            return;
        if (isCrop) {
            Uri uri = Uri.fromFile(new File(alreadySelectorPaths.get(0)));
            startCropActivity(uri);
        } else {
            setResult();
            finish();
        }
    }

    public void cancel(View view) {
        finish();
    }

    private void startCropActivity(@NonNull Uri uri) {
        UCrop uCrop = UCrop.of(uri, SelectorImgActivity.mDestinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        uCrop.withOptions(options);
        uCrop.withTargetActivity(UCropActivity.class);
        uCrop.start(this);
    }

    @Override
    public void onItemClick(View v, int position) {
        if (isActionHide) {
            show();
        } else {
            hide();
        }
        isActionHide = !isActionHide;
    }

    public void setResult() {
        Intent intent = new Intent();
        intent.putExtra(SelectorImgActivity.ALREADY_PATHS, alreadySelectorPaths);
        intent.putExtra("from", PhotoShowAndSelectorActivity.class.getSimpleName());
        super.setResult(RESULT_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                data.putExtra("from", UCropActivity.class.getSimpleName());
                super.setResult(resultCode, data);
                finish();
            }
        }
    }
}
