package com.lennon.cn.utill.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import cn.droidlover.xdroidmvp.log.XLog;
import lennon.com.utill.R;

/**
 * 项目名称：android-pay
 * 类描述：
 * 创建人：LeoPoldCrossing
 * 创建时间：16/10/28
 */

@SuppressLint("AppCompatCustomView")
public class ClearWithSpaceEditText extends EditText {

    private int contentType;
    public static final int TYPE_PHONE = 0;
    public static final int TYPE_CARD = 1;
    public static final int TYPE_IDCARD = 2;
    public static final int TYPE_MANAGER = 3;
    private int maxLength = 100;
    private int start, count, before;
    private String digits;

    public ClearWithSpaceEditText(Context context) {
        this(context, null);
    }

    public ClearWithSpaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
    }

    public ClearWithSpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.clear_edit_attrs, 0, 0);
        contentType = ta.getInt(R.styleable.clear_edit_attrs_input_type, 0);
        ta.recycle();
        initType();
        setSingleLine();
        addTextChangedListener(watcher);
    }

    private void initType() {
        if (contentType == TYPE_PHONE) {
            maxLength = 13;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (contentType == TYPE_CARD) {
            maxLength = 31;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (contentType == TYPE_IDCARD) {
            maxLength = 21;
            digits = null;
            setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (contentType == TYPE_MANAGER) {
            maxLength = 16;
            digits = "YCyc0123456789 ";
            setInputType(InputType.TYPE_CLASS_TEXT);
        }
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    @Override
    public void setInputType(int type) {
        if (contentType == TYPE_PHONE || contentType == TYPE_CARD) {
            type = InputType.TYPE_CLASS_NUMBER;
        } else if (contentType == TYPE_IDCARD || contentType == TYPE_MANAGER) {
            type = InputType.TYPE_CLASS_TEXT;
        }
        super.setInputType(type);
        /* 非常重要:setKeyListener要在setInputType后面调用，否则无效。*/
        if (!TextUtils.isEmpty(digits)) {
            final int finalType = type;
            setKeyListener(new DigitsKeyListener() {
                @Override
                public int getInputType() {
                    return finalType;
                }

                @Override
                protected char[] getAcceptedChars() {
                    char[] data = digits.toCharArray();
                    return data;
                }
            });
        }
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
        initType();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ClearWithSpaceEditText.this.start = start;
            ClearWithSpaceEditText.this.before = before;
            ClearWithSpaceEditText.this.count = count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }
            //判断是否是在中间输入，需要重新计算
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格
            boolean isNeedSpace = false;
            if (!isMiddle && isSpace(s.length())) {
                isNeedSpace = true;
            }
            boolean isNeedDelYC = false;
            boolean yc = s.toString().toUpperCase().startsWith("YC");
            boolean y = s.toString().toUpperCase().startsWith("Y");
            if (yc) {
                if (s.toString().indexOf("Y", 2) > 0 || s.toString().indexOf("C", 2) > 0) {
                    isNeedDelYC = true;
                }
            } else {
                String s1 = s.toString().toUpperCase();
                if (y) {
                    s1 = s.toString().subSequence(1, s.length()).toString();
                }
                if (s1.contains("Y") || s1.contains("C")) {
                    isNeedDelYC = true;
                }
            }
            if (isMiddle || isNeedSpace || count > 1 || isNeedDelYC) {
                String a = s.toString().toUpperCase();
                if (isNeedDelYC) {
                    String replace = a.replace("Y", "");
                    replace = replace.replace("C", "");
                    if (yc) {
                        a = "YC" + replace;
                    } else {
                        if (y) {
                            a = "Y" + replace;
                        } else {
                            a = replace;
                        }
                    }
                }
                String newStr = a;
                newStr = newStr.replace(" ", "");
                StringBuilder sb = new StringBuilder();
                int spaceCount = 0;
                for (int i = 0; i < newStr.length(); i++) {
                    sb.append(newStr.substring(i, i + 1));
                    //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
                    XLog.e(sb.toString());
                    if (isSpace(i + 2 + spaceCount)) {
                        sb.append(" ");
                        XLog.e(sb.toString());
                        spaceCount += 1;
                    }
                }
                removeTextChangedListener(watcher);
                /* 该处原来是调用setText(sb)。
                 * 但是如果调用该语句的话，在身份证输入框的情况下（允许字母和数字），当调用setText时，会导致输入法的跳转
                 * 参照网上解决方法，将该句话替换成s.replace(...)
                 * 该种方法不会导致输入法的跳转。
                 * 造成输入法跳转的原因可能是setText会重新唤起输入法控件*/
                s.replace(0, s.length(), sb);
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if (!isMiddle || count > 1) {
                    setSelection(Math.min(s.length(), maxLength));
                } else if (isMiddle) {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if (isSpace(start - before + 1)) {
                            setSelection(Math.max((start - before), 0));
                        } else {
                            setSelection(Math.min((start - before + 1), s.length()));
                        }
                    }
                    //如果是增加
                    else {
                        if (isSpace(start - before + count)) {
                            setSelection(Math.min((start + count - before + 1), s.length()));
                        } else {
                            setSelection(start + count - before);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
        }
    };

    /**
     * 获取无空格的字符串
     *
     * @return
     */
    public String getNoSpaceText() {
        String newStr = this.getText().toString().toUpperCase();
        if (!TextUtils.isEmpty(newStr)) {
            String strNOSpace = newStr.replace(" ", "");
            if (!TextUtils.isEmpty(strNOSpace)) {
                strNOSpace = strNOSpace.replace("　", "");
            }
            return strNOSpace;
        }
        return "";
    }

    public boolean checkTextRight() {
        String text = getNoSpaceText();
        //这里做个简单的内容判断
        if (contentType == TYPE_PHONE) {
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(getContext(), "手机号不能为空，请输入正确的手机号", Toast.LENGTH_SHORT);
            } else if (text.length() < 11) {
                Toast.makeText(getContext(), "手机号不足11位，请输入正确的手机号", Toast.LENGTH_SHORT);
            } else {
                return true;
            }
        } else if (contentType == TYPE_CARD) {
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(getContext(), "银行卡号不能为空，请输入正确的银行卡号", Toast.LENGTH_SHORT);
            } else if (text.length() < 14) {
                Toast.makeText(getContext(), "银行卡号位数不正确，请输入正确的银行卡号", Toast.LENGTH_SHORT);
            } else {
                return true;
            }
        } else if (contentType == TYPE_IDCARD) {
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(getContext(), "身份证号不能为空，请输入正确的身份证号", Toast.LENGTH_SHORT);
            } else if (text.length() < 18) {
                Toast.makeText(getContext(), "身份证号不正确，请输入正确的身份证号", Toast.LENGTH_SHORT);
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isSpace(int length) {
        if (contentType == TYPE_PHONE) {
            return isSpacePhone(length);
        } else if (contentType == TYPE_CARD) {
            return isSpaceCard(length);
        } else if (contentType == TYPE_IDCARD) {
            return isSpaceIDCard(length);
        } else if (contentType == TYPE_MANAGER) {
            return isSpaceManager(length);
        }
        return false;
    }

    private boolean isSpaceManager(int length) {
        XLog.e("-----------" + length + "---------" + getText());
        if (getNoSpaceText().startsWith("YC")) {
            return length == 3 || length == 7 || (length > 7 && length < 16 && (length - 2) % 5 == 0);
        } else {
            return length >= 4 && (length == 4 || (length + 1) % 5 == 0) && length < 13;
        }
    }

    private boolean isSpacePhone(int length) {
        return length >= 4 && (length == 4 || (length + 1) % 5 == 0);
    }

    private boolean isSpaceCard(int length) {
        return length % 5 == 0;
    }

    private boolean isSpaceIDCard(int length) {
        return length > 6 && (length == 7 || (length - 2) % 5 == 0);
    }

}