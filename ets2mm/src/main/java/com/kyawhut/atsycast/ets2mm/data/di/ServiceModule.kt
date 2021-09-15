package com.kyawhut.atsycast.ets2mm.data.di

import android.content.Context
import com.kyawhut.atsycast.ets2mm.BuildConfig
import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.interceptor.HeaderInterceptor
import com.kyawhut.atsycast.share.network.providers.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideAPI(@ApplicationContext context: Context): Et2API {
        return createService(
            Et2API::class,
            BuildConfig.BASE_URL,
            context,
            interceptors = listOf(HeaderInterceptor(context))
        )
    }
}
