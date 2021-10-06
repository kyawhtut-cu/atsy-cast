package com.kyawhut.atsycast.data.di

import android.content.Context
import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.share.BuildConfig
import com.kyawhut.atsycast.share.network.providers.createService
import com.kyawhut.atsycast.share.telegram.TelegramBotAPI
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.utils.CrashlyticsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideSheetAPI(@ApplicationContext context: Context): SheetAPI = createService(
        SheetAPI::class,
        BuildConfig.SHEET_BASE_URL + BuildConfig.SCRIPT_ID,
        context,
    )

    @Provides
    @Singleton
    fun provideTelegramBot(@ApplicationContext context: Context): TelegramBotAPI =
        TelegramBotAPI.provideTelegramBotAPI(
            context
        )

    @Provides
    @Singleton
    fun provideCrashlytics(crashlytics: CrashlyticsImpl): Crashlytics = crashlytics
}
