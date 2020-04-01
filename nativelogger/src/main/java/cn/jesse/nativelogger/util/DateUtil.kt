package cn.jesse.nativelogger.util

import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期工具类
 *
 * @author Jesse
 */
object DateUtil {
    private val TEMPLATE_DATE = "yyyy-MM-dd"

    /**
     * 以yyyy-MM-dd格式获取当前时间
     */
    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat(TEMPLATE_DATE)
        return sdf.format(System.currentTimeMillis())
    }


    fun DeleteFileDate(days: Int, file: File) {
        var start = getCurrentDateBefor(days)
        var end = getCurrentDate()
        val sdf = SimpleDateFormat(TEMPLATE_DATE)
        var dStart: Date?
        var dEnd: Date?
        try {
            dStart = sdf.parse(start)
            dEnd = sdf.parse(end)
            val dateList = findDates(dStart!!, dEnd!!)
            var f = file.listFiles()
            if (f != null && !f.isEmpty())
                for (f1 in f) {
                    var flag = false
                    for (date in dateList) {
                        if (f1.name.startsWith(sdf.format(date))) {
                            flag=true
                        }
                    }
                    if (!flag){
                        f1.delete()
                    }
                }
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    //JAVA获取某段时间内的所有日期
    fun findDates(dStart: Date, dEnd: Date): List<Date> {
        val cStart = Calendar.getInstance()
        cStart.time = dStart
        val dateList = ArrayList<Date>()
        //别忘了，把起始日期加上
        dateList.add(dStart)
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.time)) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1)
            dateList.add(cStart.time)
        }
        return dateList
    }

    fun isValidDate(str: String): Boolean {
        var convertSuccess = true
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        val format = SimpleDateFormat(TEMPLATE_DATE)
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.isLenient = false
            format.parse(str)
        } catch (e: ParseException) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false
        }

        return convertSuccess
    }

    fun getCurrentDateBefor(days: Int): String {
        val calendar = Calendar.getInstance()
//今天
        System.out.println(calendar.getTime())
//10天前
        calendar.add(Calendar.DATE, -days)
        System.out.println(calendar.getTime())
        val sdf = SimpleDateFormat(TEMPLATE_DATE)
        return sdf.format(calendar.time)
    }

    /**
     * @Title: isInDate
     * @Description: 判断一个时间段（YYYY-MM-DD）是否在一个区间
     * @param @param date
     * @param @param strDateBegin
     * @param @param strDateEnd
     * @param @return    设定文件
     * @return boolean    返回类型
     * @throws
     */
    fun isInDate(strDate: String, strDateBegin: String, strDateEnd: String): Boolean {
        // 截取当前时间年月日 转成整型
        val tempDate = Integer.parseInt(strDate.replace("-", ""))
        // 截取开始时间年月日 转成整型
        val tempDateBegin = Integer.parseInt(strDateBegin.replace("-", ""))
        // 截取结束时间年月日   转成整型
        val tempDateEnd = Integer.parseInt(strDateEnd.replace("-", ""))
        return (tempDate in tempDateBegin..tempDateEnd)
    }
}
