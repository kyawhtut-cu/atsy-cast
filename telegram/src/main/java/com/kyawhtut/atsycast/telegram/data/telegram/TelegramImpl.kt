package com.kyawhtut.atsycast.telegram.data.telegram

import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
internal class TelegramImpl @Inject constructor(
    private val parameters: TdApi.TdlibParameters
) : Telegram {

    private val _authUIState: MutableStateFlow<AuthState?> = MutableStateFlow(null)
    private var client: Client? = null

    override val authUIState: StateFlow<AuthState?>
        get() = _authUIState.asStateFlow()

    init {
        initializeClient()
    }

    override suspend fun sendFunctionLaunch(function: TdApi.Function) {
        send<TdApi.Ok>(function)
    }

    override suspend fun <T> send(query: TdApi.Function): Response<T> {
        return callbackFlow<Response<T>> {
            client?.send(query, {
                if (it is TdApi.Error) {
                    trySend(
                        Response.Error(
                            TelegramException(
                                code = it.code, message = it.message
                            )
                        )
                    )
                } else {
                    (it as T?)?.let { result ->
                        trySend(Response.Success(result))
                    } ?: run {
                        trySend(
                            Response.Error(
                                TelegramException(
                                    code = -1, message = "Error loading data"
                                )
                            )
                        )
                    }
                }
            }, {
                trySend(
                    Response.Error(
                        TelegramException(
                            code = -1, message = it.message ?: "App error."
                        )
                    )
                )
            })
            awaitClose {}
        }.first()
    }

    override fun onResult(data: TdApi.Object?) {
        when (data?.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                onAuthorizationUpdate((data as TdApi.UpdateAuthorizationState).authorizationState)
            }
        }
    }

    private fun initializeClient() {
        client = Client.create(this, {}, {})

        client?.send(TdApi.SetLogVerbosityLevel(1), this)
        client?.send(TdApi.GetAuthorizationState(), this)
    }

    private fun onAuthorizationUpdate(authorizationState: TdApi.AuthorizationState) {
        Timber.d("onAuthorizationUpdate: %s", authorizationState)
        _authUIState.value = when (authorizationState.constructor) {
            TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR -> {
                client?.send(TdApi.CheckDatabaseEncryptionKey()) {}
                null
            }

            TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                client?.send(TdApi.SetTdlibParameters(parameters)) {}
                null
            }

            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR, TdApi.AuthorizationStateLoggingOut.CONSTRUCTOR -> AuthState.EnterPhone

            TdApi.AuthorizationStateClosed.CONSTRUCTOR -> {
                initializeClient()
                null
            }

            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                AuthState.EnterCode((authorizationState as TdApi.AuthorizationStateWaitCode).codeInfo.phoneNumber)
            }

            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                AuthState.EnterPassword((authorizationState as TdApi.AuthorizationStateWaitPassword).passwordHint)
            }

            TdApi.AuthorizationStateWaitOtherDeviceConfirmation.CONSTRUCTOR -> AuthState.LoginWithQRCode(
                (authorizationState as TdApi.AuthorizationStateWaitOtherDeviceConfirmation).link
            )

            TdApi.AuthorizationStateReady.CONSTRUCTOR -> AuthState.LoggedIn
            else -> null
        }
    }
}
