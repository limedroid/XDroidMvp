package com.lennon.cn.utill.utill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;
import lennon.com.utill.R;

/**
 * Created by lennon on 2017/9/24.
 */

public class CountdownUtill extends CountDownTimer {
    private Activity mActivity;
    private TextView mBtn;
    private boolean mIsEnable = false;
    private Listener listener;
    private String startString = "", endString = "";

    public CountdownUtill(Activity activity, long millisInFuture, long countDownInterval, TextView button, Listener listener) {
        super(millisInFuture, countDownInterval);
        this.mActivity = activity;
        this.mBtn = button;
        this.mIsEnable = true;
        this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onFinish() {
        mBtn.setText("0" + mActivity.getResources().getString(R.string.resend_sms));
        if (listener != null) {
            listener.onFinish();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        if (mIsEnable)
            mBtn.setEnabled(false);
        mIsEnable = false;
        mBtn.setText(startString + (millisUntilFinished / 1000) + mActivity.getResources().getString(R.string.resend_sms) + endString);
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public interface Listener {
        void onFinish();
    }
}
