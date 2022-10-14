package com.kyawhtut.atsycast.telegram.ui.auth.phone

import android.view.View
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.done
import com.kyawhtut.atsycast.telegram.utils.error
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PhoneViewModel @Inject constructor(
    authRepository: AuthRepository,
    networkState: NetworkState,
) : BaseViewModel(networkState, authRepository) {

    var phone: String = ""

    fun loginWithPhone(view: View) {
        viewModelScope {
            isLoading = true

            authRepository?.sendPhone(phone)?.done {
                isLoading = false
            }?.error {
                error = it
            }
        }
    }

    fun loginWithQR(view: View) {
        viewModelScope {
            isLoading = true

            authRepository?.sendQRCodeLogin()?.done {
                isLoading = false
            }?.error {
                error = it
            }
        }
    }
}
