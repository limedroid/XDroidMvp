package com.lennon.cn.utill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import lennon.com.utill.R;

public class CustomDialog extends Dialog {

    private Context mContext;

    public CustomDialog(Context context) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        setParams();
        applyCompat();
    }

    public CustomDialog(Context context, int msg_resid) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        setParams();
        setMessage(msg_resid);
        applyCompat();
    }

    private void applyCompat() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * setMessage 提示内容
     *
     * @param strMessage
     */
    public void setMessage(String strMessage) {
        TextView tvMsg = (TextView) findViewById(R.id.progressdialog_ms_tv);
        if (tvMsg != null) {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(strMessage);
        } else {
            tvMsg.setVisibility(View.GONE);
        }
    }

    /**
     * setMessage 提示内容
     *
     * @param resid
     */
    public void setMessage(int resid) {
        TextView tvMsg = (TextView) findViewById(R.id.progressdialog_ms_tv);
        if (tvMsg != null) {
            tvMsg.setText(resid);
        }
    }

    private void setParams() {
        setContentView(R.layout.custom_dialog);
        // 设置全屏
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

}
