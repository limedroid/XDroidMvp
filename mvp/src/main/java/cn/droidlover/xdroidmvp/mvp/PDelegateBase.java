package cn.droidlover.xdroidmvp.mvp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanglei on 2016/12/22.
 */

public class PDelegateBase implements PDelegate {

    private Context context;

    private PDelegateBase(Context context) {
        this.context = context;
    }

    public static PDelegateBase create(Context context) {
        return new PDelegateBase(context);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void toastShort(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastLong(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
