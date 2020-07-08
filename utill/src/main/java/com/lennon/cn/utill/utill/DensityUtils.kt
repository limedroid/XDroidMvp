package com.lennon.cn.utill.utill

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics

object DensityUtils {
    private var appDensity: Float = 0.toFloat()
    private var appScaledDensity: Float = 0.toFloat()
    private var appDisplayMetrics: DisplayMetrics? = null
    private var barHeight: Int = 0
//    val WIDTH = "width"
//    val HEIGHT = "height"

    enum class Density {
        WIDTH, HEIGHT;
    }

    /**
     * 在Application里初始化一下
     * @param application
     */
    fun setDensity(application: Application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.resources.displayMetrics
        //获取状态栏高度
        barHeight = getStatusBarHeight(application)

        if (appDensity == 0f) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics!!.density
            appScaledDensity = appDisplayMetrics!!.scaledDensity

            //添加字体变化的监听
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
    }

    /**
     * 此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
     * 在setContentView()之前设置
     * @param activity
     */
    fun setDefault(activity: Activity) {
        setAppOrientation(activity, Density.WIDTH)
    }

    /**
     * 此方法用于在某一个Activity里面更改适配的方向
     * 在setContentView()之前设置
     * @param activity
     * @param orientation
     */
    fun setOrientation(activity: Activity, orientation: Density) {
        setAppOrientation(activity, orientation)
    }

    /**
     * 此方法用于在某一个Activity里面更改适配的方向
     * 在setContentView()之前设置
     * @param activity
     * @param orientation
     */
    fun setOrientation(activity: Activity, orientation: Density, f: Float) {
        setAppOrientation(activity, orientation, f)
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * orientation:方向值,传入width或height
     */
    private fun setAppOrientation(activity: Activity, orientation: Density, f: Float) {

        val targetDensity: Float

        if (orientation == Density.HEIGHT) {
            targetDensity = (appDisplayMetrics!!.heightPixels - barHeight) / f//设计图的高度 单位:dp
        } else {
            targetDensity = appDisplayMetrics!!.widthPixels / f//设计图的宽度 单位:dp
        }

        val targetScaledDensity = targetDensity * (appScaledDensity / appDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         * 只修改Activity的density值
         */
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * orientation:方向值,传入width或height
     */
    private fun setAppOrientation(activity: Activity, orientation: Density) {
        val targetDensity: Float
        if (orientation == Density.HEIGHT) {
            targetDensity = (appDisplayMetrics!!.heightPixels - barHeight) / 1080f//设计图的高度 单位:dp
        } else {
            targetDensity = appDisplayMetrics!!.widthPixels / 420f//设计图的宽度 单位:dp
        }

        val targetScaledDensity = targetDensity * (appScaledDensity / appDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         * 只修改Activity的density值
         */
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
