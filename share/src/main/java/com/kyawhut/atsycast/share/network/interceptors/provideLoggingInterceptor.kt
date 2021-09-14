package com.kyawhut.atsycast.share.network.interceptors

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(
    HttpLoggingInterceptor.Level.BODY
)
