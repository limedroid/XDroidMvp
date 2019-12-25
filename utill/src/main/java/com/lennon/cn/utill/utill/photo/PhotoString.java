package com.lennon.cn.utill.utill.photo;

import com.lennon.cn.utill.bean.StringBean;

public class PhotoString implements StringBean {
    private String name;

    @Override
    public String getItemString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
