package cn.droidlover.xdroidmvp.event;

/**
 * Created by wanglei on 2016/12/22.
 */

public class BusProvider {

    private static IBus bus;

    public static IBus getBus() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                if (bus == null) {
                    bus = new RxBusImpl();
                }
            }
        }
        return bus;
    }

}
