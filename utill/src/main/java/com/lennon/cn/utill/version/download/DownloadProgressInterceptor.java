package com.lennon.cn.utill.version.download;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Interceptor for download
 * Created by JokAr on 16/5/11.
 */
public class DownloadProgressInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadProgressInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                .build();
    }
}
