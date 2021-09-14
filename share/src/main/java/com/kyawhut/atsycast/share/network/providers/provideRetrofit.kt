package com.kyawhut.atsycast.share.network.providers

import android.content.Context
import com.kyawhut.atsycast.share.network.utils.ToStringConverterFactory
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlin.reflect.KClass

internal fun provideRetrofit(
    okHttpClient: OkHttpClient,
    url: String,
    isNeedStringConverterFactory: Boolean
): Retrofit = Retrofit.Builder()
    .baseUrl(url).apply {
        if (isNeedStringConverterFactory) addConverterFactory(ToStringConverterFactory())
    }
    .addConverterFactory(provideGson())
    .client(okHttpClient)
    .build()

fun <S : Any> createService(
    service: KClass<S>,
    baseURL: String,
    context: Context,
    isNeedStringConverterFactory: Boolean = false,
    isFollowRedirect: Boolean = true,
    interceptors: List<Interceptor> = emptyList(),
    authenticators: List<Authenticator> = emptyList()
): S {
    return provideRetrofit(
        provideOkHttpClient(context, isFollowRedirect, interceptors, authenticators),
        baseURL,
        isNeedStringConverterFactory
    ).create(service.java)
}
