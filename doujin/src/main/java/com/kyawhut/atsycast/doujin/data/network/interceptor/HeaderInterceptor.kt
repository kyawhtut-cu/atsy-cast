package com.kyawhut.atsycast.doujin.data.network.interceptor

import android.content.Context
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.doujin.utils.Constants
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
    private val packageName: String by lazy {
        sh.get(Constants.EXTRA_PACKAGE_NAME, "")
    }
    private val appVersion: String by lazy {
        sh.get(Constants.EXTRA_APP_VERSION, "")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().addHeader(
                "App-Package", packageName
            ).addHeader(
                "App-Version", appVersion
            ).build()
        )
    }
}
