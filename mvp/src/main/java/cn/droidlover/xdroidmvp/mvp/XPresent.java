package cn.droidlover.xdroidmvp.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by wanglei on 2016/12/29.
 */

public class XPresent<V extends IView> implements IPresent<V> {
    private WeakReference<V> v;

    @Override
    public void attachV(V view) {
        v = new WeakReference<V>(view);
    }

    @Override
    public void detachV() {
        if (v != null) {
            v.clear();
            v = null;
        }
    }

    protected V getV() {
        return v == null ? null : v.get();
    }


    @Override
    public boolean hasV() {
        return v != null && v.get() != null;
    }
}
