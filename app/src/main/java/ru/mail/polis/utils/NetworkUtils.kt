package ru.mail.polis.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils private constructor() {
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

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            var result = false
            if (activeNetwork != null) {
                result = activeNetwork.isConnectedOrConnecting
            }
            return result
        }
    }
}
