package org.chris.quick.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import org.chris.quick.tools.common.BankCardUtils;

/**
 * Created by work on 2017/5/3.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class EditTextBankCard extends EditTextClear {

    private boolean shouldStopChange = false;
    private final String WHITE_SPACE = " ";

    private OnBankCardListener listener;

    public EditTextBankCard(Context context) {
        this(context, null);
    }

    public EditTextBankCard(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextBankCard(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
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
            if (i == 3 || i == 7 || i == 11 || i == 15) {
                if (i != len - 1)
                    builder.append(WHITE_SPACE);
            }
        }
        courPos = builder.length();
        setText(builder.toString());
        setSelection(courPos);
        if (listener != null) {
            if (isBankCard()) {
                long content = Long.parseLong(getBankCardText().substring(0, 6));
                String cardNumberStr = BankCardUtils.getNameOfBank(getContext(), content);
                if (cardNumberStr.length() > 0) {
                    listener.onSuccess(cardNumberStr,getBankCardText());
                } else {
                    listener.onFailure(true,cardNumberStr);
                }
            } else {
                listener.onFailure(false,getBankCardText());
            }
        }
    }

    public String getBankCardText() {
        return getText().toString().trim().replaceAll(WHITE_SPACE, "");
    }

    public boolean isBankCard() {
        String cardStr = getBankCardText();
        if (TextUtils.isEmpty(cardStr)) {
            return false;
        }
        return checkBankCard(cardStr);
    }

    /**
     * 校验银行卡卡号
     */
    public boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     */
    public char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (TextUtils.isEmpty(nonCheckCodeCardId) || !nonCheckCodeCardId.matches("\\d+") || nonCheckCodeCardId.length() < 15) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public void setOnBankCardListener(OnBankCardListener listener) {
        this.listener = listener;
    }

    public interface OnBankCardListener {
        void onSuccess(String bankName, String bankCardNumber);

        void onFailure(boolean isBankCard, String cardNumberStr);
    }
}
