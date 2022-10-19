package com.kyawhtut.atsycast.telegram.utils

import timber.log.Timber

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
internal sealed class Response<out R> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: TelegramException) : Response<Nothing>()
}

internal val Response<*>?.isSuccess get() = this is Response.Success

internal val Response<*>?.isError get() = this is Response.Error

internal inline infix fun <T> Response<T>.success(listener: (data: T) -> Unit): Response<T> {
    if (this is Response.Success && this.data != null) {
        try {
            listener.invoke(this.data)
        } catch (e: Exception) {
            Timber.d(e, "Response<T>.success")
        }
    }
    return this
}

internal inline infix fun <T> Response<T>.error(listener: (data: TelegramException) -> Unit): Response<T> {
    if (this is Response.Error) {
        try {
            listener.invoke(this.exception)
        } catch (e: Exception) {
            Timber.d(e, "Response<T>.error")
        }
    }
    return this
}

internal inline infix fun <T> Response<T>.done(listener: () -> Unit): Response<T> {
    listener.invoke()
    return this
}

internal inline infix fun <T, R> Response<T>.map(listener: (data: T) -> R): Response<R> {
    return when (this) {
        is Response.Success -> try {
            Response.Success(listener(this.data))
        } catch (e: Exception) {
            Timber.d(e, "Response<T>.map")
            Response.Error(
                TelegramException(
                    code = -1,
                    message = e.message ?: "Code error"
                )
            )
        }

        is Response.Error -> this
    }
}

internal suspend infix fun <T, R> Response<T>.flatMap(listener: suspend (data: T) -> Response<R>): Response<R> {
    return when (this) {
        is Response.Success -> try {
            listener(this.data)
        } catch (e: Exception) {
            Timber.d(e, "Response<T>.flatMap")
            Response.Error(
                TelegramException(
                    code = -1,
                    message = e.message ?: "Code error"
                )
            )
        }

        is Response.Error -> this
    }
}
