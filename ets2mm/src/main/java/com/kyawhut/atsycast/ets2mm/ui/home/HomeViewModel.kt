package com.kyawhut.atsycast.ets2mm.ui.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.ets2mm.data.network.response.GenresResponse
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
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
            homeRepository.getHome(application, callback)
        }
    }
}
