package cn.droidlover.xdroidmvp.mvp;

import android.view.View;

/**
 * Created by wanglei on 2016/12/22.
 */

public interface IView<P extends IPresent> {

    void bindUI(View rootView);

    void bindEvent();

    int getLayoutId();

    int getOptionsMenuId();

    View getRootView();

    void attachP(P present);

    void detach();

}
