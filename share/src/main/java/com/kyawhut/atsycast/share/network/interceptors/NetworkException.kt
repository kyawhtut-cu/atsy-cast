package com.kyawhut.atsycast.share.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor

class NetworkException(message: String) : Exception(message)

fun noConnectionInterceptor(context: Context) = Interceptor { chain ->
    if (!isInternetAvailable(context)) throw NetworkException("No Internet connection")
    else chain.proceed(chain.request())
}

@Suppress("DEPRECATION")
private fun isInternetAvailable(context: Context): Boolean {
    with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = activeNetwork ?: return false
            val actNw = getNetworkCapabilities(networkCapabilities) ?: return false
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else when (activeNetworkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> true
            ConnectivityManager.TYPE_ETHERNET -> true
            else -> false
        }
    }
}
