/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain activity copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.quick.library.widgets;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import org.quick.library.widgets.scaleimg._IScaleView;
import org.quick.library.widgets.scaleimg._ScaleViewAttacher;

/**
 *缩放图片控件
 */
public class ImageViewScale extends android.support.v7.widget.AppCompatImageView implements _IScaleView {

	private final _ScaleViewAttacher mAttacher;

	private ScaleType mPendingScaleType;

	public ImageViewScale(Context context) {
		this(context, null);
		setZoomable(false);
	}

	public ImageViewScale(Context context, AttributeSet attr) {
		this(context, attr, 0);
	}

	public ImageViewScale(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		super.setScaleType(ScaleType.MATRIX);
		mAttacher = new _ScaleViewAttacher(this);

		if (null != mPendingScaleType) {
			setScaleType(mPendingScaleType);
			mPendingScaleType = null;
		}
	}

	public void setOnClickListener(OnClickListener listener) {
		mAttacher.setOnClickLinstener(listener);
	}

	@Override
	public boolean canZoom() {
		return mAttacher.canZoom();
	}

	@Override
	public RectF getDisplayRect() {
		return mAttacher.getDisplayRect();
	}

	@Override
	public float getMinScale() {
		return mAttacher.getMinScale();
	}

	@Override
	public float getMidScale() {
		return mAttacher.getMidScale();
	}

	@Override
	public float getMaxScale() {
		return mAttacher.getMaxScale();
	}

	@Override
	public float getScale() {
		return mAttacher.getScale();
	}

	@Override
	public ScaleType getScaleType() {
		return mAttacher.getScaleType();
	}

	@Override
	public void setAllowParentInterceptOnEdge(boolean allow) {
		mAttacher.setAllowParentInterceptOnEdge(allow);
	}

	@Override
	public void setMinScale(float minScale) {
		mAttacher.setMinScale(minScale);
	}

	@Override
	public void setMidScale(float midScale) {
		mAttacher.setMidScale(midScale);
	}

	@Override
	public void setMaxScale(float maxScale) {
		mAttacher.setMaxScale(maxScale);
	}

	@Override
	// setImageBitmap calls through to this method
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setOnMatrixChangeListener(_ScaleViewAttacher.OnMatrixChangedListener listener) {
		mAttacher.setOnMatrixChangeListener(listener);
	}

	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		mAttacher.setOnLongClickListener(l);
	}

	@Override
	public void setOnScaleTapListener(_ScaleViewAttacher.OnScaleTapListener listener) {
		mAttacher.setOnScaleTapListener(listener);
	}

	@Override
	public void setOnViewTapListener(_ScaleViewAttacher.OnViewTapListener listener) {
		mAttacher.setOnViewTapListener(listener);
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (null != mAttacher) {
			mAttacher.setScaleType(scaleType);
		} else {
			mPendingScaleType = scaleType;
		}
	}

	@Override
	public void setZoomable(boolean zoomable) {
		mAttacher.setZoomable(zoomable);
	}

	@Override
	public void zoomTo(float scale, float focalX, float focalY) {
		mAttacher.zoomTo(scale, focalX, focalY);
	}

	@Override
	protected void onDetachedFromWindow() {
		mAttacher.cleanup();
		super.onDetachedFromWindow();
	}

}