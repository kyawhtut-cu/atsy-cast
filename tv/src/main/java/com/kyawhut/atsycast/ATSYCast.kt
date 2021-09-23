package com.kyawhut.atsycast

import android.app.Application
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatDelegate
import com.kyawhut.atsycast.share.telegram.TelegramBotAPI
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.squareup.leakcanary.LeakCanary
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
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

        Toasty.Config.getInstance()
            .tintIcon(true) // optional (apply textColor also to the icon)
            .setToastTypeface(Typeface.createFromAsset(assets, "fonts/mmrtext.ttf")) // optional
            .apply()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}
