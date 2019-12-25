package com.lennon.cn.utill.cache;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lennon.cn.utill.base.BaseApplication;
import com.lennon.cn.utill.base.BaseView;
import com.lennon.cn.utill.bean.HttpEntity;
import com.lennon.cn.utill.conf.Lennon;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.IView;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Flowable;

/**
 * Created by lennon on 2017/10/18.
 */

public class DataCache<T extends HttpEntity> {
    BaseView activity;
    String dataKey;
    Flowable<T> flowable;
    LifecycleProvider lifecycleProvider;
    DataCallBack<T> dataCallBack;
    ACache maCache;
    protected Type tClass;
    private boolean refresh = false;

    public static void initDataCache(String DataCacheKey) {
        SharedPref.getInstance(BaseApplication.Companion.context()).putString("DataCacheKey", DataCacheKey);
    }

    public DataCache(BaseView activity, String dataKey, Flowable<T> flowable,
                     LifecycleProvider lifecycleProvider, DataCallBack<T> dataCallBack, Type tClass) {
        this.tClass = tClass;
        this.activity = activity;
        this.dataKey = dataKey;
        this.flowable = flowable;
        this.lifecycleProvider = lifecycleProvider;
        this.dataCallBack = dataCallBack;
        this.maCache = ACache.getInstance(BaseApplication.Companion.context());
    }

    public void getData() {
        T t = getLocalData();
        if (t == null) {
            activity.showProgressDialog("加载中……");
        } else {
            if (dataCallBack != null) {
                dataCallBack.upView(t);
            }
        }
        getDataFromNet();
    }

    public void refresh() {
        refresh = true;
        getDataFromNet();
    }

    String getKey(String a) {
        return a + SharedPref.getInstance(BaseApplication.Companion.context()).getString("DataCacheKey", "");
    }

    void saveData(T t) {
        maCache.put(getKey(dataKey), new Gson().toJson(t));
    }

    T getLocalData() {
        if (TextUtils.isEmpty(maCache.get(getKey(dataKey)))) {
            return null;
        }

        T t = new Gson().fromJson(maCache.get(getKey(dataKey)), tClass);
        return t;
    }

    void getDataFromNet() {
        flowable.compose(XApi.<T>getApiTransformer())
                .compose(XApi.<T>getScheduler())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .subscribe(new ApiSubscriber<T>() {
                    @Override
                    protected void onFail(NetError error) {
                        if (getLocalData() != null) {
                            dataCallBack.netError(error);
                        } else {
                            dataCallBack.netErrorForNoData(error);
                        }
                        activity.closeProgressDialog();
                        if (error.getType() == NetError.AuthError) {
                            activity.toast("登陆失效", new Runnable() {
                                @Override
                                public void run() {
                                    Lennon.Companion.requserLogin();
                                }
                            });
                            return;
                        }
                    }

                    @Override
                    public void onNext(T o) {
                        if (getLocalData() != null) {
                            String a = new Gson().toJson(getLocalData());
                            String b = new Gson().toJson(o);
                            if (a.equals(b) && refresh) {
                                dataCallBack.upView(o);
                            } else if (a.equals(b) && !refresh) {

                            } else {
                                dataCallBack.upView(o);
                                saveData(o);
                            }
                        } else {
                            dataCallBack.upView(o);
                            saveData(o);
                        }
                        activity.closeProgressDialog();
                    }
                });
    }

    public interface DataCallBack<T> {
        void upView(@NonNull T t);

        void netErrorForNoData(@NonNull NetError error);

        void netError(@NonNull NetError error);
    }
}
