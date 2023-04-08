package com.kyawhut.atsycast.msubpc.ui.adult

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@HiltViewModel
internal class AdultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: AdultRepository
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    fun getAdult(callback: (NetworkResponse<List<AdultResponse>>) -> Unit) {
        viewModelScope {
            repo.getAdult(callback)
        }
    }
}
