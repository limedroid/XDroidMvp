package com.lennon.cn.utill.utill;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.*;
import android.widget.FrameLayout;

public class SystemBarTintManager {
    public static final int DEFAULT_TINT_COLOR = 0x99000000;
    private boolean mStatusBarAvailable;
    private boolean mStatusBarTintEnabled;
    private View mStatusBarTintView;

    @TargetApi(19)
    public SystemBarTintManager(Activity activity) {

        Window win = activity.getWindow();
        //获取DecorView
        ViewGroup decorViewGroup = (ViewGroup) win.getDecorView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 检查主题中是否有透明的状态栏
            int[] attrs = {android.R.attr.windowTranslucentStatus};
            TypedArray a = activity.obtainStyledAttributes(attrs);
            try {
                mStatusBarAvailable = a.getBoolean(0, false);
            } finally {
                a.recycle();
            }
            WindowManager.LayoutParams winParams = win.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;//状态栏透明
            if ((winParams.flags & bits) != 0) {
                mStatusBarAvailable = true;
            }
        }
        if (mStatusBarAvailable) {
            setupStatusBarView(activity, decorViewGroup);
        }
    }

    /**
     * 初始化状态栏
     *
     * @param context
     * @param decorViewGroup
     */
    private void setupStatusBarView(Activity context, ViewGroup decorViewGroup) {
        mStatusBarTintView = new View(context);
        //设置高度为Statusbar的高度
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(context));
        params.gravity = Gravity.TOP;
        mStatusBarTintView.setLayoutParams(params);
        mStatusBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
        //默认不显示
        mStatusBarTintView.setVisibility(View.GONE);
        //decorView添加状态栏高度的View
        decorViewGroup.addView(mStatusBarTintView);
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    private int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 显示状态栏
     */
    public void setStatusBarTintEnabled(boolean enabled) {
        mStatusBarTintEnabled = enabled;
        if (mStatusBarAvailable) {
            mStatusBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    public void setStatusBarTintColor(int color) {
        if (mStatusBarAvailable) {
            mStatusBarTintView.setBackgroundColor(color);
        }
    }
}
