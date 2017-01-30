package cn.droidlover.xdroidmvp.test.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * Created by wanglei on 2017/1/30.
 */

public class StartActivity extends XActivity {

    @Override
    public void initData(Bundle savedInstanceState) {
        Router.newIntent(context)
                .to(EndActivity.class)
                .putString("arg_name", "xdroid")
                .launch();

        Router.newIntent(context)
                .to(EndActivity.class)
                .putString("arg_name", "xdroid")
                .requestCode(100)
                .launch();

        int exitAnim = 0, enterAnim = 0;
        Router.newIntent(context)
                .to(EndActivity.class)
                .putString("arg_name", "xdroid")
                .anim(enterAnim, exitAnim)
                .launch();

        Router.newIntent(context)
                .to(EndActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .launch();

        Router.newIntent(context)
                .to(EndActivity.class)
                .options(ActivityOptionsCompat.makeBasic())
                .launch();

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
