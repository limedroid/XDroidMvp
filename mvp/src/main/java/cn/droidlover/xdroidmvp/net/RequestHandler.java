package cn.droidlover.xdroidmvp.net;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanglei on 2016/12/24.
 */

public interface RequestHandler {
    Request onBeforeRequest(Request request, Interceptor.Chain chain);

    Response onAfterRequest(Response response, Interceptor.Chain chain);
}
