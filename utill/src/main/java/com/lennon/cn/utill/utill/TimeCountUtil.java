package com.lennon.cn.utill.utill;


import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.Button;
import lennon.com.utill.R;

/**
 * Created by Administrator on 2015/8/25.
 */
public class TimeCountUtil extends CountDownTimer {

    private Activity mActivity;
    private Button mBtn;
    private boolean mIsEnable = false;

    public TimeCountUtil(Activity activity, long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.mActivity = activity;
        this.mBtn = button;
        this.mIsEnable = true;
    }

    @Override
    public void onFinish() {
        mBtn.setText(mActivity.getResources().getString(R.string.reget_sms));
        mBtn.setEnabled(true);
        mBtn.setClickable(true);
        mBtn.setTextColor(mActivity.getResources().getColor(R.color.color_ffffff));
        mBtn.setBackground(mBtn.getResources().getDrawable(R.drawable.common_button));
        mIsEnable = true;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mIsEnable) {
            mBtn.setEnabled(false);
        }
        mIsEnable = false;
        mBtn.setText(millisUntilFinished / 1000 + mActivity.getResources().getString(R.string.resend_sms));
        mBtn.setTextColor(mActivity.getResources().getColor(R.color.color_333333));
        mBtn.setBackground(mBtn.getResources().getDrawable(R.drawable.common_button_fffff));
    }
}
