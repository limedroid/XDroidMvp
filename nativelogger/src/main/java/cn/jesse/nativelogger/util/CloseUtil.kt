package cn.jesse.nativelogger.util

import java.io.Closeable
import java.io.IOException

import cn.jesse.nativelogger.NLogger

/**
 * 关闭各种
 *
 * @author Jesse
 */
object CloseUtil {

    /**
     * 关闭Closeable对象
     *
     * @param closeable 对象
     */
    fun close(closeable: Closeable?) {
        try {
            closeable?.close()
        } catch (e: IOException) {
            NLogger.e("close", e)
        }
    }
}
