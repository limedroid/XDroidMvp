package cn.droidlover.xdroidmvp.mvp;

import android.os.Bundle;
import android.view.View;

import androidx.viewbinding.ViewBinding;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IView<P extends IPresent, E extends ViewBinding> {

    void bindEvent();

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    E getViewBinding();

    boolean useEventBus();

    P newP();

    Class<E> getViewBindingClass();

    void showProgressDialog(String msg);

    void closeProgressDialog();

    void onRefresh(Boolean bRefresh);
}
