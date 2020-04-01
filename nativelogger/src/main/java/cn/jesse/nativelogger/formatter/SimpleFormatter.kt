package cn.jesse.nativelogger.formatter

import java.text.MessageFormat
import java.util.*
import java.util.logging.Formatter
import java.util.logging.LogRecord

/**
 * 文件日志默认格式化工具
 *
 * @author Jesse
 */
class SimpleFormatter : Formatter() {

    private val LINE_SEPARATOR = "\n"

    /**
     * Converts a object into a human readable string
     * representation.
     *
     */
    override fun format(r: LogRecord): String {
        val sb = StringBuilder()
        sb.append(MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss} ", *arrayOf<Any>(Date(r.millis))))
        sb.append(r.loggerName).append(": ")
        sb.append(r.level.name).append(LINE_SEPARATOR)
        sb.append(formatMessage(r)).append(LINE_SEPARATOR)
        if (r.thrown != null) {
            sb.append("Throwable occurred: ")
            sb.append(TagFormatter.format(r.thrown))
        }
        return sb.toString()
    }
}