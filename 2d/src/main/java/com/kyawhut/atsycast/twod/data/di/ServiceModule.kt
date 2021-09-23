package com.kyawhut.atsycast.twod.data.di

import android.content.Context
import com.kyawhut.atsycast.share.network.providers.createService
import com.kyawhut.atsycast.twod.BuildConfig
import com.kyawhut.atsycast.twod.data.network.TwoDAPI
import com.kyawhut.atsycast.twod.data.network.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/20/21
 */
@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    @Singleton
    fun provide2DAPI(@ApplicationContext context: Context): TwoDAPI {
        return createService(
            TwoDAPI::class,
            BuildConfig.BASE_URL,
            context,
            interceptors = listOf(HeaderInterceptor())
        )
    }
}
