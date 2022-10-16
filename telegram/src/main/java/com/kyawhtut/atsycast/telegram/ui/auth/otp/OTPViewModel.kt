package com.kyawhtut.atsycast.telegram.ui.auth.otp

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.BR
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.done
import com.kyawhtut.atsycast.telegram.utils.error
import com.kyawhtut.atsycast.telegram.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OTPViewModel @Inject constructor(
    authRepository: AuthRepository,
    networkState: NetworkState,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(networkState, authRepository) {

    companion object {
        const val EXTRA_PHONE_NUMBER = "EXTRA_PHONE_NUMBER"
    }

    @get:Bindable
    var isFocus: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.focus)
        }

    @get:Bindable
    val isEmptyCode: Boolean
        get() = code.isEmpty()

    val phoneNumber: String by lazy {
        savedStateHandle[EXTRA_PHONE_NUMBER] ?: ""
    }

    var code: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emptyCode)
            if (value.length == 5) {
                // verify otp
                onVerifyOTP()
            }
        }

    fun onClickPhoneNumberChange(view: View) {
        viewModelScope {
            isLoading = true

            authRepository?.logout()

            isLoading = false
        }
    }

    private fun onVerifyOTP() {
        viewModelScope {
            isLoading = true

            authRepository?.verifyOTP(code)?.done {
                isLoading = false
            }?.error {
                error = it
            }
        }
    }
}
