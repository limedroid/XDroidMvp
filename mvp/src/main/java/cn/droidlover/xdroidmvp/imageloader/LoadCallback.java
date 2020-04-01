package cn.droidlover.xdroidmvp.imageloader;

import android.graphics.drawable.Drawable;

/**
 * Created by wanglei on 2016/12/21.
 */

public abstract class LoadCallback {
    public void onLoadFailed() {
    }

    public abstract void onLoadReady(Drawable drawable);

    public void onLoadCanceled() {
    }
}
