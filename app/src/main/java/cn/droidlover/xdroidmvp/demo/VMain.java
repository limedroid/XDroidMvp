package cn.droidlover.xdroidmvp.demo;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XView;

/**
 * Created by wanglei on 2016/12/22.
 */

public class VMain extends XView<MainActivity> {

    @BindView(R.id.tv_showMvp)
    TextView tvShowMvp;


    @Override
    public void bindEvent() {
        tvShowMvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getP().test();      //v 调用 p的方法
            }
        });
    }


    public void test() {
        getvDelegate().toastShort("p 调用 v的方法");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
