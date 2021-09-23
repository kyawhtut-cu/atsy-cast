package com.kyawhut.atsycast.twod.data.network.interceptor

import com.kyawhut.atsycast.twod.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author kyawhtut
 * @date 9/20/21
 */
class HeaderInterceptor : Interceptor {

    private val apiKey: String
        get() = Credentials.basic(BuildConfig.USER_NAME, BuildConfig.PASSOWRD)

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().header(
                "Authorization", apiKey
            ).build()
        )
    }
}
