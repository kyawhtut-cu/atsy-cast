package com.kyawhut.atsycast.free2air.data.di

import android.content.Context
import com.kyawhut.atsycast.free2air.data.network.Free2AirAPI
import com.kyawhut.atsycast.share.BuildConfig
import com.kyawhut.atsycast.share.network.providers.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    fun provideFree2AirAPI(@ApplicationContext context: Context): Free2AirAPI {
        return createService(
            Free2AirAPI::class,
            BuildConfig.SHEET_BASE_URL + BuildConfig.SCRIPT_ID,
            context
        )
    }
}
