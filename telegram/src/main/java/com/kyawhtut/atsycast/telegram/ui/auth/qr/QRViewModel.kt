package com.kyawhtut.atsycast.telegram.ui.auth.qr

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhut.atsycast.share.utils.ShareUtils.toQRCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class QRViewModel @Inject constructor(
    authRepository: AuthRepository,
    networkState: NetworkState,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(networkState, authRepository) {

    companion object {
        const val EXTRA_LOGIN_URL = "EXTRA_LOGIN_URL"
    }

    private val loginURL: String by lazy {
        savedStateHandle[EXTRA_LOGIN_URL] ?: ""
    }

    fun setQRCodeImage(view: ImageView) {
        view.setImageBitmap(loginURL.toQRCode())
    }

    fun loginWithPhone(view: View) {
        viewModelScope {
            isLoading = true

            authRepository?.logout()

            isLoading = false
        }
    }
}
