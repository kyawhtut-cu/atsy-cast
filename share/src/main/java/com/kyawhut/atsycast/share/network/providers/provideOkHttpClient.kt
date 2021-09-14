package com.kyawhut.atsycast.share.network.providers

import android.content.Context
import com.kyawhut.atsycast.share.BuildConfig
import com.kyawhut.atsycast.share.network.interceptors.provideLoggingInterceptor
import com.kyawhut.atsycast.share.network.utils.UnsafeOkHttpClient
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

fun provideOkHttpClient(
    context: Context,
    isFollowRedirect: Boolean = true,
    interceptors: List<Interceptor> = emptyList(),
    authenticators: List<Authenticator> = emptyList()
): OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    .callTimeout(WAIT_TIME, TimeUnit.SECONDS)
    .readTimeout(WAIT_TIME, TimeUnit.SECONDS)
    .connectTimeout(CONN_TIME, TimeUnit.SECONDS)
    .writeTimeout(WAIT_TIME, TimeUnit.SECONDS).apply {
        followRedirects(isFollowRedirect)
        interceptors.forEach { addInterceptor(it) }
        authenticators.forEach { authenticator(it) }
        // adding logging and chuck
        if (BuildConfig.DEBUG) {
            addInterceptor(provideLoggingInterceptor())
            addInterceptor(ChuckInterceptor(context))
        }
    }.build()

private const val WAIT_TIME = 60L
private const val CONN_TIME = 60L
