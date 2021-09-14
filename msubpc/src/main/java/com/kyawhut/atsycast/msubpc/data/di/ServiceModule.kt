package com.kyawhut.atsycast.msubpc.data.di

import android.content.Context
import com.kyawhut.atsycast.msubpc.BuildConfig
import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.share.network.providers.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/2/21
 */
@InstallIn(SingletonComponent::class)
@Module
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideAPIService(@ApplicationContext context: Context): MSubAPI {
        return createService(MSubAPI::class, BuildConfig.BASE_URL, context)
    }
}
