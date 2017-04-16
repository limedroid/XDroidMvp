package cn.droidlover.xdroidmvp.event;

/**
 * Created by wanglei on 2016/12/22.
 */

public class BusProvider {

    private static RxBusImpl bus;

    public static RxBusImpl getBus() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                if (bus == null) {
                    bus = RxBusImpl.get();
                }
            }
        }
        return bus;
    }

}
