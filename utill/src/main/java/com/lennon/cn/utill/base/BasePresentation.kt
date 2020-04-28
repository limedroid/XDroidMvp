package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.view.Display
import android.widget.Toast
import cn.droidlover.xdroidmvp.log.XLog
import cn.droidlover.xdroidmvp.mvp.XPresentation
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.CustomProgressDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener

abstract class BasePresentation<P : BasePresent<*>>(context: Context, display: Display) :
    XPresentation<P>(context, display), BaseView<P> {
    private var TAG = javaClass.simpleName
    private var dialog: CustomProgressDialog? = null
    override fun showProgressDialog(msg: String) {
        if (dialog != null) dialog!!.dismiss()
        dialog = CustomProgressDialog(getContext())
        dialog!!.setMessage(msg)
        dialog!!.show()
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun closeProgressDialog() {
        if (null != dialog) dialog!!.dismiss()
    }

    override fun toast(msg: String, second: Int) {
        val dialog = CommonAlertDialog(context)
        dialog.setMsg(msg)
        dialog.disableCancle()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        dialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                dialog.dismiss()
            }
        })
        Toast.makeText(getContext(), msg, second)
            .show()
    }

    override fun toast(msg: String) {
        toast(msg, true)
    }

    override fun toast(msg: String, runnable: Runnable) {
        val dialog = CommonAlertDialog(getContext())
        dialog.setMsg(msg)
        dialog.disableCancle()
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                dialog.dismiss()
                runnable.run()
            }
        })
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT)
            .show()
    }

    override fun toast(msg: String, flag: Boolean) {
        if (flag) {
            val dialog = CommonAlertDialog(context)
            dialog.setMsg(msg)
            dialog.disableCancle()
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            dialog.setDialogListener(object : OnAlertDialogListener() {
                override fun onSure() {
                    super.onSure()
                    dialog.dismiss()
                }
            })
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT)
            .show()
    }

    override fun showLoading(visibility: Int) {
    }

    override fun showLoadingError(errorType: NetError) {

    }

    override fun getActivity(): Activity? {
        return null
    }

    override fun bindEvent() {

    }

    override fun getOptionsMenuId(): Int {
        return 0
    }

    override fun onRefresh(bRefresh: Boolean) {
        XLog.e("$TAG:onRefresh()")
    }
}