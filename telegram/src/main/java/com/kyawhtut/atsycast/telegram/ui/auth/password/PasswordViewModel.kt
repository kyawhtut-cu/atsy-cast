package com.kyawhtut.atsycast.telegram.ui.auth.password

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.BR
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.TelegramException
import com.kyawhtut.atsycast.telegram.utils.done
import com.kyawhtut.atsycast.telegram.utils.error
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PasswordViewModel @Inject constructor(
    authRepository: AuthRepository,
    networkState: NetworkState,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(networkState, authRepository) {

    companion object {
        const val EXTRA_PASSWORD_HINT = "EXTRA_PASSWORD_HINT"
    }

    val hint: String by lazy {
        savedStateHandle[EXTRA_PASSWORD_HINT] ?: ""
    }

    @get:Bindable
    var isFocus: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.focus)
        }

    @get:Bindable
    val isEmptyPassword: Boolean
        get() = password.isEmpty()

    var password: String = ""
        set(value) {
            field = value
            error = null
            notifyPropertyChanged(BR.emptyPassword)
        }

    fun onClickNext(view: View) {
        if (isEmptyPassword) {
            error = TelegramException(-1, "Password is empty")
            return
        }
        viewModelScope {
            isLoading = true

            authRepository?.verifyPassword(password)?.done {
                isLoading = false
            }?.error {
                error = it
            }
        }
    }
}
