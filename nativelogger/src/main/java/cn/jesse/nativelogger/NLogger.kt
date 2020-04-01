package cn.jesse.nativelogger

import cn.jesse.nativelogger.logger.LoggerLevel
import cn.jesse.nativelogger.logger.base.IFileLogger

/**
 * NLogger 静态工具类
 *
 * @author Jesse
 */
object NLogger {

    /**
     * 压缩日志文件
     *
     * @params listener 子线程回调
     */
    @JvmStatic
    fun zipLogs(listener: (succeed: Boolean, target: String) -> Unit) {
        val fileLogger = NLoggerConfig.getInstance().getFileLogger() as IFileLogger?

        if (null == fileLogger) {
            w("unexpected zip logs, file logger is null")
            return
        }
        fileLogger.zipLogs(listener)
    }

    @JvmStatic
    fun i(msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().info(msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        } else {
            NLoggerConfig.getInstance().getFileLogger()?.info(msg)
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().info(tag, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.info(tag, msg)
    }

    @JvmStatic
    fun i(tag: String, format: String, arg: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().info(tag, format, arg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.info(tag, format, arg)
    }

    @JvmStatic
    fun i(tag: String, format: String, argA: Any, argB: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().info(tag, format, argA, argB)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.info(tag, format, argA, argB)
    }

    @JvmStatic
    fun i(tag: String, format: String, vararg args: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().info(tag, format, *args)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.info(tag, format, *args)
    }

    @JvmStatic
    fun i(tag: String, ex: Throwable) {
        NLoggerConfig.getInstance().getDefaultLogger().info(tag, ex)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.info(tag, ex)
    }

    @JvmStatic
    fun d(msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().debug(msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.debug(msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().debug(tag, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.debug(tag, msg)
    }

    @JvmStatic
    fun d(tag: String, format: String, arg: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().debug(tag, format, arg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.debug(tag, format, arg)
    }

    @JvmStatic
    fun d(tag: String, format: String, argA: Any, argB: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().debug(tag, format, argA, argB)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.debug(tag, format, argA, argB)
    }

    @JvmStatic
    fun d(tag: String, format: String, vararg args: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().debug(tag, format, *args)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.debug(tag, format, *args)
    }

    @JvmStatic
    fun d(tag: String, ex: Throwable) {
        NLoggerConfig.getInstance().getDefaultLogger().debug(tag, ex)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.debug(tag, ex)
    }

    @JvmStatic
    fun w(msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().warn(msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.warn(msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().warn(tag, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.warn(tag, msg)
    }

    @JvmStatic
    fun w(tag: String, format: String, arg: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().warn(tag, format, arg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.warn(tag, format, arg)
    }

    @JvmStatic
    fun w(tag: String, format: String, argA: Any, argB: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().warn(tag, format, argA, argB)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.warn(tag, format, argA, argB)
    }

    @JvmStatic
    fun w(tag: String, format: String, vararg args: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().warn(tag, format, *args)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.warn(tag, format, *args)
    }

    @JvmStatic
    fun w(tag: String, ex: Throwable) {
        NLoggerConfig.getInstance().getDefaultLogger().warn(tag, ex)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.warn(tag, ex)
    }

    @JvmStatic
    fun e(msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().error(msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.error(msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().error(tag, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.error(tag, msg)
    }

    @JvmStatic
    fun e(tag: String, format: String, arg: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().error(tag, format, arg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.error(tag, format, arg)
    }

    @JvmStatic
    fun e(tag: String, format: String, argA: Any, argB: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().error(tag, format, argA, argB)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.error(tag, format, argA, argB)
    }

    @JvmStatic
    fun e(tag: String, format: String, vararg args: Any) {
        NLoggerConfig.getInstance().getDefaultLogger().error(tag, format, *args)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.error(tag, format, *args)
    }

    @JvmStatic
    fun e(tag: String, ex: Throwable) {
        NLoggerConfig.getInstance().getDefaultLogger().error(tag, ex)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.error(tag, ex)
    }

    @JvmStatic
    fun json(level: LoggerLevel, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().json(level, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.json(level, msg)
    }

    @JvmStatic
    fun json(level: LoggerLevel, subTag: String, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().json(level, subTag, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.json(level, subTag, msg)
    }

    @JvmStatic
    fun log(level: LoggerLevel, subTag: String, msg: String) {
        NLoggerConfig.getInstance().getDefaultLogger().log(level, subTag, msg)

        if (NLoggerConfig.getInstance().getFileLogger() == null) {
            return
        }

        NLoggerConfig.getInstance().getFileLogger()?.log(level, subTag, msg)
    }

    @JvmStatic
    fun println(logLevel: LoggerLevel, subTag: String, formatJson: String) {
        log(logLevel, subTag, formatJson)
    }
}