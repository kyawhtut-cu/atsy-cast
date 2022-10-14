package com.kyawhtut.atsycast.telegram.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal interface NetworkState {

    val loadingState: StateFlow<Boolean>

    var isLoading: Boolean

    val errorState: StateFlow<TelegramException?>

    var error: TelegramException?
}

internal class NetworkStateImpl @Inject constructor() : NetworkState {

    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _errorState: MutableStateFlow<TelegramException?> = MutableStateFlow(null)

    override val loadingState: StateFlow<Boolean>
        get() = _loadingState.asStateFlow()

    override val errorState: StateFlow<TelegramException?>
        get() = _errorState.asStateFlow()

    override var isLoading: Boolean
        get() = _loadingState.value
        set(value) {
            _loadingState.value = value
        }

    override var error: TelegramException?
        get() = _errorState.value
        set(value) {
            _errorState.value = value
        }
}
