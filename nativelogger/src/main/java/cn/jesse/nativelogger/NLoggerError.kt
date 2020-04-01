package cn.jesse.nativelogger

/**
 * NLogger 通用错误类
 *
 * @author Jesse
 */
class NLoggerError : Error {

    /**
     * Constructs a new {@code Error} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage
     *            the detail message for this error.
     */
    constructor(detailMessage : String) : super(detailMessage)

    /**
     * Constructs a new {@code Error} with the current stack trace and the
     * specified cause.
     *
     * @param throwable
     *            the cause of this error.
     */
    constructor(throwable : Throwable) : super(throwable)

    /**
     * Constructs a new {@code Error} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage
     *            the detail message for this error.
     * @param throwable
     *            the cause of this error.
     */
    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable)
}