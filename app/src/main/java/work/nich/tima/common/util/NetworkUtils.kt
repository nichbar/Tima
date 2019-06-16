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
                return mNetworkInfo.isConnected
            }
        }
        return false
    }
}