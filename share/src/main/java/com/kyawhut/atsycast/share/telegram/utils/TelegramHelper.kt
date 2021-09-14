package com.kyawhut.atsycast.share.telegram.utils

import com.kyawhut.atsycast.share.telegram.TelegramBotAPI
import com.kyawhut.atsycast.share.telegram.TelegramBotAPI.Companion.sendLog
import timber.log.Timber
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
object TelegramHelper {

    private var telegramBotAPI: TelegramBotAPI? = null

    fun setTelegramBotAPI(telegramBotAPI: TelegramBotAPI) {
        this.telegramBotAPI = telegramBotAPI
    }

    fun sendLog(message: String, parseMode: String = "html") {
        if (telegramBotAPI == null) {
            Timber.d("Telegram bot is null. Please initialized first.")
            return
        }
        telegramBotAPI?.let {
            it.sendLog(message, parseMode)
        }
    }
}
