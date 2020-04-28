package com.lennon.cn.utill.conf

import android.app.Activity
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.utill.DensityUtils
import lennon.com.utill.R

class Lennon {
    companion object {

        var provider: LennonProvider? = null

        fun registProvider(provider: LennonProvider) {
            this.provider = provider
        }

        fun getFileProvide(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getFileProvide()
        }

        fun appName(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.appName()
        }

        fun requserLogin() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.requserLogin()
        }

        fun appExit() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.appExit()
        }

        fun getLogo(): Int {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getLogo()
        }

        fun getTitleColor(): Int {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return if (provider!!.getTitleColor() > 0) {
                provider!!.getTitleColor()
            } else {
                R.color.color_fd0202
            }
        }

        fun clean() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.clean()
        }

        fun isTest(): Boolean {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.isTest()
        }

        fun getFilePathName(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getFilePathName()
        }

        fun useDensity(activity: Activity) {
            if (provider == null) {
                DensityUtils.setDefault(activity)
            } else {
                provider!!.setDensity(activity)
            }
        }

        fun handleNetError(error: NetError): Boolean {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.handleNetError(error)
        }

        fun restartApp() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.restartApp()
        }
        fun setDensity(orientation: DensityUtils.Density) {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.setDensity(orientation)
        }
    }
}