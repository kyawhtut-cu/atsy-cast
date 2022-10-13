package com.kyawhtut.atsycast.telegram.utils

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
internal sealed interface AuthState {
    object LoggedIn : AuthState
    object EnterPhone : AuthState
    object EnterCode : AuthState
    data class LoginWithQRCode(val qrCode: String) : AuthState
    data class EnterPassword(val passwordHint: String) : AuthState
}
