package cn.droidlover.xdroidmvp.event;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by wanglei on 2016/12/22.
 */

public class RxBusImpl implements IBus {

    private FlowableProcessor<Object> bus = null;

    private RxBusImpl() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBusImpl get() {
        return Holder.instance;
    }

    @Override
    public void register(Object object) {

    }

    @Override
    public void unregister(Object object) {

    }

    @Override
    public void post(IEvent event) {
        bus.onNext(event);
    }

    @Override
    public void postSticky(IEvent event) {

    }

    public <T extends IEvent> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType).onBackpressureBuffer();
    }

    private static class Holder {
        private static final RxBusImpl instance = new RxBusImpl();
    }
}
