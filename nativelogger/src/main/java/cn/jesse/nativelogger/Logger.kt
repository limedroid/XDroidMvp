package cn.jesse.nativelogger

import cn.jesse.nativelogger.logger.LoggerLevel

/**
 * 注解模式初始化: 提供日志TAG 和日志等级快捷设置
 *
 * @author Jesse
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Logger(val tag: String = "NLogger", val level: LoggerLevel = LoggerLevel.WARN)