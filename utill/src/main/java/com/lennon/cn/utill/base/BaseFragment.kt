@file:Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")

package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import cn.droidlover.xdroidmvp.log.XLog

import cn.droidlover.xdroidmvp.mvp.XFragment
import cn.droidlover.xdroidmvp.net.NetError
import com.lennon.cn.utill.bean.ToastRunnable
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.CustomProgressDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener

abstract class BaseFragment<P : BasePresent<*>, E : ViewBinding> : XFragment<P, E>(),
    BaseView<P> {

    private var rootView: View? = null
    private var dialog: CustomProgressDialog? = null
    private var mActivity: Activity? = null
    override fun showLoading(visibility: Int) {
    }

    override fun showLoadingError(errorType: NetError) {

    }
    protected fun <T : View> findViewById(i: Int): T {
        return rootView!!.findViewById<T>(i)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        XLog.e(getName() + "  onCreateView")
        rootView = super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

    /**
     * 可见时的回调方法
     */
    open fun onVisible() {
        XLog.e(getName() + "  onVisible")
    }

    /**
     * 不可见时的回调方法
     */
    fun onInvisible() {
        XLog.e(getName() + "  onInvisible")
    }

    override fun showProgressDialog(msg: String) {
        if (dialog != null)
            dialog!!.dismiss()
        dialog = CustomProgressDialog(getContext())
        dialog!!.setMessage(msg)
        dialog!!.show()
    }


    override fun onResume() {
        super.onResume()
        XLog.e(getName() + "  onResume")
        onVisible()
    }


    protected fun getName(): String {
        return javaClass.simpleName
    }

    override fun closeProgressDialog() {
        if (null != dialog)
            dialog!!.dismiss()
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
        Handler().postDelayed({
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
        dialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                dialog.dismiss()
                runnable.run()
            }
        })
        Handler().postDelayed({
            dialog.dismiss()
            runnable.run()
        }, 2000)
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT)
            .show()
    }

    override fun toast(msg: String, flag: Boolean) {
        if (flag) {
            val dialog = CommonAlertDialog(getContext())
            dialog.setMsg(msg)
            dialog.disableCancle()
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

    override fun toast(msg: String, second: Int) {
        val dialog = CommonAlertDialog(getContext())
        dialog.setMsg(msg)
        dialog.disableCancle()
        dialog.show()
        dialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                dialog.dismiss()
            }
        })
        Toast.makeText(getContext(), msg, second).show()
    }

    override fun onStop() {
        XLog.e(getName() + "  onStop")
        super.onStop()
    }

    override fun onAttach(activity: Activity) {
        XLog.e(getName() + "  onAttach")
        super.onAttach(activity)
        mActivity = activity
    }

    override fun onPause() {
        XLog.e(getName() + "  onPause")
        super.onPause()
        onInvisible()
    }

    override fun getContext(): Context? {
        return if (mActivity != null) {
            mActivity
        } else {
            BaseApplication.getCuttureActivity()
        }
    }
}
