package com.kyawhut.atsycast.eporner.data.di

import android.content.Context
import com.kyawhut.atsycast.eporner.BuildConfig
import com.kyawhut.atsycast.eporner.data.network.EPornerAPI
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
    ): EPornerAPI {
        return createService(
            EPornerAPI::class,
            BuildConfig.BASE_URL,
            context,
        )
    }
}
