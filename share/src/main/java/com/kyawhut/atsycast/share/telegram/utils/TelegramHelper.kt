package com.kyawhut.atsycast.share.telegram.utils

import android.content.Context
import com.kyawhut.atsycast.share.telegram.TelegramBotAPI
import com.kyawhut.atsycast.share.telegram.TelegramBotAPI.Companion.sendLog
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.extension.Extension.deviceDisplayName
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/10/21
 */
object TelegramHelper {

    private var telegramBotAPI: TelegramBotAPI? = null

    fun setTelegramBotAPI(telegramBotAPI: TelegramBotAPI) {
        this.telegramBotAPI = telegramBotAPI
    }

    fun sendLog(context: Context, message: String, parseMode: String = "html") {
        if (telegramBotAPI == null) {
            Timber.d("Telegram bot is null. Please initialized first.")
            return
        }
        telegramBotAPI?.sendLog(
            """
            Device ID => ${context.deviceID}
            Device Name => ${context.deviceDisplayName}
            $message
        """.trimIndent(), parseMode
        )
    }
}
