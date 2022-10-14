package com.kyawhtut.atsycast.telegram.ui.home

import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class TelegramViewModel @Inject constructor(
    networkState: NetworkState,
    authRepository: AuthRepository
) : BaseViewModel(networkState, authRepository)
