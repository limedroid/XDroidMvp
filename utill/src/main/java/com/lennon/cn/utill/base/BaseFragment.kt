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
import com.lennon.cn.utill.bean.ToastRunnable
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.CustomProgressDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener

abstract class BaseFragment<P : BasePresent<*>,E:ViewBinding> : XFragment<P,E>(), BaseView<P,E> {
    private var isFragmentVisible: Boolean = false
    private var isReuseView: Boolean = false
    private var isFirstVisible: Boolean = true

    private var rootView: View? = null
    private var dialog: CustomProgressDialog? = null
    private var mActivity: Activity? = null
    override fun showLoading(visibility: Int) {
    }

    protected fun <T : View> findViewById(i: Int): T {
        return rootView!!.findViewById<T>(i)
    }

    private var isFirst = true
    private var stop = false

    private fun initVariable() {
        isFirstVisible = true
        isFragmentVisible = false
        rootView = null
        isReuseView = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userVisibleHint = userVisibleHint
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
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏
     *
     * @param isVisible true  不可见 -> 可见
     * false 可见  -> 不可见
     */
    private fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible) {
            if (!isFragmentVisible) {
                onVisible()
            }
        } else {
            if (isFragmentVisible) {
                onInvisible()
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        XLog.e(getName() + "  setUserVisibleHint isVisibleToUser=${isVisibleToUser} isFirstVisible=${isFirstVisible} isFragmentVisible=${isFragmentVisible}")
        if (rootView == null) {
            return
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible()
            isFirstVisible = false
            isFragmentVisible = true
        } else if (isVisibleToUser) {
            onFragmentVisibleChange(true)
            isFragmentVisible = true
            return
        } else if (isFragmentVisible) {
            isFragmentVisible = false
            onFragmentVisibleChange(false)
        }
    }

    /**
     * 在fragment首次可见时回调，可用于加载数据，防止每次进入都重复加载数据
     */
    private fun onFragmentFirstVisible() {
        onVisible()
    }

    protected fun isFragmentVisible(): Boolean {
        return isFragmentVisible
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
        stop = true
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
    }

    override fun getContext(): Context? {
        return if (mActivity != null) {
            mActivity
        } else {
            BaseApplication.getCuttureActivity()
        }
    }
}
