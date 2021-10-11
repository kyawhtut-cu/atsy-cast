package com.kyawhut.atsycast.zcm.ui.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.zcm.data.network.response.GenresResponse
import com.kyawhut.atsycast.zcm.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/3/21
 */
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val homeRepository: HomeRepository,
) : BaseViewModel() {

    var genresList: List<GenresResponse> = mutableListOf()

    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    val isHasWatchLater: Boolean
        get() = homeRepository.isHasWatchLater

    val isHasRecently: Boolean
        get() = homeRepository.isHasRecently

    fun getHome(callback: (NetworkResponse<List<GenresResponse>>) -> Unit) {
        viewModelScope {
            homeRepository.getHome(application, apiKey, callback)
        }
    }
}
