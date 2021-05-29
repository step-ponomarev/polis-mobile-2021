package ru.mail.polis.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

abstract class NetworkUtils {
    enum class NetworkState {
        UNAVAILABLE,
        WIFI,
        MOBILE_DATA
    }

    companion object {
        fun getNetworkStatus(context: Context): NetworkState {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo: NetworkInfo = cm.activeNetworkInfo ?: return NetworkState.UNAVAILABLE

            return if (networkInfo.type == ConnectivityManager.TYPE_WIFI)
                NetworkState.WIFI
            else NetworkState.MOBILE_DATA
        }
    }
}
