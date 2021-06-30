package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import cn.droidlover.xdroidmvp.log.XLog
import cn.droidlover.xdroidmvp.mvp.XPresentation
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.bean.ToastRunnable
import com.lennon.cn.utill.conf.Lennon
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.CustomProgressDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener

abstract class BasePresentation<P : BasePresent<*>,E: ViewBinding>(context: Context, display: Display) :
    XPresentation<P,E>(context, display), BaseView<P> {
    private var TAG = javaClass.simpleName
    private var dialog: CustomProgressDialog? = null
    private var listener: ChangeListener? = null

    interface ChangeListener {
        fun onCancel(basePresentation: BasePresentation<*,*>)
        fun onDismiss(basePresentation: BasePresentation<*,*>)
        fun onShow(basePresentation: BasePresentation<*,*>)
    }

    fun setListener(listener: ChangeListener?) {
        this.listener = listener
    }

    override fun showProgressDialog(msg: String) {
        if (dialog != null) dialog!!.dismiss()
        dialog = CustomProgressDialog(getContext())
        dialog!!.setMessage(msg)
        dialog!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnCancelListener { listener?.onCancel(this) }
        setOnDismissListener { listener?.onDismiss(this) }
        setOnShowListener { listener?.onShow(this) }
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

    override fun toast(msg: String, second: Int, runnable: ToastRunnable) {
        val dialog = CommonAlertDialog(getContext())
        dialog.setMsg(msg)
        dialog.disableCancle()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                dialog.dismiss()
                runnable.run()
            }
        })
        dialog.show()
        Handler().postDelayed(Runnable {
            dialog.dismiss()
            runnable.run()
        }, second * 1000L)
        Toast.makeText(getContext(), msg, second)
            .show()
    }

    override fun toast(msg: String, runnable: ToastRunnable) {
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
        Handler().postDelayed(Runnable {
            dialog.dismiss()
            runnable.run()
        }, 2000L)
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

}