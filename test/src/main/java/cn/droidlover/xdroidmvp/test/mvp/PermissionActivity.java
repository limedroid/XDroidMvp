package cn.droidlover.xdroidmvp.test.mvp;

import android.Manifest;
import android.os.Bundle;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import io.reactivex.functions.Consumer;

/**
 * Created by wanglei on 2017/1/30.
 */

public class PermissionActivity extends XActivity {
    @Override
    public void initData(Bundle savedInstanceState) {
        getRxPermissions()
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
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
