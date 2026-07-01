package cn.droidlover.xdroidmvp.demo.present;

import cn.droidlover.xdroidmvp.demo.model.GankResults;
import cn.droidlover.xdroidmvp.demo.net.Api;
import cn.droidlover.xdroidmvp.demo.ui.BasePagerFragment;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class PBasePager extends XPresent<BasePagerFragment> {
    protected static final int PAGE_SIZE = 10;


    public void loadData(String type, final int page) {
        if (!hasV()) {
            return;
        }
        Api.getGankService().getGankData(type, PAGE_SIZE, page)
                .compose(XApi.<GankResults>getApiTransformer())
                .compose(XApi.<GankResults>getScheduler())
                .compose(getV().<GankResults>bindToLifecycle())
                .subscribe(new ApiSubscriber<GankResults>() {
                    @Override
                    protected void onFail(NetError error) {
                        if (hasV()) {
                            getV().showError(error);
                        }
                    }

                    @Override
                    public void onNext(GankResults gankResults) {
                        if (hasV()) {
                            getV().showData(page, gankResults);
                        }
                    }
                });
    }
}
