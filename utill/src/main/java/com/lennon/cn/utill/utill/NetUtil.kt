package com.lennon.cn.utill.utill

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by lennon on 2017/12/25.
 */

object NetUtil {
    /**
     * 没有连接网络
     */
    val NETWORK_NONE = -1
    /**
     * 移动网络
     */
    val NETWORK_MOBILE = 0
    /**
     * 无线网络
     */
    val NETWORK_WIFI = 1

    fun getNetWorkState(context: Context): Int {
        // 得到连接管理器对象
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {

            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE
            }
        } else {
            return NETWORK_NONE
        }
        return NETWORK_NONE
    }
}
