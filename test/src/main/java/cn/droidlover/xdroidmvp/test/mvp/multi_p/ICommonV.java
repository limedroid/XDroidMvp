package cn.droidlover.xdroidmvp.test.mvp.multi_p;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * Created by wanglei on 2017/1/30.
 */

public interface ICommonV extends IView<PMulti> {
    void showError(Exception e);
}
