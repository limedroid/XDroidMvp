package com.lennon.cn.utill.conf

import android.app.Activity
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.utill.DensityUtils

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
    private var pixels: Float = 0.0f

    fun setPixels(pixels: Float) {
        this.pixels = pixels
    }

    fun setDensity(orientation: DensityUtils.Density) {
        this.orientation = orientation
    }

    open fun setDensity(activity: Activity) {
        if (useDensity()) {
            if (pixels > 0.0f) {
                DensityUtils.setOrientation(activity, orientation, pixels)
            } else {
                DensityUtils.setOrientation(activity, orientation)
            }
        }
    }

    open fun useDensity(): Boolean {
        return true
    }

    abstract fun handleNetError(error: NetError): Boolean
    abstract fun restartApp()
    abstract fun getFileProvide(): String
}