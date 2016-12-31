package cn.droidlover.xdroidmvp.demo.net;

import cn.droidlover.xdroidmvp.demo.model.GankResults;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    @GET("data/{type}/{number}/{page}")
    Observable<GankResults> getGankData(@Path("type") String type,
                                        @Path("number") int pageSize,
                                        @Path("page") int pageNum);
}
