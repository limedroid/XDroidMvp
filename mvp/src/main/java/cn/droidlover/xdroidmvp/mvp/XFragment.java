package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.droidlover.xdroidmvp.event.BusProvider;

/**
 * Created by wanglei on 2016/12/22.
 */

public abstract class XFragment<V extends IView> extends Fragment implements IPresent<V> {

    private PDelegate pDelegate;
    private V v;
    private Activity context;
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            if (getV().getLayoutId() > 0) {
                rootView = inflater.inflate(getV().getLayoutId(), null);
                getV().bindUI(rootView);
            }

        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus()) {
            BusProvider.getBus().register(this);
        }
        getV().bindEvent();
        initData(savedInstanceState);
    }

    protected PDelegate getpDelegate() {
        if (pDelegate == null) {
            pDelegate = PDelegateBase.create(getRootContext());
        }
        return pDelegate;
    }

    protected V getV() {
        if (v == null) {
            v = newV();
            v.attachP(this);
        }
        return v;
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
    public void onDestroyView() {
        super.onDestroyView();
        v = null;
        if (useEventBus()) {
            BusProvider.getBus().unregister(this);
        }
        getpDelegate().destory();
        pDelegate = null;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public Context getRootContext() {
        return context;
    }
}
