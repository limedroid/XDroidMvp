package cn.droidlover.xdroidmvp.net.progress;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import cn.droidlover.xdroidmvp.kit.Kits;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanglei on 2017/9/10.
 */

public class ProgressHelper {
    private final Map<String, Set<WeakReference<ProgressListener>>> requestListenerMap = new WeakHashMap<>();
    private final Map<String, Set<WeakReference<ProgressListener>>> responseListenerMap = new WeakHashMap<>();

    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private Interceptor interceptor;

    private ProgressHelper() {
        interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return wrapResponseBody(chain.proceed(wrapRequestBody(chain.request())));
            }
        };
    }

    public static ProgressHelper get() {
        return Holder.instance;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }


    public void addRequestListener(String url, ProgressListener listener) {
        if (Kits.Empty.check(url) || listener == null) return;

        Set<WeakReference<ProgressListener>> listeners = null;
        synchronized (ProgressHelper.class) {
            listeners = requestListenerMap.get(url);
            if (listeners == null) {
                listeners = new HashSet<>();
                requestListenerMap.put(url, listeners);
            }
            listeners.add(new WeakReference<ProgressListener>(listener));
        }
    }

    public void addResponseListener(String url, ProgressListener listener) {
        if (Kits.Empty.check(url) || listener == null) return;

        Set<WeakReference<ProgressListener>> listeners = null;
        synchronized (ProgressHelper.class) {
            listeners = responseListenerMap.get(url);
            if (listeners == null) {
                listeners = new HashSet<>();
                responseListenerMap.put(url, listeners);
            }
            listeners.add(new WeakReference<ProgressListener>(listener));
        }
    }

    public void delRequestListener(String url, ProgressListener listener) {
        if (Kits.Empty.check(requestListenerMap)) return;

        if (Kits.Empty.check(url)) {

            if (listener != null) {
                for (String key : requestListenerMap.keySet()) {
                    delReference(requestListenerMap.get(key), listener);
                }
            }
        } else {
            if (listener != null) {
                delReference(requestListenerMap.get(url), listener);
            } else {
                requestListenerMap.remove(url);
            }
        }
    }

    public void delResponseListener(String url, ProgressListener listener) {
        if (Kits.Empty.check(responseListenerMap)) return;

        if (Kits.Empty.check(url)) {

            if (listener != null) {
                for (String key : responseListenerMap.keySet()) {
                    delReference(responseListenerMap.get(key), listener);
                }
            }
        } else {
            if (listener != null) {
                delReference(responseListenerMap.get(url), listener);
            } else {
                responseListenerMap.remove(url);
            }
        }
    }

    public void clearAll() {
        requestListenerMap.clear();
        responseListenerMap.clear();
    }


    public static void dispatchProgressEvent(Set<WeakReference<ProgressListener>> listeners,
                                             final long soFarBytes, final long totalBytes) {
        if (!Kits.Empty.check(listeners)) {
            for (final WeakReference<ProgressListener> reference : listeners) {
                if (reference.get() != null) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            reference.get().onProgress(soFarBytes, totalBytes);
                        }
                    });
                }
            }
        }
    }

    public static void dispatchErrorEvent(Set<WeakReference<ProgressListener>> listeners,
                                          final Throwable throwable) {
        if (!Kits.Empty.check(listeners)) {
            for (final WeakReference<ProgressListener> reference : listeners) {
                if (reference.get() != null) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            reference.get().onError(throwable);
                        }
                    });
                }
            }
        }
    }

    private Request wrapRequestBody(Request request) {
        if (request == null || request.body() == null)
            return request;

        String key = request.url().toString();
        if (requestListenerMap.containsKey(key)) {
            Set<WeakReference<ProgressListener>> listeners = requestListenerMap.get(key);
            return request.newBuilder()
                    .method(request.method(), new ProRequestBody(request.body(), listeners))
                    .build();
        }
        return request;
    }

    private Response wrapResponseBody(Response response) {
        if (response == null || response.body() == null) return response;

        String key = response.request().url().toString();
        if (responseListenerMap.containsKey(key)) {
            Set<WeakReference<ProgressListener>> listeners = responseListenerMap.get(key);
            return response.newBuilder()
                    .body(new ProResponseBody(response.body(), listeners))
                    .build();
        }
        return response;
    }


    private void delReference(Set<WeakReference<ProgressListener>> references,
                              ProgressListener listener) {
        if (!Kits.Empty.check(references)) {
            for (WeakReference<ProgressListener> reference : references) {
                if (reference.get() != null && reference.get() == listener) {
                    references.remove(reference);
                }
            }
        }
    }


    private static class Holder {
        private static ProgressHelper instance = new ProgressHelper();
    }
}
