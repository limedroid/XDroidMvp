package cn.droidlover.xrecyclerview;

/**
 * Created by wanglei on 2016/10/30.
 */

public interface LoadMoreUIHandler {
    void onLoading();

    void onLoadFinish(boolean hasMore);
}
