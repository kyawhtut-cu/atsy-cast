package com.kyawhut.atsycast.msys.data.di

import android.content.Context
import com.kyawhut.atsycast.msys.BuildConfig
import com.kyawhut.atsycast.msys.data.network.MsysAPI
import com.kyawhut.atsycast.share.network.providers.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideAPI(@ApplicationContext context: Context): MsysAPI {
        return createService(
            MsysAPI::class,
            BuildConfig.BASE_URL,
            context,
            isFollowRedirect = false
        )
    }
}
