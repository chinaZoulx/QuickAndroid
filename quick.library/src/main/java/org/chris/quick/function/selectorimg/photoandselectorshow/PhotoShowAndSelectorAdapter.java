package org.chris.quick.function.selectorimg.photoandselectorshow;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.chris.quick.R;
import org.chris.quick.m.ImageManager;
import org.chris.quick.m.glide.progress.ProgressListener;
import org.chris.quick.m.glide.progress.ProgressModelLoader;
import org.chris.quick.widgets.ImageViewScale;
import org.chris.quick.widgets.ProgressWheel;
import org.chris.quick.widgets.scaleimg._ScaleViewAttacher;

import java.io.File;
import java.util.List;

/**
 * Created by work on 2017/6/30.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class PhotoShowAndSelectorAdapter extends PagerAdapter {

    public List<String> imgList;
    public OnItemClickListener onItemClickListener;

    @Override
    public int getCount() {
        return imgList != null ? imgList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final String filePath = imgList.get(position);
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_show, container, false);
        final ProgressWheel loadingAnimView = view.findViewById(R.id.loadingAnimView);
        final ImageViewScale imgScaleView = view.findViewById(R.id.imageScaleView);
        imgScaleView.setOnViewTapListener(new _ScaleViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
        if (filePath.contains("http")) {
            loadingAnimView.setVisibility(View.VISIBLE);
            Glide.with(container.getContext()).using(new ProgressModelLoader(new ProgressListener() {
                @Override
                public void progress(long bytesRead, long maxLength, boolean done) {
                    float progress = (float) bytesRead / maxLength;
                    loadingAnimView.setProgress((int) (progress * 360));
                    loadingAnimView.setText(((int) (progress * 100)) + "");
                }
            })).load(filePath).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imgScaleView.setVisibility(View.VISIBLE);
                    imgScaleView.setImageDrawable(resource);
                    loadingAnimView.setVisibility(View.GONE);
                }
            });
//            ImageManager.loadImage(container.getContext(),filePath,imgScaleView);
        } else {
            loadingAnimView.setVisibility(View.GONE);
            imgScaleView.setVisibility(View.VISIBLE);
//            Glide.with(container.getContext()).load(new File(filePath)).asBitmap().into(new SimpleTarget<Bitmap>(50, 50) {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    imgScaleView.setImageBitmap(resource);
//                    Glide.with(container.getContext()).load(new File(filePath)).asBitmap().placeholder(ImageManager.defaultEmpty).error(ImageManager.defaultError).into(imgScaleView);
//                }
//            });
            ImageManager.loadImage(container.getContext(),new File(filePath),imgScaleView);
        }

        container.addView(view);
        return view;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
