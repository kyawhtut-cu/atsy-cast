package com.kyawhtut.atsycast.telegram.ui.auth

import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    authRepository: AuthRepository,
    networkState: NetworkState,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(networkState, authRepository) {

    companion object {
        const val EXTRA_AUTH_STATE = "EXTRA_AUTH_STATE"
    }

    var authState: AuthState = savedStateHandle[EXTRA_AUTH_STATE] ?: AuthState.EnterPhone
}
