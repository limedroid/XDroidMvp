package cn.droidlover.xdroidmvp.mvp;

/**
 * Created by wanglei on 2016/12/22.
 */

public interface PDelegate {

    void resume();
    void pause();
    void destory();

    void toastShort(String msg);
    void toastLong(String msg);

}
