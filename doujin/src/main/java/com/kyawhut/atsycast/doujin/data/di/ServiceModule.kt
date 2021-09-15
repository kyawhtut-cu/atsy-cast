package com.kyawhut.atsycast.doujin.data.di

import android.content.Context
import com.kyawhut.atsycast.doujin.BuildConfig
import com.kyawhut.atsycast.doujin.data.network.DoujinAPI
import com.kyawhut.atsycast.doujin.data.network.interceptor.HeaderInterceptor
import com.kyawhut.atsycast.share.network.providers.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@InstallIn(SingletonComponent::class)
@Module
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideAPI(
        @ApplicationContext context: Context
    ): DoujinAPI {
        return createService(
            DoujinAPI::class,
            BuildConfig.BASE_URL,
            context,
            interceptors = listOf(
                HeaderInterceptor(context)
            )
        )
    }
}
