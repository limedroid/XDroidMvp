package cn.jesse.nativelogger

import android.os.Environment
import android.text.TextUtils
import cn.jesse.nativelogger.formatter.SimpleFormatter
import cn.jesse.nativelogger.logger.AndroidLogger
import cn.jesse.nativelogger.logger.FileLogger
import cn.jesse.nativelogger.logger.LoggerLevel
import cn.jesse.nativelogger.logger.base.IFileLogger
import cn.jesse.nativelogger.logger.base.ILogger
import cn.jesse.nativelogger.util.CrashWatcher
import java.io.File
import java.util.logging.Formatter

/**
 * NLogger配置器
 */
class NLoggerConfig {
    @Volatile
    private var builder: Builder? = null
    private val defaultLogger: ILogger = AndroidLogger("NLogger")
    @Volatile
    private var fileLogger: ILogger? = null

    /**
     * build builder to android logger & file logger & exception watcher
     * @param builder
     * @return
     */
    @Synchronized
    private fun build(builder: Builder): NLoggerConfig? {
        if (builder.isCatchException) {
            CrashWatcher.getInstance().init()
            CrashWatcher.getInstance().setListener(builder.uncaughtExceptionListener)
        } else {
            CrashWatcher.getInstance().setListener(null)
        }
        defaultLogger.setTag(builder.tag)
        defaultLogger.setLevel(builder.loggerLevel)

        if (!builder.isFileLoggerEnable) {
            fileLogger = null
            return mInstance
        }
        if (fileLogger == null) {
            fileLogger = FileLogger(builder.tag)
        } else {
            fileLogger!!.setTag(builder.tag)
        }
        fileLogger!!.setLevel(builder.loggerLevel)
        val iFileLogger = fileLogger as IFileLogger?
        iFileLogger!!.setFilePathAndFormatter(builder.fileDirectory, builder.fileFormatter, builder.expiredPeriod)
        return mInstance
    }

    /**
     * instance builder
     * @return
     */
    fun builder(): Builder {
        if (builder == null) {
            synchronized(NLoggerConfig::class.java) {
                if (builder == null) {
                    builder = Builder()
                }
            }
        }
        return builder!!
    }

    /**
     * get android logger
     *
     */
    fun getDefaultLogger(): ILogger {
        return this.defaultLogger
    }

    /**
     * get file logger
     *
     */
    fun getFileLogger(): ILogger? {
        return this.fileLogger
    }

    companion object {
        @Volatile
        private var mInstance: NLoggerConfig? = null

        /**
         * get instance
         * @return
         */
        @JvmStatic
        fun getInstance(): NLoggerConfig {
            if (mInstance == null) {
                synchronized(NLoggerConfig::class.java) {
                    if (mInstance == null) {
                        mInstance = NLoggerConfig()
                    }
                }
            }
            return mInstance!!
        }

        /**
         * init NLoggerConfig from annotation
         * @param obj
         */
        @JvmStatic
        fun init(obj: Any) {
            val clazz = obj.javaClass
            if (!clazz.isAnnotationPresent(Logger::class.java)) {
                return
            }

            val inject = clazz.getAnnotation(Logger::class.java) as Logger
            val level: LoggerLevel = inject.level

            NLoggerConfig.getInstance()
                    .builder()
                    .tag(inject.tag)
                    .loggerLevel(level)
                    .build()
        }
    }

    inner class Builder {
        var tag = NLoggerConfig::class.java.simpleName
        var loggerLevel = LoggerLevel.WARN
        var isCatchException = false
        var uncaughtExceptionListener: ((thread: Thread?, ex: Throwable?) -> Unit)? = null
        var isFileLoggerEnable = false
        var fileDirectory = Environment.getExternalStorageDirectory().path + "/native.logs/"
        var fileFormatter: Formatter = SimpleFormatter()
        var expiredPeriod = 1

        /**
         * init builder , prepare env
         *
         */
        init {
            val filePath = File(fileDirectory)
            if (!filePath.exists()) {
                filePath.mkdirs()
            }

            uncaughtExceptionListener = { _, _ ->
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }

        /**
         * set tag
         *
         * @throws IllegalArgumentException if the tag is null | empty
         */
        fun tag(tag: String): Builder {
            if (TextUtils.isEmpty(tag)) {
                throw IllegalArgumentException("unexpected tag")
            }

            this.tag = tag
            return this
        }

        /**
         * catch uncaught exception
         *
         */
        fun catchException(enable: Boolean, listener: ((thread: Thread?, ex: Throwable?) -> Unit)?): Builder {
            this.isCatchException = enable
            this.uncaughtExceptionListener = listener
            return this
        }

        /**
         * set the logger level
         *
         */
        fun loggerLevel(level: LoggerLevel): Builder {
            this.loggerLevel = level
            return this
        }

        /**
         * set file logger enable
         *
         */
        fun fileLogger(enable: Boolean): Builder {
            this.isFileLoggerEnable = enable
            return this
        }

        /**
         * set pack log period
         *
         * @throws IllegalArgumentException if the path is empty
         */
        fun fileDirectory(path: String): Builder {
            if (TextUtils.isEmpty(path)) {
                throw IllegalArgumentException("unexpected path")
            }

            val filePath = File(path)
            if (!filePath.exists() && !filePath.mkdirs()) {
                NLoggerConfig.getInstance().defaultLogger.error(tag, "can not make dir, please check permission")
            }

            this.fileDirectory = path
            return this
        }

        /**
         * set file logger formatter
         *
         * @throws IllegalArgumentException if the formatter is null
         */
        fun fileFormatter(formatter: Formatter?): Builder {
            if (null == formatter) {
                throw IllegalArgumentException("unexpected file formatter")
            }

            this.fileFormatter = formatter
            return this
        }

        /**
         * set the period of file expired
         *
         * @throws IllegalArgumentException if the period <= 0
         */
        fun expiredPeriod(period: Int): Builder {
            if (period <= 0) {
                throw IllegalArgumentException("unexpected period : $period")
            }

            expiredPeriod = period
            return this
        }

        fun build(): NLoggerConfig? {
            return mInstance!!.build(this)
        }
    }
}