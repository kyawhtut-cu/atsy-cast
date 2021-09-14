package com.kyawhut.atsycast.zcm.data.di

import android.content.Context
import com.kyawhut.atsycast.zcm.BuildConfig
import com.kyawhut.atsycast.zcm.data.network.ZCMAPI
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
    fun provideAPI(@ApplicationContext context: Context): ZCMAPI {
        return createService(ZCMAPI::class, BuildConfig.BASE_URL, context)
    }
}
