package cn.droidlover.xdroidmvp.test.mvp;

import android.Manifest;
import android.os.Bundle;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import rx.functions.Action1;

/**
 * Created by wanglei on 2017/1/30.
 */

public class PermissionActivity extends XActivity {
    @Override
    public void initData(Bundle savedInstanceState) {
        getRxPermissions()
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            //TODO 许可

                        } else {
                            //TODO 未许可

                        }
                    }
                });
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
