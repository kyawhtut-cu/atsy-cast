package com.kyawhtut.atsycast.telegram.data.di

import android.content.Context
import android.os.Build
import com.kyawhtut.atsycast.telegram.BuildConfig
import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.data.telegram.TelegramImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.drinkless.td.libcore.telegram.TdApi
import java.util.*
import javax.inject.Singleton

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
@InstallIn(SingletonComponent::class)
@Module
internal object AppModule {

    @Singleton
    @Provides
    fun provideLibTdParameters(@ApplicationContext context: Context): TdApi.TdlibParameters {
        return TdApi.TdlibParameters().apply {
            apiId = BuildConfig.TELEGRAM_APP_ID
            apiHash = BuildConfig.TELEGRAM_APP_HASH
            useMessageDatabase = true
            useSecretChats = true
            systemLanguageCode = Locale.getDefault().language
            useFileDatabase = true
            databaseDirectory = context.filesDir.absolutePath
            deviceModel = Build.MODEL
            systemVersion = Build.VERSION.RELEASE
            applicationVersion = try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: Exception) {
                "1.0.0"
            }
            enableStorageOptimizer = true
        }
    }

    @Provides
    @Singleton
    fun provideTelegram(telegram: TelegramImpl): Telegram {
        return telegram
    }
}
