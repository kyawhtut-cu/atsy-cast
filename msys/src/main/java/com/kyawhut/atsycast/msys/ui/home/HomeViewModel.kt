package com.kyawhut.atsycast.msys.ui.home

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msys.data.network.response.GenresResponse
import com.kyawhut.atsycast.msys.utils.Constants
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

    val isHasWatchLater: Boolean
        get() = homeRepository.isHasWatchLater

    val isHasRecently: Boolean
        get() = homeRepository.isHasRecently

    fun getHome(callback: (NetworkResponse<List<GenresResponse>>) -> Unit) {
        viewModelScope {
            homeRepository.getHome(apiKey, callback)
        }
    }
}
