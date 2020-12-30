package cn.droidlover.xdroidmvp.net;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;

import cn.droidlover.xdroidmvp.mvp.IView;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;


/**
 * Created by wanglei on 2016/12/26.
 */

public abstract class ApiSubscriber<T extends IModel> extends ResourceSubscriber<T> {
    private IView iView;

    public ApiSubscriber() {
    }

    public ApiSubscriber(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onError(Throwable e) {
        NetError error = null;
        if (e != null) {
            e.printStackTrace();
            if (!(e instanceof NetError)) {
                if (e instanceof IOException) {
                    error = new NetError(e, NetError.NoConnectError);
                } else if (e instanceof HttpException) {
                    error = new NetError(e, NetError.HttpError);
                    HttpException httpException = (HttpException) e;
                    error.setHttpCode(httpException.code());
                } else if (e instanceof JSONException
                        || e instanceof JsonParseException
                        || e instanceof JsonSyntaxException) {
                    error = new NetError(e, NetError.ParseError);
                } else {
                    error = new NetError(e, NetError.OtherError);
                }
            } else {
                error = (NetError) e;
            }

            if (useCommonErrorHandler()
                    && XApi.getCommonProvider() != null) {
                if (XApi.getCommonProvider().handleError(error)) {        //使用通用异常处理
                    if (iView != null) {
                        iView.closeProgressDialog();
                    }
                    afterHandleError();
                    return;
                }
            }
            onFail(error);
        }

    }

    protected void afterHandleError() {
    }

    protected abstract void onFail(NetError error);

    @Override
    public void onComplete() {

    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


}
