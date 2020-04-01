package cn.jesse.nativelogger.logger.base

import cn.jesse.nativelogger.NLoggerError
import cn.jesse.nativelogger.logger.LoggerLevel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Logger抽象类, 提供部分默认逻辑
 *
 * @author Jesse
 */
abstract class AbstractLogger(tag: String) : ILogger {
    protected open var mTag: String = "AbstractLogger"

    init {
        mTag = tag
    }

    override fun tag(): String {
        return mTag
    }


    override fun setTag(tag: String) {
        this.mTag = tag
    }

    override fun isEnabled(level: LoggerLevel): Boolean {
        return when (level) {
            LoggerLevel.DEBUG -> isDebugEnabled()
            LoggerLevel.INFO -> isInfoEnabled()
            LoggerLevel.WARN -> isWarnEnabled()
            LoggerLevel.ERROR -> isErrorEnabled()
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun log(level: LoggerLevel, subTag: String, t: Throwable) {
        when (level) {
            LoggerLevel.DEBUG -> debug(subTag, t)
            LoggerLevel.INFO -> info(subTag, t)
            LoggerLevel.WARN -> warn(subTag, t)
            LoggerLevel.ERROR -> error(subTag, t)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun log(level: LoggerLevel, msg: String) {
        when (level) {
            LoggerLevel.DEBUG -> debug(msg)
            LoggerLevel.INFO -> info(msg)
            LoggerLevel.WARN -> warn(msg)
            LoggerLevel.ERROR -> error(msg)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun log(level: LoggerLevel, subTag: String, msg: String) {
        when (level) {
            LoggerLevel.DEBUG -> debug(subTag, msg)
            LoggerLevel.INFO -> info(subTag, msg)
            LoggerLevel.WARN -> warn(subTag, msg)
            LoggerLevel.ERROR -> error(subTag, msg)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun log(level: LoggerLevel, subTag: String, format: String, arg: Any) {
        when (level) {
            LoggerLevel.DEBUG -> debug(subTag, format, arg)
            LoggerLevel.INFO -> info(subTag, format, arg)
            LoggerLevel.WARN -> warn(subTag, format, arg)
            LoggerLevel.ERROR -> error(subTag, format, arg)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun log(level: LoggerLevel, subTag: String, format: String, argA: Any, argB: Any) {
        when (level) {
            LoggerLevel.DEBUG -> debug(subTag, format, argA, argB)
            LoggerLevel.INFO -> info(subTag, format, argA, argB)
            LoggerLevel.WARN -> warn(subTag, format, argA, argB)
            LoggerLevel.ERROR -> error(subTag, format, argA, argB)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun log(level: LoggerLevel, subTag: String, format: String, vararg arguments: Any) {
        when (level) {
            LoggerLevel.DEBUG -> debug(subTag, format, *arguments)
            LoggerLevel.INFO -> info(subTag, format, *arguments)
            LoggerLevel.WARN -> warn(subTag, format, *arguments)
            LoggerLevel.ERROR -> error(subTag, format, *arguments)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun json(level: LoggerLevel, msg: String) {
        if (!isEnabled(level)) {
            return
        }

        val json = parseJson(msg)

        when (level) {
            LoggerLevel.DEBUG -> debug(json)
            LoggerLevel.INFO -> info(json)
            LoggerLevel.WARN -> warn(json)
            LoggerLevel.ERROR -> error(json)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    override fun json(level: LoggerLevel, subTag: String, msg: String) {
        if (!isEnabled(level))
            return

        val json = parseJson(msg)

        when (level) {
            LoggerLevel.DEBUG -> debug(subTag, json)
            LoggerLevel.INFO -> info(subTag, json)
            LoggerLevel.WARN -> warn(subTag, json)
            LoggerLevel.ERROR -> error(subTag, json)
            else -> throw NLoggerError(ERROR_LEVEL)
        }
    }

    /**
     * format json
     * as:
     * <pre>
     * {
     * "query": "Pizza",
     * "locations": [
     * 94043,
     * 90210
     * ]
     * }</pre>
     * @param json
     * @return
     */
    private fun parseJson(json: String?): String {
        if (null == json) {
            return ERROR_FORMAT
        }

        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                return jsonObject.toString(JSON_INDENT)
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                return jsonArray.toString(JSON_INDENT)
            }
            return ERROR_FORMAT
        } catch (e: JSONException) {
            error(this.javaClass.simpleName, e)
            return ERROR_FORMAT
        }

    }

    companion object {
        private val ERROR_FORMAT = "unexpected format"
        private val ERROR_LEVEL = "unexpected LoggerLevel"
        private val JSON_INDENT = 2
    }
}
