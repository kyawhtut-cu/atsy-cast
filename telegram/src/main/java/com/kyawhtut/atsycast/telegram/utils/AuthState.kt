package com.kyawhtut.atsycast.telegram.utils

import java.io.Serializable

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
internal sealed interface AuthState : Serializable {
    object LoggedIn : AuthState
    object EnterPhone : AuthState
    data class EnterCode(val phone: String) : AuthState
    data class LoginWithQRCode(val loginURL: String) : AuthState
    data class EnterPassword(val passwordHint: String) : AuthState
}
