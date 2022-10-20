package com.kyawhtut.atsycast.telegram.utils

import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import org.drinkless.td.libcore.telegram.TdApi

internal object TelegramExtension {

    suspend fun Telegram.setAuthenticationWithQRCode() = send<TdApi.Ok>(
        TdApi.RequestQrCodeAuthentication()
    )

    suspend fun Telegram.setAuthenticationPhoneNumber(
        phoneNumber: String,
        settings: TdApi.PhoneNumberAuthenticationSettings?
    ) = send<TdApi.Ok>(
        TdApi.SetAuthenticationPhoneNumber(
            phoneNumber,
            settings
        )
    )

    suspend fun Telegram.checkAuthenticationCode(
        code: String?
    ) = send<TdApi.Ok>(TdApi.CheckAuthenticationCode(code))

    suspend fun Telegram.checkAuthenticationPassword(
        password: String?
    ) = send<TdApi.Ok>(TdApi.CheckAuthenticationPassword(password))

    suspend fun Telegram.logout() = sendFunctionLaunch(TdApi.LogOut())

    suspend fun Telegram.getFile(fileID: Int?) = fileID?.let {
        send<TdApi.File>(
            TdApi.DownloadFile(fileID, 1, 0, 0, true)
        )
    } ?: Response.Error(
        TelegramException(
            code = -1,
            message = "Null id."
        )
    )

    suspend fun Telegram.getChatList(limit: Int = Int.MAX_VALUE) = send<TdApi.Chats>(
        TdApi.GetChats(TdApi.ChatListMain(), limit)
    )

    suspend fun Telegram.getChatByID(chatID: Long) = send<TdApi.Chat>(
        TdApi.GetChat(chatID)
    )

    suspend fun Telegram.getChatHistory(id: Long, lastMessage: Long) = send<TdApi.Messages>(
        TdApi.GetChatHistory(id, lastMessage, 0, 30, false)
    )
}
