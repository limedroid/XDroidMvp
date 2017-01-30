package cn.droidlover.xdroidmvp.test.mvp.no_p;

import android.os.Bundle;

import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by wanglei on 2017/1/30.
 */

public class NoPActivity extends XActivity {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Object newP() {
        return null;
    }
}
