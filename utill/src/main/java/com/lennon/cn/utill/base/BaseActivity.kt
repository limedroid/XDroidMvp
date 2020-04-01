package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import cn.droidlover.xdroidmvp.log.XLog
import cn.droidlover.xdroidmvp.mvp.XActivity
import cn.droidlover.xdroidmvp.net.NetError

import com.lennon.cn.utill.conf.Lennon
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.CustomProgressDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener
import com.lennon.cn.utill.utill.AppStatusManager
import com.lennon.cn.utill.utill.StatusBarUtil
import com.lennon.cn.utill.utill.Utill
import java.lang.Exception


abstract class BaseActivity<P : BasePresent<*>> : XActivity<P>(), BaseView<P> {
    private var TAG = javaClass.simpleName
    private var dialog: CustomProgressDialog? = null

    /** * 设置当前窗口亮度 * @param brightness */
    fun setWindowBrightness(brightness: Float) {
        val window = window
        val lp = window.attributes
        lp.screenBrightness = brightness
        window.attributes = lp
    }

    /**
     * 获取当前屏幕亮度
     */
    fun getBrightness(): Int {
        val lp = window?.attributes
        if (lp != null) {
            //screenBrightness 默认为-1
            if (lp.screenBrightness < 0) return 0
            return (lp.screenBrightness * 255).toInt()
        }
        return 0
    }

    /**
     * 隐藏虚拟按键，并且设置成全屏
     */
    fun hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
            decorView.systemUiVisibility = uiOptions
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }
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
        val dialog = CommonAlertDialog(this)
        dialog.setMsg(msg)
        dialog.disableCancle()
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
            val dialog = CommonAlertDialog(this)
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

    override fun showLoading(visibility: Int) {
    }

    override fun showLoadingError(errorType: NetError) {

    }

    override fun getContext(): Context {
        return this
    }

    override fun onRefresh(bRefresh: Boolean) {
        XLog.e("$TAG:onRefresh()")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        XLog.e("$TAG:onCreate")
//        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
        Lennon.useDensity(this)
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        super.onCreate(savedInstanceState)
        if (checkAppStatus()) {
            BaseApplication.restart(this)
            finish()
        } else {
            BaseApplication.addActivity(this)
            try {
                if ((this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) != null) {
                    StatusBarUtil.setColorNoTranslucent(
                        this,
                        Utill.getColor(resources, getMTitleColor())
                    )
                    //                StatusBarUtil.setImmersiveStatusBar(this, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun getMTitleColor(): Int {
        return Lennon.getTitleColor()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        XLog.e("$TAG:onActivityResult($requestCode,$resultCode)")
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onStart() {
        super.onStart()
        XLog.e("$TAG:onStart()")
    }

    override fun onDestroy() {
        BaseApplication.exitActivity(this)
        XLog.e("$TAG:onDestroy()")
        super.onDestroy()
    }

    override fun onPause() {
        XLog.e("$TAG:onPause()")
        super.onPause()
    }

    override fun onResume() {
        XLog.e("$TAG:onResume()")
        super.onResume()
    }

    override fun onBackPressed() {
        XLog.e("$TAG:onBackPressed()")
        super.onBackPressed()
    }

    override fun onPostResume() {
        XLog.e("$TAG:onPostResume()")
        super.onPostResume()
    }

    override fun onStop() {
        XLog.e("$TAG:onStop()")
        super.onStop()
    }

    private fun checkAppStatus(): Boolean {
        XLog.e(TAG + ":" + AppStatusManager.getInstance().appStatus)
        return AppStatusManager.getInstance().appStatus == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED
    }

    override fun getActivity(): Activity {
        return this
    }

}
