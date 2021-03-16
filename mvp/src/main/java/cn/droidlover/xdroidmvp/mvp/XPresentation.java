package cn.droidlover.xdroidmvp.mvp;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;

import androidx.viewbinding.ViewBinding;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.XDroidMvpUtill;
import cn.droidlover.xdroidmvp.event.BusProvider;

public abstract class XPresentation<P extends IPresent, E extends ViewBinding> extends Presentation implements IView<P> {
    private P p;
    private VDelegate vDelegate;
    protected Context context;
    private E viewBinding;
    private RxPermissions rxPermissions;

    public XPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        this.context = outerContext;
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(getOwnerActivity());
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

    public XPresentation(Context outerContext, Display display, int theme) {
        super(outerContext, display, theme);
        this.context = outerContext;
    }

    @Override
    public void onRefresh(Boolean bRefresh) {

    }

    protected final E getViewBinding() {
        return viewBinding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getP();
        try {
            Class<E> eClass = getViewBindingClass();
            Method method2 = eClass.getMethod("inflate", LayoutInflater.class);
            viewBinding = (E) method2.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (viewBinding != null) {
            setContentView(viewBinding.getRoot());
            bindEvent();
        }
        initData(savedInstanceState);
    }

    private Class<E> getViewBindingClass() {
        return XDroidMvpUtill.<E>getViewBindingClass(getClass());
    }

    protected VDelegate getvDelegate() {
        if (vDelegate == null) {
            vDelegate = VDelegateBase.create(context);
        }
        return vDelegate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusProvider.getBus().register(this);
        }
    }

    @Override
    public void onDisplayRemoved() {
        super.onDisplayRemoved();
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
}
