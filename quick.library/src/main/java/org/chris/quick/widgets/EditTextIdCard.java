package org.chris.quick.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * Created by work on 2017/5/3.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class EditTextIdCard extends EditTextClear {

    private boolean shouldStopChange = false;
    private final String WHITE_SPACE = " ";

    private OnBankCardListener listener;

    public EditTextIdCard(Context context) {
        this(context, null);
    }

    public EditTextIdCard(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextIdCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        format(getText());
        shouldStopChange = false;
        setFocusable(true);
        setEnabled(true);
        setFocusableInTouchMode(true);
        addTextChangedListener(new CardTextWatcher());
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(21)});
    }

    class CardTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            format(editable);
        }
    }

    private void format(Editable editable) {
        if (shouldStopChange) {
            shouldStopChange = false;
            return;
        }

        shouldStopChange = true;

        String str = editable.toString().trim().replaceAll(WHITE_SPACE, "");
        int len = str.length();
        int courPos;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(str.charAt(i));
            if (i == 5 || i == 9 || i == 13) {
                if (i != len - 1)
                    builder.append(WHITE_SPACE);
            }
        }
        courPos = builder.length();
        setText(builder.toString());
        setSelection(courPos);
    }

    public String getTextIdCard() {
        return super.getText().toString().trim().replaceAll(WHITE_SPACE, "");
    }

    public void setOnBankCardListener(OnBankCardListener listener) {
        this.listener = listener;
    }

    public interface OnBankCardListener {
        void onSuccess(String bankName, String bankCardNumber);

        void onFailure(boolean isBankCard, String cardNumberStr);
    }
}
