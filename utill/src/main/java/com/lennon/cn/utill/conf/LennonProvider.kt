package com.lennon.cn.utill.conf

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.utill.DensityUtils
import com.lennon.cn.utill.utill.Utill

abstract class LennonProvider {
    abstract fun appName(): String
    abstract fun requserLogin()
    abstract fun appExit()
    abstract fun getLogo(): Int
    abstract fun getTitleColor(): Int
    abstract fun clean()
    abstract fun isTest(): Boolean
    abstract fun getFilePathName(): String
    private var orientation: DensityUtils.Density = DensityUtils.Density.WIDTH
    fun setDensity(orientation: DensityUtils.Density) {
        this.orientation = orientation
    }

    open fun useDensity(activity: Activity) {
        DensityUtils.setOrientation(activity, orientation)
    }

    abstract fun handleNetError(error: NetError): Boolean
    abstract fun restartApp()
    abstract fun getFileProvide(): String
}