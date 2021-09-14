package com.kyawhut.atsycast.share.network.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.network.interceptors.NetworkException
import retrofit2.HttpException
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.UnknownHostException

data class NetworkError(var message: String = "", var resId: Int = 0, var statusCode: Int = 200) {

    companion object {
        fun NetworkError?.printStackTrace() {
            if (this == null) return
            Timber.e("Network Error => %s", this)
        }
    }

    override fun toString(): String {
        return """
            message     =>  $message
            resId       =>  $resId
            statusCode  =>  $statusCode
        """.trimIndent()
    }
}

data class UnexpectedError(val _message: String) : Exception(_message)

private const val DEFAULT_ERROR_MESSAGE = "Unknown error found"

private val String?.errorMessage: String
    get() {
        return this.toNetworkError()?.message ?: DEFAULT_ERROR_MESSAGE
    }

private fun String?.toNetworkError(): NetworkError? {
    return if (this.isNullOrEmpty()) null
    else try {
        Gson().fromJson(this, NetworkError::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }
}

val Exception.error: NetworkError
    get() {
        this.printStackTrace()
        return NetworkError().apply {
            when (this@error) {
                is HttpException -> {
                    statusCode = code()
                    when (code()) {
                        500 -> resId = R.string.lbl_internal_error
                        else -> {
                            val errorResponse: String? = response()?.errorBody()?.string()
                            message = (this@error.message() ?: "Unknown error found").takeIf {
                                errorResponse == null
                            } ?: errorResponse.errorMessage
                            resId = if (code() == 400 && errorResponse == null)
                                R.string.lbl_error_400 else if (code() == 401) R.string.lbl_error_401 else 0
                        }
                    }
                }
                is NetworkException -> {
                    statusCode = 200
                    resId = R.string.lbl_no_internet_connection
                }
                is InterruptedIOException -> {
                    statusCode = 500
                    resId = R.string.lbl_connection_time_out
                }
                is UnexpectedError -> {
                    statusCode = 500
                    message = this@error._message
                }
                is UnknownHostException -> {
                    statusCode = 500
                    resId = R.string.lbl_no_internet_connection
                }
                else -> {
                    statusCode = 500
                    resId = R.string.lbl_client_code_error
                }
            }
        }
    }
