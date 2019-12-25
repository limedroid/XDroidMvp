package com.lennon.cn.utill.utill.rx;

import cn.droidlover.xdroidmvp.net.NetError;
import org.reactivestreams.Publisher;

import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxTool {
    /**
     * 线程切换
     *
     * @return
     */
    public static <T > FlowableTransformer<T, T> getScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 异常处理变换
     *
     * @return
     */
    public static <T > FlowableTransformer<T, T> getApiTransformer() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.flatMap(new Function<T, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(T model) throws Exception {
                        if (model == null ) {
                            return Flowable.error(new NetError("无数据", NetError.NoDataError));
                        } else {
                            return Flowable.just(model);
                        }
                    }
                });
            }
        };
    }
    public static  <T> Flowable<T> makeFlowable(final Callable<T> func) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                try {
                    e.onNext(func.call());
                } catch (Exception e1) {
                }
            }
        }, BackpressureStrategy.ERROR);
    }
}
