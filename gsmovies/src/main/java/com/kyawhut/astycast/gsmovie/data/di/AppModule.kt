package com.kyawhut.astycast.gsmovie.data.di

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.GSAPI
import com.kyawhut.atsycast.share.BuildConfig
import com.kyawhut.atsycast.share.network.providers.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/29/21
 */
@InstallIn(SingletonComponent::class)
@Module
internal object AppModule {

    @Provides
    @Singleton
    fun provideGSAPI(@ApplicationContext context: Context): GSAPI {
        return createService(
            GSAPI::class,
            BuildConfig.SHEET_BASE_URL + BuildConfig.SCRIPT_ID,
            context
        )
    }
}
