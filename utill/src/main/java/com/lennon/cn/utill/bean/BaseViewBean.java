package com.lennon.cn.utill.bean;

import android.widget.ImageView;
import android.widget.TextView;

public interface BaseViewBean {
    public void onClick();

    public void onLongClick();

    public void loadIcon(ImageView icon);

    public void loadName(TextView name);
}
