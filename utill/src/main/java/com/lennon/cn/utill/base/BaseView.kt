package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import cn.droidlover.xdroidmvp.mvp.IView
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.bean.ToastRunnable

interface BaseView<P : BasePresent<*>?> :IView<P> {

    fun toast(msg: String, second: Int)
    fun toast(msg: String)
    fun toast(msg: String, runnable: ToastRunnable)
    fun toast(msg: String, second: Int, runnable: ToastRunnable)
    fun toast(msg: String, flag: Boolean)
    fun showLoading(visibility: Int)
    fun showLoadingError(errorType: NetError)
    fun getContext(): Context?

    fun getActivity(): Activity?
}