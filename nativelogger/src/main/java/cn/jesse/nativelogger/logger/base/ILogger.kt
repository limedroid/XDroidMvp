package cn.jesse.nativelogger.logger.base

import cn.jesse.nativelogger.logger.LoggerLevel

/**
 * Logger 接口
 *
 * @author Jesse
 */
interface ILogger {

    /**
     * 设置日志tag
     *
     * @param tag TAG
     */
    fun setTag(tag: String)

    /**
     * 获取日志tag
     */
    fun tag(): String

    /**
     * 设置日志等级
     *
     * @param level 等级枚举
     */
    fun setLevel(level: LoggerLevel)

    /**
     * 判断是否可以debug
     */
    fun isDebugEnabled(): Boolean

    /**
     * debug 日志
     *
     * @param msg 信息
     */
    fun debug(msg: String)

    /**
     * debug 日志
     *
     * @param subTag 子TAG
     * @param msg 信息
     */
    fun debug(subTag: String, msg: String)

    /**
     * debug 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arg 被格式化对象
     */
    fun debug(subTag: String, format: String, arg: Any)

    /**
     * debug 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param argA 被格式化对象A
     * @param argB 被格式化对象B
     */
    fun debug(subTag: String, format: String, argA: Any, argB: Any)

    /**
     * debug 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arguments 可变参数组
     */
    fun debug(subTag: String, format: String, vararg arguments: Any)

    /**
     * debug 日志
     *
     * @param subTag 子TAG
     * @param t 异常信息
     */
    fun debug(subTag: String, t: Throwable)

    /**
     * 判断是否可以info
     */
    fun isInfoEnabled(): Boolean

    /**
     * info 日志
     *
     * @param msg 信息
     */
    fun info(msg: String)

    /**
     * info 日志
     *
     * @param subTag 子TAG
     * @param msg 信息
     */
    fun info(subTag: String, msg: String)

    /**
     * info 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arg 被格式化对象
     */
    fun info(subTag: String, format: String, arg: Any)

    /**
     * info 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param argA 被格式化对象A
     * @param argB 被格式化对象B
     */
    fun info(subTag: String, format: String, argA: Any, argB: Any)

    /**
     * info 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arguments 可变参数组
     */
    fun info(subTag: String, format: String, vararg arguments: Any)

    /**
     * info 日志
     *
     * @param subTag 子TAG
     * @param t 异常信息
     */
    fun info(subTag: String, t: Throwable)

    /**
     * 判断是否可以 warn
     */
    fun isWarnEnabled(): Boolean

    /**
     * warn 日志
     *
     * @param msg 信息
     */
    fun warn(msg: String)

    /**
     * warn 日志
     *
     * @param subTag 子TAG
     * @param msg 信息
     */
    fun warn(subTag: String, msg: String)

    /**
     * warn 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arg 被格式化对象
     */
    fun warn(subTag: String, format: String, arg: Any)

    /**
     * warn 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arguments 可变参数组
     */
    fun warn(subTag: String, format: String, vararg arguments: Any)

    /**
     * warn 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param argA 被格式化对象A
     * @param argB 被格式化对象B
     */
    fun warn(subTag: String, format: String, argA: Any, argB: Any)

    /**
     * warn 日志
     *
     * @param subTag 子TAG
     * @param t 异常信息
     */
    fun warn(subTag: String, t: Throwable)

    /**
     * 判断是否可以 error
     */
    fun isErrorEnabled(): Boolean

    /**
     * error 日志
     *
     * @param msg 信息
     */
    fun error(msg: String)

    /**
     * error 日志
     *
     * @param subTag 子TAG
     * @param msg 信息
     */
    fun error(subTag: String, msg: String)

    /**
     * error 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arg 被格式化对象
     */
    fun error(subTag: String, format: String, arg: Any)

    /**
     * error 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param argA 被格式化对象A
     * @param argB 被格式化对象B
     */
    fun error(subTag: String, format: String, argA: Any, argB: Any)

    /**
     * error 日志
     *
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arguments 可变参数组
     */
    fun error(subTag: String, format: String, vararg arguments: Any)

    /**
     * error 日志
     *
     * @param subTag 子TAG
     * @param t 异常信息
     */
    fun error(subTag: String, t: Throwable)

    /**
     * 判断日志管理器是否开启
     */
    fun isEnabled(level: LoggerLevel): Boolean

    /**
     * 根据日志等级打印对应信息
     *
     * @param level 日志等级
     * @param msg 日志信息
     */
    fun log(level: LoggerLevel, msg: String)

    /**
     * 根据日志等级打印对应信息
     *
     * @param level 日志等级
     * @param subTag 子TAG
     * @param msg 日志信息
     */
    fun log(level: LoggerLevel, subTag: String, msg: String)

    /**
     * 根据日志等级打印对应信息
     *
     * @param level 日志等级
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arg 被格式化对象
     */
    fun log(level: LoggerLevel, subTag: String, format: String, arg: Any)

    /**
     * 根据日志等级打印对应信息
     *
     * @param level 日志等级
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param argA 被格式化对象A
     * @param argB 被格式化对象B
     */
    fun log(level: LoggerLevel, subTag: String, format: String, argA: Any, argB: Any)

    /**
     * 根据日志等级打印对应信息
     *
     * @param level 日志等级
     * @param subTag 子TAG
     * @param format 日志格式化公式 eg. %s %d
     * @param arguments 可变参数组
     */
    fun log(level: LoggerLevel, subTag: String, format: String, vararg arguments: Any)

    /**
     * 根据日志等级打印对应信息
     *
     * @param level 日志等级
     * @param subTag 子TAG
     * @param t 异常信息
     */
    fun log(level: LoggerLevel, subTag: String, t: Throwable)

    /**
     * 格式化json日志
     *
     * @param level 日志等级
     * @param msg json串
     */
    fun json(level: LoggerLevel, msg: String)

    /**
     * 格式化json日志
     *
     * @param level 日志等级
     * @param subTag 子TAG
     * @param msg json串
     */
    fun json(level: LoggerLevel, subTag: String, msg: String)
}