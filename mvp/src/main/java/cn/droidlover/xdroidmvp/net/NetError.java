package cn.droidlover.xdroidmvp.net;

/**
 * Created by wanglei on 2016/12/24.
 */

public class NetError {
    private Exception exception;
    private int type = NoConnectError;

    public static final int ParseError = 0;   //数据解析异常
    public static final int NoConnectError = 1;   //无连接异常
    public static final int AuthError = 2;   //用户未登录异常
    public static final int ServerError = 3;   //服务器异常
    public static final int NoDataError = 4;   //无数据返回异常
    public static final int BusinessError = 5;   //业务异常
    public static final int OtherError = 6;   //其他异常


    public NetError(Exception exception) {
        this.exception = exception;
    }

    public String getMessage() {
        if (exception != null) return exception.getMessage();
        return null;
    }

    public int getType() {
        return type;
    }
}
