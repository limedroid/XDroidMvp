package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.event.BusProvider;

public abstract class XPresentation<P extends IPresent>  extends Presentation implements IView<P>  {
    private P p;
    private VDelegate vDelegate;
    protected Context context;

    private RxPermissions rxPermissions;
    public XPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        this.context=outerContext;
    }
    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(getOwnerActivity());
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }
    public XPresentation(Context outerContext, Display display, int theme) {
        super(outerContext, display, theme);
        this.context=outerContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getP();

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindEvent();
        }
        initData(savedInstanceState);
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
