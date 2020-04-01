package cn.jesse.nativelogger.logger

import android.util.Log
import cn.jesse.nativelogger.formatter.TagFormatter
import cn.jesse.nativelogger.logger.base.AbstractLogger

/**
 * Android 日志管理器实现
 *
 * @author Jesse
 */
class AndroidLogger(tag: String) : AbstractLogger(tag) {
    private var debugEnable = true
    private var infoEnable = true
    private var warningEnable = true
    private var errorEnable = true

    override fun setLevel(level: LoggerLevel) {
        when (level) {
            LoggerLevel.DEBUG -> {
                // default
            }
            LoggerLevel.INFO -> {
                debugEnable = false
            }
            LoggerLevel.WARN -> {
                debugEnable = false
                infoEnable = false
            }
            LoggerLevel.ERROR -> {
                debugEnable = false
                infoEnable = false
                warningEnable = false
            }
            LoggerLevel.OFF -> {
                debugEnable = false
                infoEnable = false
                warningEnable = false
                errorEnable = false
            }
        }
    }

    override fun isDebugEnabled(): Boolean {
        return debugEnable
    }

    override fun debug(msg: String) {
        if (!isDebugEnabled()) {
            return
        }

        Log.d(mTag, msg)
    }

    override fun debug(subTag: String, msg: String) {
        if (!isDebugEnabled()) {
            return
        }

        Log.d(mTag, TagFormatter.format(subTag, msg))
    }

    override fun debug(subTag: String, format: String, arg: Any) {
        if (!isDebugEnabled()) {
            return
        }

        Log.d(mTag, TagFormatter.format(subTag, format, arg))
    }

    override fun debug(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isDebugEnabled()) {
            return
        }

        Log.d(mTag, TagFormatter.format(subTag, format, argA, argB))
    }

    override fun debug(subTag: String, format: String, vararg arguments: Any) {
        if (!isDebugEnabled()) {
            return
        }

        Log.d(mTag, TagFormatter.format(subTag, format, *arguments))
    }

    override fun debug(subTag: String, t: Throwable) {
        if (!isDebugEnabled()) {
            return
        }

        Log.d(mTag, subTag + " " + TagFormatter.format(t))
    }

    override fun isInfoEnabled(): Boolean {
        return infoEnable
    }

    override fun info(msg: String) {
        if (!isInfoEnabled()) {
            return
        }

        Log.i(mTag, msg)
    }

    override fun info(subTag: String, msg: String) {
        if (!isInfoEnabled()) {
            return
        }

        Log.i(mTag, TagFormatter.format(subTag, msg))
    }

    override fun info(subTag: String, format: String, arg: Any) {
        if (!isInfoEnabled()) {
            return
        }

        Log.i(mTag, TagFormatter.format(subTag, format, arg))
    }

    override fun info(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isInfoEnabled()) {
            return
        }

        Log.i(mTag, TagFormatter.format(subTag, format, argA, argB))
    }

    override fun info(subTag: String, format: String, vararg arguments: Any) {
        if (!isInfoEnabled()) {
            return
        }

        Log.i(mTag, TagFormatter.format(subTag, format, *arguments))
    }

    override fun info(subTag: String, t: Throwable) {
        if (!isInfoEnabled()) {
            return
        }

        Log.i(mTag, subTag + " " + TagFormatter.format(t))
    }

    override fun isWarnEnabled(): Boolean {
        return warningEnable
    }

    override fun warn(msg: String) {
        if (!isWarnEnabled()) {
            return
        }

        Log.w(mTag, msg)
    }

    override fun warn(subTag: String, msg: String) {
        if (!isWarnEnabled()) {
            return
        }

        Log.w(mTag, TagFormatter.format(subTag, msg))
    }

    override fun warn(subTag: String, format: String, arg: Any) {
        if (!isWarnEnabled()) {
            return
        }

        Log.w(mTag, TagFormatter.format(subTag, format, arg))
    }

    override fun warn(subTag: String, format: String, vararg arguments: Any) {
        if (!isWarnEnabled()) {
            return
        }

        Log.w(mTag, TagFormatter.format(subTag, format, *arguments))
    }

    override fun warn(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isWarnEnabled()) {
            return
        }

        Log.w(mTag, TagFormatter.format(subTag, format, argA, argB))
    }

    override fun warn(subTag: String, t: Throwable) {
        if (!isWarnEnabled()) {
            return
        }

        Log.w(mTag, subTag + " " + TagFormatter.format(t))
    }

    override fun isErrorEnabled(): Boolean {
        return errorEnable
    }

    override fun error(msg: String) {
        if (!isErrorEnabled()) {
            return
        }

        Log.e(mTag, msg)
    }

    override fun error(subTag: String, msg: String) {
        if (!isErrorEnabled()) {
            return
        }

        Log.e(mTag, TagFormatter.format(subTag, msg))
    }

    override fun error(subTag: String, format: String, arg: Any) {
        if (!isErrorEnabled()) {
            return
        }

        Log.e(mTag, TagFormatter.format(subTag, format, arg))
    }

    override fun error(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isErrorEnabled()) {
            return
        }

        Log.e(mTag, TagFormatter.format(subTag, format, argA, argB))
    }

    override fun error(subTag: String, format: String, vararg arguments: Any) {
        if (!isErrorEnabled()) {
            return
        }

        Log.e(mTag, TagFormatter.format(subTag, format, *arguments))
    }

    override fun error(subTag: String, t: Throwable) {
        if (!isErrorEnabled()) {
            return
        }

        Log.e(mTag, subTag + " " + TagFormatter.format(t))
    }
}