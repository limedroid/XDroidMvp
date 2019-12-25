package com.lennon.cn.utill.utill

object TimeUtill {
    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    fun formatDuring(mss: Long): String {
        val days = mss / (1000 * 60 * 60 * 24)
        val hours = mss % (1000 * 60 * 60 * 24) / (1000 * 60 * 60)
        val minutes = mss % (1000 * 60 * 60) / (1000 * 60)
        val seconds = mss % (1000 * 60) / 1000
        val millis = mss and 1000
        var s = ""
        if (days > 0) {
            s += days.toString() + "天"
        }
        if (hours > 0) {
            s += hours.toString() + "小时"
        }
        if (minutes > 0) {
            s += minutes.toString() + "分钟"
        }
        if (seconds > 0) {
            s += seconds.toString() + "秒"
        }
        if (millis > 0) {
            s += millis.toString() + "毫秒"
        }
        return s
    }

}