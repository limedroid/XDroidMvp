package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.text.TextUtils
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import cn.droidlover.xdroidmvp.cache.SharedPref
import cn.droidlover.xdroidmvp.log.XLog
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.NetProvider
import cn.droidlover.xdroidmvp.net.RequestHandler
import cn.droidlover.xdroidmvp.net.XApi
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.lennon.cn.utill.conf.Lennon
import com.lennon.cn.utill.utill.DensityUtils
import com.lennon.cn.utill.utill.TimeUtill
import com.lennon.cn.utill.utill.Utill
import lennon.com.utill.BuildConfig
import okhttp3.*
import java.io.BufferedReader
import java.io.FileReader
import java.util.*

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

    open fun init() {
        registerProvider()
//        val context = applicationContext
        // 获取当前包名
//        val packageName = context.packageName
        // 获取当前进程名
//        val processName = getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
//        val strategy = CrashReport.UserStrategy(context)
//        strategy.isUploadProcess = processName == null || processName == packageName
        // 初始化Bugly
//        CrashReport.initCrashReport(context, Lennon.getCrashID(), Lennon.isTest())
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

    override fun onCreate() {
        DensityUtils.setDensity(this)
        appliction = this
        super.onCreate()

    }

    companion object {
        private var list: ArrayList<Activity>? = null
        private val loger = true
        private var appliction: BaseApplication? = null
        private var cookiePersistor: SharedPrefsCookiePersistor? = null
        private var cookieJar: PersistentCookieJar? = null
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

//        fun init() {
//            NLoggerConfig.getInstance()
//                .builder()
//                .tag("APP")
//                .loggerLevel(LoggerLevel.INFO)
//                .fileLogger(true)
//                .fileDirectory(getDataFile() + "/logs")
//                .fileFormatter(SimpleFormatter())
//                .expiredPeriod(7)
//                .catchException(true) { _, throwable ->
//                    getCuttureActivity()?.finish()
//                    val t = if (throwable != null) {
//                        throwable
//                    } else {
//                        Throwable("未知异常")
//                    }
//                    t.printStackTrace()
////                if (getCuttureActivity() != null) {
//                    getCuttureActivity()!!.runOnUiThread {
//                        // 使用Toast来显示异常信息
//                        object : Thread() {
//                            override fun run() {
//                                Looper.prepare()
//                                Toast.makeText(
//                                    getCuttureActivity(),
//                                    "很抱歉,程序出现异常,即将退出,请联系客服：" + t.message,
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                Looper.loop()
//                            }
//                        }.start()
//                        //                        val dialog = CommonAlertDialog(getCuttureActivity())
////                        dialog.setTitle("非常抱歉")
////                        dialog.setCancelable(false)
////                        dialog.setCanceledOnTouchOutside(false)
////                        dialog.setMsg("程序出错了，请联系客服：" + t.message)
////                        dialog.setDialogListener(object : OnAlertDialogListener() {
////                            override fun onSure() {
////                                super.onSure()
////                                dialog.dismiss()
////                                XLog.e("uncaughtException", throwable!!)
////                                Lennon.restartApp()
////                                AppExit()
////                            }
////                        })
////                        dialog.show()
////                    }
////                } else {
//                        Toast.makeText(
//                            getCuttureActivity(),
//                            "非常抱歉，程序出错了，请联系客服：" + t.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        XLog.e("uncaughtException", t.message)
//                        Lennon.restartApp()
//                        AppExit()
//                    }
////                android.os.Process.killProcess(android.os.Process.myPid())
//                }.build()
//
//        }

        fun isLoger(): Boolean {
            return loger
        }

        fun context(): Context? {
            return appliction
        }

        fun registerProvider() {
            if (cookieJar == null) {
                cookiePersistor = SharedPrefsCookiePersistor(appliction!!)
                cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor!!)
            }
            XApi.registerProvider(object : NetProvider {

                override fun configInterceptors(): Array<Interceptor?> {
                    return arrayOfNulls(0)
                }

                override fun configHttps(builder: OkHttpClient.Builder) {

                }

                override fun configCookie(): CookieJar? {
                    return cookieJar
                }

                override fun configHandler(): RequestHandler {
                    return object : RequestHandler {
                        private var sendTime = 0L
                        private var url = ""
                        override fun onBeforeRequest(
                            request: Request,
                            chain: Interceptor.Chain
                        ): Request? {
                            val builder = chain.request().newBuilder()
                            builder.addHeader("Content-Type", "application/json;charset=utf-8")
                            builder.addHeader("Accept", "application/json;charset=utf-8")
                            sendTime = System.currentTimeMillis()
                            url = request.url().toString()
                            try {
                                return builder.build()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return null
                        }

                        override fun onAfterRequest(
                            response: Response,
                            chain: Interceptor.Chain
                        ): Response {
                            if (getCuttureActivity() != null) {
                                val end = System.currentTimeMillis()
                                val mss = end - sendTime
                                XLog.e("请求" + url + "耗时" + TimeUtill.formatDuring(mss))
                            }
                            return response
                        }
                    }
                }

                override fun configConnectTimeoutMills(): Long {
                    return 60000
                }

                override fun configReadTimeoutMills(): Long {
                    return 60000
                }

                override fun configLogEnable(): Boolean {
                    return BaseApplication.isLoger()
                }

                override fun handleError(error: NetError): Boolean {
                    return Lennon.handleNetError(error)
                }

                override fun dispatchProgressEnable(): Boolean {
                    return false
                }
            })
        }

        fun getDataFile(): String {
            Utill.makeDir(Lennon.getFilePathName())
            return Lennon.getFilePathName()
        }

        val cookie: List<Cookie>
            get() = cookiePersistor!!.loadAll()

        fun clearCookies() {
            cookieJar!!.clear()
        }

        fun setCookies(cookie: List<Cookie>) {}
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
                activity?.finish()
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

        fun addCookie(cookie: Cookie) {
            val l = cookiePersistor!!.loadAll()
            l.add(cookie)
            cookiePersistor!!.saveAll(l)
        }
    }

    open fun restart(context: Context) {

    }


}
