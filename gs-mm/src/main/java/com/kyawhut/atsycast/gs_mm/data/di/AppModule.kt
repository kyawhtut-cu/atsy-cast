package com.kyawhut.atsycast.gs_mm.data.di

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.GSMMAPI
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
 * @date 10/8/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Singleton
    fun provideAPI(@ApplicationContext context: Context): GSMMAPI {
        return createService(
            GSMMAPI::class,
            BuildConfig.SHEET_BASE_URL + BuildConfig.SCRIPT_ID,
            context
        )
    }
}
