package com.kyawhtut.atsycast.telegram.data.telegram

import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhtut.atsycast.telegram.utils.Response
import kotlinx.coroutines.flow.StateFlow
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
internal interface Telegram : Client.ResultHandler {

    val authUIState: StateFlow<AuthState?>

    suspend fun <T> send(query: TdApi.Function): Response<T>

    suspend fun sendFunctionLaunch(function: TdApi.Function)
}