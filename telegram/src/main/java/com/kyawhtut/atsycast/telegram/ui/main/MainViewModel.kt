package com.kyawhtut.atsycast.telegram.ui.main

import android.view.View
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    networkState: NetworkState,
    authRepository: AuthRepository,
) : BaseViewModel(networkState, authRepository) {

    fun onLogout(view: View) {
        viewModelScope {
            isLoading = true

            authRepository?.logout()

            isLoading = false
        }
    }
}
