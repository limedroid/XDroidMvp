package cn.jesse.nativelogger.util

/**
 * 监听全局异常, 捕获并回调
 *
 * @author Jesse
 */
class CrashWatcher private constructor() : Thread.UncaughtExceptionHandler {

    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private var listener: ((thread: Thread?, ex: Throwable?) -> Unit)? = null

    fun init() {
        if (null != mDefaultHandler) {
            return
        }

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    fun setListener(listener: ((thread: Thread?, ex: Throwable?) -> Unit)?) {
        this.listener = listener
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        listener?.invoke(t, e)
    }

    companion object {
        private var mInstance: CrashWatcher? = null

        fun getInstance(): CrashWatcher {
            if (null == mInstance) {
                synchronized(CrashWatcher::class.java) {
                    if (null == mInstance) {
                        mInstance = CrashWatcher()
                    }
                }
            }

            return mInstance!!
        }
    }
}
