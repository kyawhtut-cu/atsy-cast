package com.kyawhtut.atsycast.telegram.ui.auth.otp

import android.view.View
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
) : BaseViewModel(networkState, authRepository) {

    var code: String = ""

    fun verifyOTP(view: View) {
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
