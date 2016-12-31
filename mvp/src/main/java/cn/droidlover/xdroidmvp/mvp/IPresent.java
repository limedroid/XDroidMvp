package cn.droidlover.xdroidmvp.mvp;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IPresent<V> {
    void attachV(V view);

    void detachV();
}
