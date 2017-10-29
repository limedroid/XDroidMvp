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
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    protected V getV() {
        if (v == null || v.get() == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v.get();
    }
}
