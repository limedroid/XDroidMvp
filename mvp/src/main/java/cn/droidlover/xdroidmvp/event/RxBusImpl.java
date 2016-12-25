package cn.droidlover.xdroidmvp.event;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by wanglei on 2016/12/22.
 */

public class RxBusImpl implements IBus {

    private final Subject<IEvent, IEvent> bus = new SerializedSubject<>(PublishSubject.<IEvent>create());

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

    public Observable toObservable() {
        return bus;
    }
}
