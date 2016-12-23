package cn.droidlover.xdroidmvp.demo;

import android.os.Bundle;

import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by wanglei on 2016/12/22.
 */

public class MainActivity extends XActivity<VMain> {

    @Override
    public void initData(Bundle savedInstanceState) {
        getV().test();      //p调用v的方法
    }

    public void test() {
        getpDelegate().toastShort("v 调用 p");
    }

    @Override
    public VMain newV() {
        return new VMain();
    }
}
