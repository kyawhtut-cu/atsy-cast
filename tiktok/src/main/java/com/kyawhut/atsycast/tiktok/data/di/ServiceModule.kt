package com.kyawhut.atsycast.tiktok.data.di

import android.content.Context
import com.kyawhut.atsycast.share.BuildConfig
import com.kyawhut.atsycast.share.network.providers.createService
import com.kyawhut.atsycast.tiktok.data.network.TiktokAPI
import com.kyawhut.atsycast.tiktok.data.network.interceptors.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideAPI(@ApplicationContext context: Context): TiktokAPI {
        return createService(
            TiktokAPI::class, BuildConfig.SHEET_BASE_URL + BuildConfig.SCRIPT_ID, context, interceptors = listOf(
                HeaderInterceptor()
            )
        )
    }
}
