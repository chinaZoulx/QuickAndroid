package org.quick.library.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import org.quick.component.utils.FormatUtils;
import org.quick.library.R;

import java.util.Objects;


public class EditTextClear extends androidx.appcompat.widget.AppCompatEditText implements
        OnFocusChangeListener, TextWatcher {
    private boolean isVisible = true;
    private Drawable mClearDrawable;

    public EditTextClear(Context context) {
        this(context, null);
    }

    public EditTextClear(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextClear(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setPadding(getPaddingLeft(), getPaddingTop(), (int) FormatUtils.INSTANCE.dip2px(15), getPaddingBottom());
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_gray_24dp);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public void setClearIcon(@DrawableRes int icon) {
        mClearDrawable = ContextCompat.getDrawable(getContext(), icon);
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(this.isVisible && getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    public String getTextStr() {
        return Objects.requireNonNull(super.getText()).toString().replaceAll(" ", "").replaceAll(",", "");
    }

    public void setVisibleRightIcon(boolean isVisible) {
        this.isVisible = isVisible;
        setClearIconVisible(isVisible);
    }

    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(this.isVisible && s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }


}
