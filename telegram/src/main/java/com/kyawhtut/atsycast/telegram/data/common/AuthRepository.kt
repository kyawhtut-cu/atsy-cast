package com.kyawhtut.atsycast.telegram.data.common

import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.checkAuthenticationCode
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.checkAuthenticationPassword
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.logout
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.setAuthenticationPhoneNumber
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.setAuthenticationWithQRCode
import kotlinx.coroutines.flow.StateFlow
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

internal interface AuthRepository {

    val authFlow: StateFlow<AuthState?>

    suspend fun sendQRCodeLogin(): Response<TdApi.Ok>

    suspend fun sendPhone(phone: String): Response<TdApi.Ok>

    suspend fun verifyOTP(code: String): Response<TdApi.Ok>

    suspend fun verifyPassword(password: String): Response<TdApi.Ok>

    suspend fun logout()
}

internal class AuthRepositoryImpl @Inject constructor(
    private val telegram: Telegram
) : AuthRepository {

    override val authFlow: StateFlow<AuthState?>
        get() = telegram.authUIState

    override suspend fun sendQRCodeLogin(): Response<TdApi.Ok> {
        return telegram.setAuthenticationWithQRCode()
    }

    override suspend fun sendPhone(phone: String): Response<TdApi.Ok> {
        return telegram.setAuthenticationPhoneNumber(
            phone,
            TdApi.PhoneNumberAuthenticationSettings(
                false,
                false,
                false,
                false,
                emptyArray()
            )
        )
    }

    override suspend fun verifyOTP(code: String): Response<TdApi.Ok> {
        return telegram.checkAuthenticationCode(code)
    }

    override suspend fun verifyPassword(password: String): Response<TdApi.Ok> {
        return telegram.checkAuthenticationPassword(password)
    }

    override suspend fun logout() {
        return telegram.logout()
    }
}
