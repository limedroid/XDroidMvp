package cn.jesse.nativelogger.logger.base

import java.util.logging.Formatter

/**
 * 文件日志管理器接口
 *
 * @author Jesse
 */
interface IFileLogger {

    /**
     * 设置文件路径, 日志格式工具, 和日志保留时间
     *
     * @param directory 路径
     * @param formatter 日志格式化工具
     * @param expiredPeriod 日志过期时间, 单位为天
     */
    fun setFilePathAndFormatter(directory: String, formatter: Formatter, expiredPeriod: Int)

    /**
     * 获取日志文件存放路径
     */
    fun logDirectory(): String

    /**
     * 获取文件日志格式化工具
     */
    fun fileFormatter(): Formatter

    /**
     * 获取日志过期时间
     */
    fun expiredPeriod(): Int

    /**
     * 压缩日志
     *
     * @param listener 压缩结果回调. succeed 是否成功, target 压缩包路径
     *
     */
    fun zipLogs(listener: (succeed: Boolean, target: String) -> Unit)
}