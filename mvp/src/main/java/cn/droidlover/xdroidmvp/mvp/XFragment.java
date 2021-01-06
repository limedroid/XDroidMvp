package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.event.BusProvider;

import com.trello.rxlifecycle3.components.support.RxFragment;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wanglei on 2016/12/29.
 */

public abstract class XFragment<P extends IPresent, E extends ViewBinding> extends RxFragment implements IView<P, E> {

    private VDelegate vDelegate;
    private P p;
    protected Activity context;
    protected LayoutInflater layoutInflater;

    private RxPermissions rxPermissions;
    private E viewBinding;

    @Override
    public void onRefresh(Boolean bRefresh) {

    }

    @Override
    public E getViewBinding() {
        return viewBinding;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        try {
            Class<E> eClass = getViewBindingClass();
            Method method2 = eClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.class);
            viewBinding = (E) method2.invoke(null, inflater, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (viewBinding != null) {
            ViewGroup viewGroup = (ViewGroup) viewBinding.getRoot().getParent();
            if (viewGroup != null) {
                viewGroup.removeView(viewBinding.getRoot());
            }
        }
        return viewBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getP();

        if (useEventBus()) {
            BusProvider.getBus().register(this);
        }
        bindEvent();
        initData(savedInstanceState);
    }

    protected VDelegate getvDelegate() {
        if (vDelegate == null) {
            vDelegate = VDelegateBase.create(context);
        }
        return vDelegate;
    }

    protected P getP() {
        if (p == null) {
            p = newP();
        }
        if (p != null) {
            if (!p.hasV()) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (useEventBus()) {
            BusProvider.getBus().unregister(this);
        }
        if (getP() != null) {
            getP().detachV();
        }
        getvDelegate().destory();

        p = null;
        vDelegate = null;
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public void bindEvent() {

    }
}
