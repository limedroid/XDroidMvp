package com.lennon.cn.utill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import lennon.com.utill.R;

/**
 * Created by dingyi on 2016/11/23.
 */

public class CommonAlertDialog extends Dialog {

    private TextView mTitleTv;
    private TextView mMsgTv;
    private TextView mSureTv;
    private TextView mCancleTv;
    private EditText mQtyEdt;
    private EditText mContentEdt;
    private boolean cancelable = true;

    private OnAlertDialogListener mOnListener;
    private String mQtyString;
    private int mIndex;

    public CommonAlertDialog(Context context) {
        super(context, R.style.common_dialog);
        setParams();
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setTitle(String msg) {
        mTitleTv.setText(msg);
    }

    public void setMsg(String msg) {
        mMsgTv.setVisibility(View.VISIBLE);
        mQtyEdt.setVisibility(View.GONE);
        mContentEdt.setVisibility(View.GONE);
        mMsgTv.setText(msg);
    }

    public void setSureMsg(String msg) {
        mSureTv.setText(msg);
    }

    public void setCancleMsg(String msg) {
        mCancleTv.setText(msg);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        cancelable = flag;
    }

    public void setQty(String text) {
        mMsgTv.setVisibility(View.GONE);
        mContentEdt.setVisibility(View.GONE);
        mQtyEdt.setVisibility(View.VISIBLE);
        mQtyString = text;
        mQtyEdt.setText(text);
        mQtyEdt.setSelection(text.length());
    }

    public void setContent(String content) {
        mMsgTv.setVisibility(View.GONE);
        mQtyEdt.setVisibility(View.GONE);
        mContentEdt.setVisibility(View.VISIBLE);
        mContentEdt.setText(content);
        mContentEdt.setSelection(mContentEdt.getText().length());
    }

    public void setDialogListener(OnAlertDialogListener listener) {
        this.mOnListener = listener;
    }


    public void disableCancle() {
        mCancleTv.setVisibility(View.GONE);
    }

    private void setParams() {
        setContentView(R.layout.common_alert_dialog);
        Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mTitleTv = (TextView) findViewById(R.id.common_alert_dialog_title_tv);
        mMsgTv = (TextView) findViewById(R.id.common_alert_dialog_msg_tv);
        mSureTv = (TextView) findViewById(R.id.common_alert_dialog_sure_tv);
        mCancleTv = (TextView) findViewById(R.id.common_alert_dialog_cancle_tv);
        mQtyEdt = (EditText) findViewById(R.id.common_alert_dialog_qty_edt);
        mContentEdt = (EditText) findViewById(R.id.common_alert_dialog_content_edt);
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnListener) {
                    if (mMsgTv.getVisibility() == View.VISIBLE) {
                        mOnListener.onSure();
                    } else if (mContentEdt.getVisibility() == View.VISIBLE) {
                        mOnListener.onSure(mContentEdt.getText().toString());
                    } else {
                        mOnListener.onSure(mIndex, mQtyString);
                    }
                }
            }
        });
        mCancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnListener) {
                    mOnListener.onCancle();
                }
            }
        });

        mQtyEdt.addTextChangedListener(mWatcher);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    if (mOnListener != null) {
                        mOnListener.onCancle();
                        return true;
                    }
                    return false;
                } else {
                    return false; //默认返回 false
                }
            }
        });
    }

    private TextWatcher mWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && !"".equals(s.toString())) {
                mQtyString = s.toString();
            } else {
                mQtyString = "0";
            }
        }
    };
}
