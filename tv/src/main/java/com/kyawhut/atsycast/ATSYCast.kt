package com.kyawhut.atsycast

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kyawhut.atsycast.share.telegram.TelegramBotAPI
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@HiltAndroidApp
class ATSYCast : Application() {

    @Inject
    lateinit var telegramBotAPI: TelegramBotAPI

    override fun onCreate() {
        super.onCreate()

        TelegramHelper.setTelegramBotAPI(telegramBotAPI)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}
