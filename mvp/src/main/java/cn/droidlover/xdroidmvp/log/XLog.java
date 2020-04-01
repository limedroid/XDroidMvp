package cn.droidlover.xdroidmvp.log;

import android.text.TextUtils;
import android.util.Log;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.logger.LoggerLevel;

/**
 * Created by wanglei on 2016/11/29.
 */

public class XLog {

    public static boolean LOG = XDroidConf.LOG;
    public static String TAG_ROOT = XDroidConf.LOG_TAG;

    public static void json(String json) {
//        json(Log.DEBUG, null, json);
        NLogger.json(LoggerLevel.DEBUG, json);
    }

    public static void json(LoggerLevel logLevel, String tag, String json) {
        if (LOG) {
            String formatJson = LogFormat.formatBorder(new String[]{LogFormat.formatJson(json)});
            NLogger.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatJson);
        }
    }

    public static void xml(String xml) {
        xml(LoggerLevel.DEBUG, null, xml);
    }


    public static void xml(LoggerLevel logLevel, String tag, String xml) {
        if (LOG) {
            String formatXml = LogFormat.formatBorder(new String[]{LogFormat.formatXml(xml)});
            NLogger.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatXml);
        }
    }

    public static void error(Throwable throwable) {
        error(null, throwable);
    }

    public static void error(String tag, Throwable throwable) {
        if (LOG) {
            String formatError = LogFormat.formatBorder(new String[]{LogFormat.formatThrowable(throwable)});
            NLogger.println(LoggerLevel.ERROR, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatError);
        }
    }

    private static void msg(LoggerLevel logLevel, String tag, String format, Object... args) {
        if (LOG) {
            String formatMsg = LogFormat.formatBorder(new String[]{LogFormat.formatArgs(format, args)});
            NLogger.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatMsg);
        }
    }

    public static void d(String msg, Object... args) {
        msg(LoggerLevel.DEBUG, null, msg, args);
    }

    public static void d(String tag, String msg, Object... args) {
        msg(LoggerLevel.DEBUG, tag, msg, args);
    }

    public static void e(String msg, Object... args) {
        msg(LoggerLevel.ERROR, null, msg, args);
    }

    public static void e(String tag, String msg, Object... args) {
        msg(LoggerLevel.ERROR, tag, msg, args);
    }

}
