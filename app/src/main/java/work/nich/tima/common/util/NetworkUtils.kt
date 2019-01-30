package work.nich.tima.common.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isConnected
            }
        }
        return false
    }

    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }

    fun getConnectedType(context: Context?): String {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                when (mNetworkInfo.type) {
                    ConnectivityManager.TYPE_BLUETOOTH -> return "BLUETOOTH"
                    ConnectivityManager.TYPE_DUMMY -> return "DUMMY"
                    ConnectivityManager.TYPE_ETHERNET -> return "ETHERNET"
                    ConnectivityManager.TYPE_MOBILE -> return "MOBILE"
                    ConnectivityManager.TYPE_MOBILE_DUN -> return "MOBILE_DUN"
                    ConnectivityManager.TYPE_MOBILE_HIPRI -> return "MOBILE_HIPRI"
                    ConnectivityManager.TYPE_MOBILE_MMS -> return "MOBILE_MMS"
                    ConnectivityManager.TYPE_MOBILE_SUPL -> return "MOBILE_SUPL"
                    ConnectivityManager.TYPE_WIFI -> return "WIFI"
                    ConnectivityManager.TYPE_WIMAX -> return "WIMAX"
                    else -> {
                    }
                }
            }
        }
        return "NONE"
    }
}