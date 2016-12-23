package cn.droidlover.xdroidmvp.mvp;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wanglei on 2016/12/22.
 */

public class VDelegateBase implements VDelegate {

    private Context context;

    private VDelegateBase(Context context) {
        this.context = context;
    }

    public static VDelegateBase create(Context context) {
        return new VDelegateBase(context);
    }

    @Override
    public void visible(boolean flag, View view) {
        if (flag)
            view.setVisibility(View.VISIBLE);
    }

    @Override
    public void gone(boolean flag, View view) {
        if (flag)
            view.setVisibility(View.GONE);
    }

    @Override
    public void inVisible(View view) {
        if (false)
            view.setVisibility(View.INVISIBLE);
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
