package cn.droidlover.xdroidmvp.event;


import com.blankj.rxbus.RxBus;

/**
 * Created by wanglei on 2016/12/22.
 */

public class RxBusImpl implements IBus {

    private RxBusImpl() {
    }


    @Override
    public void register(Object object) {
    }

    @Override
    public void unregister(Object object) {
        RxBus.getDefault().unregister(object);
    }

    @Override
    public void post(AbsEvent event) {
        RxBus.getDefault().post(event);
    }

    @Override
    public void postSticky(AbsEvent event) {
        RxBus.getDefault().postSticky(event);
    }

    public <T extends AbsEvent> void subscribe(Object subscriber,
                                               RxBus.Callback<T> callback) {
        RxBus.getDefault().subscribe(subscriber, callback);
    }

    public <T extends AbsEvent> void subscribeSticky(Object subscriber,
                                                     RxBus.Callback<T> callback) {
        RxBus.getDefault().subscribeSticky(subscriber, callback);
    }

    public static RxBusImpl get() {
        return Holder.instance;
    }

    private static class Holder {
        private static final RxBusImpl instance = new RxBusImpl();
    }
}

