package com.kyawhut.atsycast.ets2mm.data.network.interceptor

import android.content.Context
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.get
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author kyawhtut
 * @date 9/15/21
 */
class HeaderInterceptor(context: Context) : Interceptor {

    private val sh by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
    private val apiKey: String by lazy {
        sh.get(Constants.EXTRA_API_KEY, "")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().header(
                "API-KEY", apiKey
            ).build()
        )
    }
}
