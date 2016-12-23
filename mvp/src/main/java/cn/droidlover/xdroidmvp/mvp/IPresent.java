package cn.droidlover.xdroidmvp.mvp;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by wanglei on 2016/12/22.
 */

public interface IPresent<V>{

    boolean useEventBus();

    V newV();

    Context getRootContext();

    void initData(Bundle savedInstanceState);

}
