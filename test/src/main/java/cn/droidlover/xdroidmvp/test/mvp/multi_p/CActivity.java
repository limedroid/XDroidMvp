package cn.droidlover.xdroidmvp.test.mvp.multi_p;

import android.os.Bundle;

import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by wanglei on 2017/1/30.
 */

public class CActivity extends XActivity<PMulti> implements ICommonV {


    @Override
    public void initData(Bundle savedInstanceState) {
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public PMulti newP() {
        return new PMulti();
    }

    @Override
    public void showError(Exception e) {

    }
}
