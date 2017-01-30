package cn.droidlover.xdroidmvp.test.rxbus;

import cn.droidlover.xdroidmvp.event.IBus;

/**
 * Created by wanglei on 2017/1/30.
 */

public class LoginEvent implements IBus.IEvent {

    @Override
    public int getTag() {
        return 0;
    }
}
