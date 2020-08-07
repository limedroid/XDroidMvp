package com.lennon.cn.utill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Loading;

import lennon.com.utill.R;


/**
 * @author luoying
 */
public class CustomProgressDialog extends Dialog {

    private Context mContext;
    private Loading loading;

    public CustomProgressDialog(Context context) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        setParams();
        applyCompat();
    }

    public CustomProgressDialog(Context context, String title, String msg) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        setParams();
        setTitile(title);
        setMessage(msg);
        applyCompat();
    }

    public CustomProgressDialog(Context context, int msg_resid) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        setParams();
        setMessage(msg_resid);
        applyCompat();
    }

    public CustomProgressDialog(Context context, int title_resid, int msg_resid) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        setParams();
        setTitile(title_resid);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        loading = (Loading) findViewById(R.id.progressdialog_anim_progress);
        loading.start();
    }

    /**
     * setTitile 标题
     *
     * @param strTitle
     * @return
     */
    public void setTitile(String strTitle) {

    }

    /**
     * setTitile 标题
     *
     * @param title_resid
     * @return
     */
    public void setTitile(int title_resid) {

    }

    /**
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
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
     * @return
     */
    public void setMessage(int resid) {
        TextView tvMsg = (TextView) findViewById(R.id.progressdialog_ms_tv);
        if (tvMsg != null) {
            tvMsg.setText(resid);
        }
    }

    private void setParams() {
        setContentView(R.layout.customprogressdialog);
        // 设置全屏
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

}
