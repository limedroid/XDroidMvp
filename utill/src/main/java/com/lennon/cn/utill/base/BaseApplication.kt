package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import cn.droidlover.xdroidmvp.cache.SharedPref
import cn.droidlover.xdroidmvp.log.XLog
import com.lennon.cn.utill.conf.Lennon
import com.lennon.cn.utill.utill.DensityUtils
import com.lennon.cn.utill.utill.Utill
import lennon.com.utill.BuildConfig
import java.io.BufferedReader
import java.io.FileReader
import java.util.*
import java.util.logging.SimpleFormatter

/**
 * Created by lennon on 2017/7/26.
 */
open class BaseApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f)
        //非默认值
            resources
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1f) {//非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults()//设置默认
            res.updateConfiguration(newConfig, res.displayMetrics)
        }
        return res
    }

    override fun onCreate() {
        DensityUtils.setDensity(this)
        appliction = this
        super.onCreate()
//        NLoggerConfig.getInstance()
//            .builder()
//            .tag("APP")
//            .loggerLevel(LoggerLevel.INFO)
//            .fileLogger(true)
//            .fileDirectory(getDataFile() + "/logs")
//            .fileFormatter(SimpleFormatter())
//            .expiredPeriod(7)
//            .catchException(true) { _, throwable ->
//                getCuttureActivity()?.finish()
//                val t = if (throwable != null) {
//                    throwable
//                } else {
//                    Throwable("未知异常")
//                }
//                t.printStackTrace()
//                getCuttureActivity()!!.runOnUiThread {
//                    // 使用Toast来显示异常信息
//                    object : Thread() {
//                        override fun run() {
//                            Looper.prepare()
//                            Toast.makeText(
//                                getCuttureActivity(),
//                                "很抱歉,程序出现异常,即将退出,请前往 我的 上传日志并联系客服：" + t.message,
//                                Toast.LENGTH_LONG
//                            ).show()
//                            Looper.loop()
//                        }
//                    }.start()
//
//                    Toast.makeText(
//                        getCuttureActivity(),
//                        "非常抱歉，程序出错了，请前往 我的 上传日志并联系客服：" + t.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    XLog.e("uncaughtException", t.message)
//                    Lennon.restartApp()
//                    AppExit()
//                }
//            }.build()
    }

    companion object {
        private var list: ArrayList<Activity>? = null
        private val loger = true
        private var appliction: BaseApplication? = null
        private var test = true
        /**
         * 获取进程号对应的进程名
         *
         * @param pid 进程号
         * @return 进程名
         */
        fun getProcessName(pid: Int): String? {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(FileReader("/proc/" + pid + "/cmdline"))
                var processName = reader.readLine()
                if (!TextUtils.isEmpty(processName)) {
                    processName = processName.trim()
                }
                return processName
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            } finally {
                try {
                    if (reader != null) {
                        reader.close()
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
            return null
        }

        fun isLoger(): Boolean {
            return loger
        }

        fun context(): Context? {
            return appliction
        }


        fun getDataFile(): String {
            Utill.makeDir(Lennon.getFilePathName())
            return Lennon.getFilePathName()
        }

        fun addActivity(activity: Activity) {
            if (list == null)
                list = ArrayList()
            list!!.add(activity)
        }

        fun exitActivity(activity: Activity?) {
            if (list != null) {
//                list!!.remove(activity)
                list!!.remove(activity)
            }
        }


        fun getCuttureActivity(): Activity? {
            return if (list == null || list!!.size == 0) null else list!![list!!.size - 1]
        }


        fun AppExit() {
            Lennon.appExit()
            for (activity in list!!) {
                activity.finish()
            }
            System.exit(0)
        }

        fun clean() {
            Lennon.clean()
            SharedPref.getInstance(context()).clear()
        }


        fun resources(): Resources {
            return context()!!.resources
        }


        fun finshActivity() {
            for (activity in list!!) {
                activity.finish()
            }
        }

        fun restart(context: Context) {
            if (list != null)
                list!!.clear()
            getAppliction()?.restart(context)
        }

        fun getAppliction(): BaseApplication? {
            return appliction
        }

        fun isTest(): Boolean {
            return test && BuildConfig.DEBUG
        }
    }

    open fun restart(context: Context) {

    }


}
