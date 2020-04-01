package cn.jesse.nativelogger.formatter

import android.util.Log
import cn.jesse.nativelogger.util.CloseUtil
import java.io.PrintWriter
import java.io.StringWriter

/**
 * 各种格式化日志工具
 *
 * @author Jesse
 */
object TagFormatter {
    private val RESULT_UNEXPECTED_FORMAT = "unexpected format"
    private val WITH = " with "

    /**
     * 按照 %s : %s格式化日志
     *
     * @param subTag 子Tag
     * @param msg 日志信息
     */
    fun format(subTag: String, msg: String) = try {
        String.format("%s : %s", subTag, msg)
    } catch (e: Exception) {
        Log.e(subTag, RESULT_UNEXPECTED_FORMAT, e)
        RESULT_UNEXPECTED_FORMAT
    }

    /**
     * 扩展日志格式 %s $子格式
     * @param subTag 子Tag
     * @param format 子格式
     * @param arg 被子格式格式的对象
     */
    fun format(subTag: String, format: String, arg: Any) = try {
        String.format("%s : $format", subTag, arg)
    } catch (e: Exception) {
        Log.e(subTag, RESULT_UNEXPECTED_FORMAT, e)
        RESULT_UNEXPECTED_FORMAT + WITH + format
    }

    /**
     * 扩展日志格式 %s $子格式
     * @param subTag 子Tag
     * @param format 子格式
     * @param argA 被子格式格式的对象A
     * @param argB 被子格式格式的对象B
     */
    fun format(subTag: String, format: String, argA: Any, argB: Any) = try {
        String.format("%s : $format", subTag, argA, argB)
    } catch (e: Exception) {
        Log.e(subTag, RESULT_UNEXPECTED_FORMAT, e)
        RESULT_UNEXPECTED_FORMAT + WITH + format
    }

    /**
     * 扩展日志格式 %s $子格式
     * @param subTag 子Tag
     * @param format 子格式
     * @param args 可变参数组
     */
    fun format(subTag: String, format: String, vararg args: Any) = try {
        String.format("%s : %s", subTag, String.format(format, *args))
    } catch (e: Exception) {
        Log.e(subTag, RESULT_UNEXPECTED_FORMAT, e)
        RESULT_UNEXPECTED_FORMAT + WITH + format
    }

    /**
     * 格式化异常信息
     *
     * @param t 异常
     */
    fun format(t: Throwable?): String {
        var result = ""

        if (null == t) {
            return result
        }

        var pw: PrintWriter? = null

        try {
            val sw = StringWriter()
            pw = PrintWriter(sw)
            t.printStackTrace(pw)
            result = sw.toString()
        } finally {
            CloseUtil.close(pw)
        }

        return result
    }
}