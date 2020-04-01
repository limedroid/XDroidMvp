package com.lennon.cn.utill.base;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.droidlover.xdroidmvp.net.IModel;
import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Flowable;

/**
 * Created by lennon on 2017/8/10.
 */

public abstract class BaseApi<T> {
    public T service;
    protected Class<T> tClass;

    public BaseApi(String url) {
        tClass = (Class<T>) getModelClass(0);
        try {
            service = XApi.getInstance().getRetrofit(url, true).create(tClass);
        } catch (Exception e) {
            e.printStackTrace();
            service = XApi.getInstance().getRetrofit(url, true).create(tClass);
        }
    }

    public final <R extends IModel> Flowable<R> compose(Flowable<R> flowable) {
        return flowable.compose(XApi.<R>getApiTransformer())
                .compose(XApi.<R>getScheduler());
    }

    //泛型类作为父类，可以获取子类的所有实际参数的类型
    @SuppressWarnings("unchecked")
    public Class<?> getModelClass(int index) {
        Class modelClass;
        // 得到泛型父类
        Type genType = this.getClass().getGenericSuperclass();
        //一个泛型类可能有多个泛型形参，比如ClassName<T,K> 这里有两个泛型形参T和K，Class Name<T> 这里只有1个泛型形参T
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length - 1 < index) {
            modelClass = null;
        } else {
            modelClass = (Class) params[index];
        }
        return modelClass;
    }
}
