package cn.jesse.nativelogger.logger

import android.os.Handler
import android.os.HandlerThread
import cn.jesse.nativelogger.formatter.TagFormatter
import cn.jesse.nativelogger.logger.base.AbstractLogger
import cn.jesse.nativelogger.logger.base.IFileLogger
import cn.jesse.nativelogger.util.DateUtil
import cn.jesse.nativelogger.util.ZipUtil
import java.io.File
import java.io.IOException
import java.util.logging.*

/**
 * 文件日志管理器实现
 */
class FileLogger(tag: String) : AbstractLogger(tag), IFileLogger {
    internal val logger: Logger
    private lateinit var logDir: String
    private lateinit var formatter: Formatter
    private var expiredPeriod: Int = 0

    private var handler: Handler

    init {
        val fileLoggerThread = HandlerThread(FileLogger::class.java.simpleName)
        fileLoggerThread.start()
        handler = Handler(fileLoggerThread.looper)

        this.logger = Logger.getLogger(tag)
        logger.useParentHandlers = false
    }

    /*
 * Java文件操作 获取不带扩展名的文件名
 *
 *  Created on: 2011-8-2
 *      Author: blueeagle
 */
    fun getFileNameNoEx(filename: String): String {
        if (filename.isNotEmpty()) {
            val dot = filename.indexOf('.')
            if (dot > -1 && dot < filename.length) {
                return filename.substring(0, dot)
            }
        }
        return filename
    }

    override fun setFilePathAndFormatter(directory: String, formatter: Formatter, expiredPeriod: Int) {
        this.logDir = directory
        this.formatter = formatter
        this.expiredPeriod = expiredPeriod

        if (!logDir.endsWith("/"))
            logDir += "/"
        val f = File(logDir)
        if (f.isDirectory && !f.exists()) {
            f.mkdirs()
        }
        DateUtil.DeleteFileDate(expiredPeriod, f)
        val file = File(logDir + DateUtil.getCurrentDate())
        val fh: FileHandler
        try {
            fh = FileHandler(file.toString(), true)
            fh.formatter = formatter
            logger.addHandler(fh)
        } catch (e: IOException) {
            //unused
            error(this.javaClass.simpleName, e)
        }
        handler.post { ZipUtil.getSuitableFilesWithClear(directory, expiredPeriod) }
    }

    override fun logDirectory(): String {
        return this.logDir
    }

    override fun fileFormatter(): Formatter {
        return this.formatter
    }

    override fun expiredPeriod(): Int {
        return this.expiredPeriod
    }

    override fun zipLogs(listener: (succeed: Boolean, target: String) -> Unit) {
        handler.post {
            var result = false
            val targetZipFileName = logDir + DateUtil.getCurrentDate() + ZipUtil.SUFFIX_ZIP
            try {
                val zipFile = File(targetZipFileName)
                if (zipFile.exists() && !zipFile.delete()) {
                    error(tag(), "can not delete exist zip file!")
                }
                result = ZipUtil.zipFiles(
                    ZipUtil.getSuitableFilesWithClear(logDir, expiredPeriod),
                    zipFile, DateUtil.getCurrentDate()
                )
            } catch (e: Exception) {
                error(tag(), e)
            }

            listener(result, targetZipFileName)
        }
    }

    override fun setLevel(level: LoggerLevel) {
        when (level) {
            LoggerLevel.OFF -> logger.level = Level.OFF
            LoggerLevel.ERROR -> logger.level = Level.SEVERE
            LoggerLevel.WARN -> logger.level = Level.WARNING
            LoggerLevel.INFO -> logger.level = Level.INFO
            LoggerLevel.DEBUG -> logger.level = Level.FINE
        }
    }

    @Synchronized
    private fun log(level: Level, msg: String, t: Throwable?) {
        val record = LogRecord(level, msg)
        record.sourceClassName=tag()
        record.loggerName = tag()
        record.thrown = t
        logger.log(record)
    }

    override fun isDebugEnabled(): Boolean {
        return logger.isLoggable(Level.FINE)
    }

    override fun debug(msg: String) {
        if (!isDebugEnabled()) {
            return
        }

        handler.post { log(Level.FINE, msg, null) }
    }

    override fun debug(subTag: String, msg: String) {
        if (!isDebugEnabled()) {
            return
        }

        handler.post { log(Level.FINE, TagFormatter.format(subTag, msg), null) }
    }

    override fun debug(subTag: String, format: String, arg: Any) {
        if (!isDebugEnabled()) {
            return
        }

        handler.post { log(Level.FINE, TagFormatter.format(subTag, format, arg), null) }
    }

    override fun debug(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isDebugEnabled()) {
            return
        }

        handler.post { log(Level.FINE, TagFormatter.format(subTag, format, argA, argB), null) }
    }

    override fun debug(subTag: String, format: String, vararg arguments: Any) {
        if (!isDebugEnabled()) {
            return
        }

        handler.post { log(Level.FINE, TagFormatter.format(subTag, format, *arguments), null) }
    }

    override fun debug(subTag: String, t: Throwable) {
        if (!isDebugEnabled()) {
            return
        }

        log(Level.FINE, subTag, t)
    }

    override fun isInfoEnabled(): Boolean {
        return logger.isLoggable(Level.INFO)
    }

    override fun info(msg: String) {
        if (!isInfoEnabled()) {
            return
        }

        handler.post { log(Level.INFO, msg, null) }
    }

    override fun info(subTag: String, msg: String) {
        if (!isInfoEnabled()) {
            return
        }

        handler.post { log(Level.INFO, TagFormatter.format(subTag, msg), null) }
    }

    override fun info(subTag: String, format: String, arg: Any) {
        if (!isInfoEnabled()) {
            return
        }

        handler.post { log(Level.INFO, TagFormatter.format(subTag, format, arg), null) }
    }

    override fun info(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isInfoEnabled()) {
            return
        }

        handler.post { log(Level.INFO, TagFormatter.format(subTag, format, argA, argB), null) }
    }

    override fun info(subTag: String, format: String, vararg arguments: Any) {
        if (!isInfoEnabled()) {
            return
        }

        handler.post { log(Level.INFO, TagFormatter.format(subTag, format, *arguments), null) }
    }

    override fun info(subTag: String, t: Throwable) {
        if (!isInfoEnabled()) {
            return
        }

        log(Level.INFO, subTag, t)
    }


    override fun isWarnEnabled(): Boolean {
        return logger.isLoggable(Level.WARNING)
    }

    override fun warn(msg: String) {
        if (!isWarnEnabled()) {
            return
        }

        handler.post { log(Level.WARNING, msg, null) }
    }

    override fun warn(subTag: String, msg: String) {
        if (!isWarnEnabled()) {
            return
        }

        handler.post { log(Level.WARNING, TagFormatter.format(subTag, msg), null) }
    }

    override fun warn(subTag: String, format: String, arg: Any) {
        if (!isWarnEnabled()) {
            return
        }

        handler.post { log(Level.WARNING, TagFormatter.format(subTag, format, arg), null) }
    }

    override fun warn(subTag: String, format: String, vararg arguments: Any) {
        if (!isWarnEnabled()) {
            return
        }

        handler.post { log(Level.WARNING, TagFormatter.format(subTag, format, *arguments), null) }
    }

    override fun warn(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isWarnEnabled()) {
            return
        }

        handler.post { log(Level.WARNING, TagFormatter.format(subTag, format, argA, argB), null) }
    }

    override fun warn(subTag: String, t: Throwable) {
        if (!isWarnEnabled()) {
            return
        }

        log(Level.WARNING, subTag, t)
    }


    override fun isErrorEnabled(): Boolean {
        return logger.isLoggable(Level.SEVERE)
    }

    override fun error(msg: String) {
        if (!isErrorEnabled()) {
            return
        }

        handler.post { log(Level.SEVERE, msg, null) }
    }

    override fun error(subTag: String, msg: String) {
        if (!isErrorEnabled()) {
            return
        }

        handler.post { log(Level.SEVERE, TagFormatter.format(subTag, msg), null) }
    }

    override fun error(subTag: String, format: String, arg: Any) {
        if (!isErrorEnabled()) {
            return
        }

        handler.post { log(Level.SEVERE, TagFormatter.format(subTag, format, arg), null) }
    }

    override fun error(subTag: String, format: String, argA: Any, argB: Any) {
        if (!isErrorEnabled()) {
            return
        }

        handler.post { log(Level.SEVERE, TagFormatter.format(subTag, format, argA, argB), null) }
    }

    override fun error(subTag: String, format: String, vararg arguments: Any) {
        if (!isErrorEnabled()) {
            return
        }

        handler.post { log(Level.SEVERE, TagFormatter.format(subTag, format, *arguments), null) }
    }

    override fun error(subTag: String, t: Throwable) {
        if (!isErrorEnabled()) {
            return
        }

        log(Level.SEVERE, subTag, t)
    }
}