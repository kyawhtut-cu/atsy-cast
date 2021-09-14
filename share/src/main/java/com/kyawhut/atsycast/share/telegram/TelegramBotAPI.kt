package com.kyawhut.atsycast.share.telegram

import android.content.Context
import com.kyawhut.atsycast.share.BuildConfig
import com.kyawhut.atsycast.share.network.providers.createService
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.response.SendLogResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/10/21
 */
interface TelegramBotAPI {

    companion object {
        fun provideTelegramBotAPI(context: Context): TelegramBotAPI {
            return createService(
                TelegramBotAPI::class,
                BuildConfig.TELEGRAM_BASE_URL + BuildConfig.TELEGRAM_BOT_ID + "/",
                context
            )
        }

        fun TelegramBotAPI.sendLog(message: String, parseMode: String) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = execute { this@sendLog.sendLogDataToDev(message, parseMode) }
                Timber.d("Send log to dev => %s", result)
            }
        }
    }

    @POST("sendMessage")
    @FormUrlEncoded
    suspend fun sendLogDataToDev(
        @Field("text") message: String,
        @Field("parse_mode") parseMode: String = "html",
        @Field("chat_id") devID: String = "924494596",
    ): SendLogResponse
}
