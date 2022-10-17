package com.kyawhtut.atsycast.telegram.ui.auth.phone

import android.view.View
import androidx.databinding.Bindable
import com.kyawhtut.atsycast.telegram.BR
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

    @get:Bindable
    var isFocusCountry: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.focusCountry)
        }

    @get:Bindable
    var isFocusPhone: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.focusPhone)
        }

    @get:Bindable
    val isEmptyPhone: Boolean
        get() = phone.isEmpty()

    var country: String = "+65"

    var phone: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emptyPhone)
        }

    fun onClickNext(view: View) {
        viewModelScope {
            isLoading = true

            authRepository?.sendPhone(country + phone.trim())?.done {
                isLoading = false
            }?.error {
                error = it
            }
        }
    }

    fun onClickLoginWithQRCode(view: View) {
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
