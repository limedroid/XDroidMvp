package cn.droidlover.xdroidmvp.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import cn.droidlover.xdroidmvp.event.BusProvider;

/**
 * Created by wanglei on 2016/12/22.
 */

public abstract class XActivity<V extends IView> extends AppCompatActivity implements IPresent<V> {

    private PDelegate pDelegate;
    private V v;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        if (getV().getLayoutId() > 0) {
            setContentView(getV().getLayoutId());
            getV().bindUI(null);
            getV().bindEvent();
        }

        initData(savedInstanceState);

    }

    protected PDelegate getpDelegate() {
        if (pDelegate == null) {
            pDelegate = PDelegateBase.create(this);
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
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusProvider.getBus().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getpDelegate().resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getpDelegate().pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (useEventBus()) {
            BusProvider.getBus().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        v = null;
        getpDelegate().destory();
        pDelegate = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getV().getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getV().getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
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
