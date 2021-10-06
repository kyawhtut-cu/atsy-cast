package com.kyawhut.atsycast.share.network.utils

import com.kyawhut.atsycast.share.utils.Crashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author kyawhtut
 * @date 7/31/21
 */
data class NetworkResponse<T>(
    val networkStatus: NetworkStatus,
    var data: T? = null,
    var error: NetworkError? = null
) {
    companion object {
        suspend fun <T> loading(callback: (NetworkResponse<T>) -> Unit) {
            NetworkResponse<T>(NetworkStatus.LOADING).post(callback)
        }

        suspend fun <T> success(data: T, callback: (NetworkResponse<T>) -> Unit) {
            NetworkResponse(NetworkStatus.SUCCESS, data).post(callback)
        }

        suspend fun <T> error(error: NetworkError?, callback: (NetworkResponse<T>) -> Unit) {
            NetworkResponse<T>(NetworkStatus.ERROR, error = error).post(callback)
        }
    }

    val isSuccess: Boolean get() = networkStatus == NetworkStatus.SUCCESS

    val isLoading: Boolean get() = networkStatus == NetworkStatus.LOADING

    val isError: Boolean get() = networkStatus == NetworkStatus.ERROR

    suspend fun post(callback: (NetworkResponse<T>) -> Unit) {
        withContext(Dispatchers.Main) {
            callback(this@NetworkResponse)
        }
    }
}

sealed class NetworkStatus {
    object LOADING : NetworkStatus()
    object SUCCESS : NetworkStatus()
    object ERROR : NetworkStatus()
}

suspend fun <T> execute(execute: suspend () -> T): NetworkResponse<T> {
    return try {
        val response = execute()
        NetworkResponse(NetworkStatus.SUCCESS, response)
    } catch (e: Exception) {
        with(e.error) {
            NetworkResponse(NetworkStatus.ERROR, error = this)
        }
    }
}

suspend fun <T> execute(crashlytics: Crashlytics, execute: suspend () -> T): NetworkResponse<T> {
    return try {
        val response = execute()
        NetworkResponse(NetworkStatus.SUCCESS, response)
    } catch (e: Exception) {
        crashlytics.sendCrashlytics(e)
        with(e.error) {
            NetworkResponse(NetworkStatus.ERROR, error = this)
        }
    }
}
